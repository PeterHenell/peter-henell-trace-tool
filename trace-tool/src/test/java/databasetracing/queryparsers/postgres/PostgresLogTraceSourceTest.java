package databasetracing.queryparsers.postgres;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import utils.TempFileManager;
import databasetracing.tracing.TraceManager;
import databasetracing.tracing.TraceResult;
import databasetracing.tracing.sources.PostgresLogTraceSource;

public class PostgresLogTraceSourceTest {

    @Test
    public void shouldInstantiateSource() {
        PostgresLogTraceSource source = new PostgresLogTraceSource(Paths.get(""), "");
        Assert.assertNotNull(source);
    }


    @Test(expected = AssertionError.class)
    public void shouldNotAllowCreatingASourceWithInvalidFileName() {
        PostgresLogTraceSource source = new PostgresLogTraceSource(Paths.get("invalid file name"), "");
        Assert.assertNotNull(source);
        source.close();
    }


    @Test(expected = AssertionError.class)
    public void shouldNotAllowNullPath() {
        PostgresLogTraceSource source = new PostgresLogTraceSource(null, "");
        Assert.assertNotNull(source);
        source.close();
    }


    @Test
    public void shouldTraceFileWithTraceManager() {

        TraceResult expected = LogParserTest.getFullExpectedResult();

        Path path = TempFileManager.getTemporaryFile("test-temp");

        PostgresLogTraceSource source = new PostgresLogTraceSource(path, "");
        try (TraceManager manager = new TraceManager(source, "c:\\temp\\traces\\")) {

            manager.startTrace();
            TempFileManager.writeLogLines(path, LogParserTest.testLog);

            manager.stopTrace();
            TraceResult actual = manager.collectTraceResult("Sample test");

            Assert.assertEquals(expected.getResult().size(), manager.getEventCount());

            Assert.assertNotNull(actual);
            Assert.assertTrue(actual.getResult().size() > 5);

            Assert.assertTrue(expected.isSameValuesAs(actual));

            manager.saveAllResults();

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void shouldAcceptAnExistingFile() {

        Path path = TempFileManager.getTemporaryFile("test-temp");

        PostgresLogTraceSource source = new PostgresLogTraceSource(path, "");
        Assert.assertNotNull(source);
        source.close();
    }

}
