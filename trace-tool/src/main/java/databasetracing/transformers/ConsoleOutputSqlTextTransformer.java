package databasetracing.transformers;

import databasetracing.tracing.TraceResult;

public class ConsoleOutputSqlTextTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<String> {

    @Override
    protected String getFileSuffix() {
        return "";
    }


    @Override
    public String transformFrom(TraceResult traceResult) {
        SqlTextTransformer transformer = new SqlTextTransformer();
        String result = transformer.transformFrom(traceResult);
        // System.out.println(result);
        return result;
    }


    @Override
    public void saveTransformation(TraceResult traceResult, String outputFolder) {
        System.err.println(transformFrom(traceResult));
    }

}
