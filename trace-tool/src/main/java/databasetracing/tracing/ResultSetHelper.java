package databasetracing.tracing;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databasetracing.tracing.dto.TraceResultData;

public class ResultSetHelper {
    /**
     * extract the column names from the result set
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private static String[] getColumnNames(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> columns = new ArrayList<String>();
            for (int i = 1; i < columnCount + 1; i++) {

                columns.add(rsmd.getColumnName(i));
            }
            return columns.toArray(new String[columns.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static List<TraceResultData> getResultValues(ResultSet rs) {
        List<TraceResultData> result = new ArrayList<>();

        try {

            String[] columnNames = getColumnNames(rs);
            int columnCount = columnNames.length;

            while (rs.next()) {
                String[] row = new String[columnCount];

                // for each column in the row, assign the value to the array, use empty string if value is null
                for (int i = 1; i < columnCount + 1; i++) {
                    if (rs.getObject(i) != null) {
                        row[i - 1] = rs.getString(i);
                    } else {
                        row[i - 1] = "";
                    }
                }
                TraceResultData data = TraceResultData.fromArray(row, columnNames);
                result.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }

}
