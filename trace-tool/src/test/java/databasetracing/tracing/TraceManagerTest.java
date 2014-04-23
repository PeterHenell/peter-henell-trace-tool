package databasetracing.tracing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import databasetracing.tracing.sources.SQLServerTraceSource;

public class TraceManagerTest {

    @Test
    public void shouldCollectTraceResults() {

        UUID traceId = UUID.randomUUID();
        MockDatabaseQueryable mockedQueryable = new MockDatabaseQueryable(traceId);

        mockedQueryable.putResult(String.format(SQLServerTraceSource.CLEAN_UP_TRACE_ID_S, traceId), null);
        mockedQueryable.putResult(String.format(SQLServerTraceSource.GET_EVENT_COUNT_TRACE_ID_S, traceId), getEventCountResult());
        mockedQueryable.putResult(String.format(SQLServerTraceSource.START_TRACE_DB_NAME_S_TRACE_ID_S, "Mockdb", traceId), null);
        mockedQueryable.putResult(String.format(SQLServerTraceSource.STOP_TRACE_TRACE_ID_S, traceId), null);
        mockedQueryable.putResult(String.format(SQLServerTraceSource.GET_TRACE_RESULT_TRACE_ID_S, traceId), getTraceResultResultSet());

        TraceManager manager = new TraceManager(mockedQueryable, "");
        manager.startTrace();
        manager.stopTrace();
        manager.collectTraceResult("traceResultsaveall");
        manager.saveAllResults();
        manager.close();
    }


    private ResultSet getTraceResultResultSet() {
        TraceResult traceResult = TraceResultExamples.getExampleTraceResult("Trace Result Save All Test");

        return new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());
    }


    private ResultSet getEventCountResult() {
        List<String[]> rows = new ArrayList<String[]>();
        List<String> columns = new ArrayList<String>();

        String[] row = { "1" };
        rows.add(row);

        columns.add("eventcount");

        MockResultSet rs = new MockResultSet(columns, rows);
        return rs;
    }
}
