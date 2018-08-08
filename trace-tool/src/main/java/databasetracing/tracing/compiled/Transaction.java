package databasetracing.tracing.compiled;

import databasetracing.tracing.dto.TraceResultData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction {

    public final String transactionId;
    private final Session session;

    private List<TraceResultData> rawEvents = new ArrayList<>();
    private Map<String, Event> events;

    public int getEventCount() {
        return rawEvents.size();
    }

    public Transaction(Session session, String transactionId) {
        this.transactionId = transactionId;
        this.session = session;
    }

    // traceResult is expected to be part of same session.
    public static Map<String, Transaction> from(Session session, List<TraceResultData> traceResult) {
        Map<String, Transaction> transactions = new HashMap<>();

        for(TraceResultData data: traceResult) {
            String currentTransactionId = data.getTransaction_id();
            if(!transactions.containsKey(currentTransactionId)) {
                transactions.put(currentTransactionId, new Transaction(session, currentTransactionId));
            }
            Transaction s = transactions.get(currentTransactionId);
            s.rawEvents.add(data);
        }

        // now that events have been distributed to the transactions, compile deeper
        for (String key: transactions.keySet()) {
            Transaction s = transactions.get(key);
            s.compile();
        }

        return transactions;
    }

    private void compile() {
        Map<String, Event> events = Event.from(this, rawEvents);
        assert events != null;
        this.events.putAll(events);
    }

}
