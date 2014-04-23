package databasetracing.tracing;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLServerDatabaseQueryable implements DataBaseQuearyable {

    private final DBConnector dbConnection;
    private final String databaseName;
    private final String serverName;


    public SQLServerDatabaseQueryable(String serverName, String dbName, String userName, String password) {
        try {
            this.databaseName = dbName;
            this.serverName = serverName;

            this.dbConnection = DBConnection.setupDB(serverName, dbName, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ResultSet executeSql(String command) throws SQLException {
        System.out.println(command);
        ResultSet res = dbConnection.executeQuery(command);
        return res;
    }


    public void executeSqlNoneQuery(String command) throws SQLException {
        System.out.println(command);
        dbConnection.execute(command);
    }


    public String getDbName() {
        return databaseName;
    }


    public String getServerName() {
        return serverName;
    }


    @Override
    public void close() {
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

}
