package databasetracing.transformers;

import java.io.BufferedWriter;
import java.io.IOException;


import databasetracing.common.Globals;
import databasetracing.queryparsers.sqlserver.SqlQueryParserBuilder;
import databasetracing.tracing.TraceResult;

public class SqlTextTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<String> {

    public String transformFrom(TraceResult traceResult) {
        StringBuilder sb = new StringBuilder();

        for (String[] row : traceResult.getResult()) {
            String sqlString = row[traceResult.getColumnPosition(TraceResult.RAW_SQL_TEXT_POSITION)];
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
