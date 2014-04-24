package databasetracing.tracing;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.tracing.dto.TraceResultData;

public class TraceResultDataTest {

    @Test
    public void shouldInstantiateTraceResultData() {
        TraceResultData data = new TraceResultData();
        Assert.assertNotNull(data);
    }


    @Test
    public void shouldCreateTraceResultDataFromArray() {
        TraceResult trace = TraceResultExamples.getExampleTraceResult("traceResultData Test", 1);

        TraceResultData data = trace.getResult().get(0);

        Assert.assertNotNull(data);
        Assert.assertEquals("Duration 0", data.getDuration());
        Assert.assertEquals("Event# in Tran 0", data.getEvent_number_in_transaction());
        Assert.assertEquals("Event Sequence 0", data.getEvent_sequence_number());
        Assert.assertEquals("New Transaction? 0", data.getIs_new_transaction());
        Assert.assertEquals("Joining Tables 0", data.getJoining_tables());
        Assert.assertEquals("Main Table 0", data.getMain_table());
        Assert.assertEquals("Operation 0", data.getOperation());
        Assert.assertEquals("With (Option) 0", data.getQuery_option());
        Assert.assertEquals("Query Parameters 0", data.getQuery_parameters());
        Assert.assertEquals("Raw Sql text 0", data.getRaw_sql());
        Assert.assertEquals("Round Trip Time(Ms) 0", data.getRound_trip_time());
        Assert.assertEquals("Session Id 0", data.getSession_id());
        Assert.assertEquals("Total Run Time (Ms) 0", data.getTotal_run_time());
        Assert.assertEquals("Transaction Id 0", data.getTransaction_id());

    }


    @Test
    public void shouldBeEqualIfAllValuesAreSame() {
        TraceResultData amy = TraceResultExamples.getExampleTraceResult("traceResultData Test", 1).getResult().get(0);
        TraceResultData bertram = TraceResultExamples.getExampleTraceResult("traceResultData Test", 1).getResult().get(0);

        Assert.assertEquals(amy, bertram);

    }
}
