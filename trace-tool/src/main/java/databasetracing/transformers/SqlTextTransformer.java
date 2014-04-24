package databasetracing.transformers;

import java.io.BufferedWriter;
import java.io.IOException;

import databasetracing.common.Globals;
import databasetracing.queryparsers.sqlserver.SqlQueryParserBuilder;
import databasetracing.tracing.TraceResult;
import databasetracing.tracing.dto.TraceResultData;

public class SqlTextTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<String> {

    public String transformFrom(TraceResult traceResult) {
        StringBuilder sb = new StringBuilder();

        for (TraceResultData row : traceResult.getResult()) {
            String sqlString = row.getRaw_sql();
            String parsed = SqlQueryParserBuilder.parseQuery(sqlString);
            if (parsed == null && !sqlString.isEmpty()) {
                sb.append("--Could not parse non-empty string: " + Globals.NEWLINE_STRING + sqlString);
                sb.append(Globals.NEWLINE_STRING);
            } else if (parsed == null) {
                // System.out.println("Ignored parsing, string was empty: " + sqlString);
            } else {
                sb.append(parsed);
                sb.append(Globals.NEWLINE_STRING);
            }
            // System.out.println(sqlString);
        }
        return sb.toString();
    }


    public void saveTransformation(TraceResult traceResult, String outputFolder) {
        try (BufferedWriter bw = getFileWriter(traceResult, outputFolder)) {
            System.out.println("saving sql text: " + getOutputFileName(traceResult, outputFolder));

            String xml = transformFrom(traceResult);
            bw.write(xml);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getFileSuffix() {
        return "_SqlText.txt";
    }
}
