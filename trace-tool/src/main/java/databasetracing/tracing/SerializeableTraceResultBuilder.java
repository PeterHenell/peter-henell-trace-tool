//package databasetracing.tracing;
//
//import databasetracing.tracing.dto.TraceResultData;
//
//public class SerializeableTraceResultBuilder {
//
//    private TraceResultData data;
//
//    private SerializeableTraceResult result = new SerializeableTraceResult();
//
//    private boolean initialized = false;
//
//
//    public SerializeableTraceResultBuilder operation(String operation) {
//        validate();
//        this.data.setOperation(operation);
//        return this;
//    }
//
//
//    private void validate() {
//        if (!initialized) {
//            throw new AssertionError("newRow() before adding any data");
//        }
//    }
//
//
//    public SerializeableTraceResultBuilder main_table(String main_table) {
//        validate();
//        this.data.setMain_table(main_table);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder query_option(String query_option) {
//        validate();
//        this.data.setQuery_option(query_option);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder total_run_time(String total_run_time) {
//        validate();
//        this.data.setTotal_run_time(total_run_time);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder joining_tables(String joining_tables) {
//        validate();
//        this.data.setJoining_tables(joining_tables);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder query_parameters(String query_parameters) {
//        validate();
//        this.data.setQuery_parameters(query_parameters);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder round_trip_time(String round_trip_time) {
//        validate();
//        this.data.setRound_trip_time(round_trip_time);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder session_id(String session_id) {
//        validate();
//        this.data.setSession_id(session_id);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder transaction_id(String transaction_id) {
//        validate();
//        this.data.setTransaction_id(transaction_id);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder is_new_transaction(String is_new_transaction) {
//        validate();
//        this.data.setIs_new_transaction(is_new_transaction);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder event_number_in_transaction(String event_number_in_transaction) {
//        validate();
//        this.data.setEvent_number_in_transaction(event_number_in_transaction);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder event_sequence_number(String event_sequence_number) {
//        validate();
//        this.data.setEvent_sequence_number(event_sequence_number);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder duration(String duration) {
//        validate();
//        this.data.setDuration(duration);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder raw_sql(String raw_sql) {
//        validate();
//        this.data.setRaw_sql(raw_sql);
//        return this;
//    }
//
//
//    public SerializeableTraceResultBuilder() {
//        initRow();
//    }
//
//
////    private void setResultColumns() {
////        result.columnNames.add(TraceResult.OPERATION_POSITION);
////        result.columnNames.add(TraceResult.MAIN_TABLE_POSITION);
////        result.columnNames.add(TraceResult.QUERY_OPTION_POSITION);
////        result.columnNames.add(TraceResult.JOINING_TABLES_POSITION);
////        result.columnNames.add(TraceResult.QUERY_PARAMETER_POSITION);
////        result.columnNames.add(TraceResult.TOTAL_RUN_TIME_POSITION);
////        result.columnNames.add(TraceResult.ROUND_TRIP_POSITION);
////        result.columnNames.add(TraceResult.SESSION_ID_POSITION);
////        result.columnNames.add(TraceResult.TRANSACTOIN_ID_POSITION);
////        result.columnNames.add(TraceResult.NEW_TRANSACTION__POSITION);
////        result.columnNames.add(TraceResult.EVENT_NUMBER_IN_TRANSACTION_POSITION);
////        result.columnNames.add(TraceResult.EVENT_SEQUENCE_POSITION);
////        result.columnNames.add(TraceResult.DURATION_POSITION);
////        result.columnNames.add(TraceResult.RAW_SQL_TEXT_POSITION);
////    }
//
//
//    private void initRow() {
//        this.data = new TraceResultData();
//    }
//
//
//    public SerializeableTraceResult build() {
//        putCurrentRow();
//        return result;
//    }
//
//
//    public static TraceResult toTraceResult(SerializeableTraceResultBuilder builder, String testName) {
//        SerializeableTraceResult sResult = builder.build();
//        return TraceResult.fromList(sResult.result, sResult.columnNames, testName);
//    }
//
//
//    private void putCurrentRow() {
//        result.result.add(data);
//    }
//
//
//    public SerializeableTraceResultBuilder newRow() {
//        // Dont put when the builder is not initialized. (First time newRow() is called)
//        if (initialized) {
//            putCurrentRow();
//        }
//        this.initialized = true;
//        return this;
//    }
//
// }
