package databasetracing.transformers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;


import databasetracing.common.Globals;
import databasetracing.tracing.TraceResult;

public class WikiMarkupTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<String> {

    private String stringArrayToWikiTableRowString(String[] r) {
        String resultString = "|";

        for (String s : r) {
            if (s.equals("")) {
                s = "&nbsp;";
            }
            resultString += s + "|";
        }
        return resultString;
    }


    private String stringArrayToWikiTableHeaderRowString(List<String> columnNames) {
        String resultString = "|| ";

        for (String colName : columnNames) {
            resultString += colName + " || ";
        }
        return resultString;
    }


    private void writeWikiMarkup(StringBuilder sb, TraceResult traceResult) {
        sb.append(stringArrayToWikiTableHeaderRowString(traceResult.getColumnNames()));
        sb.append(Globals.NEWLINE_STRING);

        for (String[] r : traceResult.getResult()) {
            String s = stringArrayToWikiTableRowString(r);
            s = cleanWikiString(s);
            sb.append(s);
            sb.append(Globals.NEWLINE_STRING);
        }
    }


    private String cleanWikiString(String s) {
        s = s.replace("\r\n", "\\\\");
        s = s.replace("\n", "\\\\");
        return s;
    }


    public String transformFrom(TraceResult traceResult) {
        StringBuilder sb = new StringBuilder();
        writeWikiMarkup(sb, traceResult);
        return sb.toString();
    }


    public void saveTransformation(TraceResult traceResult, String outputFolder) {
        try (BufferedWriter bw = getFileWriter(traceResult, outputFolder)) {
            System.out.println("saving wiki file: " + getOutputFileName(traceResult, outputFolder));
            String wikiString = transformFrom(traceResult);
            bw.write(wikiString);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getFileSuffix() {
        return ".txt";
    }
}
