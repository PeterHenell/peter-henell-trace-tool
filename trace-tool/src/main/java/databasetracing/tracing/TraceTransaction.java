package databasetracing.tracing;


public class TraceTransaction {

    public TraceTransaction(String transactionId, String sessionId) {
        this.transactionId = transactionId;
        this.sessionId = sessionId;
    }

    private final String transactionId;
    private final String sessionId;


    public String getTransactionId() {
        return transactionId;
    }


    public String getSessionId() {
        return sessionId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
        result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TraceTransaction other = (TraceTransaction) obj;
        if (sessionId == null) {
            if (other.sessionId != null)
                return false;
        } else if (!sessionId.equals(other.sessionId))
            return false;
        if (transactionId == null) {
            if (other.transactionId != null)
                return false;
        } else if (!transactionId.equals(other.transactionId))
            return false;
        return true;
    }

}
