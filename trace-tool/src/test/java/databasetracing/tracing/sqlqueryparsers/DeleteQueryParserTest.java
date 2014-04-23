package databasetracing.tracing.sqlqueryparsers;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.queryparsers.QueryParser;
import databasetracing.queryparsers.sqlserver.DeleteQueryParser;
import databasetracing.queryparsers.sqlserver.SqlQueryParserBuilder;

public class DeleteQueryParserTest {

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

    String execString = "declare @p1 int\n"
            + "set @p1=773\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 bigint,@P2 datetime,@P3 datetime,@P4 bit,@P5 nvarchar(4000)',N'EXEC WriteTracking @P0,@P1,@P2,@P3,@P4,@P5                                                ',33234,9,'2013-10-21 17:12:59.720','2013-10-21 17:12:59',1,N'H4sIAAAAAAAAAM1UwW6DMAz9owXaXSZZkTrUQyW2SaXbtGPKTBUtBJSETvz9nCBoabuqh3baBfk9P/yMnQBGrNfS2ZUR+ZfUm8wJhxy2aKysNI+A9SEUBjGrpbYpFs5nxgRsRInLqtGfSVXWCqmMMw0CO+ZBCetmufNlLRUAtkdA2SgnayXR8BjYHgIbmnucZYsEWAfAtqXlIB2WPPt4mgIL4VUI1tUeOzwcyuPJIXMsiX9/6S9NvqVOKtoXn0YR7W+APkHPNTolNXJ6rQ/D0v2KbL/wDkBd9e1GI9sBsaDYyaaXye4vk00uk8XnZPTRu0EEmEnXCH8IV21N52y+ShfP85AeZwLVDSzFLSrvM8SBpdlZHkcdHQDkNOo3oRrk0R3pdxBqJVo0SWMM6rzl89cldTjmwI/9fWi4R+RVVAZf6I6SPrvhVThhdMb76uf3/9mzkz/OHwz4YZ9PBQAA'\n"
            + "select @p1";

    String deleteString = "declare @p1 int\n"
            + "set @p1=1008\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 int',N'delete from GameState where gameRound=@P0 and version=@P1                ',33308,1\n"
            + "select @p1";


    public DeleteQueryParserTest() {

        parser = new DeleteQueryParser();
        parser.prepare(deleteString);

    }


    @Test
    public void ShouldGetOperatorOfParser() {
        String operator = parser.getOperation();

        Assert.assertEquals("DELETE FROM", operator);
    }


    @Test
    public void shouldBeParsedAsOperator() {
        Assert.assertTrue(parser.prepare(deleteString));
    }


    @Test
    public void shouldNotBeAbleToParseSelectStatement() {
        QueryParser anotherParser = new DeleteQueryParser();
        Assert.assertFalse(anotherParser.prepare(selectString));
    }


    @Test
    public void shouldNotBeAbleToParseUpdateStatement() {
        QueryParser anotherParser = new DeleteQueryParser();
        Assert.assertFalse(anotherParser.prepare(updateString));
    }


    @Test
    public void shouldNotBeAbleToParseInsertStatement() {
        QueryParser anotherParser = new DeleteQueryParser();
        Assert.assertFalse(anotherParser.prepare(insertString));
    }


    @Test
    public void shouldGetStatementBody() {
        String expected = "GameState where gameRound=@P0 and version=@P1";

        String actual = parser.getStatementBodyPart();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldGetParameterString() {
        String actual = parser.getParameterStringPart();
        String expected = "33308,1";

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParserFullStatementThroughQueryBuilder() {
        String actual = SqlQueryParserBuilder.parseQuery(deleteString);

        String expected = "DELETE FROM GameState where gameRound=33308 and version=1";

        Assert.assertEquals(expected, actual);
    }
}
