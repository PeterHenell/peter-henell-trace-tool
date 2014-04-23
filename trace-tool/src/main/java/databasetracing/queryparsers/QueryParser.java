package databasetracing.queryparsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class QueryParser {

    protected Matcher matcher;
    protected final Pattern pattern;


    public QueryParser(Pattern pattern) {
        this.pattern = pattern;
    }


    public boolean prepare(String sqlText) {
        sqlText = cleanInputString(sqlText);
        matcher = pattern.matcher(sqlText);
        return isParsedAsThis();
    }


    protected String cleanInputString(String input) {
        return input.replaceAll("[^N]''", "'");
    }


    public abstract String getOperation();


    public abstract String getStatementBodyPart();


    public abstract String getParameterStringPart();


    protected abstract boolean isParsedAsThis();

}
