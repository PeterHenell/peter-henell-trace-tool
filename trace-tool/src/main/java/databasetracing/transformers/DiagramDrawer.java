package databasetracing.transformers;

import databasetracing.tracing.compiled.CompiledTraceResult;
import databasetracing.tracing.compiled.Event;
import databasetracing.tracing.compiled.Session;
import databasetracing.tracing.compiled.Transaction;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;


public class DiagramDrawer {
    public DiagramDrawer(ImageDrawerColorProfile printableColorProfile) {
    }

    public SVGGraphics2D draw(CompiledTraceResult traceResult) {

        // Get a DOMImplementation
        DOMImplementation domImpl =
                SVGDOMImplementation.getDOMImplementation();

        // Create a document with the appropriate namespace
        SVGDocument doc =
                (SVGDocument) domImpl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
        SVGGraphics2D graphics = new SVGGraphics2D(doc);
//        graphics.setSVGCanvasSize(new Dimension(500, 500));

        int x = 0, y = 0;

        traceResult.paint(x, y, graphics);

        for (String sessionId : traceResult.sessions.keySet()) {

            Session s = traceResult.sessions.get(sessionId);

            x+=100;
            s.paint(x, y, graphics);

            for(String transactionId: s.transactions.keySet()) {
                Transaction t = s.transactions.get(transactionId);
                x+=100;
                t.paint(x, y, graphics);

                for(String eventId: s.transactions.keySet()) {
                    Event e = t.events.get(eventId);
                    y+=40;
                    e.paint(x, y, graphics);
                }
            }
        }

        return graphics;
    }
}
