package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.*;
import com.broxhouse.h5api.models.stats.common.MedalAward;
import com.broxhouse.h5api.models.stats.common.WeaponStats;
import com.broxhouse.h5api.models.stats.matches.Match;
import com.broxhouse.h5api.models.stats.reports.BaseCarnageReport;
import com.broxhouse.h5api.models.stats.reports.BaseStats;
import com.broxhouse.h5api.models.stats.reports.SpartanRankedPlayerStats;
import com.broxhouse.h5api.models.stats.servicerecords.ArenaStat;
import com.broxhouse.h5api.models.stats.servicerecords.BaseServiceRecordResult;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.broxhouse.h5api.gameType.ARENA;
import static com.broxhouse.h5api.gameType.CUSTOM;
import static com.broxhouse.h5api.gameType.WARZONE;


enum gameType {WARZONE, ARENA, CUSTOM}

public class HaloApi {


    private static final String PLAYER_UF = "That Ax Guy";
    private static final String PLAYER = formatString(PLAYER_UF);
    private static final String TOKEN = "293bb4a86da743bdb983b97efa5bb265";
    private static final String BASE_URL = "https://www.haloapi.com/";
    private static final String STATS_URL = "https://www.haloapi.com/stats/h5/";
    private static final String META_URL = "https://www.haloapi.com/metadata/h5/metadata/";
    private static final String PLAYER_MATCHES = STATS_URL + "players/" + PLAYER + "/matches";
    private static final String CUSTOM_STATS = STATS_URL + "servicerecords/custom?players=%s";
    private static final String ARENA_STATS = STATS_URL + "servicerecords/arena?players=%s";
    private static final String WARZONE_STATS = STATS_URL + "servicerecords/warzone?players=%s";
    private static final String META_WEAPONS = META_URL + "weapons";
    private static final String META_MEDALS = META_URL + "medals";
    private static final String META_PLAYLISTS = META_URL + "playlists";
    private static final String META_MAPS = META_URL + "maps";
    private static final String META_MAP_VARIANTS = META_URL + "map-variants/%s";
    private static final String POST_GAME_CARNAGE = BASE_URL + "stats/h5/arena/matches/%s";

    public static String formatString(String string)
    {
        string = string.replaceAll("\\s+", "%20");
        return string;
    }

    public static String postGameCarnage(String matchID) throws Exception
    {
        return api(String.format(POST_GAME_CARNAGE, matchID));
    }

    //https://www.haloapi.com/stats/h5/players/that%20brock%20guy/matches?modes=arena,custom&start=10&count=26
    public static String playerMatches(String gt, String modes, int start, int count) throws Exception {
        String pURL = PLAYER_MATCHES;
        pURL = pURL +"?modes=" + modes + "&";
        pURL = pURL + "start=" + start + "&";
        pURL = pURL + "count=" + count;

        return api(pURL);
    }

    public static String warzoneMatches(String gt) throws Exception{
        return api(String.format(WARZONE_STATS, gt));
    }

    public static String customMatches(String gt) throws Exception
    {
        return api(String.format(CUSTOM_STATS, gt));
    }

    public static String arenaStats(String gt) throws Exception
    {
        return api(String.format(ARENA_STATS, gt));
    }

    public static String listMedals() throws Exception
    {
        return api(META_MEDALS);
    }

    public static String listWeapons() throws Exception
    {
        return api(META_WEAPONS);
    }

    public static String listPlaylists() throws Exception
    {
        return api(META_PLAYLISTS);
    }

    public static String listMaps() throws Exception
    {
        return api(META_MAPS);
    }

    public static String listMapVariants(String mapVariantID) throws Exception
    {
        return api(String.format(META_MAP_VARIANTS, mapVariantID));
    }

    private static String api(String url) throws Exception
    {
//        System.out.println(url);
        HttpClient httpclient = HttpClients.createDefault();
        String getResponse;

            URIBuilder builder = new URIBuilder(url);


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", TOKEN);


            // Request body
//            StringEntity reqEntity = new StringEntity("{body}");
//            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                getResponse = EntityUtils.toString(entity);
//                System.out.println(getResponse);
                return getResponse;
            }
        else
            {
                return null;
            }
    }

    public static void main(String[] args) throws Exception {
        try
        {
//            testJSONWeapons();
//            testJSONMedals();
//            testMedalStats(CUSTOM);
//            testWeaponKills(CUSTOM);
            testPlayerMatches();
//            totalGames();
//            testPlayerStats();
//            testBaseStats(ARENA);
//            testBaseResults();
//            testActivePlaylists();
//            comparePlayers("That Brock Guy", "That Ax Guy");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void testJSONWeapons() throws Exception
    {
        Gson gson = new Gson();
        Weapon[] data = gson.fromJson(listWeapons(), Weapon[].class);
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < data.length; i++)
        {
            System.out.println(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public static void testJSONMedals() throws Exception
    {
        Gson gson = new Gson();
        Medal[] data = gson.fromJson(listMedals(), Medal[].class);
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < data.length; i++)
        {
            System.out.println(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public static Medal[] getMedals() throws Exception
    {
        Gson gson = new Gson();
        String medalData = listMedals();
        Medal[] medals = gson.fromJson(medalData, Medal[].class);
        return medals;
    }

    public static Map[] getMaps() throws Exception
    {
        Gson gson = new Gson();
        String mapData = listMaps();
        Map[] maps = gson.fromJson(mapData, Map[].class);
        return maps;
    }

    public static Weapon[] getWeapons() throws Exception
    {
        Gson gson = new Gson();
        String weaponData = listWeapons();
        Weapon[] weapons = gson.fromJson(weaponData, Weapon[].class);
        return weapons;
    }

    public static Match[] getMatches() throws Exception
    {
        JSONObject obj = null;
        String var = null;
        String var2 = "";
        Integer start = 0;
        double totalGames = totalGames(ARENA, PLAYER_UF);
        TimeUnit.SECONDS.sleep(8);
        double iterations = totalGames / 25;
        Gson gson = new Gson();
        for (int i = 1; i < iterations; i++)
        {
            obj = new JSONObject(playerMatches(PLAYER, "arena", start, 25));
            JSONArray obj2 = obj.getJSONArray("Results");
            var = obj2.toString();
            if (i < iterations)
            {
                var = var.substring(1, var.length() - 1);
                var2 = var2.concat(var);
                var2 = var2 + ",";
            }
            else
            {
                var2 = var2.concat(var);
            }
            start = start + obj.getInt("Count");
            if (i % 10 == 0)
            {
                TimeUnit.SECONDS.sleep(10);
            }
        }
        var2 = var2.substring(0, var2.length() - 1);
        var2 = "[" + var2 + "]";
        Match[] matches = gson.fromJson(var2, Match[].class);
        return matches;
    }

    public static MapVariant getMapVariant(String mapVariantID) throws Exception
    {
        Gson gson = new Gson();
        String mapData = listMapVariants(mapVariantID);
        MapVariant mapVariant = gson.fromJson(mapData, MapVariant.class);
        return mapVariant;
    }

    public static String getMedalName(long medalID, Medal[] medals) throws Exception
    {
        String medalName = null;
        for (int i = 0; i < medals.length; i++)
        {
            if (medals[i].getId() == medalID)
            {
                medalName = medals[i].getName();
            }

        }
        return medalName;
    }

    public static String getMedalDescription(long medalID, Medal[] medals) throws Exception
    {
        String medalDesc = null;
        for (int i = 0; i < medals.length; i++)
        {
            if (medals[i].getId() == medalID)
            {
                medalDesc = medals[i].getDescription();
            }

        }
        return medalDesc;
    }

    public static long getMedalID(String medalName, Medal[] medals)
    {
        long medalID = 0;
        for (int i = 0; i < medals.length; i++)
        {
            if (medals[i].getName().equalsIgnoreCase(medalName))
            {
                medalID = medals[i].getId();
            }
        }
        return medalID;
    }

    public static String getWeaponName(long weaponID, Weapon[] weapons) throws Exception
    {
        String weaponName = null;
        for (int i = 0; i < weapons.length; i++)
        {
            if (weapons[i].getId() == weaponID)
            {
                weaponName = weapons[i].getName();
            }

        }
        return weaponName;
    }

    public static String getMapName(String id, Map[] maps) throws Exception
    {
        String mapName = null;
        for (int i = 0; i < maps.length; i++)
        {
            if (maps[i].getId().equalsIgnoreCase(id))
            {
                mapName = maps[i].getName();
            }
        }
        return mapName;
    }

    public static BaseStats getBaseStats(Enum gameType, String gamertag) throws Exception{
        JSONObject obj = getPlayerStatsJSON(gameType, gamertag);
        String var = obj.toString();
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
        return stats;
    }


    public static JSONObject getPlayerStatsJSON (Enum gameType) throws Exception
    {
        JSONObject obj = null;
        if (gameType == WARZONE)
        {
            obj = new JSONObject(warzoneMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat");
        }
        if (gameType == ARENA)
        {
            obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats");
        }
        if (gameType == CUSTOM)
        {
            obj = new JSONObject(customMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats");
        }

        return obj;
    }

    public static JSONObject getPlayerStatsJSON (Enum gameType, String gt) throws Exception
    {
        JSONObject obj = null;
        if (gameType == WARZONE)
        {
            obj = new JSONObject(warzoneMatches(formatString(gt))).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat");
        }
        if (gameType == ARENA)
        {
            obj = new JSONObject(arenaStats(formatString(gt))).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats");
        }
        if (gameType == CUSTOM)
        {
            obj = new JSONObject(customMatches(formatString(gt))).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats");
        }

        return obj;
    }

    public static void testMedalStats(Enum gameType) throws Exception
    {
        JSONArray obj = getPlayerStatsJSON(gameType).getJSONArray("MedalAwards");
        String mostEarnedMedal = null;
        double average = 0;
        int highestMedalCount = 0;
        String var = obj.toString();
        System.out.println(var);
        double games = totalGames(gameType, PLAYER);
        games = (double)Math.round(games *1000d) / 1000d;
        Gson gson = new Gson();
        Medal[] medals = getMedals();
        MedalAward[] stats = gson.fromJson(var, MedalAward[].class);
        for (int row = 0; row < stats.length; row++)
        {
            stats[row].setName(getMedalName(stats[row].getMedalId(), medals));
         }
        System.out.println("Showing medal stats for " + PLAYER_UF);
        for (int row = 0; row < stats.length; row++)
        {
            double medalCount = stats[row].getCount()/games;
            medalCount = (double)Math.round(medalCount *1000d) / 1000d;
            System.out.println(stats[row].getName() + ": " + stats[row].getCount() + " ||  Earned per game: " + medalCount);
        }
        for (int i = 0; i < stats.length; i++)
        {
            for (int k = i + 1; k < stats.length; k++)
            {
                if (stats[i].getCount() > stats[k].getCount() && stats[i].getCount() > highestMedalCount) {
                    highestMedalCount = stats[i].getCount();
                    mostEarnedMedal = stats[i].getName();
                }
            }
        }
        average = highestMedalCount / games;
        average = (double)Math.round(average *1000d) / 1000d;
        System.out.println("Your most earned medal is the " + mostEarnedMedal + " medal with a total of " + highestMedalCount + " and an average of " + average + " per game");
    }

    public static void testPlayerStats() throws Exception
    {
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(arenaStats(PLAYER), ArenaStat.class);
        System.out.println(stats.getEnemyKills());
    }

    public static double totalGames(Enum gameType, String gt) throws Exception
    {
        JSONObject obj = getPlayerStatsJSON(gameType, formatString(gt));
        String gType = gameType.toString();
        String var = obj.toString();
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
        System.out.println(gt + " has played a total of " + stats.getTotalGamesCompleted() + " " + gType + " games : " + " with a K/D spread of: " + getKD(totalDeaths(gameType, gt), totalKills(gameType, gt)));
        return stats.getTotalGamesCompleted();
    }

    public static double totalGamesAll() throws Exception
    {
        double totalGames = totalGames(CUSTOM, PLAYER) + totalGames(WARZONE, PLAYER) + totalGames(ARENA, PLAYER);
        return totalGames;
    }

    public static String totalWins(Enum gameType) throws Exception
    {
        String gType = gameType.toString();
        BaseStats stats = getBaseStats(gameType, PLAYER);
        System.out.println("Total " + gType + " games won: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
        String winLoss = ("Total number of Wins: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
        return winLoss;
    }

    public static int totalKills(Enum gameType, String player) throws Exception{
        BaseStats stats = getBaseStats(gameType, player);
        int totalKills = stats.getTotalKills();
//        System.out.println(totalKills);
        return totalKills;
    }

    public static int totalDeaths(Enum gameType, String player) throws Exception{
        BaseStats stats = getBaseStats(gameType, player);
        int totalDeaths = stats.getTotalDeaths();
//        System.out.println(totalDeaths);
        return totalDeaths;
    }

    public static double getKD(double deaths, double kills){
        double kdRatio = (kills/deaths);
        kdRatio = (double)Math.round(kdRatio *1000d) / 1000d;
//        System.out.println(kdRatio);
        return kdRatio;
    }

    public static void testWeaponKills(Enum gameType) throws Exception
    {
        JSONArray obj = getPlayerStatsJSON(gameType).getJSONArray("WeaponStats");
        String favWeapon = null;
        int totalKills = 0;
        Gson gson = new Gson();
        String var = obj.toString();
        Weapon[] weapons = getWeapons();
        WeaponStats[] stats = gson.fromJson(var, WeaponStats[].class);
        double games = totalGames(gameType, PLAYER);
        String wl = totalWins(gameType);
        games = (double)Math.round(games *1000d) / 1000d;

        for (int row = 0; row < stats.length; row++)
        {
            stats[row].setName(getWeaponName(stats[row].getWeaponId().getStockId(), weapons));
        }
        for (int i = 0; i < stats.length; i++)
        {
            for (int k = i + 1; k < stats.length; k++)
            {
                if (stats[i].getTotalKills() > stats[k].getTotalKills() && stats[i].getTotalKills() > totalKills) {
                    totalKills = stats[i].getTotalKills();
                    favWeapon = stats[i].getName();
                }
            }
        }
//        System.out.println("Total kills per weapon for " + PLAYER_UF);
//        for (int i = 0; i < stats.length; i++)
//        {
//            double killCount = stats[i].getTotalKills()/games;
//            killCount = (double)Math.round(killCount * 1000d) / 1000d;
//            System.out.println(stats[i].getName() + ": " + stats[i].getTotalKills() + "  ||  Avg kills per game: " + killCount);
//        }
        System.out.println("Your favorite weapon is the " + favWeapon + " with a kill total of: " + totalKills);
    }

    public static void testBestMap() throws Exception
    {
        Match matches[] = getMatches();
    }


    public static void testBaseStats(Enum gameType) throws Exception
    {
        JSONObject obj = getPlayerStatsJSON(gameType);
        String gType = gameType.toString();
        String var = obj.toString();
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
        System.out.println("Total " + gType + " games played: " + stats.getTotalKills());

        List<WeaponStats> weaponList = stats.getWeapons();
        for(WeaponStats en : weaponList)
        {
            System.out.println(en.getName() + " " + en.getTotalKills());
        }
    }

    public static void testBaseResults() throws Exception
    {
        JSONObject obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result");
        String var = obj.toString();
        Gson gson = new Gson();
        BaseServiceRecordResult stats = gson.fromJson(var, BaseServiceRecordResult.class);
        List<ArenaStat.ArenaPlaylistStats> arenaStats = stats.getArenaStat().getArenaPlaylistStats();
        for (ArenaStat.ArenaPlaylistStats sta : arenaStats)
        {
            System.out.println(sta.getPlaylistId() + " " + sta.getTotalGamesCompleted());
        }
    }

    public static void testActivePlaylists() throws Exception
    {
        JSONArray obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats").getJSONArray("ArenaPlaylistStats");
        String var = obj.toString();
        System.out.println(var);
        Gson gson = new Gson();
        String playlistData = listPlaylists();
        ArenaStat.ArenaPlaylistStats[] playlistStats = gson.fromJson(var, ArenaStat.ArenaPlaylistStats[].class);
        Playlist[] playlists = gson.fromJson(playlistData, Playlist[].class);
        for (int i = 0; i < playlistStats.length; i++)
        {
            for (int k = 0; k < playlists.length; k++)
            {
                if (playlists[k].isActive() == true)
                {
                    System.out.println(playlists[k].getName());
                }
                if (playlists[k].getId().equalsIgnoreCase(playlistStats[i].getPlaylistId()))
                {
                    playlistStats[i].setName(playlists[k].getName());
                }
            }
        }

//        for (int i = 0; i < playlistStats.length; i++)
//        {
//            System.out.println(playlistStats[i].getName() + " " + " " + playlistStats[i].getTotalKills());
//        }
    }

    public static void comparePlayers(String player1, String player2) throws Exception
    {
        JSONArray obj = getPlayerStatsJSON(ARENA, formatString(player1)).getJSONArray("MedalAwards");
        JSONArray obj2 = getPlayerStatsJSON(ARENA, formatString(player2)).getJSONArray("MedalAwards");
        Gson gson = new Gson();
        String var1 = obj.toString();
        String var2 = obj2.toString();
        MedalAward[] p1Medals = gson.fromJson(var1, MedalAward[].class);
        MedalAward[] p2Medals = gson.fromJson(var2, MedalAward[].class);
        Medal[] medals = getMedals();
        String[] betterMedals = new String [p1Medals.length + p2Medals.length];
        String[] worseMedals = new String[p1Medals.length];
        String[] allMedals1 = new String [p1Medals.length];
        String[] allMedals2 = new String [p2Medals.length];
        double p1totalMedals = 0;
        double p2totalMedals = 0;
        double totalGames1 = totalGames(ARENA, player1);
        double totalGames2 = totalGames(ARENA, player2);
        double gamePercentage1 = 0;
        double gamePercentage2 = 0;
        long[] p1Has = null;
        long[] p2Has = null;

        for (int i = 0; i < p1Medals.length; i++)
        {
            allMedals1[i] = getMedalName(p1Medals[i].getMedalId(), medals);
            for (int k = 0; k < p2Medals.length; k++)
            {
                  allMedals2[k] = getMedalName(p2Medals[k].getMedalId(), medals);
                if (p1Medals[i].getMedalId() == p2Medals[k].getMedalId()) {
                    p1totalMedals = p1Medals[i].getCount();
                    p2totalMedals = p2Medals[k].getCount();
                    gamePercentage1 = p1totalMedals/totalGames1;
                    gamePercentage2 = p2totalMedals/totalGames2;
                    gamePercentage1 = (double)Math.round(gamePercentage1 *1000d) / 1000d;
                    gamePercentage2 = (double)Math.round(gamePercentage2 *1000d) / 1000d;


                    if (gamePercentage1 > gamePercentage2) {
//                        betterMedals[i] = (player1 + " has " + p1Medals[i].getCount() + " " + getMedalName(p1Medals[i].getMedalId(), medals) + " medals \n\t-" + player2 + " only has : " + p2Medals[k].getCount());
                        betterMedals[i] = getMedalName(p1Medals[i].getMedalId(), medals) + " x " + p1Medals[i].getCount() + " : " + "Earned per game: " + gamePercentage1 + " " + player2 + "  only earns " + gamePercentage2 + " per game";
                    }
                    else if (gamePercentage2 > gamePercentage1)
                    {
                        //worseMedals[i] = (player2 + " has " + p2Medals[k].getCount() + " " + getMedalName(p2Medals[k].getMedalId(), medals) + " medals \n\t-" + player1 + " only has : " + p1Medals[i].getCount());
                        worseMedals[i] = getMedalName(p2Medals[k].getMedalId(), medals) + " x " + p2Medals[i].getCount() + " : " + "Earned per game: " + gamePercentage2 + " " + player1 + " only earns " + gamePercentage1 + " per game";
                        //getMedalDescription(p2Medals[k].getMedalId(), medals)
                    }
                }
            }
        }

        System.out.println(player1 + " has earned these medals more than " + player2);
        for (int i = 0; i < betterMedals.length; i++)
        {
            if (betterMedals[i] != null) {
                System.out.println(betterMedals[i]);
            }
        }
        System.out.println("\n" + player2 + " has earned these medals more than " + player1);
        for (int i = 0; i < worseMedals.length; i++){
            if (worseMedals[i] != null){
                System.out.println(worseMedals[i]);
            }
        }
        Arrays.sort(allMedals1);
        Arrays.sort(allMedals2);
        p1Has = new long[allMedals1.length];
        p2Has = new long[allMedals2.length];
        for (int i = 0; i < allMedals1.length; i++)
        {
            if (! Arrays.asList(allMedals2).contains(allMedals1[i]))
            {
                p1Has[i] = getMedalID(allMedals1[i], medals);
            }
        }

        for (int i = 0; i < allMedals2.length; i++)
        {
            if (! Arrays.asList(allMedals1).contains(allMedals2[i]))
            {
                p2Has[i] = getMedalID(allMedals2[i], medals);
            }
        }
        System.out.println("\nMedals that " + player1 + " has earned that " + player2 + " hasn't: ");
        for (int k = 0; k < p1Medals.length; k++) {
            for (int i = 0; i < p1Has.length; i++) {
                if (p1Medals[k].getMedalId() == p1Has[i])
                {
//                    System.out.println(player2 + " hasn't earned any " + getMedalName(p1Has[i], medals) + " medals but " + player1 + " has " + p1Medals[k].getCount());
                    System.out.println(getMedalName(p1Has[i], medals) + " : " + getMedalDescription(p1Has[i], medals) + " count: " + p1Medals[k].getCount());
                }
            }
        }
        System.out.println("\nMedals that " + player2 + " has earned that " + player1 + " hasn't: ");
        for (int k = 0; k < p2Medals.length; k++) {
            for (int i = 0; i < p2Has.length; i++) {
                if (p2Medals[k].getMedalId() == p2Has[i])
                {
//                    System.out.println(player1 + " hasn't earned any " + getMedalName(p2Has[i], medals) + " medals but " + player2 + " has " + p2Medals[k].getCount());
                    System.out.println(getMedalName(p2Has[i], medals) + " : " + getMedalDescription(p2Has[i], medals)+ " count: " + p2Medals[k].getCount());
                }
            }
        }
    }

    public static void testPlayerMatches() throws Exception
    {
        double totalGames = totalGames(ARENA, PLAYER_UF);
        Medal[] medals = getMedals();
        Map[] maps = getMaps();
        double kdRatio = 0;
        double averageKills = 0;
        double averageDeaths = 0;
        String bestMatchID = null;
        BaseStats stats = getBaseStats(ARENA, PLAYER_UF);
        int totalShotsFired = stats.getTotalShotsFired();
        int totalShotsLanded = stats.getTotalShotsLanded();
        int totalHeadShots = stats.getTotalHeadshots();
        int totalPowerWeapon = stats.getTotalPowerWeaponGrabs();
        int totalPowerWeaponKills = stats.getTotalPowerWeaponKills();
        int positiveCount = 0;
        Gson gson = new Gson();
        Match[] matches = getMatches();
        for (int i = 0; i < matches.length; i++){
            double totalKills = matches[i].getPlayers().get(0).getTotalKills();
            double totalDeaths = matches[i].getPlayers().get(0).getTotalDeaths();
            double currentKD = totalKills / totalDeaths;
            String matchID = matches[i].getId().getMatchId();
            if (totalDeaths == 0){
                    totalDeaths = 1;
                }
            averageKills += totalKills;
            averageDeaths += totalDeaths;
            if (! getMapName(matches[i].getMapId(), maps).equalsIgnoreCase("Breakout Arena")) {
                if (currentKD > kdRatio && totalKills > 4) {
                    kdRatio = currentKD;
                    bestMatchID = matchID;
                }
            }
            if (currentKD >= 1){
                positiveCount++;
            }
        }
        kdRatio = (double)Math.round(kdRatio *1000d) / 1000d;
//        System.out.println("Number of iterations: " + iterations);
        float percentagePositive = (float) ((positiveCount * 100.0f) / totalGames);
        System.out.println("Your total number of kills: " + averageKills + " Your total number of deaths: " + averageDeaths);
        System.out.println("Your average K/D spread is: " + averageKills / averageDeaths);
        System.out.println("You've had a positive K/D spread " + positiveCount + " times. That's " + percentagePositive + "% of your games!");
        System.out.println("Your best Kill/Death ratio in any Arena game is: " + kdRatio);

        JSONObject obj2 = new JSONObject(postGameCarnage(bestMatchID));
        BaseCarnageReport carnageReport = gson.fromJson(obj2.toString(), BaseCarnageReport.class);
        SpartanRankedPlayerStats[] playerStats = gson.fromJson(obj2.getJSONArray("PlayerStats").toString(), SpartanRankedPlayerStats[].class);
        String mapName = getMapVariant(carnageReport.getMapVariantId()).getName();
        System.out.println("Your best game was played on map: " + mapName);
        System.out.println("Here are the medals you earned in that game: \n");
        for (int i = 0; i < playerStats.length; i++){
            for (MedalAward medal : playerStats[i].getMedalAwards()){
                for (int k = 0; k < medals.length; k++){
                    if (medal.getMedalId() == medals[k].getId()){
                        medal.setName(medals[k].getName());
                    }
                }
                if (playerStats[i].getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    System.out.println(medal.getName() + " x " + medal.getCount());
                }
            }
        }

        System.out.println("You have fired a total of: " + totalShotsFired + " shots");
        System.out.println("Of those, you've landed " + totalShotsFired + " shots");
        double accuracy = (totalShotsLanded/totalShotsFired);
        System.out.println("That's an accuracy of " + accuracy + "%");
        System.out.println("You have murdered " + totalHeadShots + " Spartans with a headshot.");
        System.out.println("You have grabbed a power weapon " + totalPowerWeapon + " times " + " you've killed " + totalPowerWeaponKills + " Spartans with those power weapons");
        System.out.println("You have Spartan Charged " + stats.getTotalShoulderBashKills() + " Spartans");
        System.out.println("You have murdered " + stats.getTotalGrenadeKills() + " Spartans with grenades");
        System.out.println("You have tied the stupid enemy team " + stats.getTotalGamesTied() + " times");
    }

}