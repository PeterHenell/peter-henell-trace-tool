package databasetracing.tracing.compiled;

import databasetracing.tracing.dto.TraceResultData;
import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction implements SvgDrawable{

    public final String transactionId;

    private final Session session;
    private final List<TraceResultData> rawEvents = new ArrayList<>();
    public final Map<String, Event> events = new HashMap<>();

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

    @Override
    public void paint(int x, int y, SVGGraphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, 60, 20);
    }
}
