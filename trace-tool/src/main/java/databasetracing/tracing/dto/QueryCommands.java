package databasetracing.tracing.dto;

/**
 * Created by peterhenell on 2018-07-27.
 */
public enum QueryCommands {
    Select,
    Insert,
    Update,
    Delete,
    Exec,
    CommitorRollback,
    Other
}
