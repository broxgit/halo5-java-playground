//
//
//package com.broxhouse.h5api;
//
//import com.broxhouse.h5api.models.stats.servicerecords.*;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import retrofit2.Response;
//
//import java.io.IOException;
//
//@RunWith(JUnit4.class)
//public class ServiceRecordsApiTest extends BaseTest {
//
////    private static String apiKey;
//
//    private static final String PLAYER = "Vercobrixx";
//
////    @BeforeClass
////    public static void setUpClass() {
////        apiKey = KeyReader.getApiKey();
////    }
//
//    @Test
//    public void testWarzoneServiceRecord() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Response<ServiceRecordCollection<WarzoneResult>> response = stats
//                .getWarzoneServiceRecords(PLAYER)
//                .execute();
//
//        Assert.assertEquals(200, response.code());
//    }
//
//    @Test
//    public void testArenaServiceRecord() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Response<ServiceRecordCollection<ArenaResult>> response = stats
//                .getArenaServiceRecords(PLAYER)
//                .execute();
//
//        Assert.assertEquals(200, response.code());
//    }
//
//    @Test
//    public void testCustomServiceRecord() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Response<ServiceRecordCollection<CustomResult>> response = stats
//                .getCustomServiceRecords(PLAYER)
//                .execute();
//
//        Assert.assertEquals(200, response.code());
//    }
//
//    @Test
//    public void testCampaignServiceRecord() throws IOException {
//        ApiFactory factory = new ApiFactory();
//        Stats stats = factory.getStats();
//        Response<ServiceRecordCollection<CampaignResult>> response = stats
//                .getCampaignServiceRecords(PLAYER)
//                .execute();
//
//        Assert.assertEquals(200, response.code());
//    }
//}
