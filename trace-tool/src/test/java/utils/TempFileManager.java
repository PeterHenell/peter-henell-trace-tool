package utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;

/**
 * Helper class to create and write to temporary files
 * 
 * @author peter.henell
 * 
 */
public class TempFileManager {

    public static Path getTemporaryFile(String filename) {
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
    public static void writeLogLines(Path path, String[] lines) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {

            for (String logLine : lines) {
                writer.append(logLine);
                writer.newLine();
            }

            writer.flush();
        } catch (IOException exception) {
            System.out.println("Error writing to file");
        }
    }

}
