//
//
//package com.broxhouse.h5api;
//
//import com.broxhouse.h5api.models.stats.matches.Match;
//import com.broxhouse.h5api.models.stats.matches.Page;
//import com.broxhouse.h5api.models.stats.reports.*;
//import com.broxhouse.h5api.utils.Constants;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import retrofit2.Response;
//
//import java.io.IOException;
//
//@RunWith(JUnit4.class)
//public class ReportsApiTest extends BaseTest {
////    private static String apiKey;
//
//    private static final String PLAYER = "CreamMaded";
//    private static final String MODES = "warzone,arena,custom";
//
//    {
//        setTimeout(Constants.TIMEOUT * 2);
//    }
//
////    @BeforeClass
////    public static void setUpClass() {
////        apiKey = KeyReader.getApiKey();
////    }
//
//    @Test
//    public void testRecentMatchInfo() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Response<Page<Match>> response = stats.getRecentMatchInfo(PLAYER, MODES, 0, 20).execute();
//        Assert.assertEquals(response.code(), 200);
//    }
//
//    @Test
//    public void testArenaReport() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Match match = getMatchByType(stats, "arena");
//        Response<VersusCarnageReport<ArenaPlayerStats>> arenaReport = stats.getArenaCarnageReport(match.getId().getMatchId()).execute();
//        Assert.assertEquals(arenaReport.code(), 200);
//    }
//
//    @Test
//    public void testWarzoneReport() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Match match = getMatchByType(stats, "warzone");
//        Response<VersusCarnageReport<WarzonePlayerStats>> warzoneReport = stats.getWarzoneCarnageReport(match.getId().getMatchId()).execute();
//        Assert.assertEquals(warzoneReport.code(), 200);
//    }
//
//    @Test
//    public void testCampaignReport() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Match match = getMatchByType(stats, "campaign");
//        Response<CampaignCarnageReport> campaignReport = stats.getCampaignCarnageReport(match.getId().getMatchId()).execute();
//        Assert.assertEquals(campaignReport.code(), 200);
//    }
//
//    @Test
//    public void testCustomReport() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Match match = getMatchByType(stats, "custom");
//        Response<VersusCarnageReport<VersusPlayerStats>> customReport = stats.getCustomCarnageReport(match.getId().getMatchId()).execute();
//        Assert.assertEquals(customReport.code(), 200);
//    }
//
//    private Match getMatchByType(Stats stats, String type) throws IOException {
//        Response<Page<Match>> response = stats.getRecentMatchInfo(
//                PLAYER,
//                type,
//                0,
//                1).execute();
//
//        Assert.assertEquals(response.code(), 200);
//        Assert.assertEquals(response.body().getResultCount(), 1);
//        return response.body().getResults().get(0);
//    }
//}
