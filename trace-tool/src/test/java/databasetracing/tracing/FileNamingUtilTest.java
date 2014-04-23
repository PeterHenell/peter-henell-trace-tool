package databasetracing.tracing;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class FileNamingUtilTest {

    @Test
    public void shouldGetFileName() {
        // String result = FileNamingUtil.getDatePrefixedFileName("peter.java");
        Path p = Paths.get("").resolve("2013_10_08").resolve("peter.xml").toAbsolutePath();
        System.out.println(p);

    }

}
