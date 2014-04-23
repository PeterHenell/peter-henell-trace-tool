package databasetracing.queryparsers.sqlserver;

import java.util.regex.Pattern;

import databasetracing.queryparsers.QueryParser;

public class SelectQueryParser extends QueryParser {

    public SelectQueryParser() {
        super(Pattern.compile("select (distinct )?(top [0-9]+)?.+?from (.+?)'(,)?(.+)?", Pattern.CASE_INSENSITIVE));
    }


    @Override
    public String getOperation() {
        String statement = "SELECT ";

        if (matcher.find(0)) {
            String distinct = matcher.group(1);
            if (null != distinct && !distinct.isEmpty()) {
                statement += "DISTINCT ";
            }
            String top = matcher.group(2);
            if (null != top && !top.isEmpty()) {
                statement = statement + top.toUpperCase() + " ";
            }
            statement += "* FROM";
        }
        return statement;
    }


    @Override
    public String getStatementBodyPart() {
        if (matcher.find(0)) {
            return matcher.group(3).trim();
        }
        return null;
    }


    @Override
    public String getParameterStringPart() {
        if (matcher.find(0)) {
            String parameters = matcher.group(5);
            if (null != parameters) {
                return parameters.trim();
            } else {
                return "";
            }
        }
        return null;
    }


    @Override
    protected boolean isParsedAsThis() {
        return matcher.find(0);
    }

}
