package databasetracing.tracing;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import databasetracing.tracing.sources.TraceSource;
import databasetracing.transformers.ImageTransformer;
import databasetracing.transformers.SqlTextTransformer;
import databasetracing.transformers.TraceResultTransformer;
import databasetracing.transformers.WikiMarkupTransformer;

public class TraceManager implements AutoCloseable {

    private final List<TraceResult> allResults = new ArrayList<TraceResult>();
    private final String outputFolder;
    private final TraceSource traceSource;


    public TraceManager(TraceSource traceSource, String traceFilesOutputFolder) {
        assert (traceSource != null);
        assert (traceFilesOutputFolder != null);

        this.traceSource = traceSource;
        this.outputFolder = Paths.get(traceFilesOutputFolder).toAbsolutePath().toString();
        File f = new File(outputFolder);
        // create folders up to the output folder
        f.mkdirs();
    }


    public void cleanup() {
        allResults.clear();
        traceSource.cleanup();
    }


    public int getEventCount() {
        return traceSource.getEventCount();
    }


    public void startTrace() {
        traceSource.startTrace();
    }


    public void saveAllResults() {
        saveAllResults(new WikiMarkupTransformer(), new SqlTextTransformer(), new ImageTransformer());
    }


    public void saveAllResults(TraceResultTransformer<?>... transformers) {
        for (TraceResult tr : allResults) {
            for (TraceResultTransformer<?> transformer : transformers) {
                try {
                    transformer.saveTransformation(tr, outputFolder);
                } catch (Exception e) {
                    System.err.println("Could not transform result using " + transformer);
                }
            }
        }
    }


    public void stopTrace() {
        System.out.println("TraceManager: stopTrace");
        traceSource.stopTrace();
    }


    public TraceResult collectTraceResult(String testName) {
        TraceResult result = traceSource.collectTraceResult(testName);
        allResults.add(result);
        return result;
    }


    public TraceResult addTraceResult(TraceResult traceResult) {
        this.allResults.add(traceResult);
        return traceResult;
    }


    @Override
    public void close() {
        if (null != traceSource) {
            cleanup();
            traceSource.close();
        }
    }

}
