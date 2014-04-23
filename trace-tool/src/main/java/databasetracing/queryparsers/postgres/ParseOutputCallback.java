package databasetracing.queryparsers.postgres;

public interface ParseOutputCallback {

    void callback(String parsedString);

}
