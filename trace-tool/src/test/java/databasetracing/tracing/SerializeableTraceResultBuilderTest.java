package databasetracing.tracing;

import junit.framework.Assert;

import org.junit.Test;

public class SerializeableTraceResultBuilderTest {

    @Test
    public void shouldInitializeBuilder() {
        SerializeableTraceResultBuilder builder = initBuilder();
        Assert.assertNotNull(builder);
    }


    @Test
    public void shouldBuildOneRow() {
        SerializeableTraceResultBuilder builder = initBuilder();
        SerializeableTraceResult result = builder.build();
        Assert.assertNotNull(result);

        assertColumnNameAndValue(result, TraceResult.OPERATION_POSITION, "SELECT", 0);
        assertColumnNameAndValue(result, TraceResult.MAIN_TABLE_POSITION, "customer", 1);
        assertColumnNameAndValue(result, TraceResult.QUERY_OPTION_POSITION, "q opt", 2);
        assertColumnNameAndValue(result, TraceResult.JOINING_TABLES_POSITION, "none in here", 3);
        assertColumnNameAndValue(result, TraceResult.QUERY_PARAMETER_POSITION, "parararameters", 4);
        assertColumnNameAndValue(result, TraceResult.TOTAL_RUN_TIME_POSITION, "100", 5);
        assertColumnNameAndValue(result, TraceResult.ROUND_TRIP_POSITION, "1234", 6);
        assertColumnNameAndValue(result, TraceResult.SESSION_ID_POSITION, "111", 7);
        assertColumnNameAndValue(result, TraceResult.TRANSACTOIN_ID_POSITION, "1001", 8);
        assertColumnNameAndValue(result, TraceResult.NEW_TRANSACTION__POSITION, "0", 9);
        assertColumnNameAndValue(result, TraceResult.EVENT_NUMBER_IN_TRANSACTION_POSITION, "11111", 10);
        assertColumnNameAndValue(result, TraceResult.EVENT_SEQUENCE_POSITION, "1", 11);
        assertColumnNameAndValue(result, TraceResult.DURATION_POSITION, "100", 12);
        assertColumnNameAndValue(result, TraceResult.RAW_SQL_TEXT_POSITION, "SELECT * from customer", 13);
    }


    @Test(expected = AssertionError.class)
    public void shouldRequireNewRowBeforeAddingData() {
        SerializeableTraceResultBuilder builder = new SerializeableTraceResultBuilder();
        setDefaultTestValues(builder);
        builder.build();
    }


    @Test
    public void shouldBeSameValuesEachTime() {
        SerializeableTraceResultBuilder first = initBuilder();
        SerializeableTraceResultBuilder second = initBuilder();

        SerializeableTraceResult a = first.build();
        SerializeableTraceResult b = second.build();

        TraceResult amy = TraceResult.fromList(a.result, a.columnNames, "amy");
        TraceResult balthazar = TraceResult.fromList(b.result, b.columnNames, "balthazar");

        Assert.assertTrue(amy.isSameValuesAs(balthazar));
        Assert.assertTrue(balthazar.isSameValuesAs(amy));
    }


    @Test
    public void shouldBeDifferentWhenDifferentBuilds() {
        SerializeableTraceResultBuilder first = initBuilder();
        SerializeableTraceResultBuilder second = initBuilder();

        // Make one of them different
        first.newRow();
        first.duration("one hundred");

        SerializeableTraceResult a = first.build();
        SerializeableTraceResult b = second.build();

        TraceResult amy = TraceResult.fromList(a.result, a.columnNames, "amy");
        TraceResult balthazar = TraceResult.fromList(b.result, b.columnNames, "balthazar");

        Assert.assertFalse(amy.isSameValuesAs(balthazar));
        Assert.assertFalse(balthazar.isSameValuesAs(amy));
    }


    @Test
    public void shouldAddRowToResultCollection() {
        SerializeableTraceResultBuilder builder = new SerializeableTraceResultBuilder();

        for (int i = 0; i < 13; i++) {
            builder.newRow();
            setDefaultTestValues(builder);
        }

        SerializeableTraceResult result = builder.build();

        Assert.assertEquals(13, result.result.size());
    }


    private void assertColumnNameAndValue(SerializeableTraceResult result, String columnName, String expectedValue, int columnPos) {
        Assert.assertEquals(expectedValue, result.result.get(0)[columnPos]);
        Assert.assertEquals(columnName, result.columnNames.get(columnPos));
    }


    private SerializeableTraceResultBuilder initBuilder() {
        SerializeableTraceResultBuilder builder = new SerializeableTraceResultBuilder();
        builder.newRow();
        setDefaultTestValues(builder);
        return builder;
    }


    private void setDefaultTestValues(SerializeableTraceResultBuilder builder) {
        builder.duration("100").event_number_in_transaction("11111").event_sequence_number("1").is_new_transaction("0")
                .joining_tables("none in here").main_table("customer").operation("SELECT").query_option("q opt")
                .query_parameters("parararameters").raw_sql("SELECT * from customer").round_trip_time("1234").session_id("111")
                .total_run_time("100").transaction_id("1001");
    }

}
