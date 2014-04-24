package databasetracing.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import databasetracing.tracing.TraceResult;
import databasetracing.tracing.TraceResultXmlParser;
import databasetracing.transformers.ImageTransformer;
import databasetracing.transformers.TraceResultTransformer;
import databasetracing.transformers.WikiMarkupTransformer;

public class TraceFileTransformerConsole {

    public static void main(String[] args) {
        String fileName = args[0];
        if (nullOrEmpty(fileName)) {
            System.err.println("Please supply the filname to parse");
            System.exit(-1);
        }
        File f = new File(fileName);
        if (!f.exists()) {
            System.err.println("File not found: " + f.getAbsolutePath());
            System.exit(-1);
        }
        TraceFileTransformerConsole tft = new TraceFileTransformerConsole();

        tft.transformTraceResultFromFile(fileName);
    }


    private static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


    public void transformTraceResultFromFile(String fileName) {
        List<TraceResultTransformer<?>> transformers = new ArrayList<TraceResultTransformer<?>>();
        transformers.add(new ImageTransformer());
        // transformers.add(new XmlTransformer());
        transformers.add(new WikiMarkupTransformer());

        TraceResult traceResult = TraceResultXmlParser.fromXmlFile(fileName);

        for (TraceResultTransformer<?> transformer : transformers) {
            String newFileName = fileName + "_reparsed";
            transformer.saveTransformation(traceResult, newFileName);
        }
    }

}
