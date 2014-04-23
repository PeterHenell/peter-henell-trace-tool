package databasetracing.queryparsers.postgres;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import databasetracing.tracing.SerializeableTraceResult;
import databasetracing.tracing.SerializeableTraceResultBuilder;
import databasetracing.tracing.TraceResult;

/**
 * @author peter.henell
 * 
 */
public class LogParser {

    private final SerializeableTraceResultBuilder builder;

    private final Map<String, Integer> transactions;
    private final String filter;
    private final Map<String, String> sessionLogStore = new HashMap<>();
    private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S z");

    private int event_sequence_counter = 0;
    private Long firstLogLineDate = null;


    // log_line_prefix must be: [%d][%p][%c][%m][%i][%l][%x]
    // [database][processID][sessionId][timestamp][command tag][Number of the log line][tranId]

    public LogParser(String filter) {
        this.builder = new SerializeableTraceResultBuilder();
        transactions = new HashMap<String, Integer>();
        this.filter = filter;
    }


    public LogParser() {
        this("");
    }


    private int countEventsInTransaction(String transactionId) {
        Integer currentTrancount = transactions.get(transactionId);

        if (currentTrancount == null) {
            transactions.put(transactionId, 1);
            return 1;
        } else {
            transactions.put(transactionId, ++currentTrancount);
            return currentTrancount.intValue();
        }
    }


    /**
     * @param input
     * @return true if the logline was parsed as a full event
     */
    public boolean parse(String input) {

        LogFileData logData = LogFileData.get(input);

        if (!passesFilter(logData)) {
            return false;
        }
        if (null == firstLogLineDate) {
            firstLogLineDate = parseDateFrom(logData.getTimestamp());
        }

        String operation = logData.getCommandTag();
        String session_id = logData.getSessionId();

        if (operation.toLowerCase().startsWith("idle")) {
            sessionLogStore.put(session_id, input);
            return false;
        }

        String row1 = sessionLogStore.remove(session_id);

        // row1 is the row containing the "idle"
        // input is containing duration in the rawSQL text

        LogFileData previousLogData = LogFileData.get(row1);

        String duration = parseDuration(logData.getRawSQL());
        String transaction_id = logData.getTranId();
        String event_sequence_number = String.valueOf(++event_sequence_counter);
        String joining_tables = "";
        String main_table = "";
        String query_option = "";
        String query_parameters = "";
        String raw_sql = previousLogData.getRawSQL();
        String round_trip_time = "";
        String total_run_time = String.valueOf(getMsDiffFromSessionStart(logData));

        if (operation.equals("COMMIT")) {
            transaction_id = previousLogData.getTranId();
        }

        int tranCount = countEventsInTransaction(transaction_id);
        String is_new_transaction = tranCount == 1 ? "1" : "0";

        String event_number_in_transaction = String.valueOf(tranCount);

        builder.newRow();
        builder.event_number_in_transaction(event_number_in_transaction).duration(duration).event_sequence_number(event_sequence_number)
                .is_new_transaction(is_new_transaction).joining_tables(joining_tables).main_table(main_table).operation(operation)
                .query_option(query_option).query_parameters(query_parameters).raw_sql(raw_sql).round_trip_time(round_trip_time)
                .session_id(session_id).total_run_time(total_run_time).transaction_id(transaction_id);

        return true;
    }


    private Long parseDateFrom(String timestamp) {
        try {
            Date result = df.parse(timestamp);
            return result.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private long getMsDiffFromSessionStart(LogFileData logData) {
        long currentTime = parseDateFrom(logData.getTimestamp());

        return currentTime - firstLogLineDate;

    }


    private boolean passesFilter(LogFileData logData) {

        // if no filter specified, approve all
        if (filter.equals("")) {
            return true;
        }

        if (logData.getDatabase().equalsIgnoreCase(filter)) {
            return true;
        }

        return false;
    }


    public String parseDuration(String group) {
        if (group.contains("duration")) {
            return group.substring(group.indexOf("duration: ") + 10, group.indexOf(" ms"));
        } else {
            throw new AssertionError("String does not contain duration");
        }
    }


    public TraceResult CollectResult(String resultName) {
        if (sessionLogStore.size() > 0) {
            throw new AssertionError("Log is invalid, missing a row?");
        }

        SerializeableTraceResult internalResult = builder.build();

        if (event_sequence_counter == 0) {
            // no events where fully recorded
            internalResult.result = new ArrayList<String[]>();
        }

        return TraceResult.fromList(internalResult.result, internalResult.columnNames, resultName);
    }

}