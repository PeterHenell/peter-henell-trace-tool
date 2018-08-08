package databasetracing.tracing.compiled;

import databasetracing.tracing.TraceResult;
import databasetracing.tracing.dto.TraceResultData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    public final String sessionId;

    // raw event data used internally to compile
    private List<TraceResultData> rawEvents = new ArrayList<>();
    private Map<String, Transaction> transactions = new HashMap<>();

    public Session(String sessionId){
        this.sessionId = sessionId;
    }

    public int getEventCount() {
        return rawEvents.size();
    }


    /// returns map of sessionId->Session
    public static Map<String, Session> from(TraceResult traceResult) {
        Map<String, Session> sessions = new HashMap<>();

        for(TraceResultData data: traceResult.getResult()) {
            String currentSessionId = data.getSession_id();
            if(!sessions.containsKey(currentSessionId)) {
                sessions.put(currentSessionId, new Session(currentSessionId));
            }
            Session s = sessions.get(currentSessionId);
            s.rawEvents.add(data);
        }

        // now that events have been distributed to the sessions, compile deeper
        for (String key: sessions.keySet()) {
            Session s = sessions.get(key);
            s.compile();
        }

        return sessions;
    }

    private void compile() {
        Map<String, Transaction> events = Transaction.from(this, rawEvents);
        assert events != null;
        this.transactions.putAll(events);
    }
}
