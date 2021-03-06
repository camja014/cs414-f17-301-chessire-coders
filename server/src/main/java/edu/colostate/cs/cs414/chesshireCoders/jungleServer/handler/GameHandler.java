package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.util.GameStateException;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.BoardUpdateEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.GameEndedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetActiveGamesRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.QuitGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.CreateGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetActiveGamesResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.QuitGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UpdateGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.*;

import java.util.List;

public class GameHandler extends Listener {

    private JungleServer server;
    private GameService gameService = new GameServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();

    public GameHandler() {
    }

    public GameHandler(JungleServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection connection, Object received) {
        try {
            if (received instanceof CreateGameRequest) {
                connection.sendTCP(handleCreateGame((CreateGameRequest) received, connection));
            } else if (received instanceof GetActiveGamesRequest) {
                connection.sendTCP(handleGetActiveGames((GetActiveGamesRequest) received, connection));
            } else if (received instanceof GetGameRequest) {
                connection.sendTCP(handleGetGame((GetGameRequest) received, connection));
            } else if (received instanceof UpdateGameRequest) {
                connection.sendTCP(handleUpdateGame((UpdateGameRequest) received, connection));
            } else if (received instanceof QuitGameRequest) {
                connection.sendTCP(handleQuitGameRequest((QuitGameRequest) received, connection));
            }
        } catch (Exception e) {
            connection.sendTCP(new CreateGameResponse(SERVER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Handle a request to quit a game.
     */
    private QuitGameResponse handleQuitGameRequest(QuitGameRequest received, Connection connection) {
        JungleConnection jConn = JungleConnection.class.cast(connection);
        try {
            if (sessionService.validateSessionRequest(received, connection)) {
                // Quit the game.
                String notifyUser = gameService.quitGame(jConn.getNickName(), received.getGameId());
                // Notify the opposing player (if there was one).
                if (notifyUser != null)
                    server.sendToTCPWithNickName(new GameEndedEvent(received.getGameId()), notifyUser);
                // Return a success response.
                return new QuitGameResponse().setGameId(received.getGameId());
            } else return new QuitGameResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (Exception e) {
            e.printStackTrace();
            return new QuitGameResponse(SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Update the stored game board
     */
    private UpdateGameResponse handleUpdateGame(UpdateGameRequest received, Connection connection) {
        JungleConnection jConn = JungleConnection.class.cast(connection);
        try {
            if (sessionService.validateSessionRequest(received, connection)) {

                Game game = received.getGame();
                String sendingUser = jConn.getNickName();
                gameService.updateGame(sendingUser, game);

                // Opposing player nick name
                String playerToUpdate;

                // If sending player is p1, get nickname of p2
                if (sendingUser.equals(game.getPlayerOneNickName()))
                    playerToUpdate = game.getPlayerTwoNickName();

                    // if sending player is p2, get nickname of p1
                else playerToUpdate = game.getPlayerOneNickName();

                if (game.getGameStatus() == GameStatus.ONGOING) {
                    server.sendToTCPWithNickName(new BoardUpdateEvent(game.getGameID()), playerToUpdate);
                } else if (game.getGameStatus() == GameStatus.WINNER_PLAYER_ONE
                        || game.getGameStatus() == GameStatus.WINNER_PLAYER_TWO) {
                    server.sendToTCPWithNickName(new GameEndedEvent(game.getGameID()), playerToUpdate);
                }

                return new UpdateGameResponse(); // Defaults to success

            } else return new UpdateGameResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (GameStateException e) {
            return new UpdateGameResponse(CLIENT_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new UpdateGameResponse(SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Facilitates the setup of a new game, and provides any error handling for performed operations.
     */
    private CreateGameResponse handleCreateGame(CreateGameRequest request, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(request, connection)) {
                JungleConnection jungleConnection = JungleConnection.class.cast(connection);
                long gameId = gameService.newGame(jungleConnection.getNickName()).getGameID();
                return new CreateGameResponse(gameId);
            } else return new CreateGameResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (Exception e) {
            e.printStackTrace();
            return new CreateGameResponse(SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Facilitates the retrieval of the requested game
     */
    private GetGameResponse handleGetGame(GetGameRequest received, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(received, connection)) {
                Game game = gameService.fetchGame(received.getGameID());
                return new GetGameResponse().setGame(game);
            } else return new GetGameResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (Exception e) {
            e.printStackTrace();
            return new GetGameResponse(SERVER_ERROR, e.getMessage());
        }
    }
    
    /**
     * Facilitates the retrieval of the requested game
     */
    private GetActiveGamesResponse handleGetActiveGames(GetActiveGamesRequest received, Connection connection) {
        JungleConnection jConn = JungleConnection.class.cast(connection);
    	try {
            if (sessionService.validateSessionRequest(received, connection)) {
            	
                List<Game> games = gameService.fetchUserGames(jConn.getNickName(), GameStatus.ONGOING, GameStatus.PENDING);
                return new GetActiveGamesResponse().setGames(games);
            } else return new GetActiveGamesResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (Exception e) {
            e.printStackTrace();
            return new GetActiveGamesResponse(SERVER_ERROR, e.getMessage());
        }
    }
}
