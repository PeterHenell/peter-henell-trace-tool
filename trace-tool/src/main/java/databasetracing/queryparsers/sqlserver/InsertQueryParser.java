package databasetracing.queryparsers.sqlserver;

import java.util.regex.Pattern;

import databasetracing.queryparsers.QueryParser;

public class InsertQueryParser extends QueryParser {

    public InsertQueryParser() {
        super(Pattern.compile("insert into (.+\\))?[\\w\\s]+?',(.+)?", Pattern.CASE_INSENSITIVE));
    }


    @Override
    public String getOperation() {
        return "INSERT INTO";
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
    protected boolean isParsedAsThis() {
        return matcher.find(0);
    }
}
