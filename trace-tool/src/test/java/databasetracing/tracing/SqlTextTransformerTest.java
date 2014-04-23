package databasetracing.tracing;

import org.junit.Ignore;
import org.junit.Test;


import databasetracing.tracing.TraceResult;
import databasetracing.tracing.TraceResultXmlParser;
import databasetracing.transformers.SqlTextTransformer;

public class SqlTextTransformerTest {

    @Test
    @Ignore
    public void shouldTransformToSQlText() {
        TraceResult r = TraceResultXmlParser.fromXml("some xml to be implemented soon (tm)");
        SqlTextTransformer transformer = new SqlTextTransformer();
        transformer.saveTransformation(r, "");
    }
}
