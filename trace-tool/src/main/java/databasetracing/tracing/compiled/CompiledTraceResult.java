package databasetracing.tracing.compiled;


import databasetracing.tracing.TraceResult;

import java.util.HashMap;
import java.util.Map;

/// is a trace result which have have been compile into a deeper structure
public class CompiledTraceResult {

    public Map<String, Session> sessions = new HashMap<>();

    private CompiledTraceResult() {
    }

    public static CompiledTraceResult from(TraceResult traceResult) {
        CompiledTraceResult compiled = new CompiledTraceResult();
        compiled.sessions.putAll(Session.from(traceResult));
        return compiled;
    }
}

