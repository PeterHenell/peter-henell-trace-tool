The trace tool collects all the actions taken on the database server and produces several kinds of output that are much easier to interpret.

For SQL Server it is using Extended events to collect action events.
For PostgreSQL it is reading the statement log (which has to be configured correctly).

For both servers it is not recommended to use this in production environments. It will simply require too many resources.