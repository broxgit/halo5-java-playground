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

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.broxhouse.h5api.gameType.ARENA;
import static com.broxhouse.h5api.gameType.CUSTOM;
import static com.broxhouse.h5api.gameType.WARZONE;


enum gameType {WARZONE, ARENA, CUSTOM}


public class HaloApi {



    private static final String PLAYER_UF = "That Noah Guy";
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
    private static final String META_CUSTOM_MAPS = BASE_URL + "ugc/h5/players/%s";
    private static final String POST_GAME_CARNAGE = BASE_URL + "stats/h5/arena/matches/%s";
    private static final String POST_GAME_CARNAGE_CUST = BASE_URL + "stats/h5/custom/matches/%s";

    private static boolean cachingResources = false;
    private static boolean cachingMatches = false;
    private static boolean cachingMapVariants = false;
    private static boolean cacheMetaData = false;

    private static String api(String url) throws Exception
    {
        System.out.println(url);
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
        try{
//            testPlayerMatches(CUSTOM);
//            cachePlayerData(CUSTOM);
//            cacheAllPlayerData();
            cacheGameCarnage(CUSTOM);
//            testBaseStats(ARENA);
//            comparePlayers("That Brock Guy", "That Ax Guy");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getFileName(Enum gameType, String gamertag, String cacheItem) throws Exception{
        String fileName = null;
        gamertag = gamertag.toLowerCase();
        String gametype = gameType.toString().toLowerCase();
        switch(gamertag){
            case "that brock guy": fileName = "brock_";
                break;
            case "that ax guy": fileName = "ax_";
                break;
            case "that sturt guy": fileName = "stu_";
                break;
            case "that trev guy": fileName = "trev_";
                break;
            case "phantom6030": fileName = "mitch_";
                break;
            case "kiiyy": fileName = "erin_";
                break;
            default: fileName = gamertag + "_";
        }
        switch(cacheItem){
            case "mapMin": fileName += "mapvars";
                break;
            case "mapMax": fileName += "mapvars2";
                break;
            case "matches": fileName += "matches";
                break;
            case "carnage": fileName += "carnage";
                break;
            default: fileName+= cacheItem;
        }
        switch(gametype){
            case "arena": fileName += "_arena";
                break;
            case "custom": fileName += "_custom";
                break;
            case "warzone": fileName += "_warzone";
                break;
            default: fileName += "_" + gametype;
        }
        fileName += ".txt";

        return fileName;
    }

    public static void cachePlayerData(Enum gameType) throws Exception{
        cachingMatches = true;
        getMatches(gameType);
        TimeUnit.SECONDS.sleep(10);
        cachingMatches = false;
        cachingMapVariants = true;
        favoriteMapVariant(gameType);
        cachingMapVariants = false;
    }

    public static void cacheAllPlayerData() throws Exception{
        Thread customThread = new Thread() {
            @Override
            public void run() {
                try {
                    cachePlayerData(CUSTOM);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }};
        Thread arenaThread = new Thread() {
            @Override
            public void run() {
                try {
                    cachePlayerData(ARENA);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }};
        Thread warzoneThread = new Thread() {
            @Override
            public void run() {
                try {
                    cachePlayerData(WARZONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }};
        customThread.start();
        TimeUnit.SECONDS.sleep(10);
        arenaThread.start();
        TimeUnit.SECONDS.sleep(10);
        warzoneThread.start();
    }

    public static void cacheMetaData() throws Exception{
        cacheMetaData = true;
        getMaps();
        getWeapons();
        getMedals();
    }

    public static void cacheGameCarnage(Enum gameType) throws Exception{
        double totalGames = totalGames(gameType, PLAYER_UF);
        Match[] matches = getMatches(gameType);
        TimeUnit.SECONDS.sleep(8);
        String var3 = "";
        JSONObject obj = null;
        String var = null;
        String fileName = getFileName(gameType, PLAYER_UF, "carnage");
        System.out.println(totalGames);
        for (int i = 0; i < totalGames; i++) {
            if (gameType == ARENA)
                obj = new JSONObject(postGameCarnage(matches[i].getId().getMatchId()));
            else if(gameType == CUSTOM)
                obj = new JSONObject(postCustomGameCarnage(matches[i].getId().getMatchId()));
            var = obj.toString();
            var3 = var3.concat(var);
            var3 = var3 + ",";
            var3 = var3.concat(var);
            if (i % 10 == 0) {
                TimeUnit.SECONDS.sleep(10);
            }
        }
        var3 = var3.substring(0, var3.length() - 1);
        var3 = "[" + var3 + "]";
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"));
        writer.write(var3);
        writer.close();
    }

    public static String formatString(String string)
    {
        string = string.replaceAll("\\s+", "%20");
        return string;
    }


    public static String capitalize(final String line){
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static String postGameCarnage(String matchID) throws Exception
    {
        return api(String.format(POST_GAME_CARNAGE, matchID));
    }

    public static String postCustomGameCarnage(String matchID) throws Exception{
        return api(String.format(POST_GAME_CARNAGE_CUST, matchID));
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

    public static String listCustomMapVariants(String mapVariantID, String player) throws Exception{
        String url =  player + "/mapvariants/" + mapVariantID;
        return api(String.format(META_CUSTOM_MAPS, url));
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
        String fileName = "medals_metadata.txt";
        String medalData = null;
        if (cacheMetaData = true) {
            medalData = listMedals();
            Writer writer1 = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            writer1.write(medalData);
            writer1.close();
        }else{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            medalData = br.readLine();
            br.close();
        }
        Medal[] medals = gson.fromJson(medalData, Medal[].class);
        return medals;
    }

    public static Map[] getMaps() throws Exception
    {
        Gson gson = new Gson();
        String fileName = "maps_metadata.txt";
        String mapData = null;
        if (cacheMetaData = true) {
            mapData = listMaps();
            Writer writer1 = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            writer1.write(mapData);
            writer1.close();
        }else{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            mapData = br.readLine();
            br.close();
        }
        Map[] maps = gson.fromJson(mapData, Map[].class);
        return maps;
    }

    public static Weapon[] getWeapons() throws Exception
    {
        Gson gson = new Gson();
        String fileName = "weapons_metadata.txt";
        String weaponData = null;
        if (cacheMetaData = true) {
            weaponData = listWeapons();
            Writer writer1 = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            writer1.write(weaponData);
            writer1.close();
        }else{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            weaponData = br.readLine();
            br.close();
        }
        Weapon[] weapons = gson.fromJson(weaponData, Weapon[].class);
        return weapons;
    }

    public static Match[] getMatches(Enum gameType) throws Exception {
        JSONObject obj = null;
        String var = null;
        String var2 = "";
        Integer start = 0;
        String fileName = getFileName(gameType, PLAYER_UF, "matches");
        String fileName2 = getFileName(gameType, PLAYER_UF, "mapMin");
        if (cachingMatches){
            double totalGames = totalGames(gameType, PLAYER_UF);
           if(totalGames == 0)
               return null;
            TimeUnit.SECONDS.sleep(8);
            int m = 0;
            String var3 = "";
            double iterations = totalGames / 25;
            for (int i = 1; i < iterations; i++) {
                obj = new JSONObject(playerMatches(PLAYER, gameType.toString().toLowerCase(), start, 25));
                JSONArray obj2 = obj.getJSONArray("Results");
                for (int k = 0; k < obj2.length(); k++) {
                    m++;
//                System.out.println(k);
                    JSONObject obj3 = obj2.getJSONObject(k).getJSONObject("MapVariant");
                    var = obj3.toString();
                    if (k < obj2.length()) {
                        var3 = var3.concat(var);
                        var3 = var3 + ",";
                    } else {
                        var3 = var3.concat(var);
                    }
                }
                var = obj2.toString();
                if (i < iterations) {
                    var = var.substring(1, var.length() - 1);
                    var2 = var2.concat(var);
                    var2 = var2 + ",";
                } else {
                    var2 = var2.concat(var);
                }
                start = start + obj.getInt("Count");
                if (i % 10 == 0) {
                    TimeUnit.SECONDS.sleep(10);
                }
            }
        var3 = var3.substring(0, var3.length() - 1);
        var3 = "[" + var3 + "]";
//            System.out.println(var3);
        Writer writer1 = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName2), "utf-8"));
        writer1.write(var3);
        writer1.close();
        var2 = var2.substring(0, var2.length() - 1);
        var2 = "[" + var2 + "]";

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"));
        writer.write(var2);
        writer.close();
    }else{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        var2 = br.readLine();
        br.close();
    }
        Gson gson = new Gson();
        Match[] matches = gson.fromJson(var2, Match[].class);
        return matches;
    }

//    public static BaseCarnageReport[] getCarnageReport() throws Exception{
//
//    }

    public static Set<String> getResources(Enum gameType) throws Exception
    {
//        JSONObject obj = null;
//        String var = null;
//        String var3 = "";
//        Integer start = 0;
//        int m = 0;
//        double totalGames = totalGames(gameType, PLAYER_UF);
//        double iterations = totalGames/25;
//        TimeUnit.SECONDS.sleep(10);
        String fileName = getFileName(gameType, PLAYER_UF, "mapMin");
//        if(cachingResources) {
//            Gson gson = new Gson();
//            for (int i = 1; i < iterations; i++) {
//                obj = new JSONObject(playerMatches(PLAYER, gameType.toString().toLowerCase(), start, 25));
//                JSONArray obj2 = obj.getJSONArray("Results");
////            System.out.println(i);
//                for (int k = 0; k < obj2.length(); k++) {
//                    m++;
////                System.out.println(k);
//                    JSONObject obj3 = obj2.getJSONObject(k).getJSONObject("MapVariant");
//                    var = obj3.toString();
//                    if (k < obj2.length()) {
//                        var3 = var3.concat(var);
//                        var3 = var3 + ",";
//                    } else {
//                        var3 = var3.concat(var);
//                    }
//                }
//                start = start + 25;
//                if (i % 10 == 0) {
//                    TimeUnit.SECONDS.sleep(10);
//                }
//            }
//            var3 = var3.substring(0, var3.length() - 1);
//            var3 = "[" + var3 + "]";
//            Writer writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(fileName), "utf-8"));
//            writer.write(var3);
//            writer.close();
//        }else{
//            BufferedReader br = new BufferedReader(new FileReader(fileName));
//            var3 = br.readLine();
//            br.close();
//        }
        String var3 = null;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        var3 = br.readLine();
        br.close();
//        System.out.println(var3);
        JSONArray obj3 = new JSONArray(var3);
        Set<String> mapIDs = new HashSet<String>();
        for (int i = 0; i < obj3.length(); i++){
            String mapID = obj3.getJSONObject(i).getString("ResourceId");
            if(mapIDs.contains(mapID)){
                continue;
            }
            else{
                mapIDs.add(mapID);
            }
        }
//        System.out.println(mapIDs.size());
//        System.out.println(var3);
        return mapIDs;
    }

    public static MapVariant getMapVariant(String mapVariantID) throws Exception
    {
        Gson gson = new Gson();
        String mapData = listMapVariants(mapVariantID);
        MapVariant mapVariant = gson.fromJson(mapData, MapVariant.class);
        return mapVariant;
    }

    public static CustomMapVariant getCustomMapVariant(String mapVariantID, String player) throws Exception{
        Gson gson = new Gson();
        String mapData = listCustomMapVariants(mapVariantID, player);
        CustomMapVariant mapVariant = gson.fromJson(mapData, CustomMapVariant.class);
        return mapVariant;
    }

    public static CustomMapVariant[] getCachedCustMapVariants() throws Exception{
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("brock_mapvars2_custom.txt"));
        String var3 = br.readLine();
        br.close();
        CustomMapVariant[] mapVariant = gson.fromJson(var3, CustomMapVariant[].class);
        return mapVariant;
    }

    public static String getCustomMapName(String mapID) throws Exception{
        CustomMapVariant[] mapVariants = getCachedCustMapVariants();
        String mapName = null;
        for (int i = 0; i < mapVariants.length; i++){
            if(mapVariants[i].getIdentity().getResourceId().equalsIgnoreCase(mapID)){
                mapName = mapVariants[i].getName();
            }
        }
            return mapName;
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

    public static String getMapVariantName(String id, MapVariant[] maps) throws Exception{
        String mapName = null;
        for (int i = 0; i < maps.length; i++){
            if(maps[i].getId().equalsIgnoreCase(id)){
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

    public static String testMedalStats(Enum gameType) throws Exception
    {
        JSONArray obj = getPlayerStatsJSON(gameType).getJSONArray("MedalAwards");
        String mostEarnedMedal = null;
        double average = 0;
        int highestMedalCount = 0;
        String var = obj.toString();
//        System.out.println(var);
//
        double games = totalGames(gameType, PLAYER);
        games = (double)Math.round(games *1000d) / 1000d;
        Gson gson = new Gson();
        Medal[] medals = getMedals();
        MedalAward[] stats = gson.fromJson(var, MedalAward[].class);
        for (int row = 0; row < stats.length; row++)
        {
            stats[row].setName(getMedalName(stats[row].getMedalId(), medals));
         }
//
//        System.out.println("Showing medal stats for " + PLAYER_UF);
        for (int row = 0; row < stats.length; row++)
        {
            double medalCount = stats[row].getCount()/games;
            medalCount = (double)Math.round(medalCount *1000d) / 1000d;
//            System.out.println(stats[row].getName() + ": " + stats[row].getCount() + " ||  Earned per game: " + medalCount);
//
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
//        System.out.println("Your most earned medal is the " + mostEarnedMedal + " medal with a total of " + highestMedalCount + " and an average of " + average + " per game");
//
        return ("Your most earned medal is the " + mostEarnedMedal + " medal with a total of " + highestMedalCount + " and an average of " + average + " per game");
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
        String var = obj.toString();
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
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
        System.out.println("Total " + capitalize(gType.toString().toLowerCase()) + " games won: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
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

    public static double getKD(double kills, double deaths){
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

        for (int row = 0; row < stats.length; row++){
            stats[row].setName(getWeaponName(stats[row].getWeaponId().getStockId(), weapons));
        }
        for (int i = 0; i < stats.length; i++) {
            for (int k = i + 1; k < stats.length; k++){
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
        JSONObject obj = getPlayerStatsJSON(gameType);
        String gType = capitalize(gameType.toString().toLowerCase());
        String var = obj.toString();
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
        double totalShotsFired = stats.getTotalShotsFired();
        double totalShotsLanded = stats.getTotalShotsLanded();
        int totalHeadShots = stats.getTotalHeadshots();
        double totalPowerWeapon = stats.getTotalPowerWeaponGrabs();
        double totalPowerWeaponKills = stats.getTotalPowerWeaponKills();
        double powerWeaponKillAvg = (totalPowerWeaponKills/totalPowerWeapon);
        String totalPowerWeaponTime = stats.getTotalPowerWeaponPossessionTime();
        totalPowerWeaponTime = totalPowerWeaponTime.replaceAll("[^-?0-9]+", " ");
        String[] pWeaponTime = totalPowerWeaponTime.trim().split("[a-zA-Z ]+");
        String totalTimePlayed = stats.getTotalTimePlayed();
        totalTimePlayed = totalTimePlayed.replaceAll("[^-?0-9]+", " ");
        String[] playTime = totalTimePlayed.trim().split("[a-zA-Z ]+");
        powerWeaponKillAvg = (double)Math.round(powerWeaponKillAvg * 100d) / 100d;
        double totalDamageDealt = stats.getTotalGrenadeDamage() + stats.getTotalMeleeDamage() + stats.getTotalGroundPoundDamage() + stats.getTotalWeaponDamage() + stats.getTotalPowerWeaponDamage() + stats.getTotalShoulderBashDamage();
        totalDamageDealt = (double)Math.round(totalDamageDealt * 100d) / 100d;
        double accuracy = (totalShotsLanded/totalShotsFired);
        WeaponStats favWeapon = stats.getWeapon();
//        Weapon[] weapon = getWeapons();
//        String favWeaponName = getWeaponName(favWeapon.getWeaponId().getStockId(), weapon);
        accuracy = (double)Math.round(accuracy * 100d);
        System.out.println("\nHere are some Random Stats: ");
        System.out.println(PLAYER_UF + " has completed " + stats.getTotalGamesCompleted()+ " " + capitalize(gType.toString().toLowerCase()) + " games.");
        System.out.println("You have wasted a total of: " + playTime[0] + " days " + playTime[1] + " hours " + playTime[2] + " minutes and "  + playTime[3] + " seconds playing Halo 5!");
        testWeaponKills(gameType);
        System.out.println("You have fired a total of: " + (int)totalShotsFired + " shots. Of those, you've landed " + (int)totalShotsLanded + " shots.");
        System.out.println("That's an accuracy of " + accuracy + "%");
        System.out.println("You have slaughtered " + totalHeadShots + " Spartans with a headshot.");
        System.out.println("You've stroked a power weapon in your hands for a total of " + pWeaponTime[0] + " days " + pWeaponTime[1] + " hours " + pWeaponTime[2] + " minutes and "  + pWeaponTime[3] + " seconds!");
        System.out.println("You have grabbed a power weapon " + (int)totalPowerWeapon + " times, you've killed " + (int)totalPowerWeaponKills + " Spartans with those power weapons.");
        System.out.println("That's an average of " + powerWeaponKillAvg + " kills each time you pick up a power weapon!");
        System.out.println("You have Spartan Charged " + stats.getTotalShoulderBashKills() + " dumb-dumbs.");
        System.out.println("You have murdered " + stats.getTotalGrenadeKills() + " Spartans with grenades.");
        System.out.println("You have tied the stupid enemy team " + stats.getTotalGamesTied() + " time(s).");
        System.out.println("You have dealt " + totalDamageDealt + " damage points in your Arena career.");
        System.out.println("You have performed a total of " + stats.getTotalAssassinations() + " assassinations!");
        System.out.println(testMedalStats(gameType));

//
//
//        List<WeaponStats> weaponList = stats.getWeapons();
//        for(WeaponStats en : weaponList)
//        {
//            System.out.println(en.getName() + " " + en.getTotalKills());
//        }
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

    public static String favoriteMap(Match[] matches, Map[] maps) throws Exception{
        String favMap = null;
        int favMapCount = 0;
        for (int i = 0; i < matches.length; i++){
            for (int k = 0; k < maps.length; k++){
                if (matches[i].getMapVariant().getResourceId().equalsIgnoreCase(maps[k].getId())){
                    maps[k].setCount(maps[k].getCount()+ 1);
                }
            }
        }
        for (int i = 0; i < maps.length; i++){
            for (int k = 0; k < maps.length; k++){
                if (maps[i].getCount() > maps[k].getCount() && maps[i].getCount() >= favMapCount){
                    favMapCount = maps[i].getCount();
                    favMap = maps[i].getName();
                }
//                else if (maps[k].getCount() > maps[i].getCount() && maps[k].getCount() >= favMapCount){
//
//                }
            }
        }
        return ("Your favorite map is: " + favMap + " with a total play count of: " + favMapCount);
    }

    public static String favoriteMapVariant(Enum gameType) throws Exception{
        if (totalGames(gameType, PLAYER_UF) == 0)
            return null;
        if (gameType == CUSTOM){
            return favoriteCustomMapVariant(gameType);
        }
        Match[] matches = getMatches(gameType);
        String var = null;
        int iterations = 1;
        Set<String> mapIDs = getResources(gameType);
        String fileName = getFileName(gameType, PLAYER_UF, "mapMax");
        String fileName2 = getFileName(gameType, PLAYER_UF, "mapMin");
        BufferedReader br = new BufferedReader(new FileReader(fileName2));
        String var3 = br.readLine();
        br.close();
        JSONArray jsonArray = new JSONArray(var3);
        String var2 = "";
        if(cachingMapVariants){
            TimeUnit.SECONDS.sleep(8);
            for (String s: mapIDs){
                if (s != null){
                        MapVariant mv = getMapVariant(s.toLowerCase());
                        System.out.println(mv.getName() + " " +  mv.getId());
                        var = "{\"name\":\"" +mv.getName()+ "\",\"description\":\"" + mv.getDescription() + "\",\"mapImageUrl\":\"" + mv.getMapImageUrl() +  "\",\"mapId\":\"" + mv.getMapId() + "\",\"id\":\"" + mv.getId() +  "\",\"contentId\":\"" + mv.getContentId() + "\"}";
                        var = var + ",";
                        var2 = var2.concat(var);
                        if (iterations % 10 == 0){
                            TimeUnit.SECONDS.sleep(10);
                        }
                    else{
                        continue;
                    }
                }
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            var2 = var2.substring(0, var2.length() - 1);
            var2 = "[" + var2 + "]";
            writer.write(var2);
            writer.close();
            return null;
        }else {
            String favMap = null;
            int favMapCount = 0;
            br = new BufferedReader(new FileReader(fileName));
            var = br.readLine();
            Gson gson = new Gson();
            br.close();
                MapVariant[] mapv = gson.fromJson(var, MapVariant[].class);
                for (int i = 0; i < matches.length; i++) {
                    for (int k = 0; k < mapv.length; k++) {
                        if (getMapVariantName(matches[i].getMapVariant().getResourceId(), mapv).equalsIgnoreCase(mapv[k].getName())) {
                            mapv[k].setCount(mapv[k].getCount() + 1);
                        }
                    }
                }
                for (int i = 0; i < mapv.length; i++) {
                    for (int k = 0; k < mapv.length; k++) {
                        if (mapv[i].getCount() > mapv[k].getCount() && mapv[i].getCount() >= favMapCount) {
                            favMapCount = mapv[i].getCount();
                            favMap = mapv[i].getName();
                        }
                    }
                }

            return ("Your favorite map is " + favMap + " with a total play count of " + favMapCount);
        }
    }

    public static String favoriteCustomMapVariant(Enum gameType) throws Exception {
        Match[] matches = getMatches(gameType);
        String var = null;
        int iterations = 1;
        Set<String> mapIDs = getResources(gameType);
        String fileName = getFileName(gameType, PLAYER_UF, "mapMax");
        String fileName2 = getFileName(gameType, PLAYER_UF, "mapMin");
        BufferedReader br = new BufferedReader(new FileReader(fileName2));
        String var3 = br.readLine();
        br.close();
        JSONArray jsonArray = new JSONArray(var3);
        String var2 = "";
        if (cachingMapVariants) {
            TimeUnit.SECONDS.sleep(8);
            for (String s : mapIDs) {
                if ((jsonArray.getJSONObject(iterations).getString("Owner")).isEmpty() || (jsonArray.getJSONObject(iterations).getString("Owner")) == "") {
                    iterations++;
                    continue;
                }
                CustomMapVariant mv = getCustomMapVariant(jsonArray.getJSONObject(iterations).getString("ResourceId"), formatString(jsonArray.getJSONObject(iterations).getString("Owner")));
                System.out.println(mv.getName() + " " + mv.getBaseMap().getResourceId());
                var = "{\"BaseMap\":{\"ResourceType\":" + mv.getBaseMap().getResourceType() + ",\"ResourceId\":\"" + mv.getBaseMap().getResourceId() + "\",\"OwnerType\":" + mv.getBaseMap().getOwnerType() + ",\"Owner\":null},\"Name\":\"" + mv.getName() + "\",\"Description\":\"" + mv.getDescription() + "\",\"AccessControl\":" + mv.getAccessControl() + ",\"Links\":{},\"CreationTimeUtc\":{\"ISO8601Date\":\"" + mv.getCreationTimeUtc() + "\"},\"LastModifiedTimeUtc\":{\"ISO8601Date\":\"" + mv.getLastModifiedTimeUtc() + "\"},\"Banned\":" + mv.getBanned() + ",\"Identity\":{\"ResourceType\":" + mv.getIdentity().getResourceType() + ",\"ResourceId\":\"" + mv.getIdentity().getResourceId() + "\",\"OwnerType\":" + mv.getIdentity().getOwnerType() + ",\"Owner\":\"" + mv.getIdentity().getOwner() + "\"},\"Stats\":{\"BookmarkCount\":" + mv.getStats().getBookmarkCount() + ",\"HasCallerBookmarked\":" + mv.getStats().getHasCallerBookmarked() + "}}";
                var = var + ",";
                var2 = var2.concat(var);
                iterations++;
                if (iterations % 10 == 0) {
                    TimeUnit.SECONDS.sleep(10);
                } else {
                    continue;
                }
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            var2 = var2.substring(0, var2.length() - 1);
            var2 = "[" + var2 + "]";
            writer.write(var2);
            writer.close();
            return null;
        }else{
            String favMap = null;
            int favMapCount = 0;
            br = new BufferedReader(new FileReader(fileName));
            var = br.readLine();
            Gson gson = new Gson();
            br.close();
            CustomMapVariant[] mapv = gson.fromJson(var, CustomMapVariant[].class);
            for (int i = 0; i < matches.length; i++) {
                for (int k = 0; k < mapv.length; k++) {
                    if (getCustomMapName(matches[i].getMapVariant().getResourceId()) != null && getCustomMapName(matches[i].getMapVariant().getResourceId()).equalsIgnoreCase(mapv[k].getName())) {
                        mapv[k].setCount(mapv[k].getCount() + 1);
                    }
                }
            }
            for (int i = 0; i < mapv.length; i++) {
                for (int k = 0; k < mapv.length; k++) {
                    if (mapv[i].getCount() > mapv[k].getCount() && mapv[i].getCount() >= favMapCount) {
                        favMapCount = mapv[i].getCount();
                        favMap = mapv[i].getName();
                    }
                }
            }
            return ("Your favorite map is " + favMap + " with a total play count of " + favMapCount);
        }
    }

    public static void testPlayerMatches(Enum gameType) throws Exception
    {
        double totalGames = totalGames(gameType, PLAYER_UF);
        Medal[] medals = getMedals();
        Map[] maps = getMaps();
        double kdRatio = 0;
        double averageKills = 0;
        double averageDeaths = 0;
        double bestTotalKills = 0;
        double bestTotalDeaths = 0;
        int matchesDNF = 0;
        String bestMatchID = null;
        BaseStats stats = getBaseStats(gameType, PLAYER_UF);
        int positiveCount = 0;
        Gson gson = new Gson();
        Match[] matches = getMatches(gameType);
        for (int i = 0; i < matches.length; i++){
            double totalKills = matches[i].getPlayers().get(0).getTotalKills();
            double totalDeaths = matches[i].getPlayers().get(0).getTotalDeaths();
            double currentKD = 0;
            if (totalDeaths == 0){
                totalDeaths = 1;
                currentKD = totalKills / totalDeaths;
                totalDeaths = 0;
            }
            if (matches[i].getPlayers().get(0).getResult() == 0){
                matchesDNF++;
            }
            String matchID = matches[i].getId().getMatchId();
            averageKills += totalKills;
            averageDeaths += totalDeaths;
            if (! getMapName(matches[i].getMapId(), maps).equalsIgnoreCase("Breakout Arena")) {
                if (currentKD > kdRatio && totalKills > 4) {
                    kdRatio = currentKD;
                    bestMatchID = matchID;
                    bestTotalKills = totalKills;
                    bestTotalDeaths = totalDeaths;
                }
            }
            if (currentKD >= 1){
                positiveCount++;
            }
        }
        kdRatio = (double)Math.round(kdRatio *1000d) / 1000d;
        System.out.println(PLAYER_UF + " has played: " + (int)totalGames + " " + capitalize(gameType.toString().toLowerCase()) + " games");
        double percentagePositive = (positiveCount/totalGames);
        percentagePositive = (double)Math.round(percentagePositive *100d);
        System.out.println("Your total number of kills: " + (int)averageKills + " Your total number of deaths: " + (int)averageDeaths);
        System.out.println("Your average K/D spread is: " + getKD(stats.getTotalKills(),stats.getTotalDeaths()));
        System.out.println("You have quit or been booted from " + matchesDNF + " games");
        System.out.println("You have assisted your teammates " + stats.getTotalAssists() + " times");
        System.out.println("You've had a positive K/D spread " + positiveCount + " times. That's " + percentagePositive + "% of your games!");
        System.out.println("Your best Kill/Death ratio in any " + capitalize(gameType.toString().toLowerCase()) + " game is: " + kdRatio + " with " + (int)bestTotalKills + " kills and " + (int)bestTotalDeaths + " deaths!");
//        System.out.println(bestMatchID);
        JSONObject obj2 = null;
        if (gameType == CUSTOM){
            obj2 = new JSONObject(postCustomGameCarnage(bestMatchID));
        }else if(gameType == ARENA){
            obj2 = new JSONObject(postGameCarnage(bestMatchID));
        }
        SpartanRankedPlayerStats[] playerStats = gson.fromJson(obj2.getJSONArray("PlayerStats").toString(), SpartanRankedPlayerStats[].class);
        BaseCarnageReport carnageReport = gson.fromJson(obj2.toString(), BaseCarnageReport.class);
        String mapName = null;
        if (gameType == ARENA){
            mapName = getMapVariant(carnageReport.getMapVariantId()).getName();
        }else if(gameType == CUSTOM){
            mapName = getCustomMapName(carnageReport.getMapVariantId());
        }else if(gameType == WARZONE){
            mapName = "Warzone Map";
        }
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
        TimeUnit.SECONDS.sleep(8);
        testBaseStats(gameType);
        System.out.println(favoriteMapVariant(gameType));
    }

}