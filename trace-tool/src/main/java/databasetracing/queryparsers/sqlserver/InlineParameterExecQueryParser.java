package databasetracing.queryparsers.sqlserver;

import java.util.regex.Pattern;

import databasetracing.queryparsers.QueryParser;

public class InlineParameterExecQueryParser extends QueryParser {

    public InlineParameterExecQueryParser() {
        super(Pattern.compile("['| ]EXEC ([\\w]+) (.+)'", Pattern.CASE_INSENSITIVE));

    }


    @Override
    public String getOperation() {
        return "EXEC";
    }


    @Override
    public String getStatementBodyPart() {
        if (matcher.find(0)) {
            return matcher.group(1) + " " + matcher.group(2).trim();
        }
        return null;
    }


    @Override
    public String getParameterStringPart() {
        return "";
    }


    @Override
    protected boolean isParsedAsThis() {
        return matcher.find(0);
    }

}
