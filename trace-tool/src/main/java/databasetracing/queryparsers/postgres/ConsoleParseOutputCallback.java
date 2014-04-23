package databasetracing.queryparsers.postgres;

public class ConsoleParseOutputCallback implements ParseOutputCallback {

    @Override
    public void callback(String parsedString) {
        System.out.println(parsedString);
    }

}
