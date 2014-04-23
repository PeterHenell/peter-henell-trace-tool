package databasetracing.tracing.sources;

import databasetracing.tracing.TraceResult;

public interface TraceSource extends AutoCloseable {

    void cleanup();


    int getEventCount();


    void startTrace();


    void stopTrace();


    TraceResult collectTraceResult(String testName);


    // Override close without Exception
    @Override
    void close();

}
