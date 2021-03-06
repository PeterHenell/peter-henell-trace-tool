package databasetracing.tracing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import databasetracing.tracing.dto.TraceResultData;

public class TraceResult {

    private TraceResult(List<TraceResultData> result, String testName) {
        this.result = result;
        this.testName = testName;
        actionInTranCount = getEventCountPerTransaction();
    }


    public TraceResult getFilteredTraceResult(int minActionPerTransaction) {
        List<TraceResultData> filteredRows = getResult(minActionPerTransaction);
        return new TraceResult(filteredRows, testName);
    }

    private final List<TraceResultData> result;
    private final String testName;
    private Map<String, Integer> actionInTranCount;
    private Map<String, Integer> columnPositions;

    public final static String OPERATION_POSITION = "Operation";
    public final static String MAIN_TABLE_POSITION = "Main Table";
    public final static String QUERY_OPTION_POSITION = "With (Option)";
    public final static String JOINING_TABLES_POSITION = "Joining Tables";
    public final static String QUERY_PARAMETER_POSITION = "Query Parameters";
    public final static String TOTAL_RUN_TIME_POSITION = "Total Run Time (Ms)";
    public final static String ROUND_TRIP_POSITION = "Round Trip Time(Ms)";
    public final static String SESSION_ID_POSITION = "Session Id";
    public final static String TRANSACTOIN_ID_POSITION = "Transaction Id";
    public final static String NEW_TRANSACTION__POSITION = "New Transaction?";
    public final static String EVENT_NUMBER_IN_TRANSACTION_POSITION = "Event# in Tran";
    public final static String EVENT_SEQUENCE_POSITION = "Event Sequence";
    public final static String DURATION_POSITION = "Duration";
    public final static String RAW_SQL_TEXT_POSITION = "Raw Sql text";


    public int getColumnPosition(String positionName) {
        return columnPositions.get(positionName);
    }


    /**
     * get the results, do not filter anything
     * 
     * @return
     */
    public List<TraceResultData> getResult() {
        return getResult(0);
    }


    /**
     * return the result, filter out events that are part of transactions that do not have at least <minActionPerTransaction> events per
     * transaction
     * 
     * @param minActionPerTransaction
     * @return
     */
    public List<TraceResultData> getResult(int minActionPerTransaction) {
        if (minActionPerTransaction <= 0) {
            return result;
        }

        int removedCount = 0;
        List<TraceResultData> newResult = new ArrayList<>();
        for (TraceResultData row : result) {
            if (getEventCountPerTransaction(row.getTransaction_id()) > minActionPerTransaction) {
                newResult.add(row);
            } else {
                removedCount++;
            }
        }
        System.out.println("Filter out " + removedCount + " events that did not have more than " + minActionPerTransaction
                + " events per transaction ");
        return newResult;
    }


    public String getTestName() {
        return testName;
    }



    public void printResult() {
        for (TraceResultData data : result) {
            System.out.println(data.toString());
        }
    }


    /**
     * create a new TraceResult based on a List of string arrays, no public constructor
     * 
     * @param traceRes
     * @param testName
     * @return
     */
    public static TraceResult fromList(List<TraceResultData> traceRes, String testName) {
        return new TraceResult(traceRes, testName);
    }


    /**
     * create a new TraceResult based on a ResultSet, no public constructor
     * 
     * @param rs
     * @param testName
     * @return
     */
    public static TraceResult fromResultSet(ResultSet rs, String testName) {
        List<TraceResultData> result = ResultSetHelper.getResultValues(rs);

        return new TraceResult(result, testName);
    }


    /**
     * @return list of unique transactions used in the flow
     */
    public List<TraceTransaction> getTransactionIds() {
        Set<TraceTransaction> uniqueTransactions = new HashSet<TraceTransaction>();

        // Add all transaction Ids to a set, making each entry unique
        for (TraceResultData data : result) {
            String transactionId = data.getTransaction_id();
            String sessionId = data.getSession_id();

            uniqueTransactions.add(new TraceTransaction(transactionId, sessionId));
        }

        return asSortedList(uniqueTransactions);
    }


    private List<TraceTransaction> asSortedList(Set<TraceTransaction> trans) {
        // Make a list of the unique transactions and sort them
        List<TraceTransaction> transactionNumbers = new ArrayList<TraceTransaction>();
        for (TraceTransaction tranId : trans) {
            transactionNumbers.add(tranId);
        }

        Collections.sort(transactionNumbers, new TraceTransactionComparer());
        return transactionNumbers;
    }


    /**
     * get the number of events that occured in the provided transactionId
     * 
     * @param transactionId
     * @return
     */
    public int getEventCountPerTransaction(String transactionId) {
        return actionInTranCount.get(transactionId).intValue();
    }


    /**
     * calculate the number of events per transaction
     * 
     * @return transactionId to eventCount - map
     */
    private Map<String, Integer> getEventCountPerTransaction() {
        Map<String, Integer> eventPerTran = new HashMap<>();
        for (TraceResultData row : result) {
            Integer v = eventPerTran.get(row.getTransaction_id());
            // if the transaction is already in the map, then add one to it's counter;
            if (null == v) {
                eventPerTran.put(row.getTransaction_id(), 1);
            } else {
                eventPerTran.put(row.getTransaction_id(), v + 1);
            }
        }
        return eventPerTran;
    }


    public boolean isSameValuesAs(TraceResult other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            System.err.println("Other was null");
            return false;
        }

        if (this.result == null || other.result == null) {
            System.err.println("result was null in one of the results");
            return false;
        }

        if (this.result.size() != other.result.size()) {
            System.err.println(String.format("this and Other had results of different sizes: this: %d, other: %d", this.result.size(),
                    other.result.size()));
            return false;
        }

        for (int r = 0; r < this.result.size(); r++) {
            TraceResultData row = this.result.get(r);
            TraceResultData otherRow = other.result.get(r);

            if (!row.equals(otherRow)) {
                System.err.println(String.format("Row was missmatching: \n[%s]\n[%s]", row.toString(), otherRow.toString()));
                return false;
            }
        }

        return true;
    }
}
