package databasetracing.queryparsers.postgres;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogFileData {

    private final String database;
    private final String processID;
    private final String sessionId;
    private final String timestamp;
    private final String commandTag;
    private final String Numberofthelogline;
    private final String tranId;
    private final String rawSQL;

    // log_line_prefix must be: [%d][%p][%c][%m][%i][%l][%x]
    // [database][processID][sessionId][timestamp][command tag][Number of the log line][tranId]

    private static final Pattern pattern;

    static {
        String searchPattern = "^\\[(.+?)\\]\\[(.+?)\\]\\[(.+?)\\]\\[(.+?)\\]\\[(.+?)\\]\\[(.+?)\\]\\[(.+?)\\](.*)";
        pattern = Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE);
    }


    public static final LogFileData get(final String logLine) throws UnmatchableException {
        // [database][processID][sessionId][timestamp][command tag][Number of the log line][tranId]
        Matcher m = getMatcherWithFind(logLine);
        return new LogFileData(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8));
    }


    private LogFileData(final String database, final String processID, final String sessionId, final String timestamp,
            final String commandTag, final String Numberofthelogline, final String tranId, final String rawSQL) {

        this.database = database;
        this.processID = processID;
        this.sessionId = sessionId;
        this.timestamp = timestamp;
        this.commandTag = commandTag;
        this.Numberofthelogline = Numberofthelogline;
        this.tranId = tranId;
        this.rawSQL = rawSQL;
    }


    private static Matcher getMatcherWithFind(String input) throws UnmatchableException {
        Matcher matcher = pattern.matcher(input);

        // parse it!
        if (!matcher.find()) {
            throw new UnmatchableException(String.format("Not matchable [%s]", input));
        }
        return matcher;
    }


    public String getDatabase() {
        return database;
    }


    public String getProcessID() {
        return processID;
    }


    public String getSessionId() {
        return sessionId;
    }


    public String getTimestamp() {
        return timestamp;
    }


    public String getCommandTag() {
        return commandTag;
    }


    public String getNumberofthelogline() {
        return Numberofthelogline;
    }


    public String getTranId() {
        return tranId;
    }


    public String getRawSQL() {
        return rawSQL;
    }

}
