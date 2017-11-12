package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.GamePieceDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerOwnerType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgresGamePieceDAO extends BaseDAO<GamePiece, Long> implements GamePieceDAO {

    private static RowMapper<GamePiece> GAME_PIECE_ROW_MAPPER = rs -> new GamePiece()
            .setPieceId(rs.getLong("piece_id"))
            .setPlayerOwner(PlayerOwnerType.valueOf(rs.getString("player_owner")))
            .setGameId(rs.getLong("game_id"))
            .setPieceType(PieceType.valueOf(rs.getString("piece_type")))
            .setRow(rs.getInt("position_row"))
            .setColumn(rs.getInt("position_column"));

    public PostgresGamePieceDAO(Connection connection) {
        super(connection);
    }

    /**
     * Add a new object to the table.
     *
     * @param newInstance The object to add
     * @return The primary key of the added object.
     * @throws SQLException
     */
    @Override
    public Long create(GamePiece gamePiece) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO game_piece (player_owner, game_id, piece_type, position_row, position_column) VALUES (?, ?, ?, ?, ?)";
        return add(sql, Long.class,
                gamePiece.getPlayerOwner().name(),
                gamePiece.getGameId(),
                gamePiece.getPieceType().name(),
                gamePiece.getRow(),
                gamePiece.getColumn());
    }

    /**
     * Selects a row from the table using the primary key column.
     *
     * @param gamePieceId primary key of row to select
     * @return Object corresponding to the selected row, or null on no result.
     * @throws SQLException
     */
    @Override
    public GamePiece findByPrimaryKey(Long gamePieceId) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game_piece WHERE piece_id = ?";
        return query(sql, GAME_PIECE_ROW_MAPPER, gamePieceId).get(0);
    }

    /**
     * Returns all rows in the table.
     *
     * @return A list of all rows (be careful!)
     * @throws SQLException
     */
    @Override
    public List<GamePiece> findAll() throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game_piece";
        return query(sql, GAME_PIECE_ROW_MAPPER);
    }

    /**
     * Updates the row corresponding to the given object in the database.
     *
     * @param gamePiece object to update
     * @return rows affected (should only be 1)
     * @throws SQLException
     */
    @Override
    public int update(GamePiece gamePiece) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE game_piece \n" +
                "SET position_row = ?, position_column = ?\n" +
                "WHERE piece_id = ?";
    }

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param aLong Primary key
     * @return rows affected (should only be 1)
     * @throws SQLException
     */
    @Override
    public int delete(Long aLong) throws SQLException {
        return 0;
    }

    /**
     * Delete a row/rows from the database using an existing object for search criteria.
     * <p>
     * Implementations should build the DML statement to ignore NULL fields, and use only
     * non-NULL fields as criteria.
     *
     * @param gamePiece Search criteria object
     * @return rows affected.
     */
    @Override
    public int delete(GamePiece gamePiece) throws SQLException {
        return 0;
    }
}
