package databasetracing.tracing;

public class TraceResultExamples {

    public static TraceResult getExampleTraceResult(String testName, int rowCount) {
        TraceResultBuilder builder = new TraceResultBuilder();

        // produce some rows
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
        return getExampleTraceResult(testName, 25);
    }

    public static TraceResult getTwoNestedTransactionTraceResult(String testName) {
        TraceResultBuilder builder = new TraceResultBuilder();
        int i = 0;
        int totalRunTime = 0;


        builder.newRow().duration("5")
                .event_number_in_transaction(String.valueOf(1))
                .event_sequence_number(String.valueOf(i++))
                .is_new_transaction("yes")
                .joining_tables("")
                .main_table("customer")
                .operation("SELECT")
                .query_option("")
                .query_parameters("@id")
                .raw_sql("select * from customer where id = @id")
                .round_trip_time("20")
                .session_id("1")
                .total_run_time(String.valueOf(totalRunTime+=20))
                .transaction_id("1");

        builder.newRow().duration("5")
                .event_number_in_transaction(String.valueOf(2))
                .event_sequence_number(String.valueOf(i++))
                .is_new_transaction("no")
                .joining_tables("")
                .main_table("customer")
                .operation("UPDATE")
                .query_option("")
                .query_parameters("@id")
                .raw_sql("Update customer set status = @status where id = @id")
                .round_trip_time("20")
                .session_id("1")
                .total_run_time(String.valueOf(totalRunTime+=20))
                .transaction_id("1");

        // session does another transaction begin and commit tran events
        builder.newRow().duration("5")
                .event_number_in_transaction(String.valueOf(1))
                .event_sequence_number(String.valueOf(i++))
                .is_new_transaction("no")
                .joining_tables("")
                .main_table("")
                .operation("BEGIN TRAN")
                .query_option("")
                .query_parameters("")
                .raw_sql("BEGIN TRAN")
                .round_trip_time("20")
                .session_id("1")
                .total_run_time(String.valueOf(totalRunTime+=20))
                .transaction_id("2");
        builder.newRow().duration("5")
                .event_number_in_transaction(String.valueOf(2))
                .event_sequence_number(String.valueOf(i++))
                .is_new_transaction("yes")
                .joining_tables("")
                .main_table("Account")
                .operation("UPDATE")
                .query_option("")
                .query_parameters("@id")
                .raw_sql("update account set status = 'locked where cid = @cid")
                .round_trip_time("20")
                .session_id("1")
                .total_run_time(String.valueOf(totalRunTime+=20))
                .transaction_id("2");

        builder.newRow().duration("5")
                .event_number_in_transaction(String.valueOf(3))
                .event_sequence_number(String.valueOf(i++))
                .is_new_transaction("no")
                .joining_tables("")
                .main_table("")
                .operation("COMMIT")
                .query_option("")
                .query_parameters("")
                .raw_sql("COMMIT TRAN")
                .round_trip_time("20")
                .session_id("1")
                .total_run_time(String.valueOf(totalRunTime+=20))
                .transaction_id("2");

        builder.newRow().duration("5")
                .event_number_in_transaction(String.valueOf(3))
                .event_sequence_number(String.valueOf(i++))
                .is_new_transaction("no")
                .joining_tables("")
                .main_table("customer")
                .operation("SELECT")
                .query_option("")
                .query_parameters("@id")
                .raw_sql("select * from customer where id = @id")
                .round_trip_time("20")
                .session_id("1")
                .total_run_time(String.valueOf(totalRunTime+=20))
                .transaction_id("1");



        return builder.build(testName);

    }
}
