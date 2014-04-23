package databasetracing.tracing;

public class SerializeableTraceResultBuilder {

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

    private SerializeableTraceResult result = new SerializeableTraceResult();

    private boolean initialized = false;


    public SerializeableTraceResultBuilder operation(String operation) {
        validate();
        this.operation = operation;
        return this;
    }


    private void validate() {
        if (!initialized) {
            throw new AssertionError("newRow() before adding any data");
        }
    }


    public SerializeableTraceResultBuilder main_table(String main_table) {
        validate();
        this.main_table = main_table;
        return this;
    }


    public SerializeableTraceResultBuilder query_option(String query_option) {
        validate();
        this.query_option = query_option;
        return this;
    }


    public SerializeableTraceResultBuilder total_run_time(String total_run_time) {
        validate();
        this.total_run_time = total_run_time;
        return this;
    }


    public SerializeableTraceResultBuilder joining_tables(String joining_tables) {
        validate();
        this.joining_tables = joining_tables;
        return this;
    }


    public SerializeableTraceResultBuilder query_parameters(String query_parameters) {
        validate();
        this.query_parameters = query_parameters;
        return this;
    }


    public SerializeableTraceResultBuilder round_trip_time(String round_trip_time) {
        validate();
        this.round_trip_time = round_trip_time;
        return this;
    }


    public SerializeableTraceResultBuilder session_id(String session_id) {
        validate();
        this.session_id = session_id;
        return this;
    }


    public SerializeableTraceResultBuilder transaction_id(String transaction_id) {
        validate();
        this.transaction_id = transaction_id;
        return this;
    }


    public SerializeableTraceResultBuilder is_new_transaction(String is_new_transaction) {
        validate();
        this.is_new_transaction = is_new_transaction;
        return this;
    }


    public SerializeableTraceResultBuilder event_number_in_transaction(String event_number_in_transaction) {
        validate();
        this.event_number_in_transaction = event_number_in_transaction;
        return this;
    }


    public SerializeableTraceResultBuilder event_sequence_number(String event_sequence_number) {
        validate();
        this.event_sequence_number = event_sequence_number;
        return this;
    }


    public SerializeableTraceResultBuilder duration(String duration) {
        validate();
        this.duration = duration;
        return this;
    }


    public SerializeableTraceResultBuilder raw_sql(String raw_sql) {
        validate();
        this.raw_sql = raw_sql;
        return this;
    }


    public SerializeableTraceResultBuilder() {
        initRow();
        setResultColumns();
    }


    private void setResultColumns() {
        result.columnNames.add(TraceResult.OPERATION_POSITION);
        result.columnNames.add(TraceResult.MAIN_TABLE_POSITION);
        result.columnNames.add(TraceResult.QUERY_OPTION_POSITION);
        result.columnNames.add(TraceResult.JOINING_TABLES_POSITION);
        result.columnNames.add(TraceResult.QUERY_PARAMETER_POSITION);
        result.columnNames.add(TraceResult.TOTAL_RUN_TIME_POSITION);
        result.columnNames.add(TraceResult.ROUND_TRIP_POSITION);
        result.columnNames.add(TraceResult.SESSION_ID_POSITION);
        result.columnNames.add(TraceResult.TRANSACTOIN_ID_POSITION);
        result.columnNames.add(TraceResult.NEW_TRANSACTION__POSITION);
        result.columnNames.add(TraceResult.EVENT_NUMBER_IN_TRANSACTION_POSITION);
        result.columnNames.add(TraceResult.EVENT_SEQUENCE_POSITION);
        result.columnNames.add(TraceResult.DURATION_POSITION);
        result.columnNames.add(TraceResult.RAW_SQL_TEXT_POSITION);
    }


    private void initRow() {
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


    public SerializeableTraceResult build() {
        putCurrentRow();
        return result;
    }


    public static TraceResult toTraceResult(SerializeableTraceResultBuilder builder, String testName) {
        SerializeableTraceResult sResult = builder.build();
        return TraceResult.fromList(sResult.result, sResult.columnNames, testName);
    }


    private void putCurrentRow() {
        result.result.add(new String[]{ operation, main_table, query_option, joining_tables, query_parameters, total_run_time,
                round_trip_time, session_id, transaction_id, is_new_transaction, event_number_in_transaction, event_sequence_number,
                duration, raw_sql });
    }


    public SerializeableTraceResultBuilder newRow() {
        // Dont put when the builder is not initialized. (First time newRow() is called)
        if (initialized) {
            putCurrentRow();
        }
        this.initialized = true;
        return this;
    }

}
