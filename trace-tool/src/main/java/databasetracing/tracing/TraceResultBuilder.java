package databasetracing.tracing;

import java.util.ArrayList;
import java.util.List;

import databasetracing.tracing.dto.TraceResultData;

public class TraceResultBuilder {
    private TraceResultData data;
    private final List<TraceResultData> result;

    // In order to prevent that data is added before newRow have been called
    private boolean initialized = false;

    // In order to be able to return empty list when none of the values have been set.
    private boolean validatedOnce = false;


    public TraceResultBuilder() {
        result = new ArrayList<>();
        initRow();
    }


    public TraceResultBuilder operation(String operation) {
        validate();
        this.data.setOperation(operation);
        return this;
    }


    private void validate() {
        if (!initialized) {
            throw new AssertionError("newRow() before adding any data");
        }
        this.validatedOnce = true;
    }


    public TraceResultBuilder main_table(String main_table) {
        validate();
        this.data.setMain_table(main_table);
        return this;
    }


    public TraceResultBuilder query_option(String query_option) {
        validate();
        this.data.setQuery_option(query_option);
        return this;
    }


    public TraceResultBuilder total_run_time(String total_run_time) {
        validate();
        this.data.setTotal_run_time(total_run_time);
        return this;
    }


    public TraceResultBuilder joining_tables(String joining_tables) {
        validate();
        this.data.setJoining_tables(joining_tables);
        return this;
    }


    public TraceResultBuilder query_parameters(String query_parameters) {
        validate();
        this.data.setQuery_parameters(query_parameters);
        return this;
    }


    public TraceResultBuilder round_trip_time(String round_trip_time) {
        validate();
        this.data.setRound_trip_time(round_trip_time);
        return this;
    }


    public TraceResultBuilder session_id(String session_id) {
        validate();
        this.data.setSession_id(session_id);
        return this;
    }


    public TraceResultBuilder transaction_id(String transaction_id) {
        validate();
        this.data.setTransaction_id(transaction_id);
        return this;
    }


    public TraceResultBuilder is_new_transaction(String is_new_transaction) {
        validate();
        this.data.setIs_new_transaction(is_new_transaction);
        return this;
    }


    public TraceResultBuilder event_number_in_transaction(String event_number_in_transaction) {
        validate();
        this.data.setEvent_number_in_transaction(event_number_in_transaction);
        return this;
    }


    public TraceResultBuilder event_sequence_number(String event_sequence_number) {
        validate();
        this.data.setEvent_sequence_number(event_sequence_number);
        return this;
    }


    public TraceResultBuilder duration(String duration) {
        validate();
        this.data.setDuration(duration);
        return this;
    }


    public TraceResultBuilder raw_sql(String raw_sql) {
        validate();
        this.data.setRaw_sql(raw_sql);
        return this;
    }


    private void initRow() {
        this.data = new TraceResultData();
    }


    public TraceResult build(String testName) {
        if (validatedOnce) {
            putCurrentRow();
        }
        List<TraceResultData> newList = new ArrayList<>();
        newList.addAll(result);
        result.clear();
        initRow();
        return TraceResult.fromList(newList, testName);
    }


    private void putCurrentRow() {
        result.add(data);
    }


    public TraceResultBuilder newRow() {
        // Dont put when the builder is not initialized. (First time newRow() is called)
        if (initialized) {
            putCurrentRow();
        }
        this.initialized = true;
        initRow();
        return this;
    }
}
