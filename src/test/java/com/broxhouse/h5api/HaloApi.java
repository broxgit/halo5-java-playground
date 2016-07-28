package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.Medal;
import com.broxhouse.h5api.models.metadata.Playlist;
import com.broxhouse.h5api.models.metadata.Weapon;
import com.broxhouse.h5api.models.stats.common.MedalAward;
import com.broxhouse.h5api.models.stats.common.WeaponStats;
import com.broxhouse.h5api.models.stats.matches.Match;
import com.broxhouse.h5api.models.stats.reports.BaseStats;
import com.broxhouse.h5api.models.stats.reports.TeamPlayer;
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
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.broxhouse.h5api.gameType.ARENA;
import static com.broxhouse.h5api.gameType.CUSTOM;
import static com.broxhouse.h5api.gameType.WARZONE;


enum gameType {WARZONE, ARENA, CUSTOM}

public class HaloApi {

    static JSonHelper jSonHelper = new JSonHelper();

    private static final String PLAYER_UF = "That Brock Guy";
    private static final String PLAYER = formatString(PLAYER_UF);
    private static final String TOKEN = "293bb4a86da743bdb983b97efa5bb265";
    private static final String STATS_URL = "https://www.haloapi.com/stats/h5/";
    private static final String META_URL = "https://www.haloapi.com/metadata/h5/metadata/";
    private static final String PLAYER_MATCHES = STATS_URL + "players/" + PLAYER + "/matches";
    private static final String CUSTOM_STATS = STATS_URL + "servicerecords/custom?players=%s";
    private static final String ARENA_STATS = STATS_URL + "servicerecords/arena?players=%s";
    private static final String WARZONE_STATS = STATS_URL + "servicerecords/warzone?players=%s";
    private static final String META_WEAPONS = META_URL + "weapons";
    private static final String META_MEDALS = META_URL + "medals";
    private static final String META_PLAYLISTS = META_URL + "playlists";

    public static String formatString(String string)
    {
        string = string.replaceAll("\\s+", "%20");
        return string;
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

//    private static String api(String url) throws Exception {
//        System.out.println(url);
//        URL apiUrl = new URL(url);
//        HttpURLConnection urlConn = (HttpURLConnection)apiUrl.openConnection();
//        urlConn.setRequestMethod("GET");
//        urlConn.setRequestProperty("Ocp-Apim-Subscription-Key", TOKEN);
//
//        System.out.println(urlConn.getResponseCode());
//
//        InputStream response = urlConn.getInputStream();
//
//            Scanner scanner = new Scanner(response);
//            String responseBody = scanner.useDelimiter("\\A").next();
//            System.out.println(responseBody);
//            return responseBody;
//
//    }

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

    public static void testMedalStats(Enum gameType) throws Exception
    {
        JSONArray obj = null;
        String mostEarnedMedal = null;
        String gType = null;
        double average = 0;
        int highestMedalCount = 0;
        if (gameType == WARZONE)
        {
            obj = new JSONObject(warzoneMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat").getJSONArray("MedalAwards");
            gType = "Warzone";
        }
        if (gameType == ARENA)
        {
            obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats").getJSONArray("MedalAwards");
            gType = "Arena";
        }
        if (gameType == CUSTOM)
        {
            obj = new JSONObject(customMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats").getJSONArray("MedalAwards");
            gType = "Custom";
        }
        String var = obj.toString();
        System.out.println(var);
        double games = totalGames(gameType);
        games = (double)Math.round(games *1000d) / 1000d;
        Gson gson = new Gson();
        String medalData = listMedals();
        Medal[] medals = gson.fromJson(medalData, Medal[].class);
        MedalAward[] stats = gson.fromJson(var, MedalAward[].class);
        for (int row = 0; row < stats.length; row++)
        {
            for (int i = 0; i < medals.length; i++)
            {
                if (medals[i].getId() == stats[row].getMedalId())
                {
                    stats[row].setName(medals[i].getName());
                }
            }
        }
        System.out.println("Showing medal stats for " + PLAYER_UF);
        for (int row = 0; row < stats.length; row++)
        {
            double medalCount = stats[row].getCount()/games;
            medalCount = (double)Math.round(medalCount *1000d) / 1000d;
            System.out.println(stats[row].getName() + ": " + stats[row].getCount() + " ||  Earned per game: " + medalCount);
        }
//        for (int row = 0; row < stats.length; row++)
//        {
//            if (stats[row].getName().equalsIgnoreCase("Top Gun"))
//            {
//                System.out.println("Brock has earned: " + stats[row].getCount() + " Top Gun medals, which is way more than Axel");
//            }
//        }
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

    public static double totalGames(Enum gameType) throws Exception
    {
        JSONObject obj = null;
        String gType = null;
        if (gameType == WARZONE)
        {
            obj = new JSONObject(warzoneMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat");
            gType = "Warzone";
        }
        if (gameType == ARENA)
        {
            obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats");
            gType = "Arena";
        }
        if (gameType == CUSTOM)
        {
            obj = new JSONObject(customMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats");
            gType = "Custom Games";
        }
        String var = obj.toString();
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
        System.out.println("Total " + gType + " games played: " + stats.getTotalGamesCompleted());
        return stats.getTotalGamesCompleted();
    }

    public static double totalGamesAll() throws Exception
    {
        double totalGames = totalGames(CUSTOM) + totalGames(WARZONE) + totalGames(ARENA);
        return totalGames;
    }

    public static String totalWins(Enum gameType) throws Exception
    {
        JSONObject obj = null;
        String gType = null;
        if (gameType == WARZONE)
        {
            obj = new JSONObject(warzoneMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat");
            gType = "Warzone";
        }
        if (gameType == ARENA)
        {
            obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats");
            gType = "Arena";
        }
        if (gameType == CUSTOM)
        {
            obj = new JSONObject(customMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats");
            gType = "Custom Games";
        }
        String var = obj.toString();
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
        System.out.println("Total " + gType + " games won: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
        String winLoss = ("Total number of Wins: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
        return winLoss;
    }

    public static void testWeaponKills(Enum gameType) throws Exception
    {
        JSONArray obj = null;
        String favWeapon = null;
        int totalKills = 0;
        if (gameType == WARZONE)
        {
            obj = new JSONObject(warzoneMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat").getJSONArray("WeaponStats");
        }
        if (gameType == ARENA)
        {
            obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats").getJSONArray("WeaponStats");
        }
        if (gameType == CUSTOM)
        {
            obj = new JSONObject(customMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats").getJSONArray("WeaponStats");
        }
        Gson gson = new Gson();
        String var = obj.toString();
        WeaponStats[] stats = gson.fromJson(var, WeaponStats[].class);
        String weaponData = listWeapons();
        Weapon[] weapons = gson.fromJson(weaponData, Weapon[].class);
        double games = totalGames(gameType);
        String wl = totalWins(gameType);
        games = (double)Math.round(games *1000d) / 1000d;

        for (int row = 0; row < stats.length; row++)
        {
            for (int i = 0; i < weapons.length; i++)
            {
                if (weapons[i].getId() == stats[row].getWeaponId().getStockId())
                {
                    stats[row].setName(weapons[i].getName());
                }
            }
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

    public static void testBaseStats(Enum gameType) throws Exception
    {
        JSONObject obj = null;
        String gType = null;
        if (gameType == WARZONE)
        {
            obj = new JSONObject(warzoneMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat");
            gType = "Warzone";
        }
        if (gameType == ARENA)
        {
            obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats");
            gType = "Arena";
        }
        if (gameType == CUSTOM)
        {
            obj = new JSONObject(customMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats");
            gType = "Custom";
        }
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

    public static void testPlayerMatches() throws Exception
    {
        JSONObject obj = null;
        String var = null;
        String var2 = "";
        double iterations = totalGames(ARENA) / 25;
        double totalGames = totalGames(ARENA);
        int count = 25;
        int start = 0;
        BigDecimal kdRatio = new BigDecimal(0);
        BigDecimal averageKills = new BigDecimal(0);
        BigDecimal averageDeaths = new BigDecimal(0);
        int avgKills = 0;
        int avgDeaths = 0;

        for (int i = 0; i < iterations; i++)
        {
//            System.out.println(start);
            obj = new JSONObject(playerMatches(PLAYER, "arena", start, 25));
//            System.out.println(count);
//            String var1 = obj.toString();
//            System.out.println(var1);
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
//            System.out.println(var);
//            count = count + obj.getInt("Count");
//            System.out.println(count);
            start = start + obj.getInt("Count");
            if (i % 10 == 0)
            {
                TimeUnit.SECONDS.sleep(10);
            }
        }
        var2 = var2.substring(0, var2.length() - 1);
        var2 = "[" + var2 + "]";
//        System.out.println(var2);
        Gson gson = new Gson();
        Match[] matches = gson.fromJson(var2, Match[].class);
        List<List<TeamPlayer>> players = new ArrayList<List<TeamPlayer>>();
        for (int i = 0; i < matches.length; i++)
        {
//            System.out.println(matches[i].getPlayers());
            players.add(matches[i].getPlayers());
        }
//        System.out.println(matches.length);

        for (List<TeamPlayer> teamPlayer : players)
        {
            for (TeamPlayer player : teamPlayer)
            {
                BigDecimal totalKills = new BigDecimal(player.getTotalKills());
                BigDecimal totalDeaths = new BigDecimal(player.getTotalDeaths());
//                BigDecimal one = new BigDecimal(1);
                if (totalDeaths.equals(0)){
                    totalDeaths = new BigDecimal(1);
                }
                if (totalKills.equals(0)){
                    totalKills = new BigDecimal(1);
                }
                BigDecimal currentKD = totalKills.divide(totalDeaths, 2, BigDecimal.ROUND_HALF_UP);
                avgDeaths += player.getTotalDeaths();
                avgKills += player.getTotalKills();
                int res = currentKD.compareTo(kdRatio);
                System.out.println(currentKD);
                if (res == 1)
                {
                    kdRatio = currentKD;
                    System.out.println(currentKD);
                }
            }
        }
        averageDeaths = new BigDecimal(avgDeaths);
        averageKills = new BigDecimal(avgKills);
        //kdRatio= (double)Math.round(kdRatio *1000d) / 1000d;
        System.out.println("Number of iterations: " + iterations);
        System.out.println("Your total number of kills: " + averageKills + " Your total number of deaths: " + averageDeaths);
        System.out.println("Your best Kill/Death ratio in any Arena game is: " + kdRatio);
        System.out.println("Your average K/D spread is: " + averageKills.divide(averageDeaths, 2, BigDecimal.ROUND_HALF_UP));
    }


}