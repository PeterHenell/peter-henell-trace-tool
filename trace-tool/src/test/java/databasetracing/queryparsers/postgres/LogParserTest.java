package databasetracing.queryparsers.postgres;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.tracing.SerializeableTraceResultBuilder;
import databasetracing.tracing.TraceResult;

public class LogParserTest {

    public final static String[] testLog = new String[]{
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.831 CEST][idle][1][0]LOG:  statement: BEGIN",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.831 CEST][BEGIN][2][0]LOG:  duration: 0.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.831 CEST][idle in transaction][3][0]LOG:  statement: DROP TABLE IF EXISTS cars",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.832 CEST][DROP TABLE][4][7472]LOG:  duration: 1.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.832 CEST][idle in transaction][5][7472]LOG:  statement: CREATE TABLE cars(id INT PRIMARY KEY, name TEXT, price INT)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.840 CEST][CREATE TABLE][6][7472]LOG:  duration: 8.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.840 CEST][idle in transaction][7][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (1, 'Audi', 52642)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.841 CEST][INSERT][8][7472]LOG:  duration: 1.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.841 CEST][idle in transaction][9][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (2, 'Mercedes', 57127)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.841 CEST][INSERT][10][7472]LOG:  duration: 0.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.841 CEST][idle in transaction][11][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (3, 'Skoda', 9000)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.841 CEST][INSERT][12][7472]LOG:  duration: 0.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.841 CEST][idle in transaction][13][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (4, 'Volvo', 29000)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][INSERT][14][7472]LOG:  duration: 1.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][idle in transaction][15][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (5, 'Bentley', 350000)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][INSERT][16][7472]LOG:  duration: 0.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][idle in transaction][17][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (6, 'Citroen', 21000)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][INSERT][18][7472]LOG:  duration: 0.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][idle in transaction][19][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (7, 'Hummer', 41400)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][INSERT][20][7472]LOG:  duration: 0.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][idle in transaction][21][7472]LOG:  statement: INSERT INTO cars (id, name, price) VALUES (8, 'Volkswagen', 21600)",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][INSERT][22][7472]LOG:  duration: 0.000 ms",

            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.842 CEST][idle in transaction][23][7472]LOG:  statement: COMMIT",
            "[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.845 CEST][COMMIT][24][0]LOG:  duration: 3.000 ms" };


    @Test
    public void shouldParseOneTransaction() {
        // Prepare expected
        TraceResult expected = getFullExpectedResult();

        // Now parse
        LogParser p = new LogParser();
        for (String logLine : testLog) {
            p.parse(logLine);
        }
        TraceResult actual = p.CollectResult("sample");

        // Compare
        Assert.assertTrue(expected.isSameValuesAs(actual));
    }


    @Test
    public void shouldIgnoreEventsIfFilterIsSet() {
        // Prepare expected
        TraceResult expected = getFullExpectedResult();

        // Now parse
        LogParser p = new LogParser("another database");
        for (String logLine : testLog) {
            p.parse(logLine);
        }
        TraceResult actual = p.CollectResult("sample");

        // Compare
        Assert.assertFalse(expected.isSameValuesAs(actual));
        Assert.assertEquals(0, actual.getResult().size());
    }


    public static TraceResult getFullExpectedResult() {
        SerializeableTraceResultBuilder builder = new SerializeableTraceResultBuilder();
        builder.newRow().operation("BEGIN").session_id("534cf0be.7c4").event_number_in_transaction("1").transaction_id("0")
                .raw_sql("LOG:  statement: BEGIN").event_sequence_number("1").is_new_transaction("1").duration("0.000").total_run_time("0");

        builder.newRow().operation("DROP TABLE").session_id("534cf0be.7c4").event_number_in_transaction("1").transaction_id("7472")
                .raw_sql("LOG:  statement: DROP TABLE IF EXISTS cars").event_sequence_number("2").is_new_transaction("1").duration("1.000")
                .total_run_time("1");

        builder.newRow().operation("CREATE TABLE").session_id("534cf0be.7c4").event_number_in_transaction("2").transaction_id("7472")
                .raw_sql("LOG:  statement: CREATE TABLE cars(id INT PRIMARY KEY, name TEXT, price INT)").event_sequence_number("3")
                .is_new_transaction("0").duration("8.000").total_run_time("9");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("3").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (1, 'Audi', 52642)").event_sequence_number("4")
                .is_new_transaction("0").duration("1.000").total_run_time("10");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("4").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (2, 'Mercedes', 57127)").event_sequence_number("5")
                .is_new_transaction("0").duration("0.000").total_run_time("10");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("5").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (3, 'Skoda', 9000)").event_sequence_number("6")
                .is_new_transaction("0").duration("0.000").total_run_time("10");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("6").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (4, 'Volvo', 29000)").event_sequence_number("7")
                .is_new_transaction("0").duration("1.000").total_run_time("11");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("7").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (5, 'Bentley', 350000)").event_sequence_number("8")
                .is_new_transaction("0").duration("0.000").total_run_time("11");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("8").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (6, 'Citroen', 21000)").event_sequence_number("9")
                .is_new_transaction("0").duration("0.000").total_run_time("11");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("9").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (7, 'Hummer', 41400)").event_sequence_number("10")
                .is_new_transaction("0").duration("0.000").total_run_time("11");

        builder.newRow().operation("INSERT").session_id("534cf0be.7c4").event_number_in_transaction("10").transaction_id("7472")
                .raw_sql("LOG:  statement: INSERT INTO cars (id, name, price) VALUES (8, 'Volkswagen', 21600)").event_sequence_number("11")
                .is_new_transaction("0").duration("0.000").total_run_time("11");

        builder.newRow().operation("COMMIT").session_id("534cf0be.7c4").event_number_in_transaction("11").transaction_id("7472")
                .raw_sql("LOG:  statement: COMMIT").event_sequence_number("12").is_new_transaction("0").duration("3.000")
                .total_run_time("14");

        TraceResult expected = SerializeableTraceResultBuilder.toTraceResult(builder, "expected");
        return expected;
    }


    @Test
    public void shouldInstantiateLogParser() {
        LogParser p = new LogParser();
        Assert.assertNotNull(p);
    }


    @Test
    public void shouldParseAllLines() {
        LogParser p = new LogParser();

        Assert.assertEquals(0, testLog.length % 2);

        for (String logLine : testLog) {
            p.parse(logLine);
        }

        TraceResult actual = p.CollectResult("sample");
        Assert.assertNotNull(actual);
    }


    @Test
    public void shouldParseTwoLogLinesAsOneResultRow() {
        // Prepare expected
        SerializeableTraceResultBuilder builder = new SerializeableTraceResultBuilder();
        builder.newRow().operation("BEGIN").session_id("534cf0be.7c4").event_number_in_transaction("1").transaction_id("0")
                .raw_sql("LOG:  statement: BEGIN").event_sequence_number("1").is_new_transaction("1").duration("0.000").total_run_time("0");

        TraceResult expected = SerializeableTraceResultBuilder.toTraceResult(builder, "expected");

        // Now parse two lines
        LogParser p = new LogParser();
        p.parse(testLog[0]);
        p.parse(testLog[1]);

        TraceResult actual = p.CollectResult("sample");
        actual.printResult();

        // Compare
        Assert.assertTrue(expected.isSameValuesAs(actual));
    }


    @Test
    public void shouldParseDurationToDigitsOnly() {
        LogParser p = new LogParser();
        String actual = p.parseDuration("LOG:  duration: 1.000 ms");
        Assert.assertEquals("1.000", actual);
    }


    @Test(expected = AssertionError.class)
    public void ShouldOnlyBeAbeToCollectResultFromLogFileWithAllLogLines() {
        LogParser p = new LogParser();
        p.parse(testLog[0]);
        p.CollectResult("sample");
    }


    @Test(expected = AssertionError.class)
    public void shouldNotBeAbleToParseInvalidDuration() {
        LogParser p = new LogParser();
        p.parse("[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.831 CEST][idle][1][0]LOG:  statement: BEGIN");
        p.parse("[MppPOC][1988][534cf0be.7c4][2014-04-15 10:41:34.831 CEST][BEGIN][2][0]LOG:  MISSING");
    }


    @Test(expected = AssertionError.class)
    public void shouldOnlyBeAbleToParseCorrectLogFile() {
        LogParser p = new LogParser();
        p.parse("select * from errors where description = 'this is an errornous log line'");
    }


    @Test
    public void shouldAggregateTotalRunTimePerTransaction() {

        // Prepare expected
        SerializeableTraceResultBuilder builder = new SerializeableTraceResultBuilder();
        builder.newRow().operation("BEGIN").session_id("534cf0be.7c4").event_number_in_transaction("1").transaction_id("0")
                .raw_sql("LOG:  statement: BEGIN").event_sequence_number("1").is_new_transaction("1").duration("0.000").total_run_time("0");

        builder.newRow().operation("DROP TABLE").session_id("534cf0be.7c4").event_number_in_transaction("1").transaction_id("7472")
                .raw_sql("LOG:  statement: DROP TABLE IF EXISTS cars").event_sequence_number("2").is_new_transaction("1").duration("1.000")
                .total_run_time("1");

        TraceResult expected = SerializeableTraceResultBuilder.toTraceResult(builder, "expected");

        // Now parse two lines
        LogParser p = new LogParser();
        p.parse(testLog[0]);
        p.parse(testLog[1]);
        p.parse(testLog[2]);
        p.parse(testLog[3]);

        TraceResult actual = p.CollectResult("sample");
        // actual.printResult();

        // Compare
        Assert.assertTrue(expected.isSameValuesAs(actual));

    }

    // /**
    // * Stolen with pride from: http://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances Get a
    // * diff between two dates
    // *
    // * @param date1 the oldest date
    // * @param date2 the newest date
    // * @param timeUnit the unit in which you want the diff
    // * @return the diff value, in the provided unit
    // */
    // public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
    // long diffInMillies = date2.getTime() - date1.getTime();
    // return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    // }

}
