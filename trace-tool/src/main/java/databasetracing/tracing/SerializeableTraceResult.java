//package databasetracing.tracing;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
//import com.thoughtworks.xstream.annotations.XStreamImplicit;
//
//import databasetracing.tracing.dto.TraceResultData;
//
///**
// * Serializeable version of the TraceResult
// * 
// * @author peter.henell
// * 
// */
//public class SerializeableTraceResult {
//
//    public SerializeableTraceResult() {
//    }
//
//    @XStreamImplicit
//    public List<String> columnNames = new ArrayList<>();
//
//    @XStreamAsAttribute
//    public String testName = "";
//
//    @XStreamImplicit
//    public List<TraceResultData> result = new ArrayList<>();;
// }
