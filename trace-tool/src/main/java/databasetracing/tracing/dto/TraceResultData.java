package databasetracing.tracing.dto;

import databasetracing.tracing.TraceResult;

/**
 * Contain all the columns values in a TraceResult
 * 
 * @author peter.henell
 * 
 */
public class TraceResultData {

    private String operation;
    private String main_table;
    private String query_option;
    private String total_run_time;
    private String joining_tables;
    private String query_parameters;
    private String round_trip_time;
    private String session_id;
    private String transaction_id;
    private String is_new_transaction;
    private String event_number_in_transaction;
    private String event_sequence_number;
    private String duration;
    private String raw_sql;


    public TraceResultData() {
        this.operation = "";
        this.main_table = "";
        this.query_option = "";
        this.joining_tables = "";
        this.query_parameters = "";
        this.total_run_time = "";
        this.round_trip_time = "";
        this.session_id = "";
        this.transaction_id = "";
        this.is_new_transaction = "";
        this.event_number_in_transaction = "";
        this.event_sequence_number = "";
        this.duration = "";
        this.raw_sql = "";
    }


    public String[] toStringArray() {
        return new String[]{ operation, main_table, query_option, joining_tables, query_parameters, total_run_time, round_trip_time,
                session_id, transaction_id, is_new_transaction, event_number_in_transaction, event_sequence_number, duration, raw_sql };
    }


    public String getOperation() {
        return operation;
    }


    public void setOperation(String operation) {
        this.operation = operation;
    }


    public String getMain_table() {
        return main_table;
    }


    public void setMain_table(String main_table) {
        this.main_table = main_table;
    }


    public String getQuery_option() {
        return query_option;
    }


    public void setQuery_option(String query_option) {
        this.query_option = query_option;
    }


    public String getTotal_run_time() {
        return total_run_time;
    }


    public void setTotal_run_time(String total_run_time) {
        this.total_run_time = total_run_time;
    }


    public String getJoining_tables() {
        return joining_tables;
    }


    public void setJoining_tables(String joining_tables) {
        this.joining_tables = joining_tables;
    }


    public String getQuery_parameters() {
        return query_parameters;
    }


    public void setQuery_parameters(String query_parameters) {
        this.query_parameters = query_parameters;
    }


    public String getRound_trip_time() {
        return round_trip_time;
    }


    public void setRound_trip_time(String round_trip_time) {
        this.round_trip_time = round_trip_time;
    }


    public String getSession_id() {
        return session_id;
    }


    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }


    public String getTransaction_id() {
        return transaction_id;
    }


    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }


    public String getIs_new_transaction() {
        return is_new_transaction;
    }


    public void setIs_new_transaction(String is_new_transaction) {
        this.is_new_transaction = is_new_transaction;
    }


    public String getEvent_number_in_transaction() {
        return event_number_in_transaction;
    }


    public void setEvent_number_in_transaction(String event_number_in_transaction) {
        this.event_number_in_transaction = event_number_in_transaction;
    }


    public String getEvent_sequence_number() {
        return event_sequence_number;
    }


    public void setEvent_sequence_number(String event_sequence_number) {
        this.event_sequence_number = event_sequence_number;
    }


    public String getDuration() {
        return duration;
    }


    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getRaw_sql() {
        return raw_sql;
    }


    public void setRaw_sql(String raw_sql) {
        this.raw_sql = raw_sql;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((event_number_in_transaction == null) ? 0 : event_number_in_transaction.hashCode());
        result = prime * result + ((event_sequence_number == null) ? 0 : event_sequence_number.hashCode());
        result = prime * result + ((is_new_transaction == null) ? 0 : is_new_transaction.hashCode());
        result = prime * result + ((joining_tables == null) ? 0 : joining_tables.hashCode());
        result = prime * result + ((main_table == null) ? 0 : main_table.hashCode());
        result = prime * result + ((operation == null) ? 0 : operation.hashCode());
        result = prime * result + ((query_option == null) ? 0 : query_option.hashCode());
        result = prime * result + ((query_parameters == null) ? 0 : query_parameters.hashCode());
        result = prime * result + ((raw_sql == null) ? 0 : raw_sql.hashCode());
        result = prime * result + ((round_trip_time == null) ? 0 : round_trip_time.hashCode());
        result = prime * result + ((session_id == null) ? 0 : session_id.hashCode());
        result = prime * result + ((total_run_time == null) ? 0 : total_run_time.hashCode());
        result = prime * result + ((transaction_id == null) ? 0 : transaction_id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TraceResultData other = (TraceResultData) obj;
        if (duration == null) {
            if (other.duration != null) {
                return false;
            }
        } else if (!duration.equals(other.duration)) {
            return false;
        }
        if (event_number_in_transaction == null) {
            if (other.event_number_in_transaction != null) {
                return false;
            }
        } else if (!event_number_in_transaction.equals(other.event_number_in_transaction)) {
            return false;
        }
        if (event_sequence_number == null) {
            if (other.event_sequence_number != null) {
                return false;
            }
        } else if (!event_sequence_number.equals(other.event_sequence_number)) {
            return false;
        }
        if (is_new_transaction == null) {
            if (other.is_new_transaction != null) {
                return false;
            }
        } else if (!is_new_transaction.equals(other.is_new_transaction)) {
            return false;
        }
        if (joining_tables == null) {
            if (other.joining_tables != null) {
                return false;
            }
        } else if (!joining_tables.equals(other.joining_tables)) {
            return false;
        }
        if (main_table == null) {
            if (other.main_table != null) {
                return false;
            }
        } else if (!main_table.equals(other.main_table)) {
            return false;
        }
        if (operation == null) {
            if (other.operation != null) {
                return false;
            }
        } else if (!operation.equals(other.operation)) {
            return false;
        }
        if (query_option == null) {
            if (other.query_option != null) {
                return false;
            }
        } else if (!query_option.equals(other.query_option)) {
            return false;
        }
        if (query_parameters == null) {
            if (other.query_parameters != null) {
                return false;
            }
        } else if (!query_parameters.equals(other.query_parameters)) {
            return false;
        }
        if (raw_sql == null) {
            if (other.raw_sql != null) {
                return false;
            }
        } else if (!raw_sql.equals(other.raw_sql)) {
            return false;
        }
        if (round_trip_time == null) {
            if (other.round_trip_time != null) {
                return false;
            }
        } else if (!round_trip_time.equals(other.round_trip_time)) {
            return false;
        }
        if (session_id == null) {
            if (other.session_id != null) {
                return false;
            }
        } else if (!session_id.equals(other.session_id)) {
            return false;
        }
        if (total_run_time == null) {
            if (other.total_run_time != null) {
                return false;
            }
        } else if (!total_run_time.equals(other.total_run_time)) {
            return false;
        }
        if (transaction_id == null) {
            if (other.transaction_id != null) {
                return false;
            }
        } else if (!transaction_id.equals(other.transaction_id)) {
            return false;
        }
        return true;
    }


    public static TraceResultData fromArray(String[] source, String[] columnNames) {
        TraceResultData data = new TraceResultData();

        for (int i = 0; i < columnNames.length; i++) {
            String colName = columnNames[i];
            String value = source[i];

            switch (colName) {
            case TraceResult.DURATION_POSITION:
                data.duration = value;
                break;
            case TraceResult.EVENT_NUMBER_IN_TRANSACTION_POSITION:
                data.event_number_in_transaction = value;
                break;
            case TraceResult.EVENT_SEQUENCE_POSITION:
                data.event_sequence_number = value;
                break;
            case TraceResult.JOINING_TABLES_POSITION:
                data.joining_tables = value;
                break;
            case TraceResult.MAIN_TABLE_POSITION:
                data.main_table = value;
                break;
            case TraceResult.NEW_TRANSACTION__POSITION:
                data.is_new_transaction = value;
                break;
            case TraceResult.OPERATION_POSITION:
                data.operation = value;
                break;
            case TraceResult.QUERY_OPTION_POSITION:
                data.query_option = value;
                break;
            case TraceResult.QUERY_PARAMETER_POSITION:
                data.query_parameters = value;
                break;
            case TraceResult.RAW_SQL_TEXT_POSITION:
                data.raw_sql = value;
                break;
            case TraceResult.ROUND_TRIP_POSITION:
                data.round_trip_time = value;
                break;
            case TraceResult.SESSION_ID_POSITION:
                data.session_id = value;
                break;
            case TraceResult.TOTAL_RUN_TIME_POSITION:
                data.total_run_time = value;
                break;

            case TraceResult.TRANSACTOIN_ID_POSITION:
                data.transaction_id = value;
                break;

            default:
                break;
            }
        }

        return data;
    }


    @Override
    public String toString() {
        return String
                .format("TraceResultData [operation=%s, main_table=%s, query_option=%s, total_run_time=%s, joining_tables=%s, query_parameters=%s, round_trip_time=%s, session_id=%s, transaction_id=%s, is_new_transaction=%s, event_number_in_transaction=%s, event_sequence_number=%s, duration=%s, raw_sql=%s]",
                        operation, main_table, query_option, total_run_time, joining_tables, query_parameters, round_trip_time, session_id,
                        transaction_id, is_new_transaction, event_number_in_transaction, event_sequence_number, duration, raw_sql);
    }

}
