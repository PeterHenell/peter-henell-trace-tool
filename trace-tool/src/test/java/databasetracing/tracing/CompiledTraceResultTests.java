package databasetracing.tracing;

import databasetracing.tracing.compiled.CompiledTraceResult;
import databasetracing.tracing.compiled.Event;
import databasetracing.tracing.compiled.Session;
import databasetracing.tracing.compiled.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class CompiledTraceResultTests {

    @Test
    public void shouldCompileTraceResultIntoCompiledTraceResult() {
        TraceResult traceResult = getTestTraceResult_OneSessionTwoTransactions("compileTest");
        CompiledTraceResult compiled = CompiledTraceResult.from(traceResult);

        Assert.assertEquals("Should have 1 session", 1, compiled.sessions.size());
        Assert.assertEquals("Session should have id 1 and be found in map", "1", compiled.sessions.get("1").sessionId);
        Assert.assertEquals("Session should have 5 events", 6, compiled.sessions.get("1").getEventCount());

    }

    @Test
    public void shouldCompileTransactionFromTraceResult() {
        TraceResult traceResult = getTestTraceResult_OneSessionOneTransaction("compileTest");
        Map<String, Transaction> compiledTransactions = Transaction.from(new Session("1"), traceResult.getResult());

        Assert.assertEquals(1, compiledTransactions.size());

        Transaction transaction = compiledTransactions.get("1");

        Assert.assertEquals("Should have 1 as id", "1", transaction.transactionId);
        Assert.assertEquals("Should have 3 events in transaction", 3, transaction.getEventCount());
    }

    @Test
    public void shouldCompileEventsFromTraceResult() {
        TraceResult traceResult = getTestTraceResult_OneSessionOneTransaction("compileTest");
        Map<String, Event> events = Event.from(new Transaction(new Session("Session 1"), "Transaction 1"), traceResult.getResult());

        Assert.assertEquals("Should have 3 events", 3, events.size());
        Assert.assertNotNull(events.get("1"));
        Assert.assertNotNull(events.get("2"));
        Assert.assertNotNull(events.get("3"));
    }


    public TraceResult getTestTraceResult_OneSessionTwoTransactions(String testName) {
        TraceResultBuilder builder = new TraceResultBuilder();
        int eventCounter = 0;
        int totalRunTime = 0;


        builder.newRow().duration("5")
                .event_number_in_transaction(String.valueOf(1))
                .event_sequence_number(String.valueOf(eventCounter++))
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
                .event_sequence_number(String.valueOf(eventCounter++))
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
                .event_sequence_number(String.valueOf(eventCounter++))
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
                .event_sequence_number(String.valueOf(eventCounter++))
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
                .event_sequence_number(String.valueOf(eventCounter++))
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
                .event_sequence_number(String.valueOf(eventCounter++))
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

    public TraceResult getTestTraceResult_OneSessionOneTransaction(String testName) {
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
