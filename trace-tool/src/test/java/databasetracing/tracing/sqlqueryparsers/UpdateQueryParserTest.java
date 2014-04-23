package databasetracing.tracing.sqlqueryparsers;

import junit.framework.Assert;

import org.junit.Test;

import databasetracing.queryparsers.sqlserver.SqlQueryParserBuilder;
import databasetracing.queryparsers.sqlserver.UpdateQueryParser;

public class UpdateQueryParserTest {

    private UpdateQueryParser parser;

    String selectString = "declare @p1 int\n"
            + "set @p1=35\n"
            + "exec sp_prepexec @p1 output,N'@P0 bigint,@P1 nvarchar(4000)',N'select top 2 gamestate0_.gameRound as gameRound94_, gamestate0_.gameId as gameId94_, gamestate0_.GameRoundData as GameRoun3_94_, gamestate0_.State as State94_, gamestate0_.occurrenceId as occurren5_94_, gamestate0_.userId as userId94_, gamestate0_.version as version94_ from GameState gamestate0_ where gamestate0_.userId=@P0 and gamestate0_.gameId=@P1 and (gamestate0_.occurrenceId is null)                ',9,N'alienrobots_sw'\n"
            + "select @p1";

    String updateString = "declare @p1 int\n"
            + "set @p1=38\n"
            + "exec sp_prepexec @p1 output,N'@P0 nvarchar(4000),@P1 nvarchar(4000),@P2 nvarchar(4000),@P3 nvarchar(4000),@P4 bit,@P5 datetime,@P6 datetime,@P7 datetime,@P8 decimal(38,6),@P9 decimal(38,6),@P10 datetime,@P11 datetime,@P12 int,@P13 bigint',N'update PlayerSessions set Channel=@P0, GameCategoryGroupIdsPlayedIn=@P1, Host=@P2, InstanceName=@P3, HasPlayedSeamlessWalletGames=@P4, GamingStartedTimestamp=@P5, LastGamingActivityTimestamp=@P6, LastResetTimestamp=@P7, TotalBet=@P8, TotalWin=@P9, RefreshedDate=@P10, RemovedDate=@P11, RemovedReason=@P12 where id=@P13                                                                                                            ',N'bbg',N'7,10',N'10.99.11.116',N'devdba-gp02-cl01-c-i',1,'2013-10-17 16:32:19.673','2013-10-17 16:40:32.543','2013-10-17 16:32:19.673',51.000000,88.000000,'2013-10-17 16:45:45.173',NULL,NULL,262\n"
            + "select @p1\n";


    public UpdateQueryParserTest() {

        parser = new UpdateQueryParser();
        parser.prepare(updateString);
    }


    @Test
    public void ShouldGetOperatorOfParser() {
        String operator = parser.getOperation();

        Assert.assertEquals("UPDATE", operator);
    }


    @Test
    public void shouldBeParsedAsUpdate() {
        Assert.assertTrue(parser.isParsedAsThis());
    }


    @Test
    public void shouldNotBeAbleToParseSelectStatement() {
        UpdateQueryParser anotherParser = new UpdateQueryParser();
        Assert.assertFalse(anotherParser.prepare(selectString));
    }


    @Test
    public void shouldGetStatementBody() {
        String expected = "PlayerSessions set Channel=@P0, GameCategoryGroupIdsPlayedIn=@P1, Host=@P2, InstanceName=@P3, HasPlayedSeamlessWalletGames=@P4, GamingStartedTimestamp=@P5, LastGamingActivityTimestamp=@P6, LastResetTimestamp=@P7, TotalBet=@P8, TotalWin=@P9, RefreshedDate=@P10, RemovedDate=@P11, RemovedReason=@P12 where id=@P13";

        String actual = parser.getStatementBodyPart();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldGetParameterString() {
        String actual = parser.getParameterStringPart();
        String expected = "N'bbg',N'7,10',N'10.99.11.116',N'devdba-gp02-cl01-c-i',1,'2013-10-17 16:32:19.673','2013-10-17 16:40:32.543','2013-10-17 16:32:19.673',51.000000,88.000000,'2013-10-17 16:45:45.173',NULL,NULL,262";

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldParserFullStatementThroughQueryBuilder() {
        String actual = SqlQueryParserBuilder.parseQuery(updateString);
        String expected = "UPDATE PlayerSessions set Channel=N'bbg', GameCategoryGroupIdsPlayedIn=N'7,10', Host=N'10.99.11.116', InstanceName=N'devdba-gp02-cl01-c-i', HasPlayedSeamlessWalletGames=1,"
                + " GamingStartedTimestamp='2013-10-17 16:32:19.673', LastGamingActivityTimestamp='2013-10-17 16:40:32.543', LastResetTimestamp='2013-10-17 16:32:19.673', TotalBet=51.000000, TotalWin=88.000000, RefreshedDate='2013-10-17 16:45:45.173', RemovedDate=NULL, RemovedReason=NULL where id=262";

        Assert.assertEquals(expected, actual);
    }

}
