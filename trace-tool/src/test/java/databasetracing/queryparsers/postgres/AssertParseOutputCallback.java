package databasetracing.queryparsers.postgres;

import junit.framework.Assert;

public class AssertParseOutputCallback implements ParseOutputCallback {

    private String expected;


    public void setExpected(String expected) {
        this.expected = expected;
    }


    @Override
    public void callback(String parsedString) {
        Assert.assertEquals(expected, parsedString);
    }

}
