package databasetracing.tracing.compiled;


import databasetracing.tracing.TraceResult;
import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.util.HashMap;
import java.util.Map;

/// is a trace result which have have been compile into a deeper structure
public class CompiledTraceResult implements  SvgDrawable {

    private final TraceResult traceResult;
    public final Map<String, Session> sessions = new HashMap<>();

    private CompiledTraceResult(TraceResult traceResult) {
        this.traceResult = traceResult;
    }

    public static CompiledTraceResult from(TraceResult traceResult) {
        CompiledTraceResult compiled = new CompiledTraceResult(traceResult);
        compiled.sessions.putAll(Session.from(traceResult));
        return compiled;
    }

    @Override
    public void paint(int x, int y, SVGGraphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, 60, 20);
    }
}

