package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.*;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by Brock Berrett on 7/21/2016.
 */
public class MetadataTest extends BaseTest
{

//    private static String ;
//
//    @BeforeClass
//    public static void setUpClass() {
//         = KeyReader.getApiKey();
//    }

    @Test
    public void testWeapons() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Weapon>> weapons = metadata.getWeapons().execute();
        System.out.println(weapons);
        Assert.assertEquals(200, weapons.code());
    }

    
    public void testVehicles() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Vehicle>> vehicles = metadata.getVehicles().execute();
        Assert.assertEquals(200, vehicles.code());
    }

    
    public void testTeamColor() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<TeamColor>> teamColors = metadata.getTeamColors().execute();
        Assert.assertEquals(200, teamColors.code());
    }

    
    public void testSpartanRank() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<SpartanRank>> spartanRanks = metadata.getSpartanRanks().execute();
        Assert.assertEquals(200, spartanRanks.code());
    }

    
    public void testSkull() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Skull>> skulls = metadata.getSkulls().execute();
        Assert.assertEquals(200, skulls.code());
    }

    
    public void testCampaignMissions() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<CampaignMission>> campaignMissions = metadata.getCampaignMissions().execute();
        Assert.assertEquals(200, campaignMissions.code());
    }

    
    public void testCommendations() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Commendation>> commendations = metadata.getCommendations().execute();
        Assert.assertEquals(200, commendations.code());
    }

    
    public void testCSRDesignations() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<CSRDesignation>> csrDesignations = metadata.getCSRDesignations().execute();
        Assert.assertEquals(200, csrDesignations.code());
    }

    
    public void testEnemies() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Enemy>> enemies = metadata.getEnemies().execute();
        Assert.assertEquals(200, enemies.code());
    }

    
    public void testFlexibleStats() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<FlexibleStat>> flexibleStats = metadata.getFlexibleStats().execute();
        Assert.assertEquals(200, flexibleStats.code());
    }

    
    public void testGameBaseVariants() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<GameBaseVariant>> gameBaseVariants = metadata.getGameBaseVariants().execute();
        Assert.assertEquals(200, gameBaseVariants.code());
    }

    
    public void testImpulses() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Impulse>> impulses = metadata.getImpulses().execute();
        Assert.assertEquals(200, impulses.code());
    }

    
    public void testMaps() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Map>> maps = metadata.getMaps().execute();
        Assert.assertEquals(200, maps.code());
    }

    
    public void testMedals() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Medal>> response = metadata.getMedals().execute();
        Assert.assertEquals(200, response.code());
    }

    
    public void testPlaylists() throws IOException {
        ApiFactory factory = new ApiFactory();
        Metadata metadata = factory.getMetadata();
        Response<List<Playlist>> response = metadata.getPlaylists().execute();
        Assert.assertEquals(200, response.code());
    }


}
