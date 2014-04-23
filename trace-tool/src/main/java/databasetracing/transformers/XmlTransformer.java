package databasetracing.transformers;

import java.io.BufferedWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import databasetracing.tracing.SerializeableTraceResult;
import databasetracing.tracing.TraceResult;
import databasetracing.tracing.TraceResultXmlParser;

public class XmlTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<String> {

    public String transformFrom(TraceResult traceResult) {
        XStream xstream = new XStream();
        SerializeableTraceResult serializeable = TraceResultXmlParser.toSerializableObject(traceResult);
        String xml = xstream.toXML(serializeable);
        return xml;
    }


    public void saveTransformation(TraceResult traceResult, String outputFolder) {
        try (BufferedWriter bw = getFileWriter(traceResult, outputFolder)) {
            System.out.println("saving xml: " + getOutputFileName(traceResult, outputFolder));

            String xml = transformFrom(traceResult);
            bw.write(xml);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getFileSuffix() {
        return ".xml";
    }
}
