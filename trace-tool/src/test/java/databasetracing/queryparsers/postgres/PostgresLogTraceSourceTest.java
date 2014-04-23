package databasetracing.queryparsers.postgres;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

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

        Path path = getTemporaryFile("test-temp");

        PostgresLogTraceSource source = new PostgresLogTraceSource(path, "");
        try (TraceManager manager = new TraceManager(source, "c:\\temp\\traces\\")) {

            manager.startTrace();
            writeLogLines(path);
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


    private Path getTemporaryFile(String filename) {
        Path path = null;
        try {
            path = Files.createTempFile(filename, ".tmp");
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        return path;
    }


    /**
     * Write test data to the specified file.
     * 
     * @param path
     */
    private void writeLogLines(Path path) {
        // Writing to file4
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {

            for (String logLine : LogParserTest.testLog) {
                writer.append(logLine);
                writer.newLine();
            }

            writer.flush();
        } catch (IOException exception) {
            System.out.println("Error writing to file");
        }
    }


    @Test
    public void shouldAcceptAnExistingFile() {

        Path path = getTemporaryFile("test-temp");

        PostgresLogTraceSource source = new PostgresLogTraceSource(path, "");
        Assert.assertNotNull(source);
        source.close();
    }

}
