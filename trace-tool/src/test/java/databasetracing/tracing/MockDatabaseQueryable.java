package databasetracing.tracing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import databasetracing.tracing.sources.TraceSource;

public class MockDatabaseQueryable implements DataBaseQuearyable, TraceSource {

    private final Map<String, ResultSet> mockResults;
    private final UUID traceId;


    public MockDatabaseQueryable(UUID traceId) {
        this.mockResults = new HashMap<String, ResultSet>();
        this.traceId = traceId;
    }


    public void putResult(String command, ResultSet resultSet) {
        mockResults.put(command, resultSet);
    }


    public ResultSet executeSql(String command) throws SQLException {
        System.out.println("Mocking command: " + command);
        return mockResults.get(command);
    }


    public void executeSqlNoneQuery(String command) throws SQLException {
        System.out.println("Mocking command with no results: " + command);
    }


    public String getDbName() {
        return "MockDB";
    }


    public String getServerName() {
        return "MockServer";
    }


    @Override
    public void close() {

    }


    @Override
    public void cleanup() {
        // TODO Auto-generated method stub

    }


    @Override
    public int getEventCount() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void startTrace() {
        // TODO Auto-generated method stub

    }


    @Override
    public void stopTrace() {
        // TODO Auto-generated method stub

    }


    @Override
    public TraceResult collectTraceResult(String testName) {
        // TODO Auto-generated method stub
        return null;
    }

}
