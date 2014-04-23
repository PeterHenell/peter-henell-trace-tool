package databasetracing.tracing.sqlqueryparsers;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.queryparsers.QueryParser;
import databasetracing.queryparsers.sqlserver.SelectQueryParser;
import databasetracing.queryparsers.sqlserver.SqlQueryParserBuilder;

public class SelectQueryParserTest {

    private databasetracing.queryparsers.QueryParser parser;

    public static final String selectString = "declare @p1 int\n"
            + "set @p1=35\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 nvarchar(4000)',N'select gamestate0_.gameRound as gameRound94_, gamestate0_.gameId as gameId94_, gamestate0_.GameRoundData as GameRoun3_94_, gamestate0_.State as State94_, gamestate0_.occurrenceId as occurren5_94_, gamestate0_.userId as userId94_, gamestate0_.version as version94_ from GameState gamestate0_ where gamestate0_.userId=@P0 and gamestate0_.gameId=@P1 and (gamestate0_.occurrenceId is null)                ',9,N'alienrobots_sw'\n"
            + "select @p1";

    String selectTop2String = "declare @p1 int\n"
            + "set @p1=35\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 nvarchar(4000)',N'select top 2 gamestate0_.gameRound as gameRound94_, gamestate0_.gameId as gameId94_, gamestate0_.GameRoundData as GameRoun3_94_, gamestate0_.State as State94_, gamestate0_.occurrenceId as occurren5_94_, gamestate0_.userId as userId94_, gamestate0_.version as version94_ from GameState gamestate0_ where gamestate0_.userId=@P0 and gamestate0_.gameId=@P1 and (gamestate0_.occurrenceId is null)                ',9,N'alienrobots_sw'\n"
            + "select @p1";

    String selectTop200String = "declare @p1 int\n"
            + "set @p1=35\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 nvarchar(4000)',N'select top 200 gamestate0_.gameRound as gameRound94_, gamestate0_.gameId as gameId94_, gamestate0_.GameRoundData as GameRoun3_94_, gamestate0_.State as State94_, gamestate0_.occurrenceId as occurren5_94_, gamestate0_.userId as userId94_, gamestate0_.version as version94_ from GameState gamestate0_ where gamestate0_.userId=@P0 and gamestate0_.gameId=@P1 and (gamestate0_.occurrenceId is null)                ',9,N'alienrobots_sw'\n"
            + "select @p1";

    String updateString = "declare @p1 int\n"
            + "set @p1=38\n"
            + "exec sp_prepexec @p1 output,N'@P0 nvarchar(4000),@P1 nvarchar(4000),@P2 nvarchar(4000),@P3 nvarchar(4000),@P4 bit,@P5 datetime,@P6 datetime,@P7 datetime,@P8 decimal(38,6),@P9 decimal(38,6),@P10 datetime,@P11 datetime,@P12 int,@P13 bigint',N'update PlayerSessions set Channel=@P0, GameCategoryGroupIdsPlayedIn=@P1, Host=@P2, InstanceName=@P3, HasPlayedSeamlessWalletGames=@P4, GamingStartedTimestamp=@P5, LastGamingActivityTimestamp=@P6, LastResetTimestamp=@P7, TotalBet=@P8, TotalWin=@P9, RefreshedDate=@P10, RemovedDate=@P11, RemovedReason=@P12 where id=@P13                                                                                                            ',N'bbg',N'7,10',N'10.99.11.116',N'devdba-gp02-cl01-c-i',1,'2013-10-17 16:32:19.673','2013-10-17 16:40:32.543','2013-10-17 16:32:19.673',51.000000,88.000000,'2013-10-17 16:45:45.173',NULL,NULL,262\n"
            + "select @p1\n";

    String deleteString = "declare @p1 int\n"
            + "set @p1=1008\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 int',N'delete from GameState where gameRound=@P0 and version=@P1                ',33308,1\n"
            + "select @p1";

    public static final String selectSubQuery = "declare @p1 int\n"
            + "set @p1=3913\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 bigint',N'select jackpothis0_.JackpotHistoryId as JackpotH1_57_, jackpothis0_.CasinoCurrencyAmountPayedOut as CasinoCu2_57_, jackpothis0_.playerCurrencyId as playerCu3_57_, jackpothis0_.GameRoundId as GameRoun4_57_, jackpothis0_.JackpotId as JackpotId57_, jackpothis0_.PayoutDate as PayoutDate57_, jackpothis0_.PlayerCurrencyAmountPayedOut as PlayerCu7_57_, jackpothis0_.WinnerUserId as WinnerUs8_57_ from JackpotHistory jackpothis0_ where jackpothis0_.JackpotId=@P0 and jackpothis0_.PayoutDate=(select max(jackpothis1_.PayoutDate) from JackpotHistory jackpothis1_ where jackpothis1_.JackpotId=@P1)                ',9,9\n"
            + "select @p1";;


    public SelectQueryParserTest() {

        parser = new SelectQueryParser();
        parser.prepare(selectString);
    }


    @Test
    public void parserShouldReturnNullOnRubishInput() {
        String rubish = "select some rubish";

        String actual = SqlQueryParserBuilder.parseQuery(rubish);
        Assert.assertNull(actual);
    }


    @Test
    public void ShouldGetOperatorOfParser() {
        String operator = parser.getOperation();

        Assert.assertEquals("SELECT * FROM", operator);
    }


    @Test
    public void ShouldGetOperatorOfParserWithTop2() {
        SelectQueryParser anotherParser = new SelectQueryParser();
        anotherParser.prepare(selectTop2String);
        String operator = anotherParser.getOperation();

        Assert.assertEquals("SELECT TOP 2 * FROM", operator);
    }


    @Test
    public void ShouldGetOperatorOfParserWithTop200() {
        SelectQueryParser anotherParser = new SelectQueryParser();
        anotherParser.prepare(selectTop200String);
        String operator = anotherParser.getOperation();

        Assert.assertEquals("SELECT TOP 200 * FROM", operator);
    }


    @Test
    public void shouldBeParsedAsOperator() {
        Assert.assertTrue(parser.prepare(selectString));
    }


    @Test
    public void shouldNotBeAbleToParseSelectStatement() {
        QueryParser anotherParser = new SelectQueryParser();
        Assert.assertFalse(anotherParser.prepare(updateString));
    }


    @Test
    public void shouldNotBeAbleToParseDeleteStatement() {
        QueryParser anotherParser = new SelectQueryParser();

        Assert.assertFalse(anotherParser.prepare(deleteString));
    }


    @Test
    public void shouldGetStatementBody() {
        String expected = "GameState gamestate0_ where gamestate0_.userId=@P0 and gamestate0_.gameId=@P1 and (gamestate0_.occurrenceId is null)";

        String actual = parser.getStatementBodyPart();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldGetParameterString() {
        String actual = parser.getParameterStringPart();
        String expected = "9,N'alienrobots_sw'";

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParserFullStatementThroughQueryBuilder() {
        String actual = SqlQueryParserBuilder.parseQuery(selectString);

        String expected = "SELECT * FROM GameState gamestate0_ where gamestate0_.userId=9 and gamestate0_.gameId=N'alienrobots_sw' and (gamestate0_.occurrenceId is null)";

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseTopXSelectStatement() {
        String actual = SqlQueryParserBuilder.parseQuery(selectTop2String);
        String expected = "SELECT TOP 2 * FROM GameState gamestate0_ where gamestate0_.userId=9 and gamestate0_.gameId=N'alienrobots_sw' and (gamestate0_.occurrenceId is null)";

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseBugSelect() {
        String sqlString = "declare @p1 int\n"
                + "set @p1=3552\n"
                + "exec sp_prepexec @p1 output,NULL,N'select currency0_.id as id11_, currency0_.casinoCurrency as casinoCu2_11_, currency0_.countryCode as countryC3_11_, currency0_.enabledAsPlayerCurrency as enabledA4_11_, currency0_.hasBeenUsed as hasBeenU5_11_, currency0_.languageCode as language6_11_, currency0_.majorCurrencySymbol as majorCur7_11_, currency0_.maximumFractionDigits as maximumF8_11_, currency0_.minimumFractionDigits as minimumF9_11_, currency0_.name as name11_, currency0_.operatorEnabled as operato11_11_, currency0_.provider as provider11_ from Currencies currency0_ where currency0_.operatorEnabled=1'\n"
                + "select @p1";
        String actual = SqlQueryParserBuilder.parseQuery(sqlString);
        String expected = "SELECT * FROM Currencies currency0_ where currency0_.operatorEnabled=1";

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseDistinctSelect() {
        String sqlString = "declare @p1 int\n"
                + "set @p1=3835\n"
                + "exec sp_prepexec @p1 output,NULL,N'select distinct top 5 UserName, CasinoCurrencyWin from dbo.TopPlayers order by CasinoCurrencyWin desc'\n"
                + "select @p1";

        Assert.assertTrue(parser.prepare(sqlString));
        Assert.assertEquals("SELECT DISTINCT TOP 5 * FROM", parser.getOperation());
        Assert.assertEquals("", parser.getParameterStringPart());
        Assert.assertEquals("dbo.TopPlayers order by CasinoCurrencyWin desc", parser.getStatementBodyPart());

        String actual = SqlQueryParserBuilder.parseQuery(sqlString);
        String expected = "SELECT DISTINCT TOP 5 * FROM dbo.TopPlayers order by CasinoCurrencyWin desc";

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseInlineParameters() {
        String sqlString = "declare @p1 int\n"
                + "set @p1=368\n"
                + "exec sp_prepexec @p1 output,NULL,N'SELECT JackpotId, GameRoundId, PlayerCurrencyAmount, CasinoCurrencyAmount, CurrencyId, GlobalCurrencyAmount, GlobalCurrencyId, DateStamp FROM GlobalJackpotContributions WHERE DateStamp < getDate()'\n"
                + "select @p1";
        String actual = SqlQueryParserBuilder.parseQuery(sqlString);
        String expected = "SELECT * FROM GlobalJackpotContributions WHERE DateStamp < getDate()";

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseNoParameters() {
        String sqlString = "declare @p1 int\n"
                + "set @p1=3504\n"
                + "exec sp_prepexec @p1 output,NULL,N'select jackpot0_.Id as Id56_, jackpot0_.BabyFraction as BabyFrac2_56_, jackpot0_.CasinoCurrencyAmount as CasinoCu3_56_, jackpot0_.CasinoCurrencyAmountNotToAddToCost as CasinoCu4_56_, jackpot0_.CasinoCurrencyBaby as CasinoCu5_56_, jackpot0_.CasinoCurrencyResetValue as CasinoCu6_56_, jackpot0_.CreationDate as Creation7_56_, jackpot0_.FirstSeedValue as FirstSee8_56_, jackpot0_.GlobalCurrencyId as GlobalCu9_56_, jackpot0_.JackpotId as JackpotId56_, jackpot0_.Type as Type56_, jackpot0_.version as version56_ from Jackpot jackpot0_'\n"
                + "select @p1";
        String actual = SqlQueryParserBuilder.parseQuery(sqlString);
        String expected = "SELECT * FROM Jackpot jackpot0_";

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseNoFromStatement() {
        String sqlString = "declare @p1 int\n" + "set @p1=3551\n" + "exec sp_prepexec @p1 output,NULL,N'SELECT GETDATE()'\n" + "select @p1";

        Assert.assertFalse(parser.prepare(sqlString));
        Assert.assertEquals("SELECT ", parser.getOperation());
        Assert.assertNull(parser.getParameterStringPart());
        Assert.assertNull(parser.getStatementBodyPart());

        String actual = SqlQueryParserBuilder.parseQuery(sqlString);
        String expected = "SELECT GETDATE()";

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseQuartzQuery() {
        String sqlString = "declare @p1 int\n"
                + "set @p1=4632\n"
                + "exec sp_prepexec @p1 output,N'@P0 nvarchar(4000)',N'SELECT TRIGGER_GROUP FROM QRTZ_PAUSED_TRIGGER_GRPS WHERE TRIGGER_GROUP = @P0        ',N'_$_ALL_GROUPS_PAUSED_$_'\n"
                + "select @p1";

        Assert.assertTrue(parser.prepare(sqlString));
        Assert.assertEquals("SELECT * FROM", parser.getOperation());
        Assert.assertEquals("N'_$_ALL_GROUPS_PAUSED_$_'", parser.getParameterStringPart());
        Assert.assertEquals("QRTZ_PAUSED_TRIGGER_GRPS WHERE TRIGGER_GROUP = @P0", parser.getStatementBodyPart());

        String expected = "SELECT * FROM QRTZ_PAUSED_TRIGGER_GRPS WHERE TRIGGER_GROUP = N'_$_ALL_GROUPS_PAUSED_$_'";
        String actual = SqlQueryParserBuilder.parseQuery(sqlString);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParseSubQueries() {

        Assert.assertTrue(parser.prepare(selectSubQuery));
        Assert.assertEquals("SELECT * FROM", parser.getOperation());
        Assert.assertEquals("9,9", parser.getParameterStringPart());
        Assert.assertEquals(
                "JackpotHistory jackpothis0_ where jackpothis0_.JackpotId=@P0 and jackpothis0_.PayoutDate=(select max(jackpothis1_.PayoutDate) from JackpotHistory jackpothis1_ where jackpothis1_.JackpotId=@P1)",
                parser.getStatementBodyPart());

        String expected = "SELECT * FROM JackpotHistory jackpothis0_ where jackpothis0_.JackpotId=9 and jackpothis0_.PayoutDate=(select max(jackpothis1_.PayoutDate) from JackpotHistory jackpothis1_ where jackpothis1_.JackpotId=9)";
        String actual = SqlQueryParserBuilder.parseQuery(selectSubQuery);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }
}
