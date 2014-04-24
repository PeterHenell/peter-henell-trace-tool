package databasetracing.tracing;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.tracing.dto.TraceResultData;

public class TraceResultBuilderTest {

    @Test
    public void shouldInitializeBuilder() {
        TraceResultBuilder builder = initBuilder();
        Assert.assertNotNull(builder);
    }


    @Test
    public void shouldBuildOneRow() {
        TraceResultBuilder builder = initBuilder();
        TraceResult sr = builder.build("test builder");
        Assert.assertNotNull(sr);

        TraceResultData row = sr.getResult().get(0);

        Assert.assertEquals(row.getOperation(), "SELECT");
        Assert.assertEquals(row.getMain_table(), "customer");
        Assert.assertEquals(row.getQuery_option(), "q opt");
        Assert.assertEquals(row.getJoining_tables(), "none in here");
        Assert.assertEquals(row.getQuery_parameters(), "parararameters");
        Assert.assertEquals(row.getTotal_run_time(), "100");
        Assert.assertEquals(row.getRound_trip_time(), "1234");
        Assert.assertEquals(row.getSession_id(), "111");
        Assert.assertEquals(row.getTransaction_id(), "1001");
        Assert.assertEquals(row.getIs_new_transaction(), "0");
        Assert.assertEquals(row.getEvent_number_in_transaction(), "11111");
        Assert.assertEquals(row.getEvent_sequence_number(), "1");
        Assert.assertEquals(row.getDuration(), "100");
        Assert.assertEquals(row.getRaw_sql(), "SELECT * from customer");

    }


    @Test(expected = AssertionError.class)
    public void shouldRequireNewRowBeforeAddingData() {
        TraceResultBuilder builder = new TraceResultBuilder();
        setDefaultTestValues(builder);
        builder.build("test builder");
    }


    @Test
    public void shouldBeSameValuesEachTime() {
        TraceResultBuilder first = initBuilder();
        TraceResultBuilder second = initBuilder();

        TraceResult a = first.build("test builder");
        TraceResult b = second.build("test builder");

        Assert.assertTrue(a.isSameValuesAs(b));
        Assert.assertTrue(b.isSameValuesAs(a));
    }


    @Test
    public void shouldBeDifferentWhenDifferentBuilds() {
        TraceResultBuilder first = initBuilder();
        TraceResultBuilder second = initBuilder();

        // Make one of them different
        first.newRow();
        first.duration("one hundred");

        TraceResult a = first.build("test builder");
        TraceResult b = second.build("test builder");

        Assert.assertFalse(a.isSameValuesAs(b));
        Assert.assertFalse(b.isSameValuesAs(a));
    }


    @Test
    public void shouldAddRowToResultCollection() {
        TraceResultBuilder builder = new TraceResultBuilder();

        for (int i = 0; i < 13; i++) {
            builder.newRow();
            setDefaultTestValues(builder);
        }

        TraceResult result = builder.build("test builder");

        Assert.assertEquals(13, result.getResult().size());
    }


    private TraceResultBuilder initBuilder() {
        TraceResultBuilder builder = new TraceResultBuilder();
        builder.newRow();
        setDefaultTestValues(builder);
        return builder;
    }


    private void setDefaultTestValues(TraceResultBuilder builder) {
        builder.duration("100").event_number_in_transaction("11111").event_sequence_number("1").is_new_transaction("0")
                .joining_tables("none in here").main_table("customer").operation("SELECT").query_option("q opt")
                .query_parameters("parararameters").raw_sql("SELECT * from customer").round_trip_time("1234").session_id("111")
                .total_run_time("100").transaction_id("1001");
    }

}
