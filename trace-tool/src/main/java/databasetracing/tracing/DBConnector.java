package databasetracing.tracing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector implements AutoCloseable {

    private final Connection conn;
    private Statement stmt = null;


    public DBConnector(Connection con) {
        this.conn = con;

    }


    /**
     * This method executes the given SQL statement, which returns a single ResultSet object.
     * 
     * @param query SQL statement to be sent to the database, typically a static SQL SELECT statement.
     * @return true if the first result is a ResultSet object; false if it is an update count or there are no results.
     * @throws SQLException if a database access error occurs, the Statement is closed or the given SQL statement produces anything other
     *             than a single ResultSet object.
     */

    public ResultSet executeQuery(String query) throws SQLException {
        stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(query);
        return results;
    }


    /**
     * This method executes the given SQL statement and indicates the form of the first result.
     * 
     * @param query SQL statement to be sent to the database, typically a static SQL SELECT statement.
     * @return true if the first result is a ResultSet object; false if it is an update count or there are no results.
     * @throws SQLException if a database access error occurs or the Statement is closed.
     */
    public boolean execute(String query) throws SQLException {
        stmt = conn.createStatement();
        boolean result = stmt.execute(query);
        return result;
    }


    /**
     * This method executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns
     * nothing, such as an SQL DDL statement.
     * 
     * @param query SQL statement to be sent to the database, typically a static SQL SELECT statement.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     * @throws SQLException if a database access error occurs or the Statement is closed.
     */
    public int executeUpdate(String query) throws SQLException {
        stmt = conn.createStatement();
        int result = stmt.executeUpdate(query);
        return result;
    }


    private void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() {
        closeConnection();
    }
}
