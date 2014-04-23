package databasetracing.queryparsers.sqlserver;

import java.util.regex.Pattern;

import databasetracing.queryparsers.QueryParser;

public class UpdateQueryParser extends QueryParser {

    public UpdateQueryParser() {
        super(Pattern.compile("N'update (.+) ',(.+)", Pattern.CASE_INSENSITIVE));
    }


    @Override
    public String getOperation() {
        return "UPDATE";
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
            return matcher.group(2).trim();
        }
        return null;
    }


    @Override
    public boolean isParsedAsThis() {
        return matcher.find(0);
    }

}
