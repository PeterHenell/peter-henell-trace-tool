package databasetracing.transformers;

import databasetracing.tracing.TraceResult;
import databasetracing.tracing.compiled.CompiledTraceResult;
import org.apache.batik.svggen.SVGGraphics2D;
import java.io.*;

public class DiagramTransformer extends AbstractTraceResultTransformer implements TraceResultTransformer<SVGGraphics2D> {

    public SVGGraphics2D transformFrom(TraceResult traceResult) {
        DiagramDrawer drawer = new DiagramDrawer(ImageDrawerColorProfile.getPrintableColorProfile());
        return drawer.draw(CompiledTraceResult.from(traceResult));
    }

    public void saveTransformation(TraceResult traceResult, String outputFolder) {

        SVGGraphics2D graphics = transformFrom(traceResult);
        try {
            System.out.println("saving image: " + getOutputFileName(traceResult, outputFolder));
            File outputfile = getOutputFile(traceResult, outputFolder);
            boolean useCSS = true; // we want to use CSS style attribute
            try (Writer out = new OutputStreamWriter(new FileOutputStream(outputfile), "UTF-8")) {
                graphics.stream(out, useCSS);
                out.flush();
            }
            System.out.println("wrote file");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getFileSuffix() {
        return ".svg";
    }
}
