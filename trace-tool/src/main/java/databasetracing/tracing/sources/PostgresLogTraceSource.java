package databasetracing.tracing.sources;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;

import databasetracing.queryparsers.postgres.LogParser;
import databasetracing.tracing.TraceResult;

public class PostgresLogTraceSource implements TraceSource {

    private final Path fileName;
    private final LogParser logParser;
    private Tailer tailer;
    private int eventCounter;
    private TailerListener listener;


    public PostgresLogTraceSource(Path logFile, String databaseName) {
        // Path logFile = Paths.get(fileName);

        if (null == logFile) {
            throw new AssertionError("LogFile must exist and be readable by user executing trace tool");
        }

        if (!Files.isReadable(logFile)) {
            throw new AssertionError("LogFile must exist and be readable by user executing trace tool");
        }

        this.fileName = logFile;
        this.logParser = new LogParser(databaseName);
    }


    @Override
    public void close() {
        if (tailer != null) {
            tailer.stop();
        }
    }


    @Override
    public void cleanup() {
        close();
    }


    @Override
    public int getEventCount() {
        return eventCounter;
    }


    @Override
    public void startTrace() {
        listener = new MyTailerListener();
        tailer = Tailer.create(fileName.toFile(), listener, 100);
    }


    @Override
    public void stopTrace() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tailer.stop();
    }


    @Override
    public TraceResult collectTraceResult(String testName) {
        return logParser.CollectResult(testName);
    }

    public class MyTailerListener extends TailerListenerAdapter {
        public void handle(String line) {
            if (logParser.parse(line)) {
                eventCounter++;
            }
        }
    }

}
