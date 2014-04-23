package databasetracing.tracing;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.tracing.TraceResult;

public class MockResultSetTest {

    TraceResult traceResult = TraceResultExamples.getExampleTraceResult("Trace Result Save All Test");


    @Test
    public void shouldCreateMockedResultSet() throws SQLException {

        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());
        rs.next();
        rs.close();
    }


    @Test
    public void shouldHaveRowCountSameAsSource() throws SQLException {
        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());
        Assert.assertEquals(traceResult.getResult().size(), rs.getRowCount());
        rs.close();
    }


    @Test
    public void shouldFindColumn() throws SQLException {
        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());
        Assert.assertEquals(1, rs.findColumn("Raw Sql text"));
        Assert.assertEquals(2, rs.findColumn("Operation"));
        rs.next();
        rs.close();

    }


    @Test
    public void shouldGetDataFromResultSetByName() throws SQLException {
        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());

        for (int rowCount = 0; rowCount < traceResult.getResult().size(); rowCount++) {
            rs.next();
            Assert.assertEquals(traceResult.getResult().get(rowCount)[0], rs.getString("Raw Sql text"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[1], rs.getString("Operation"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[2], rs.getString("New Transaction?"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[3], rs.getString("Event# in Tran"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[4], rs.getString("Main Table"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[5], rs.getString("Total Run Time (Ms)"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[6], rs.getString("With (Option)"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[7], rs.getString("Event Sequence"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[8], rs.getString("Transaction Id"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[9], rs.getString("Round Trip Time(Ms)"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[10], rs.getString("Duration"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[11], rs.getString("Session Id"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[12], rs.getString("Joining Tables"));
            Assert.assertEquals(traceResult.getResult().get(rowCount)[13], rs.getString("Query Parameters"));
        }
        rs.close();
    }


    @Test
    public void shouldGetDataFromResultSetByIndex() throws SQLException {
        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());

        for (int rowCount = 0; rowCount < traceResult.getResult().size(); rowCount++) {
            rs.next();
            for (int colCount = 0; colCount < traceResult.getColumnNames().size(); colCount++) {
                Assert.assertEquals(traceResult.getResult().get(rowCount)[colCount], rs.getString(colCount + 1));
            }
        }
        rs.close();
    }


    @Test
    public void shouldIterateThroughAllRows() throws SQLException {
        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());

        while (rs.next()) {
            Assert.assertNotNull(rs.getString("Raw Sql text"));
        }
        rs.close();
    }


    @Test
    public void shouldHaveSameColumnsAsInputed() throws SQLException {
        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());

        ResultSetMetaData meta = rs.getMetaData();
        Assert.assertEquals(traceResult.getColumnNames().size(), meta.getColumnCount());

        for (int i = 0; i < traceResult.getColumnNames().size(); i++) {
            int columnIndex = i + 1;

            System.out.println("Colname: " + i + "=" + meta.getColumnName(columnIndex));
            Assert.assertNotNull(meta.getColumnName(columnIndex));
            Assert.assertFalse(meta.getColumnName(columnIndex).isEmpty());
            Assert.assertEquals(traceResult.getColumnNames().get(i), meta.getColumnName(columnIndex));
        }

        rs.close();
    }


    @Test
    public void shouldHaveSameNumberOfColumnsAsSource() throws SQLException {
        MockResultSet rs = new MockResultSet(traceResult.getColumnNames(), traceResult.getResult());
        ResultSetMetaData meta = rs.getMetaData();
        Assert.assertEquals(traceResult.getColumnNames().size(), meta.getColumnCount());
        rs.close();
    }

}
