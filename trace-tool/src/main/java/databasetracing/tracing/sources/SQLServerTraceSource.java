package databasetracing.tracing.sources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import databasetracing.tracing.DataBaseQuearyable;
import databasetracing.tracing.TraceResult;

public class SQLServerTraceSource implements TraceSource {

    public static final String GET_TRACE_RESULT_TRACE_ID_S = "EXEC [DbaDb].[Traces].[GetTraceResult] @TraceId = '%s';";
    public static final String STOP_TRACE_TRACE_ID_S = "EXEC [DbaDb].[Traces].[StopTrace] @TraceId = '%s';";
    public static final String START_TRACE_DB_NAME_S_TRACE_ID_S = "EXEC [DbaDb].[Traces].[StartTrace] @db_name = '%s', @TraceId = '%s';";
    public static final String GET_EVENT_COUNT_TRACE_ID_S = "EXEC [DbaDb].[Traces].[GetEventCount] @TraceId = '%s';";
    public static final String CLEAN_UP_TRACE_ID_S = "EXEC [DbaDb].[Traces].[CleanUp] @TraceId = '%s';";

    private final DataBaseQuearyable db;
    private final UUID traceId;


    public SQLServerTraceSource(DataBaseQuearyable dbConnection, UUID traceId) {
        this.traceId = traceId;
        this.db = dbConnection;
    }


    @Override
    public void close() {
        if (null != db) {
            db.close();
        }
    }


    @Override
    public void cleanup() {
        try {
            db.executeSqlNoneQuery(String.format(CLEAN_UP_TRACE_ID_S, traceId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getEventCount() {
        int eventCount = 0;
        try {
            ResultSet rs = db.executeSql(String.format(GET_EVENT_COUNT_TRACE_ID_S, traceId));
            rs.next();
            eventCount = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(eventCount);
        return eventCount;
    }


    @Override
    public void startTrace() throws RuntimeException {
        try {
            db.executeSqlNoneQuery(String.format(START_TRACE_DB_NAME_S_TRACE_ID_S, db.getDbName(), traceId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void stopTrace() throws RuntimeException {
        try {
            db.executeSqlNoneQuery(String.format(STOP_TRACE_TRACE_ID_S, traceId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public TraceResult collectTraceResult(String testName) throws RuntimeException {
        try {
            stopTrace();
            System.out.println("Allow a few seconds for the results to be collected");
            TraceResult result = TraceResult.fromResultSet(db.executeSql(String.format(GET_TRACE_RESULT_TRACE_ID_S, traceId)), testName);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
