package databasetracing.transformers;

import java.io.BufferedWriter;
import java.io.IOException;

import databasetracing.common.Globals;
import databasetracing.tracing.TraceResult;
import databasetracing.tracing.dto.TraceResultData;

public class WikiMarkupTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<String> {

    private String stringArrayToWikiTableRowString(TraceResultData r) {
        String resultString = "|";

        // for (String s : r) {
        // if (s.equals("")) {
        // s = "&nbsp;";
        // }
        // resultString += s + "|";
        // }
        // TODO: this is not WIKI markup but will compile for now
        return r.toString();
    }


    private String stringArrayToWikiTableHeaderRowString() {
        String resultString = "|| ";

        for (int i = 0; i < 14; i++) {
            resultString += "colName" + " || ";
        }
        return resultString;
    }


    private void writeWikiMarkup(StringBuilder sb, TraceResult traceResult) {
        sb.append(stringArrayToWikiTableHeaderRowString());
        sb.append(Globals.NEWLINE_STRING);

        for (TraceResultData r : traceResult.getResult()) {
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
