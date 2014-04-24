package databasetracing.tracing;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import databasetracing.transformers.ImageTransformer;
import databasetracing.transformers.SqlTextTransformer;
import databasetracing.transformers.WikiMarkupTransformer;

public class ResultSetTransformerTest {

    @Test
    public void shouldDrawSimpleImage() {
        TraceResult traceResult = TraceResultExamples.getExampleTraceResult("ImageTransformerTest");
        ImageTransformer transformer = new ImageTransformer();

        transformer.saveTransformation(traceResult, "");
    }


    @Test
    public void shouldOutputFileWithSqlText() {
        TraceResult traceResult = TraceResultExamples.getExampleTraceResult("TextTransformerTest");
        SqlTextTransformer transformer = new SqlTextTransformer();

        transformer.saveTransformation(traceResult, "");
    }


    // @Test
    // public void shouldWriteSimpleXml() {
    // TraceResult traceResult = TraceResultExamples.getExampleTraceResult("XmlTransformerTest");
    // XmlTransformer transformer = new XmlTransformer();
    //
    // transformer.saveTransformation(traceResult, "");
    // }

    @Test
    public void shouldWriteSimpleWikiMarkup() {
        TraceResult traceResult = TraceResultExamples.getExampleTraceResult("WikiTransformerTest");
        WikiMarkupTransformer transformer = new WikiMarkupTransformer();

        transformer.saveTransformation(traceResult, "");
    }


    @Test
    public void shouldGetTransactinoNumbersFromResultArray() {
        TraceResult trace = TraceResultExamples.getExampleTraceResult("TransactionsTest", 5);

        List<TraceTransaction> tranIds = trace.getTransactionIds();
        Assert.assertEquals(5, tranIds.size());
    }

    // @Test
    // public void shouldParseXmlToTraceResult() {
    // String xml = "<databasetracing.tracing.SerializeableTraceResult>\n" + "  <columnNames>\n" + "    <string>Raw Sql text</string>\n"
    // + "    <string>Operation</string>\n" + "    <string>New Transaction?</string>\n" + "    <string>Event# in Tran</string>\n"
    // + "    <string>Main Table</string>\n" + "    <string>Total Run Time (Ms)</string>\n"
    // + "    <string>With (Option)</string>\n" + "    <string>Event Sequence</string>\n"
    // + "    <string>Transaction Id</string>\n" + "    <string>Round Trip Time(Ms)</string>\n"
    // + "    <string>Duration</string>\n" + "    <string>Session Id</string>\n" + "    <string>Joining Tables</string>\n"
    // + "    <string>Query Parameters</string>\n" + "  </columnNames>\n" + "  <testName>Trace test</testName>\n" + "  <result>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 0</string>\n" + "      <string>Operation 0</string>\n"
    // + "      <string>New Transaction? 0</string>\n" + "      <string>Event# in Tran 0</string>\n"
    // + "      <string>Main Table 0</string>\n" + "      <string>Total Run Time (Ms) 0</string>\n"
    // + "      <string>With (Option) 0</string>\n" + "      <string>Event Sequence 0</string>\n"
    // + "      <string>Transaction Id 0</string>\n" + "      <string>Round Trip Time(Ms) 0</string>\n"
    // + "      <string>Duration 0</string>\n" + "      <string>Session Id 0</string>\n"
    // + "      <string>Joining Tables 0</string>\n" + "      <string>Query Parameters 0</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 1</string>\n" + "      <string>Operation 1</string>\n"
    // + "      <string>New Transaction? 1</string>\n" + "      <string>Event# in Tran 1</string>\n"
    // + "      <string>Main Table 1</string>\n" + "      <string>Total Run Time (Ms) 1</string>\n"
    // + "      <string>With (Option) 1</string>\n" + "      <string>Event Sequence 1</string>\n"
    // + "      <string>Transaction Id 1</string>\n" + "      <string>Round Trip Time(Ms) 1</string>\n"
    // + "      <string>Duration 1</string>\n" + "      <string>Session Id 1</string>\n"
    // + "      <string>Joining Tables 1</string>\n" + "      <string>Query Parameters 1</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 2</string>\n" + "      <string>Operation 2</string>\n"
    // + "      <string>New Transaction? 2</string>\n" + "      <string>Event# in Tran 2</string>\n"
    // + "      <string>Main Table 2</string>\n" + "      <string>Total Run Time (Ms) 2</string>\n"
    // + "      <string>With (Option) 2</string>\n" + "      <string>Event Sequence 2</string>\n"
    // + "      <string>Transaction Id 2</string>\n" + "      <string>Round Trip Time(Ms) 2</string>\n"
    // + "      <string>Duration 2</string>\n" + "      <string>Session Id 2</string>\n"
    // + "      <string>Joining Tables 2</string>\n" + "      <string>Query Parameters 2</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 3</string>\n" + "      <string>Operation 3</string>\n"
    // + "      <string>New Transaction? 3</string>\n" + "      <string>Event# in Tran 3</string>\n"
    // + "      <string>Main Table 3</string>\n" + "      <string>Total Run Time (Ms) 3</string>\n"
    // + "      <string>With (Option) 3</string>\n" + "      <string>Event Sequence 3</string>\n"
    // + "      <string>Transaction Id 3</string>\n" + "      <string>Round Trip Time(Ms) 3</string>\n"
    // + "      <string>Duration 3</string>\n" + "      <string>Session Id 3</string>\n"
    // + "      <string>Joining Tables 3</string>\n" + "      <string>Query Parameters 3</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 4</string>\n" + "      <string>Operation 4</string>\n"
    // + "      <string>New Transaction? 4</string>\n" + "      <string>Event# in Tran 4</string>\n"
    // + "      <string>Main Table 4</string>\n" + "      <string>Total Run Time (Ms) 4</string>\n"
    // + "      <string>With (Option) 4</string>\n" + "      <string>Event Sequence 4</string>\n"
    // + "      <string>Transaction Id 4</string>\n" + "      <string>Round Trip Time(Ms) 4</string>\n"
    // + "      <string>Duration 4</string>\n" + "      <string>Session Id 4</string>\n"
    // + "      <string>Joining Tables 4</string>\n" + "      <string>Query Parameters 4</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 0</string>\n" + "      <string>Operation 0</string>\n"
    // + "      <string>New Transaction? 0</string>\n" + "      <string>Event# in Tran 0</string>\n"
    // + "      <string>Main Table 0</string>\n" + "      <string>Total Run Time (Ms) 0</string>\n"
    // + "      <string>With (Option) 0</string>\n" + "      <string>Event Sequence 0</string>\n"
    // + "      <string>Transaction Id 0</string>\n" + "      <string>Round Trip Time(Ms) 0</string>\n"
    // + "      <string>Duration 0</string>\n" + "      <string>Session Id 0</string>\n"
    // + "      <string>Joining Tables 0</string>\n" + "      <string>Query Parameters 0</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 1</string>\n" + "      <string>Operation 1</string>\n"
    // + "      <string>New Transaction? 1</string>\n" + "      <string>Event# in Tran 1</string>\n"
    // + "      <string>Main Table 1</string>\n" + "      <string>Total Run Time (Ms) 1</string>\n"
    // + "      <string>With (Option) 1</string>\n" + "      <string>Event Sequence 1</string>\n"
    // + "      <string>Transaction Id 1</string>\n" + "      <string>Round Trip Time(Ms) 1</string>\n"
    // + "      <string>Duration 1</string>\n" + "      <string>Session Id 1</string>\n"
    // + "      <string>Joining Tables 1</string>\n" + "      <string>Query Parameters 1</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 2</string>\n" + "      <string>Operation 2</string>\n"
    // + "      <string>New Transaction? 2</string>\n" + "      <string>Event# in Tran 2</string>\n"
    // + "      <string>Main Table 2</string>\n" + "      <string>Total Run Time (Ms) 2</string>\n"
    // + "      <string>With (Option) 2</string>\n" + "      <string>Event Sequence 2</string>\n"
    // + "      <string>Transaction Id 2</string>\n" + "      <string>Round Trip Time(Ms) 2</string>\n"
    // + "      <string>Duration 2</string>\n" + "      <string>Session Id 2</string>\n"
    // + "      <string>Joining Tables 2</string>\n" + "      <string>Query Parameters 2</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 3</string>\n" + "      <string>Operation 3</string>\n"
    // + "      <string>New Transaction? 3</string>\n" + "      <string>Event# in Tran 3</string>\n"
    // + "      <string>Main Table 3</string>\n" + "      <string>Total Run Time (Ms) 3</string>\n"
    // + "      <string>With (Option) 3</string>\n" + "      <string>Event Sequence 3</string>\n"
    // + "      <string>Transaction Id 3</string>\n" + "      <string>Round Trip Time(Ms) 3</string>\n"
    // + "      <string>Duration 3</string>\n" + "      <string>Session Id 3</string>\n"
    // + "      <string>Joining Tables 3</string>\n" + "      <string>Query Parameters 3</string>\n" + "    </string-array>\n"
    // + "    <string-array>\n" + "      <string>Raw Sql text 4</string>\n" + "      <string>Operation 4</string>\n"
    // + "      <string>New Transaction? 4</string>\n" + "      <string>Event# in Tran 4</string>\n"
    // + "      <string>Main Table 4</string>\n" + "      <string>Total Run Time (Ms) 4</string>\n"
    // + "      <string>With (Option) 4</string>\n" + "      <string>Event Sequence 4</string>\n"
    // + "      <string>Transaction Id 4</string>\n" + "      <string>Round Trip Time(Ms) 4</string>\n"
    // + "      <string>Duration 4</string>\n" + "      <string>Session Id 4</string>\n"
    // + "      <string>Joining Tables 4</string>\n" + "      <string>Query Parameters 4</string>\n" + "    </string-array>\n"
    // + "  </result>\n" + "</databasetracing.tracing.SerializeableTraceResult>";
    //
    // TraceResult traceResult = TraceResultXmlParser.fromXml(xml);
    // Assert.assertNotNull(traceResult);
    //
    // XmlTransformer transformer = new XmlTransformer();
    // String newXml = transformer.transformFrom(traceResult);
    // Assert.assertEquals(xml, newXml);
    //
    // }

    // @Test
    // public void shoudSaveToXmlAndThenReadItBack() {
    // TraceResult traceResult = TraceResultExamples.getExampleTraceResult("Trace test");
    // XmlTransformer transformer = new XmlTransformer();
    // String outputFolder = "";
    // String originalXml = transformer.transformFrom(traceResult);
    //
    // transformer.saveTransformation(traceResult, outputFolder);
    //
    // TraceResult fromXmlFile = TraceResultXmlParser.fromXmlFile("Trace test.xml");
    // TraceResult fromXml = TraceResultXmlParser.fromXml(originalXml);
    //
    // Assert.assertEquals(originalXml, transformer.transformFrom(fromXml));
    // Assert.assertEquals(originalXml, transformer.transformFrom(fromXmlFile));
    //
    // }
}
