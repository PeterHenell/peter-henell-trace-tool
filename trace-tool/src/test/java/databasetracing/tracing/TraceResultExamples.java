package databasetracing.tracing;

public class TraceResultExamples {

    public static TraceResult getExampleTraceResult(String testName, int rowCount) {
        TraceResultBuilder builder = new TraceResultBuilder();

        // produce 10 rows
        for (int i = 0; i < rowCount; i++) {
            builder.newRow().duration("Duration " + i).event_number_in_transaction("Event# in Tran " + i)
                    .event_sequence_number("Event Sequence " + i).is_new_transaction("New Transaction? " + i)
                    .joining_tables("Joining Tables " + i).main_table("Main Table " + i).operation("Operation " + i)
                    .query_option("With (Option) " + i).query_parameters("Query Parameters " + i).raw_sql("Raw Sql text " + i)
                    .round_trip_time("Round Trip Time(Ms) " + i).session_id("Session Id " + i).total_run_time("Total Run Time (Ms) " + i)
                    .transaction_id("Transaction Id " + i % 5); // NOTE THAT WE ONLY HAVE FIVE TRANSACTIONS

        }
        return builder.build(testName);
    }


    public static TraceResult getExampleTraceResult(String testName) {
        return getExampleTraceResult(testName, 10);
    }
}
