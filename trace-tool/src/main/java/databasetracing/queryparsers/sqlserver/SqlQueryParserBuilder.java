package databasetracing.queryparsers.sqlserver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import databasetracing.queryparsers.QueryParser;

/**
 * Will parse any query using specific parsers
 * 
 * @author peter.henell
 * 
 */
public class SqlQueryParserBuilder {

    private static final UpdateQueryParser updateParser;
    private static final InsertQueryParser insertParser;
    private static final ExecQueryParser execParser;
    private static final DeleteQueryParser deleteParser;
    private static final SelectQueryParser selectParser;
    private static final InlineParameterExecQueryParser inlineParamExecParser;

    static {
        updateParser = new UpdateQueryParser();
        insertParser = new InsertQueryParser();
        execParser = new ExecQueryParser();
        deleteParser = new DeleteQueryParser();
        selectParser = new SelectQueryParser();
        inlineParamExecParser = new InlineParameterExecQueryParser();
    }


    /**
     * parses sql query if it can, return null if it could not
     * 
     * @param sqlText
     * @return the parsed query, or null if it could not parse it
     */
    public static String parseQuery(String sqlText) {

        String result = null;
        try {

            if (selectParser.prepare(sqlText)) {
                result = buildQuery(selectParser);
            } else if (updateParser.prepare(sqlText)) {
                result = buildQuery(updateParser);
            } else if (insertParser.prepare(sqlText)) {
                result = buildQuery(insertParser);
            } else if (execParser.prepare(sqlText)) {
                result = buildQuery(execParser);
            } else if (inlineParamExecParser.prepare(sqlText)) {
                result = buildQuery(inlineParamExecParser);
            } else if (deleteParser.prepare(sqlText)) {
                result = buildQuery(deleteParser);
            } else if (sqlText.contains("SELECT GETDATE()")) {
                result = "SELECT GETDATE()";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception while parsing: \n" + sqlText);
        }
        return result;
    }


    private static String buildQuery(QueryParser parser) {

        String result = "";
        result += parser.getOperation() + " ";
        result += parser.getStatementBodyPart();
        String parameters = parser.getParameterStringPart();
        List<String> parameterList = getParameterList(parameters);
        result = replaceParametersWithValues(result, parameterList);
        return result;
    }


    public static String replaceParametersWithValues(String statementBody, List<String> parameterValueList) {
        for (int i = 0; i < parameterValueList.size(); i++) {
            String parameterValue = parameterValueList.get(i);
            String parameterName = "@P" + i;
            statementBody = statementBody.replaceFirst(parameterName, Matcher.quoteReplacement(parameterValue));
        }
        return statementBody;
    }


    public static List<String> getParameterList(String parameters) {
        List<String> parsedParameters = new ArrayList<>();

        String[] paramArray = parameters.split(",");
        for (int i = 0; i < paramArray.length; i++) {
            String param = paramArray[i];

            if (param.startsWith("N'") && !param.endsWith("'")) {
                String concatenatedParam = param;
                // Look forward in the array until hit by ending fnutt
                while (!paramArray[i].endsWith("'")) {
                    i++;
                    if (i < paramArray.length) {
                        concatenatedParam = concatenatedParam + "," + paramArray[i];
                    } else {
                        break;
                    }
                }
                param = concatenatedParam;
            }
            parsedParameters.add(param);
        }

        return parsedParameters;
    }
}
