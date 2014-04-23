package databasetracing.tracing;

import java.util.ArrayList;
import java.util.List;

import databasetracing.tracing.TraceResult;

public class TraceResultExamples {

    public static TraceResult getExampleTraceResult(String testName) {
        List<String> columns = getTraceColumnNames();
        List<String[]> traceRes = getTraceRows(columns, 10);

        TraceResult trace = TraceResult.fromList(traceRes, columns, testName);
        return trace;
    }


    public static TraceResult getExampleTraceResult(String testName, int numberOfRows) {
        List<String> columns = getTraceColumnNames();
        List<String[]> traceRes = getTraceRows(columns, numberOfRows);

        TraceResult trace = TraceResult.fromList(traceRes, columns, testName);
        return trace;
    }


    public static List<String> getTraceColumnNames() {
        List<String> columns = new ArrayList<String>();
        for (String e : TraceResult.getExpectedColumnNames()) {
            columns.add(e);
        }
        return columns;
    }


    public static List<String[]> getTraceRows(List<String> columns, int rowCount) {
        List<String[]> traceRes = new ArrayList<String[]>();
        // produce 10 rows
        for (int i = 10; i < rowCount + 10; i++) {

            String[] row = new String[columns.size()];
            // for each column name
            for (int j = 0; j < columns.size(); j++) {
                // reuse the same value every other time
                row[j] = columns.get(j) + " " + i % 5;
            }
            traceRes.add(row);
        }
        return traceRes;
    }
}
