package databasetracing.tracing.sqlqueryparsers;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.queryparsers.QueryParser;
import databasetracing.queryparsers.sqlserver.InsertQueryParser;
import databasetracing.queryparsers.sqlserver.SqlQueryParserBuilder;

public class InsertQueryParserTest {

    private databasetracing.queryparsers.QueryParser parser;

    String selectString = "declare @p1 int\n"
            + "set @p1=35\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 nvarchar(4000)',N'select top 2 gamestate0_.gameRound as gameRound94_, gamestate0_.gameId as gameId94_, gamestate0_.GameRoundData as GameRoun3_94_, gamestate0_.State as State94_, gamestate0_.occurrenceId as occurren5_94_, gamestate0_.userId as userId94_, gamestate0_.version as version94_ from GameState gamestate0_ where gamestate0_.userId=@P0 and gamestate0_.gameId=@P1 and (gamestate0_.occurrenceId is null)                ',9,N'alienrobots_sw'\n"
            + "select @p1";

    String updateString = "declare @p1 int\n"
            + "set @p1=38\n"
            + "exec sp_prepexec @p1 output,N'@P0 nvarchar(4000),@P1 nvarchar(4000),@P2 nvarchar(4000),@P3 nvarchar(4000),@P4 bit,@P5 datetime,@P6 datetime,@P7 datetime,@P8 decimal(38,6),@P9 decimal(38,6),@P10 datetime,@P11 datetime,@P12 int,@P13 bigint',N'update PlayerSessions set Channel=@P0, GameCategoryGroupIdsPlayedIn=@P1, Host=@P2, InstanceName=@P3, HasPlayedSeamlessWalletGames=@P4, GamingStartedTimestamp=@P5, LastGamingActivityTimestamp=@P6, LastResetTimestamp=@P7, TotalBet=@P8, TotalWin=@P9, RefreshedDate=@P10, RemovedDate=@P11, RemovedReason=@P12 where id=@P13                                                                                                            ',N'bbg',N'7,10',N'10.99.11.116',N'devdba-gp02-cl01-c-i',1,'2013-10-17 16:32:19.673','2013-10-17 16:40:32.543','2013-10-17 16:32:19.673',51.000000,88.000000,'2013-10-17 16:45:45.173',NULL,NULL,262\n"
            + "select @p1\n";

    String insertString = "declare @p1 int\n"
            + "set @p1=8\n"
            + "exec sp_prepexec @p1 output,N'@P0 int,@P1 decimal(38,0),@P2 decimal(38,0),@P3 datetime,@P4 nvarchar(4000),@P5 bigint,@P6 nvarchar(4000),@P7 decimal(38,0),@P8 decimal(38,0),@P9 decimal(38,6),@P10 decimal(38,0),@P11 decimal(38,0),@P12 decimal(38,0),@P13 decimal(38,0),@P14 decimal(38,0),@P15 datetime',N'insert into GameRounds (MoneyType, CasinoCurrencyBet, CasinoCurrencyWin, EndDate, GameId, UserId, PlayerCurrencyId, PlayerCurrencyActualBalanceAfter, PlayerCurrencyBalanceAfter, PlayerCurrencyBalanceBefore, PlayerCurrencyBet, PlayerCurrencyBonusBet, PlayerCurrencyBonusWin, PlayerCurrencyNonWithdrawableBet, PlayerCurrencyWin, StartDate) values (@P0, @P1, @P2, @P3, @P4, @P5, @P6, @P7, @P8, @P9, @P10, @P11, @P12, @P13, @P14, @P15)                                                                                                                           select SCOPE_IDENTITY() AS GENERATED_KEYS',5,NULL,NULL,NULL,N'alienrobots_sw',9,N'EUR',NULL,NULL,100.000000,NULL,NULL,NULL,NULL,NULL,'2013-10-17 16:45:45'\n"
            + "select @p1";


    public InsertQueryParserTest() {

        parser = new InsertQueryParser();
        parser.prepare(insertString);
    }


    @Test
    public void ShouldGetOperatorOfParser() {
        String operator = parser.getOperation();

        Assert.assertEquals("INSERT INTO", operator);
    }


    @Test
    public void shouldBeParsedAsOperator() {
        Assert.assertTrue(parser.prepare(insertString));
    }


    @Test
    public void shouldNotBeAbleToParseSelectStatement() {
        QueryParser anotherParser = new InsertQueryParser();
        Assert.assertFalse(anotherParser.prepare(selectString));
    }


    @Test
    public void shouldGetStatementBody() {
        String expected = "GameRounds (MoneyType, CasinoCurrencyBet, CasinoCurrencyWin, EndDate, GameId, UserId, PlayerCurrencyId, PlayerCurrencyActualBalanceAfter, PlayerCurrencyBalanceAfter, PlayerCurrencyBalanceBefore, PlayerCurrencyBet, PlayerCurrencyBonusBet, PlayerCurrencyBonusWin, PlayerCurrencyNonWithdrawableBet, PlayerCurrencyWin, StartDate) values (@P0, @P1, @P2, @P3, @P4, @P5, @P6, @P7, @P8, @P9, @P10, @P11, @P12, @P13, @P14, @P15)                                                                                                                           select SCOPE_IDENTITY()";

        String actual = parser.getStatementBodyPart();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldGetParameterString() {
        String actual = parser.getParameterStringPart();
        String expected = "5,NULL,NULL,NULL,N'alienrobots_sw',9,N'EUR',NULL,NULL,100.000000,NULL,NULL,NULL,NULL,NULL,'2013-10-17 16:45:45'";

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParserFullStatementThroughQueryBuilder() {
        String actual = SqlQueryParserBuilder.parseQuery(insertString);

        String expected = "INSERT INTO GameRounds (MoneyType, CasinoCurrencyBet, CasinoCurrencyWin, EndDate, GameId, UserId, PlayerCurrencyId, PlayerCurrencyActualBalanceAfter, PlayerCurrencyBalanceAfter, PlayerCurrencyBalanceBefore, PlayerCurrencyBet, PlayerCurrencyBonusBet, PlayerCurrencyBonusWin, PlayerCurrencyNonWithdrawableBet, PlayerCurrencyWin, StartDate) values (5, NULL, NULL, NULL, N'alienrobots_sw', 9, N'EUR', NULL, NULL, 100.000000, NULL, NULL, NULL, NULL, NULL, '2013-10-17 16:45:45')                                                                                                                           select SCOPE_IDENTITY()";

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseBugRelatedToUpperCaseCharacters() {
        String buggedString = "set @p1=20\n"
                + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 int,@P2 decimal(38,6),@P3 decimal(38,6),@P4 decimal(38,6),@P5 int,@P6 int,@P7 int,@P8 int,@P9 int,@P10 nvarchar(4000),@P11 nvarchar(4000),@P12 int,@P13 int,@P14 nvarchar(4000),@P15 int,@P16 decimal(38,6),@P17 nvarchar(4000),@P18 int,@P19 nvarchar(4000)',N'INSERT INTO AlienRobotsTracking(GameRound,SessionId,Credit,Bet,Win,Pos1,Pos2,Pos3,Pos4,Pos5,ReelSetName,State,BetCoins,WinCoins,BetWays,BetLevel,CoinValue,Wins, FreeSpinsLeft, TriggeredStickyReels) VALUES (@P0,@P1,@P2,@P3,@P4,@P5,@P6,@P7,@P8,@P9,@P10,@P11,@P12,@P13,@P14,@P15,@P16,@P17,@P18,@P19)                                                                                                                                                       select SCOPE_IDENTITY() AS GENERATED_KEYS',32925,NULL,107.000000,1.000000,8.000000,2,1,4,0,0,N'basic',N'BASIC',10,80,N'0,1,2,3,4,5,6,7,8,9',1,0.100000,N'p=(0,0)(1,0)(2,0)(3,0);w=800;fs=0;l=1',0,N''\n"
                + "select @p1";
        String expectedParameterString = "32925,NULL,107.000000,1.000000,8.000000,2,1,4,0,0,N'basic',N'BASIC',10,80,N'0,1,2,3,4,5,6,7,8,9',1,0.100000,N'p=(0,0)(1,0)(2,0)(3,0);w=800;fs=0;l=1',0,N''";

        parser.prepare(buggedString);
        String actualParameterString = parser.getParameterStringPart();

        Assert.assertNotNull(actualParameterString);
        Assert.assertEquals(expectedParameterString, actualParameterString);
    }


    @Test
    public void shouldParseQuartzInsertQuery() {
        String sqlString = "declare @p1 int\n"
                + "set @p1=1953\n"
                + "exec sp_prepexec @p1 output,N'@P0 nvarchar(4000),@P1 nvarchar(4000),@P2 nvarchar(4000),@P3 nvarchar(4000),@P4 bit,@P5 nvarchar(4000),@P6 decimal(38,0),@P7 decimal(38,0),@P8 nvarchar(4000),@P9 nvarchar(4000),@P10 decimal(38,0),@P11 decimal(38,0),@P12 nvarchar(4000),@P13 int,@P14 varbinary(8000),@P15 int',N'INSERT INTO QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, IS_VOLATILE, DESCRIPTION, NEXT_FIRE_TIME, PREV_FIRE_TIME, TRIGGER_STATE, TRIGGER_TYPE, START_TIME, END_TIME, CALENDAR_NAME, MISFIRE_INSTR, JOB_DATA, PRIORITY)  VALUES(@P0, @P1, @P2, @P3, @P4, @P5, @P6, @P7, @P8, @P9, @P10, @P11, @P12, @P13, @P14, @P15)                                                                                                                          ',N'MT_4ju0163ru04dl',N'MANUAL_TRIGGER',N'SeamlessWalletPendingTransactionsJob',N'CLEAN',0,NULL,1382700214590,-1,N'WAITING',N'SIMPLE',1382700214590,0,NULL,0,0x,5\n"
                + "select @p1";

        Assert.assertTrue(parser.prepare(sqlString));
        Assert.assertEquals("INSERT INTO", parser.getOperation());
        Assert.assertEquals(
                "N'MT_4ju0163ru04dl',N'MANUAL_TRIGGER',N'SeamlessWalletPendingTransactionsJob',N'CLEAN',0,NULL,1382700214590,-1,N'WAITING',N'SIMPLE',1382700214590,0,NULL,0,0x,5",
                parser.getParameterStringPart());
        Assert.assertEquals(
                "QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, IS_VOLATILE, DESCRIPTION, NEXT_FIRE_TIME, PREV_FIRE_TIME, TRIGGER_STATE, TRIGGER_TYPE, START_TIME, END_TIME, CALENDAR_NAME, MISFIRE_INSTR, JOB_DATA, PRIORITY)  VALUES(@P0, @P1, @P2, @P3, @P4, @P5, @P6, @P7, @P8, @P9, @P10, @P11, @P12, @P13, @P14, @P15)",
                parser.getStatementBodyPart());

        String expected = "INSERT INTO QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, IS_VOLATILE, DESCRIPTION, NEXT_FIRE_TIME, PREV_FIRE_TIME, TRIGGER_STATE, TRIGGER_TYPE, START_TIME, END_TIME, CALENDAR_NAME, MISFIRE_INSTR, JOB_DATA, PRIORITY)  VALUES(N'MT_4ju0163ru04dl', N'MANUAL_TRIGGER', N'SeamlessWalletPendingTransactionsJob', N'CLEAN', 0, NULL, 1382700214590, -1, N'WAITING', N'SIMPLE', 1382700214590, 0, NULL, 0, 0x, 5)";
        String actual = SqlQueryParserBuilder.parseQuery(sqlString);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

}
