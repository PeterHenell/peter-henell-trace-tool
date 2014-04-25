package databasetracing.queryparsers.postgres;

public class UnmatchableException extends Exception {

    private static final long serialVersionUID = 5389798580583964417L;


    public UnmatchableException(String message) {
        super(message);
    }

}
