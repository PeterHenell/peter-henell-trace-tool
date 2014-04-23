package databasetracing.tracing;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockResultSet implements ResultSet {

    private Map<String, List<String>> columnMap;
    private List<String> columnNameList;
    private int cursor = -1;
    private MockResultSetMetaData resultSetMetaData;
    private int rowCount = 0;


    public MockResultSet(List<String> columns, List<String[]> rows) {
        columnMap = new HashMap<String, List<String>>();
        columnNameList = new ArrayList<String>();

        columnNameList.addAll(columns);
        rowCount = rows.size();

        for (int columnCounter = 0; columnCounter < columnNameList.size(); columnCounter++) {
            String columnName = columnNameList.get(columnCounter);

            List<String> rowValues = new ArrayList<String>();
            for (int rowCounter = 0; rowCounter < rows.size(); rowCounter++) {
                rowValues.add(rows.get(rowCounter)[columnCounter]);
            }

            columnMap.put(columnName, rowValues);
        }
    }


    private int getColumnCount() {
        return columnMap.size();
    }


    public int getRowCount() {
        return rowCount;
    }


    public List<String> getColumn(int number) {
        if (number > getColumnCount())
            return null;
        if (number < 1)
            return null;

        int index = number - 1;
        String columnName = (String) columnNameList.get(index);
        return getColumn(columnName);
    }


    private List<String> getColumn(String name) {
        List<String> list = new ArrayList<String>();
        List<String> columnList = columnMap.get(name);

        if (null == columnList)
            throw new RuntimeException("Column not found");

        list.addAll(columnList);
        return list;
    }


    public ResultSetMetaData getMetaData() throws SQLException {
        if (null != resultSetMetaData)
            return resultSetMetaData;

        resultSetMetaData = new MockResultSetMetaData();
        resultSetMetaData.setColumnCount(getColumnCount());
        for (int ii = 0; ii < columnNameList.size(); ii++) {
            resultSetMetaData.setColumnName(ii + 1, columnNameList.get(ii));
        }
        return resultSetMetaData;
    }


    public boolean next() throws SQLException {
        if (getRowCount() == 0)
            return false;
        cursor++;
        return isCurrentRowValid();
    }


    private boolean isCurrentRowValid() {
        return cursor <= getRowCount() - 1;
    }


    public int findColumn(String columnName) throws SQLException {
        for (int ii = 0; ii < columnNameList.size(); ii++) {
            if (columnName.equals(columnNameList.get(ii))) {
                System.out.println("Found column [" + columnName + "] at: " + (ii + 1));
                return ii + 1;
            }
        }
        System.err.println("Could not find column");
        throw new SQLException("No column with name " + columnName + " found");
    }


    public String getString(int columnIndex) throws SQLException {
        return getColumn(columnIndex).get(cursor);
    }


    public int getInt(int columnIndex) throws SQLException {
        return Integer.valueOf(getColumn(columnIndex).get(cursor)).intValue();
    }


    public String getString(String columnLabel) throws SQLException {
        return getColumn(findColumn(columnLabel)).get(cursor);
    }


    public Object getObject(int columnIndex) throws SQLException {
        return getColumn(columnIndex).get(cursor);
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public boolean absolute(int row) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void cancelRowUpdates() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void clearWarnings() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void deleteRow() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Array getArray(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Array getArray(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Blob getBlob(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Blob getBlob(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public boolean getBoolean(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public byte getByte(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public byte getByte(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public byte[] getBytes(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public byte[] getBytes(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Reader getCharacterStream(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Reader getCharacterStream(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Clob getClob(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Clob getClob(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public int getConcurrency() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public String getCursorName() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Date getDate(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public double getDouble(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public double getDouble(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public int getFetchDirection() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public int getFetchSize() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public float getFloat(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public float getFloat(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public int getHoldability() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public int getInt(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public long getLong(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public long getLong(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public NClob getNClob(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public NClob getNClob(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public String getNString(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public String getNString(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Object getObject(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Ref getRef(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Ref getRef(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public RowId getRowId(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public RowId getRowId(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public short getShort(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public short getShort(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Time getTime(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Time getTime(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public int getType() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public URL getURL(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public URL getURL(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public SQLWarning getWarnings() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void insertRow() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void moveToCurrentRow() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void moveToInsertRow() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void refreshRow() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public boolean relative(int rows) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public boolean rowDeleted() throws SQLException {
        throw new NotImplementedSQLException();
    }


    public boolean rowInserted() throws SQLException {
        throw new NotImplementedSQLException();
    }


    public boolean rowUpdated() throws SQLException {
        throw new NotImplementedSQLException();
    }


    public void setFetchDirection(int direction) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void setFetchSize(int rows) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateArray(String columnLabel, Array x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateByte(String columnLabel, byte x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateClob(String columnLabel, Clob x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateDate(String columnLabel, Date x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateDouble(String columnLabel, double x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateFloat(String columnLabel, float x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateInt(String columnLabel, int x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateLong(String columnLabel, long x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNString(int columnIndex, String nString) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNString(String columnLabel, String nString) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNull(int columnIndex) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateNull(String columnLabel) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateObject(String columnLabel, Object x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateRef(String columnLabel, Ref x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateRow() throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateShort(String columnLabel, short x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateString(int columnIndex, String x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateString(String columnLabel, String x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateTime(String columnLabel, Time x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        throw new NotImplementedSQLException();

    }


    public boolean wasNull() throws SQLException {
        throw new NotImplementedSQLException();
    }


    public void afterLast() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public void beforeFirst() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public void close() throws SQLException {

    }


    public boolean first() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public boolean getBoolean(int columnIndex) throws SQLException {

        throw new NotImplementedSQLException();
    }


    public Date getDate(int columnIndex) throws SQLException {

        throw new NotImplementedSQLException();
    }


    public int getRow() throws SQLException {
        throw new NotImplementedSQLException();
    }


    public Statement getStatement() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public boolean isAfterLast() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public boolean isBeforeFirst() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public boolean isClosed() throws SQLException {

        return false;
    }


    public boolean isFirst() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public boolean isLast() throws SQLException {

        return false;
    }


    public boolean last() throws SQLException {

        throw new NotImplementedSQLException();
    }


    public boolean previous() throws SQLException {

        throw new NotImplementedSQLException();
    }
}
