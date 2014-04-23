package databasetracing.commands;

public abstract class Command<T> {

    private final String name;


    public Command(String name) {
        this.name = name;
    }


    public abstract void begin(T actionHelper);


    public abstract void end(T actionHelper);


    public String getName() {
        return name;
    }
}
