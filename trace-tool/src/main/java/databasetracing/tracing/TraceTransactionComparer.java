package databasetracing.tracing;

import java.util.Comparator;

public class TraceTransactionComparer implements Comparator<TraceTransaction> {

    public int compare(TraceTransaction o1, TraceTransaction o2) {

        // int sessionIdComparison = o1.getSessionId().compareTo(o2.getSessionId());
        // if (0 == sessionIdComparison) {
        return o1.getTransactionId().compareTo(o2.getTransactionId());
        // } else {
        // return sessionIdComparison;
        // }
    }
}
