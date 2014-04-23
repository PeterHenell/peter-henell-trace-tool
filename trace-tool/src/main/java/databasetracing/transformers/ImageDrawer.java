package databasetracing.transformers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import databasetracing.tracing.TraceResult;
import databasetracing.tracing.TraceTransaction;

public class ImageDrawer {

    private static final int HEIGHT_OF_EACH_COMMAND = 18;
    private static final int MARGIN_LEFT = 50;
    private static final int MARGIN_TOP = 50;
    private static final int COLUMN_WIDTH = 230;

    private static final Font TEXT_FONT = new Font("Verdana", Font.PLAIN, 12);;
    private static final int TEXT_MARGIN_FROM_TRANSACTION_LINE = 5;
    private static final int TRANSACTION_TEXT_Y_POS = 10;

    private int imageWidth;
    private int imageHeight;

    private final ImageDrawerColorProfile colorProfile;


    public ImageDrawer(ImageDrawerColorProfile colorProfile) {
        this.colorProfile = colorProfile;
    }


    public BufferedImage draw(TraceResult traceResult) {
        // To avoid getting transactions that have no rpc_completed statements, filter all transactions with 2 or less events
        traceResult = traceResult.getFilteredTraceResult(2);
        this.imageWidth = COLUMN_WIDTH * traceResult.getTransactionIds().size() + COLUMN_WIDTH;
        this.imageHeight = traceResult.getResult().size() * HEIGHT_OF_EACH_COMMAND + MARGIN_TOP;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_USHORT_565_RGB);
        System.out.format("Drawing image for trace %s,  h:%d w:%d", traceResult.getTestName(), this.imageHeight, this.imageWidth);
        System.out.println();

        Graphics2D g = bi.createGraphics();

        Font font = TEXT_FONT;
        g.setFont(font);

        paintTraceResult(g, traceResult);
        g.drawImage(bi, null, 0, 0);

        g.dispose();

        return bi;
    }


    private void paintTraceResult(Graphics2D g2, TraceResult traceResult) {
        clear(g2);

        g2.setColor(colorProfile.getLINE_COLOR());

        Map<String, Integer> tranPositions = drawTransactionLines(g2, traceResult);
        drawEventTexts(g2, traceResult, tranPositions);
    }


    private void drawTotalRunTimeCounter(Graphics2D g2, int i, String totalRunTime) {
        int xPosition = 1;
        int yPosition = i * HEIGHT_OF_EACH_COMMAND + MARGIN_TOP;

        g2.setColor(getColorForOperator("LINE"));
        g2.drawChars(totalRunTime.toCharArray(), 0, totalRunTime.length(), xPosition, yPosition);
    }


    private Map<String, Integer> drawTransactionLines(Graphics2D g2, TraceResult traceResult) {
        Map<String, Integer> tranPositions = new HashMap<String, Integer>();

        for (int i = 0; i < traceResult.getTransactionIds().size(); i++) {
            int xPosition = i * COLUMN_WIDTH + MARGIN_LEFT;
            TraceTransaction tran = traceResult.getTransactionIds().get(i);
            // Save the x-position of the transaction, to be able to match actions to this transaction later
            tranPositions.put(tran.getTransactionId(), xPosition);

            String outputText = tran.getTransactionId() + " [spid:" + tran.getSessionId() + "]";

            g2.drawLine(xPosition, 10, xPosition, imageHeight - 10);
            g2.drawChars(outputText.toCharArray(), 0, outputText.length(), xPosition + TEXT_MARGIN_FROM_TRANSACTION_LINE,
                    TRANSACTION_TEXT_Y_POS);
        }

        return tranPositions;
    }


    /**
     * Draws all the events on the image with color coded statements
     * 
     * @param g2
     * @param trace
     * @param tranPositions
     */
    private void drawEventTexts(Graphics2D g2, TraceResult trace, Map<String, Integer> tranPositions) {

        for (int i = 0; i < trace.getResult().size(); i++) {
            String[] row = trace.getResult().get(i);

            String operator = row[trace.getColumnPosition(TraceResult.OPERATION_POSITION)];
            String mainTableName = row[trace.getColumnPosition(TraceResult.MAIN_TABLE_POSITION)];
            String params = row[trace.getColumnPosition(TraceResult.QUERY_PARAMETER_POSITION)];
            String options = row[trace.getColumnPosition(TraceResult.QUERY_OPTION_POSITION)];
            String totalRunTime = row[trace.getColumnPosition(TraceResult.TOTAL_RUN_TIME_POSITION)];
            totalRunTime += "ms";
            String transactionId = row[trace.getColumnPosition(TraceResult.TRANSACTOIN_ID_POSITION)];

            if (operator.equalsIgnoreCase("commit") || operator.equalsIgnoreCase("rollback")) {
                mainTableName = "[" + row[trace.getColumnPosition(TraceResult.DURATION_POSITION)] + "ms";
                mainTableName += ", " + row[trace.getColumnPosition(TraceResult.EVENT_NUMBER_IN_TRANSACTION_POSITION)] + " stmnts]";
            }

            String eventText = formatEventText(operator, mainTableName, params, options);
            int xPosition = tranPositions.get(transactionId) + TEXT_MARGIN_FROM_TRANSACTION_LINE;
            int yPosition = i * HEIGHT_OF_EACH_COMMAND + MARGIN_TOP;

            drawBoxBehindText(g2, yPosition, eventText, xPosition);

            g2.setColor(getColorForOperator(operator));
            g2.drawChars(eventText.toCharArray(), 0, eventText.length(), xPosition, yPosition);

            drawTotalRunTimeCounter(g2, i, totalRunTime);
        }
    }


    private String formatEventText(String command, String mainTableName, String params, String options) {
        if (!options.isEmpty()) {
            options = "(" + options + ")";
        }
        if (!params.isEmpty()) {
            params = "[" + params + "]";
        }

        String text = command + " " + mainTableName + " " + options + params;
        return text;
    }


    private void drawBoxBehindText(Graphics2D g2, int y, String text, int xPosition) {
        FontMetrics metrics = g2.getFontMetrics(TEXT_FONT);
        Dimension size = new Dimension(metrics.stringWidth(text), metrics.getHeight());

        g2.setColor(colorProfile.getBACKGROUND_COLOR());
        g2.fillRect(xPosition + 2, y - (HEIGHT_OF_EACH_COMMAND / 2) - 2, size.width, size.height);
    }


    private Color getColorForOperator(String command) {
        if (command.equals("SELECT"))
            return colorProfile.getSELECT_COLOR();
        else if (command.equals("UPDATE"))
            return colorProfile.getUPDATE_COLOR();
        else if (command.equals("INSERT"))
            return colorProfile.getINSERT_COLOR();
        else if (command.equals("DELETE"))
            return colorProfile.getDELETE_COLOR();
        else if (command.equals("EXEC"))
            return colorProfile.getEXEC_COLOR();
        else
            return colorProfile.getLINE_COLOR();
    }


    private void clear(Graphics2D g2) {
        g2.setColor(colorProfile.getBACKGROUND_COLOR());
        g2.fillRect(0, 0, imageWidth, imageHeight);
    }

}
