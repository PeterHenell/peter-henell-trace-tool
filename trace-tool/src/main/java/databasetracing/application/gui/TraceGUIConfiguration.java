package databasetracing.application.gui;

public class TraceGUIConfiguration {

    private String serverName;
    private String dbName;
    private String userName;
    private String password;


    public String getServerName() {
        return serverName;
    }


    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    public String getDbName() {
        return dbName;
    }


    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public TraceGUIConfiguration() {
        dbName = "devdba_gp_cl01";
        userName = "sqldeploy";
        password = "sqldeploy";
        // serverName = "cyd-dbadb-02.dev.necorp.dom";
        serverName = "D:\\Program Files\\PostgreSQL\\9.3\\data\\pg_log\\postgresql-2014-04-22_000000.log";
    }

}
