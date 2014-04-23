package databasetracing.tracing;

import org.junit.Assert;
import org.junit.Test;

public class TraceResultTest {

    @Test
    public void shouldCalculateActionPerTransaction() {
        TraceResult result = TraceResultExamples.getExampleTraceResult("ImageTransformerTest", 18);

        for (TraceTransaction tran : result.getTransactionIds()) {
            int actionCountPerTransaction = result.getEventCountPerTransaction(tran.getTransactionId());
            System.out.println("" + actionCountPerTransaction + " : " + tran.getTransactionId());

            if (tran.getTransactionId().endsWith("0") || tran.getTransactionId().endsWith("1") || tran.getTransactionId().endsWith("2")) {
                Assert.assertEquals(4, actionCountPerTransaction);
            } else {
                Assert.assertEquals(3, actionCountPerTransaction);
            }
        }

    }


    @Test
    public void shouldFilterResultOnActionPerTransaction() {
        TraceResult result = TraceResultExamples.getExampleTraceResult("ImageTransformerTest", 23);

        for (String[] row : result.getResult(2)) {
            String tran = row[result.getColumnPosition(TraceResult.TRANSACTOIN_ID_POSITION)];
            Assert.assertTrue("Every transaction in this result set should have action count 2 or greater",
                    2 < result.getEventCountPerTransaction(tran));
        }
    }


    @Test
    public void shouldTestIfResultSetsIsHavingSameValues() {
        TraceResult amy = TraceResultExamples.getExampleTraceResult("ImageTransformerTest", 18);
        TraceResult balthazar = TraceResultExamples.getExampleTraceResult("ImageTransformerTest", 18);
        TraceResult caesar = TraceResultExamples.getExampleTraceResult("ImageTransformerTest", 5);
        TraceResult duncan = TraceResultExamples.getExampleTraceResult("ImageTransformerTest", 18);

        Assert.assertTrue(amy.isSameValuesAs(balthazar));
        Assert.assertTrue(balthazar.isSameValuesAs(amy));

        Assert.assertFalse(amy.isSameValuesAs(caesar));

        duncan.getResult().get(0)[0] = "another value here";
        Assert.assertFalse(amy.isSameValuesAs(duncan));

    }

}
