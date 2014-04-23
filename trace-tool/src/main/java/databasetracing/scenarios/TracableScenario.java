package databasetracing.scenarios;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import databasetracing.commands.Command;

public final class TracableScenario<T> {

    private final List<Command<T>> commands;
    private final TraceableAction<T> action;


    public TracableScenario(TraceableAction<T> action) {
        commands = new ArrayList<>();
        this.action = action;
    }


    @Override
    public String toString() {
        return getScenarioName();
    }


    public List<Command<T>> getCommands() {
        return commands;
    }


    /**
     * execute the begin method of each command of this scenario
     * 
     * @param actionHelper
     */
    public void prepare(T actionHelper) {
        for (Command<T> command : getCommands()) {
            command.begin(actionHelper);
        }
    }


    /**
     * execute the end method of each command of this scenario
     * 
     * @param actionHelper
     */
    public void unprepare(T actionHelper) {
        List<Command<T>> commands = getCommands();
        ListIterator<Command<T>> iterator = commands.listIterator(commands.size());
        while (iterator.hasPrevious()) {
            iterator.previous().end(actionHelper);
        }
    }


    public String getScenarioName() {
        StringBuilder sb = new StringBuilder();
        for (Command<T> command : commands) {
            if (!command.getName().isEmpty()) {
                sb.append(command.getName());
                sb.append("_");
            }
        }
        return sb.toString();
    }


    public TracableScenario<T> addCommand(Command<T> command) {
        commands.add(command);
        return this;
    }


    public void runScenario(T actionHelper) {
        action.run(actionHelper);
    }
}
