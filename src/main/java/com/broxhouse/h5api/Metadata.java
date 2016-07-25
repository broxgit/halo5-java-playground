package com.broxhouse.h5api; /**
 * Created by Brock Berrett on 7/20/2016.
 */

import com.broxhouse.h5api.models.metadata.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;


public interface Metadata
{
    String PATH_PREFIX = "/metadata/h5/metadata/";

    @GET(PATH_PREFIX + "campaign-missions")
    Call<List<CampaignMission>> getCampaignMissions();

    @GET(PATH_PREFIX + "commendations")
    Call<List<Commendation>> getCommendations();

    @GET(PATH_PREFIX + "csr-designations")
    Call<List<CSRDesignation>> getCSRDesignations();

    @GET(PATH_PREFIX + "enemies")
    Call<List<Enemy>> getEnemies();

    @GET(PATH_PREFIX + "flexible-stats")
    Call<List<FlexibleStat>> getFlexibleStats();

    @GET(PATH_PREFIX + "game-base-variants")
    Call<List<GameBaseVariant>> getGameBaseVariants();

    @GET(PATH_PREFIX + "game-variants/{id}")
    Call<GameVariant> getGameVariant(@Path("id") String id);

    @GET(PATH_PREFIX + "map-variants/{id}")
    Call<MapVariant> getMapVariant(@Path("id") String id);

    @GET(PATH_PREFIX + "impulses")
    Call<List<Impulse>> getImpulses();

    @GET(PATH_PREFIX + "maps")
    Call<List<Map>> getMaps();

    @GET(PATH_PREFIX + "medals")
    Call<List<Medal>> getMedals();

    @GET(PATH_PREFIX + "playlists")
    Call<List<Playlist>> getPlaylists();

    @GET(PATH_PREFIX + "requisition-packs/{id}")
    Call<RequisitionPack> getRequisitionPack(@Path("id") String id);

    @GET(PATH_PREFIX + "requisitions/{id}")
    Call<Requisition> getRequisition(@Path("id") String id);

    @GET(PATH_PREFIX + "skulls")
    Call<List<Skull>> getSkulls();

    @GET(PATH_PREFIX + "spartan-ranks")
    Call<List<SpartanRank>> getSpartanRanks();

    @GET(PATH_PREFIX + "team-colors")
    Call<List<TeamColor>> getTeamColors();

    @GET(PATH_PREFIX + "vehicles")
    Call<List<Vehicle>> getVehicles();

    @GET(PATH_PREFIX + "weapons")
    Call<List<Weapon>> getWeapons();
}
