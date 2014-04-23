package databasetracing.transformers;

import databasetracing.tracing.TraceResult;

public interface TraceResultTransformer<T> {

    /**
     * Transform the traceresult into T
     * 
     * @param traceResult
     * @return transformed version of traceResult
     */
    T transformFrom(TraceResult traceResult);


    /**
     * Transforms and save the traceresult to the specified outputfolder. The file will have a suffix depending on the transformer.
     * 
     * @param traceResult
     * @param outputFolder - the folder where the generated file will be located
     */
    void saveTransformation(TraceResult traceResult, String outputFolder);

}
