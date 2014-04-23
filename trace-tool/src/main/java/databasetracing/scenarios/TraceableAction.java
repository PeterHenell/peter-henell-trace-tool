package databasetracing.scenarios;


public interface TraceableAction<T> {

    public void run(T actionHelper);

}
