package edu.colostate.cs.cs414.chesshireCoders.jungleServer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.ServerEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.ServerEventType;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JungleServer extends Server {

    private FileOutputStream fileOutputStream;
    private DataSource dataSource;
    private static Logger logger;

    /**
     *
     */
    public JungleServer() {

        logger = Logger.getLogger(this.getClass().getSimpleName());

        logger.log(Level.FINER, "Registered all network message objects");

        // Add a listener for logging.
        this.addListener(new Listener.ThreadedListener(new Listener() {

            @Override
            public void connected(com.esotericsoftware.kryonet.Connection c) {
                logger.log(
                        Level.INFO,
                        "New connection from {0} with ID {1}.",
                        new Object[]{c.getRemoteAddressTCP(), c.getID()}
                );
            }

            @Override
            public void received(com.esotericsoftware.kryonet.Connection c, Object object) {
            	if (!(object instanceof FrameworkMessage.KeepAlive)) {
            		logger.log(
            				Level.INFO,
            				"Received {0} from client at {1} with ID {2}.",
            				new Object[]{object.getClass().getSimpleName(), c.getRemoteAddressTCP(), c.getID()}
            		);
            	}	
            }

            @Override
            public void disconnected(com.esotericsoftware.kryonet.Connection c) {
                logger.log(
                        Level.INFO,
                        "Client at {0} with ID {1} disconnected.",
                        new Object[]{c.getRemoteAddressTCP(), c.getID()}
                );
            }
        }));
    }

    public void setLogFile(String path) throws IOException {
        logger.addHandler(new FileHandler(path));
    }

    @Override
    public void stop() {
        logger.log(Level.INFO, "Stopping server...");
        this.sendToAllTCP(new ServerEvent(ServerEventType.SERVER_STOP, "Server is shutting down."));
        super.stop();
    }

    @Override
    public void bind(int tcpPort) throws IOException {
        logger.log(Level.INFO, "Binding server to TCP port {0}...", tcpPort);
        super.bind(tcpPort);
    }

    @Override
    public void start() {
        logger.log(Level.INFO, "Starting server...");
        super.start();
    }

    @Override
    public void sendToAllTCP(Object o) {
        logger.log(
                Level.INFO,
                "Sending {0} to all clients.",
                o.getClass().getSimpleName()
        );
        super.sendToAllTCP(o);
    }

    public void sendToTCPWithToken(Object o, AuthToken token) {
        Connection[] connections = this.getConnections();
        for (Connection connection : connections) {
            JungleConnection jungleConnection = JungleConnection.class.cast(connection);
            if (jungleConnection.getAuthToken().equals(token))
                jungleConnection.sendTCP(o);
        }
    }

    public void sendToTCPWithNickName(Object o, String nickName) {
        Connection[] connections = this.getConnections();
        for (Connection connection : connections) {
            JungleConnection jungConn = JungleConnection.class.cast(connection);
            if (jungConn.getNickName().equals(nickName))
                jungConn.sendTCP(o);
        }
    }

    @Override
    public void sendToTCP(int connectionID, Object o) {
        logger.log(
                Level.INFO,
                "Sending {0} to client with ID of {1}",
                new Object[]{o.getClass().getSimpleName(), connectionID}
        );
        super.sendToTCP(connectionID, o);
    }

    @Override
    public Connection newConnection() {
        return new JungleConnection();
    }
}
