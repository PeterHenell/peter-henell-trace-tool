package databasetracing.queryparsers.sqlserver;

import java.util.regex.Pattern;

import databasetracing.queryparsers.QueryParser;

public class ExecQueryParser extends QueryParser {

    public ExecQueryParser() {
        super(Pattern.compile("N'EXEC (.+?)',(.+)", Pattern.CASE_INSENSITIVE));
    }


    @Override
    public String getOperation() {
        return "EXEC";
    }


    @Override
    public String getStatementBodyPart() {
        if (matcher.find(0)) {
            return matcher.group(1).trim();
        }
        return null;
    }


    @Override
    public String getParameterStringPart() {
        if (matcher.find(0)) {
            String parameters = matcher.group(2);
            if (null != parameters && !parameters.isEmpty()) {
                return parameters.trim();
            }
            return "";
        }
        return null;
    }


    @Override
    protected boolean isParsedAsThis() {
        return matcher.find(0);
    }

}
