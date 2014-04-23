package databasetracing.transformers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import databasetracing.tracing.TraceResult;

public abstract class AbstractTraceResultTransformer {

    protected File getOutputFile(TraceResult traceResult, String outputFolder) {
        String fileName = getOutputFileName(traceResult, outputFolder);
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }


    protected String getOutputFileName(TraceResult traceResult, String outputFolder) {
        String fileName = Paths.get(outputFolder).resolve(traceResult.getTestName() + getFileSuffix()).toAbsolutePath().toString();
        return fileName;
    }


    protected abstract String getFileSuffix();


    protected BufferedWriter getFileWriter(TraceResult traceResult, String outputFolder) {
        try {

            File file = getOutputFile(traceResult, outputFolder);
            FileWriter writer = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(writer);

            return bw;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
