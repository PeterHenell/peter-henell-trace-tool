package databasetracing.tracing;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.thoughtworks.xstream.XStream;

public class TraceResultXmlParser {

    public static TraceResult fromXml(String xml) {
        XStream x = new XStream();

        TraceResult traceResult = (TraceResult) x.fromXML(xml);
        // TraceResult traceResult = TraceResult.fromList(obj.result, obj.columnNames, obj.testName);
        return traceResult;
    }


    public static TraceResult fromXmlFile(String fileName) {

        String xml;
        try {
            xml = readFile(fileName, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TraceResult traceResult = fromXml(xml);
        return traceResult;
    }


    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    // /**
    // * Create a version of the TraceResult that can be serialized/deserialized
    // *
    // * @param traceResult
    // * @return
    // */
    // public static Trace toSerializableObject(TraceResult traceResult) {
    // SerializeableTraceResult seria = new SerializeableTraceResult();
    //
    // List<TraceResultData> rows = new ArrayList<>();
    //
    // for (TraceResultData data : traceResult.getResult()) {
    // rows.add(data);
    // }
    //
    // seria.result = rows;
    // seria.testName = traceResult.getTestName();
    //
    // return seria;
    // }
}
