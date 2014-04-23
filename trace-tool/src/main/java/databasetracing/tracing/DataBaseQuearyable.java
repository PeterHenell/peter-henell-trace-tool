package databasetracing.tracing;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataBaseQuearyable extends AutoCloseable {

    public ResultSet executeSql(String command) throws SQLException;


    public void executeSqlNoneQuery(String command) throws SQLException;


    public String getDbName();


    public String getServerName();


    @Override
    void close();

}
