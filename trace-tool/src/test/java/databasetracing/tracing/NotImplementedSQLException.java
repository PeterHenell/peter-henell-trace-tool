package databasetracing.tracing;

import java.sql.SQLException;

public class NotImplementedSQLException extends SQLException {

    private static final long serialVersionUID = -7170120464129929471L;


    public NotImplementedSQLException() {
    }


    public NotImplementedSQLException(String arg0) {
        super(arg0);
    }


    public NotImplementedSQLException(Throwable arg0) {
        super(arg0);
    }


    public NotImplementedSQLException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
