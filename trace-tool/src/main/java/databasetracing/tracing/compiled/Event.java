package databasetracing.tracing.compiled;

import databasetracing.tracing.dto.TraceResultData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {

    public final Transaction transaction;
    public final String eventId;

    public final TraceResultData data;

    public Event(Transaction transaction, String eventId, TraceResultData data) {
        this.eventId = eventId;
        this.transaction = transaction;
        this.data = data;
    }

    public static Map<String, Event> from(Transaction transaction, List<TraceResultData> traceResult) {
        Map<String, Event> events = new HashMap<>();

        for(TraceResultData data: traceResult) {
            String eventId = data.getEvent_number_in_transaction();
            if(!events.containsKey(eventId)) {
                events.put(eventId, new Event(transaction, eventId, data));
            } else {
                throw new IllegalArgumentException("found duplicate event number");
            }
        }
        return events;
    }
}
