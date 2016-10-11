package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.*;
import com.broxhouse.h5api.models.stats.common.*;
import com.broxhouse.h5api.models.metadata.Map;
import com.broxhouse.h5api.models.stats.common.Impulse;
import com.broxhouse.h5api.models.stats.matches.Match;
import com.broxhouse.h5api.models.stats.reports.*;
import com.broxhouse.h5api.models.stats.servicerecords.ArenaStat;
import com.broxhouse.h5api.models.stats.servicerecords.BaseServiceRecordResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.net.URI;
import java.util.*;
import java.util.HashSet;

import static com.broxhouse.h5api.gameType.*;


enum gameType {WARZONE, ARENA, CUSTOM, NA}


public class HaloApi {

    static Logger log = Logger.getLogger("Logging");

    public static String PLAYER_UF = "That Brock Guy";
    public final String PLAYER = formatString(PLAYER_UF);
    private static final String TOKEN = "293bb4a86da743bdb983b97efa5bb265";
    private static final String BASE_URL = "https://www.haloapi.com/";
    private static final String STATS_URL = "https://www.haloapi.com/stats/h5/";
    private static final String META_URL = "https://www.haloapi.com/metadata/h5/metadata/";
    private static final String META_IMPULSES = META_URL + "impulses";
    private final String PLAYER_MATCHES = STATS_URL + "players/%s/matches";
    private static final String CUSTOM_STATS = STATS_URL + "servicerecords/custom?players=%s";
    private static final String ARENA_STATS = STATS_URL + "servicerecords/arena?players=%s";
    private static final String WARZONE_STATS = STATS_URL + "servicerecords/warzone?players=%s";
    private static final String META_WEAPONS = META_URL + "weapons";
    private static final String META_MEDALS = META_URL + "medals";
    private static final String META_PLAYLISTS = META_URL + "playlists";
    private static final String META_MAPS = META_URL + "maps";
    private static final String META_MAP_VARIANTS = META_URL + "map-variants/%s";
    private static final String META_GAME_VARIANTS = META_URL + "game-variants/%s";
    private static final String META_UGC = BASE_URL + "ugc/h5/players/%s";
    private static final String POST_GAME_CARNAGE = BASE_URL + "stats/h5/arena/matches/%s";
    private static final String POST_GAME_CARNAGE_CUST = BASE_URL + "stats/h5/custom/matches/%s";
    private static final long HALO_RELEASE_DAYS = 0;

    static Database db = new Database();
    static PopulateDatabase pd = new PopulateDatabase();
    static Gson gson = new Gson();

    public HaloApi() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s %n");
    }

    private static String api(String url) throws Exception
    {
        String getResponse = null;
        boolean callSuccessful = false;
        int i = 0;
        String[] token = new String[]{"ad00d31dde2c44a8b6f07c05621699d9", "a2cb028867254a79b5783c3786d19bfe", "293bb4a86da743bdb983b97efa5bb265"};
        while (!callSuccessful) {
//        log.info(url);
            HttpClient httpclient = HttpClients.createDefault();

            URIBuilder builder = new URIBuilder(url);

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
//            if (i == 0)
                request.setHeader("Ocp-Apim-Subscription-Key", token[0]);
//            else if (i == 1)
//                request.setHeader("Ocp-Apim-Subscription-Key", token[1]);
//            else if (i == 2)
//                request.setHeader("Ocp-Apim-Subscription-Key", token[2]);
//            else {
//                request.setHeader("Ocp-Apim-Subscription-Key", token[0]);
//            }

            // Request body
//            StringEntity reqEntity = new StringEntity("{body}");
//            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
//            log.info(statusCode + "");
            if (entity != null) {
                getResponse = EntityUtils.toString(entity);
                if (statusCode == 200) {
                    callSuccessful = true;
                }
                if (statusCode == 404 || statusCode == 503){
//                    log.info("404 status code");
                    return "{" +
                            "  \"Results\": []," +
                            "  \"Start\": 0," +
                            "  \"Count\": 100," +
                            "  \"ResultCount\": 0," +
                            "  \"TotalCount\": 0," +
                            "  \"Links\": {}" +
                            "}";
                }
                else if (statusCode != 200 && statusCode != 429)
                    log.info(getResponse);
                else if (statusCode == 429) {
//                    if (i >= 0 && i < 3) {
//                        log.info(token[i]);
                        String temp = getResponse.replaceAll("\\D+", "").replaceAll("429", "");
                        int waitTime = Integer.parseInt(temp);
                        Thread.sleep(waitTime * 1000);
//                    }
//                    log.info("Rate limit exceeded, waiting " + waitTime + " seconds before trying again.");
//                    i++;
                }
            } else {
                return "{" +
                        "  \"Results\": []," +
                        "  \"Start\": 0," +
                        "  \"Count\": 100," +
                        "  \"ResultCount\": 0," +
                        "  \"TotalCount\": 0," +
                        "  \"Links\": {}" +
                        "}";
            }
        }
        return getResponse;
    }

    public void printData() throws Exception{
//        db.clearTable("CUSTOMMATCHESBLOB");
//        Map[] metaData = getMaps();
//        MapVariant[] metaData = getArenaMapVariants();
//        CustomMapVariant[] metaData = (CustomMapVariant[]) db.getMetadataFromDB(dataType.CUSTOMMAPVARIANTS, false, NA);
        String[] metaData = Database.getMetadataTextFromDB(dataType.CARNAGE, true, ARENA);
//        CarnageReport[] metaData = getPlayerCarnageReports(ARENA);
//        Weapon[] metaData = (Weapon[]) db.getMetadataFromDB(dataType.MEDALS);
        int i = 0;
        for (; i < metaData.length; i++){
            log.info(metaData[i]);
        }
        log.info("" + metaData.length);
    }

    public void test() throws Exception {
//        List<String> players = db.getPlayersFromDB();
//        log.info(players.size());
//        Match[] matches = getAllMatches(ARENA);
//        log.info("" + matches.length);
//        CarnageReport[] carnageReports = getAllCarnageReports(ARENA);
        log.info("" + Database.getNumberOfRows(dataType.CARNAGE, true, ARENA));
//        db.clearTable("arenamatchesblob");
//        MapVariant[] maps = new MapVariant[1];
//        maps[0] = getMapVariant("17f171aa-1464-4c2e-8cad-b4f4ccab9238");
//        db.writeMapVariantsToDB(maps);
    }


    public static void main(String[] args) throws Exception {
        try{
            long start = System.currentTimeMillis()/1000;
            HaloApi hapi = new HaloApi();
//            hapi.printData();
//            hapi.clearSparanCompanyTables();
//            hapi.cacheThatOneSpartanCompany();
//            pd.cacheMatches(ARENA, true);
//            pd.cachePlayerCarnageThreadTest(ARENA);
            pd.cacheArenaGameTypes();
//            hapi.testPlayerMatches(ARENA);
//            hapi.testImpulses();
//            pd.cacheGameCarnage(ARENA, true, null);
//            pd.cacheAShitTonOfPlayerCaranage(ARENA);
//            pd.cacheCustomMaps();
//            hapi.comparePlayers("That Ax Guy", "That Brock Guy", CUSTOM);
//            hapi.cacheEnemyKills(CUSTOM);
//            hapi.cachePlayerData(CUSTOM, hapi.PLAYER_UF);
//            log.info(hapi.killedByOponent(CUSTOM));
//            hapi.cacheAllPlayerData(hapi.PLAYER_UF);
//            pd.cacheGameCarnage(ARENA, true);
//            pd.cacheAShitTonOfMatches(ARENA);
//            pd.cacheMatchesThreadTest(ARENA);
//            db.addItemsToDatabase(dataType.PLAYERS);
//            log.info(hapi.favoriteMapVariant(ARENA));
//            log.info(hapi.favoriteCustomMapVariant(CUSTOM));
//            log.info(hapi.killedByOponent(CUSTOM));
//            hapi.getPlayerMatches(ARENA);
//            hapi.testBaseStats(ARENA);
//            hapi.postCustomGameCarnage("af5c2264-91d4-4056-a206-7c4111351d24");
//            hapi.comparePlayers("That Ax Guy", "That Brock Guy", CUSTOM);
            long endTime = System.currentTimeMillis()/1000;
            long duration = (endTime - start);
            log.info("duration:" + duration);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cacheThatOneSpartanCompany() throws Exception {
        pd.cacheMatches(ARENA, true);
        pd.cachePlayerCarnageThreadTest(ARENA);
        pd.cacheMatches(CUSTOM, true);
        pd.cacheGameCarnage(CUSTOM, true, null);
//        PLAYER_UF = "That Brock Guy";
//        pd.cacheMatches(ARENA, true);
//        pd.cachePlayerCarnageThreadTest(ARENA);
//        pd.cacheMatches(CUSTOM, true);
//        pd.cachePlayerCarnageThreadTest(CUSTOM);

    }

    public String getPlayer() {
        return PLAYER_UF;
    }

    public void clearSparanCompanyTables() throws Exception {
            db.clearTable("THATNOAHGUYCUSTOMCARNAGETEXT");
            db.clearTable("THATBROCKGUYCUSTOMCARNAGETEXT");
            db.clearTable("THATTREVGUYCUSTOMCARNAGETEXT");
            db.clearTable("THATAXGUYCUSTOMCARNAGETEXT");
            db.clearTable("THATSTURTGUYCUSTOMCARNAGETEXT");
            db.clearTable("THATNOAHGUYARENACARNAGETEXT");
            db.clearTable("THATBROCKGUYARENACARNAGETEXT");
            db.clearTable("THATTREVGUYARENACARNAGETEXT");
            db.clearTable("THATAXGUYARENACARNAGETEXT");
            db.clearTable("THATSTURTGUYARENACARNAGETEXT");
            db.clearTable("THATNOAHGUYCUSTOMMATCHESTEXT");
            db.clearTable("THATBROCKGUYCUSTOMMATCHESTEXT");
            db.clearTable("THATTREVGUYCUSTOMMATCHESTEXT");
            db.clearTable("THATAXGUYCUSTOMMATCHESTEXT");
            db.clearTable("THATSTURTGUYCUSTOMMATCHESTEXT");
            db.clearTable("THATNOAHGUYARENAMATCHESTEXT");
            db.clearTable("THATBROCKGUYARENAMATCHESTEXT");
            db.clearTable("THATTREVGUYARENAMATCHESTEXT");
            db.clearTable("THATAXGUYARENAMATCHESTEXT");
            db.clearTable("THATSTURTGUYARENAMATCHESTEXT");
    }

    public static String getTotalDuration(long timeInSeconds) throws Exception {
        String day, hour = null, minute = null, second = null;
        int days = (int) TimeUnit.SECONDS.toDays(timeInSeconds);
        long hours = TimeUnit.SECONDS.toHours(timeInSeconds) - (days *24);
        long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds) - (TimeUnit.SECONDS.toHours(timeInSeconds)* 60);
        long seconds = TimeUnit.SECONDS.toSeconds(timeInSeconds) - (TimeUnit.SECONDS.toMinutes(timeInSeconds) *60);
        if (days > 1 || days == 0)
            day = "days";
        else
            day = "day";
        if (hours > 1 || hours == 0)
            hour = "hours";
        else
            hour = "hour";
        if (minutes > 1 || minutes == 0)
            minute = "minutes";
        else
            minute = "minute";
        if (seconds > 1 || seconds == 0)
            second = "seconds";
        else
            second = "second";
        return " " + days + " "+ day +", " + hours + " "+ hour +", " + minutes + " " + minute + ", and " + seconds + " " + second;
    }

    public static long getHaloReleaseTime() throws Exception {
        String haloReleaseDate = "2015-10-27T00:00:00Z";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        long releaseDate = sdf.parse(haloReleaseDate).getTime()/1000;
        long today = System.currentTimeMillis() / 1000;
        return today - releaseDate;
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
            case "mapMin": fileName += "mapSimpleData";
                break;
            case "mapMax": fileName += "mapAllData";
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

    public  String formatString(String string)
    {
        string = string.replaceAll("\\s+", "%20");
        return string;
    }

    public  String capitalize(final String line){
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public  String postGameCarnage(String matchID) throws Exception
    {
        return api(String.format(POST_GAME_CARNAGE, matchID));
    }

    public  String postCustomGameCarnage(String matchID) throws Exception{
        return api(String.format(POST_GAME_CARNAGE_CUST, matchID));
    }

    public  String playerMatches(String gt, String modes, int start, int count) throws Exception {
        String pURL = String.format(PLAYER_MATCHES, formatString(gt));
        pURL = pURL +"?modes=" + modes + "&";
        pURL = pURL + "start=" + start + "&";
        pURL = pURL + "count=" + count;

        return api(pURL);
    }

    public  String warzoneMatches(String gt) throws Exception{
        return api(String.format(WARZONE_STATS, gt));
    }

    public  String customMatches(String gt) throws Exception
    {
        return api(String.format(CUSTOM_STATS, gt));
    }

    public  String arenaStats(String gt) throws Exception
    {
        return api(String.format(ARENA_STATS, gt));
    }

    public  String listMedals() throws Exception
    {
        return api(META_MEDALS);
    }

    public  String listWeapons() throws Exception
    {
        return api(META_WEAPONS);
    }

    public  String listPlaylists() throws Exception
    {
        return api(META_PLAYLISTS);
    }

    public  String listMaps() throws Exception {
        return api(META_MAPS);
    }

    public  String listMapVariants(String mapVariantID) throws Exception {
        return api(String.format(META_MAP_VARIANTS, mapVariantID));
    }

    public  String listCustomMapVariants(String mapVariantID, String player) throws Exception{
        String url =  player + "/mapvariants/" + mapVariantID;
        return api(String.format(META_UGC, url));
    }

    public String listCustomMapVariantsByPlayer(String player) throws Exception {
        String url = player + "/mapvariants";
        return api(String.format(META_UGC, url));
    }

    public String listGameVariant(String gameVariantID) throws Exception{
        return api(String.format(META_GAME_VARIANTS, gameVariantID));
    }

    public String listCustomGameVariants(String gameVariantID, String player) throws Exception{
        String url =  player + "/gamevariants/" + gameVariantID;
        return api(String.format(META_UGC, url));
    }

    public String listImpulses() throws Exception {
        return api(META_IMPULSES);
    }


    public  void testJSONWeapons() throws Exception {

        Weapon[] data = gson.fromJson(listWeapons(), Weapon[].class);
        log.info(Arrays.toString(data));
        for (int i = 0; i < data.length; i++){
            log.info(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public  void testJSONMedals() throws Exception
    {

        Medal[] data = gson.fromJson(listMedals(), Medal[].class);
        log.info(Arrays.toString(data));
        for (int i = 0; i < data.length; i++){
            log.info(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public  Medal[] getMedals() throws Exception {
        return gson.fromJson(listMedals(), Medal[].class);
    }

    public  Map[] getMaps() throws Exception{
        return gson.fromJson(listMaps(), Map[].class);
    }

    public  Weapon[] getWeapons() throws Exception {
        return gson.fromJson(listWeapons(), Weapon[].class);

//        String[] metaData = Database.getMetadataTextFromDB(dataType.WEAPONS, false, NA);
//        Weapon[] metaDataA = new Weapon[metaData.length];
//        for (int i = 0; i < metaData.length; i++){
//            metaDataA[i] = gson.fromJson(metaData[i], .class);
//        }
//        return metaDataA;
//        return (Weapon[]) Database.getMetadataFromDB(dataType.WEAPONS, false, NA);
    }

    public List<String> getPlayers() throws Exception{
        return Database.getPlayersFromDB(false);
    }

    public Match[] getPlayerMatches(Enum gameType) throws Exception{
//        if (Database.getMetadataFromDB(dataType.MATCHES, true, gameType) == null)
//            pd.cacheMatches(gameType, true);
//        String[] metaData = Database.getMetadataTextFromDB(dataType.MATCHES, true, gameType);
//        Match[] metaDataA = new Match[metaData.length];
//        for (int i = 0; i < metaData.length; i++){
//            metaDataA[i] = gson.fromJson(metaData[i], Match.class);
//        }
//        return metaDataA;
        if (Database.getMetadataFromDB(dataType.MATCHES, true, gameType) == null)
            pd.cacheMatches(gameType, true);
        return (Match[]) Database.getMetadataFromDB(dataType.MATCHES, true, gameType);
    }

    public Match[] getAllMatches(Enum gameType) throws Exception{
//        if ((Match[]) db.getMetadataFromDB(dataType.MATCHES, false, gameType) == null)
//            pd.cacheMatches(gameType, true);
//        return (Match[]) db.getMetadataFromDB(dataType.MATCHES, false, gameType);
        String[] matches = Database.getMetadataTextFromDB(dataType.MATCHES, false, gameType);
        Match[] matches1 = new Match[matches.length];
        for (int i = 0; i < matches.length; i++){
            matches1[i] = gson.fromJson(matches[i], Match.class);
        }
        return matches1;
    }


    public  CarnageReport[] getPlayerCarnageReports(Enum gameType) throws Exception{
//        String[] metaData = Database.getMetadataTextFromDB(dataType.CARNAGE, true, gameType);
//        CarnageReport[] metaDataA = new CarnageReport[metaData.length];
//        for (int i = 0; i < metaData.length; i++){
//            try {
//                metaDataA[i] = gson.fromJson(metaData[i], CarnageReport.class);
//            }catch (JsonSyntaxException e){/**log.info(metaData[i])**/}
//        }
//        return metaDataA;
        return (CarnageReport[]) Database.getMetadataFromDB(dataType.CARNAGE, true, gameType);
//        String[] carnageReports = db.getMetadataTextFromDB(dataType.CARNAGE, true, gameType);
//        CarnageReport[] reports = new CarnageReport[carnageReports.length];
//        int finalI = 0;
//        try {
//            for (int i = 0; i < reports.length; i++) {
//                reports[i] = gson.fromJson(carnageReports[i], CarnageReport.class);
//                finalI = i;
//            }
//        }catch (JsonSyntaxException e){
//            log.info(reports[finalI+1].getMatchId());
//            log.info(finalI + "");
//            log.info(carnageReports[finalI+1]);
//        }
//        return reports;
    }

    public CarnageReport[] getAllCarnageReports(Enum gameType) throws Exception {
        String[] metaData = Database.getMetadataTextFromDB(dataType.CARNAGE, false, gameType);
        CarnageReport[] metaDataA = new CarnageReport[metaData.length];
        for (int i = 0; i < metaData.length; i++){
            metaDataA[i] = gson.fromJson(metaData[i], CarnageReport.class);
        }
        return metaDataA;
    }

    public  Set<String> getUniqueResources(Resource[] resources) throws Exception {
        Set<String> mapIDs = new HashSet<>();
        for (int i = 0; i < resources.length; i++){
            if(mapIDs.contains(resources[i].getResourceId()))
                continue;
            else
                mapIDs.add(resources[i].getResourceId());
        }
        return mapIDs;
    }

    public  MapVariant getMapVariant(String mapVariantID) throws Exception {
        String mapData = listMapVariants(mapVariantID);
        MapVariant mapVariant = gson.fromJson(mapData, MapVariant.class);
        return mapVariant;
    }

    public GameVariant getGameVariant(String gameVariantID) throws Exception{

        String gameData = listGameVariant(gameVariantID);
//        log.info(gameVariantID);
        return gson.fromJson(gameData, GameVariant.class);
    }

    public String getMapOwner(String mapID) throws Exception{
        String mapOwner = null;
        CustomMapVariant[] maps = getCachedCustMapVariants();
        for (int i = 0; i < maps.length; i++){
            if (mapID.equalsIgnoreCase(maps[i].getIdentity().getResourceId())) {
                mapOwner = maps[i].getIdentity().getOwner();
                break;
            }
        }
        if (mapOwner != null && mapOwner.contains(" "))
            mapOwner = formatString(mapOwner);
        return mapOwner;
    }

    public  CustomMapVariant getCustomMapVariant(String mapVariantID, String player) throws Exception{

        if (player == null)
//            log.info(mapVariantID);
        player = formatString(player);
        String mapData = listCustomMapVariants(mapVariantID, player);
        return gson.fromJson(mapData, CustomMapVariant.class);
    }

    public CustomMapVariant[] getCustomMapVariantsByPlayer(String player) throws Exception {

        JSONObject obj = new JSONObject(listCustomMapVariantsByPlayer(player));
        String mapData = obj.getJSONArray("Results").toString();
//        log.info(mapData);
        if (mapData.contains("ResultCount\": 0")){
            return null;
        }
        else if (mapData.contains("ResultCount\": 1")){
            CustomMapVariant[] oneMap = new CustomMapVariant[1];
            oneMap[0] = gson.fromJson(mapData, CustomMapVariant.class);
            return oneMap;
        }
        else
            return gson.fromJson(mapData, CustomMapVariant[].class);
    }

    public CustomGameVariant getCustomGameVariant(String gameVariantID, String player) throws Exception{

        String gameData = listCustomGameVariants(gameVariantID, player);
        return gson.fromJson(gameData, CustomGameVariant.class);
    }

    public  CustomMapVariant[] getCachedCustMapVariants() throws Exception{
//        String[] metaData = Database.getMetadataTextFromDB(dataType.CUSTOMMAPVARIANTS, true, NA);
//        CustomMapVariant[] metaDataA = new CustomMapVariant[metaData.length];
//        for (int i = 0; i < metaData.length; i++){
//            metaDataA[i] = gson.fromJson(metaData[i], CustomMapVariant.class);
//        }
//        return metaDataA;
        return  (CustomMapVariant[]) Database.getMetadataFromDB(dataType.CUSTOMMAPVARIANTS, false, NA);
    }

    public MapVariant[] getArenaMapVariants() throws Exception{
//        String[] metaData = Database.getMetadataTextFromDB(dataType.ARENAMAPVARIANTS, true, NA);
//        MapVariant[] metaDataA = new MapVariant[metaData.length];
//        for (int i = 0; i < metaData.length; i++){
//            metaDataA[i] = gson.fromJson(metaData[i], MapVariant.class);
//        }
//        return metaDataA;
        return  (MapVariant[]) Database.getMetadataFromDB(dataType.ARENAMAPVARIANTS, false, NA);
    }

    public  String getCustomMapName(String mapID) throws Exception{
        CustomMapVariant[] mapVariants = getCachedCustMapVariants();
        String mapName = null;
//        log.info(mapID);
        for (int i = 0; i < mapVariants.length; i++){
            if(mapVariants[i].getIdentity().getResourceId() != null && mapVariants[i].getIdentity().getResourceId().equalsIgnoreCase(mapID))
                mapName = mapVariants[i].getName();
        }
            return mapName;
    }


    public  String getMedalName(long medalID, Medal[] medals) throws Exception
    {
        String medalName = null;
        String medalDescription = null;
        for (int i = 0; i < medals.length; i++) {
            if (medals[i].getId() == medalID) {
                medalName = medals[i].getName();
                medalDescription = medals[i].getDescription();
            }
        }
        if (medalName.equalsIgnoreCase("Perfect Kill")){
//            log.info("Perfect Kill Medal" + medalDescription);
            if (medalDescription.contains("Carbine"))
                medalName = medalName + "(Carbine)";
            else if (medalDescription.contains("LightRifle"))
                medalName = medalName + "(Light Rifle)";
            else if (medalDescription.contains("Halo One Pistol"))
                medalName = medalName + "(Halo One Pistol)";
            else if (medalDescription.contains("DMR"))
                medalName = medalName + "(DMR)";
            else if (medalDescription.contains("Magnum"))
                medalName = medalName + "(Magnum)";
            else if (medalDescription.contains("Battle Rifle"))
                medalName = medalName + "(Battle Rifle)";
        }
        return medalName;
    }

    public  String getMedalDescription(long medalID, Medal[] medals) throws Exception
    {
        String medalDesc = null;
        for (int i = 0; i < medals.length; i++){
            if (medals[i].getId() == medalID)
                medalDesc = medals[i].getDescription();
        }
        return medalDesc;
    }

    public int getMedalDifficulty(long medalID, Medal[] medals) throws Exception {
        int medalDiff = 0;
        for (int i = 0; i < medals.length; i++){
            if (medals[i].getId() == medalID)
                medalDiff = medals[i].getDifficulty();
        }
        return medalDiff;
    }

    public  long getMedalID(String medalName, Medal[] medals)
    {
        long medalID = 0;
        for (int i = 0; i < medals.length; i++) {
            if (medals[i].getName().equalsIgnoreCase(medalName))
                medalID = medals[i].getId();
        }
        return medalID;
    }

    public  String getWeaponName(long weaponID, Weapon[] weapons) throws Exception
    {
        String weaponName = null;
        for (int i = 0; i < weapons.length; i++){
            if (weapons[i].getId() == weaponID)
                weaponName = weapons[i].getName();
        }
        return weaponName;
    }

    public  String getMapName(String id, Map[] maps) throws Exception
    {

        String mapName = null;
        String mapID = null;
        for (int i = 0; i < maps.length; i++) {
            if (maps[i].getId().equalsIgnoreCase(id)) {
                mapName = maps[i].getName();
            }
        }
            if (mapName == null) {
//                log.info(id);
                mapName = "Unknown";
            }
        return mapName;
    }

    public  String getMapVariantName(String id, MapVariant[] maps) throws Exception{
        String mapName = null;
        for (int i = 0; i < maps.length; i++){
            if(maps[i].getId().equalsIgnoreCase(id)){
                mapName = maps[i].getName();
            }
        }
        return mapName;
    }

    public GameVariant[] getArenaGameVariants(String[] gameVariantsString) throws Exception {
//        GameVariant[] gameVariants  = new GameVariant[gameVariantsString.length];
//        for (int i = 0; i < gameVariants.length; i++){
//            gameVariants[i] = gson.fromJson(gameVariantsString[i], GameVariant.class);
//        }
//        return gameVariants;
        return (GameVariant[]) Database.getMetadataFromDB(dataType.ARENAGAMEVARIANTS, false, NA);
    }

    public int getTeamID (String gamertag, CarnageReport report) throws Exception {
        int teamID = 0;
        for (PlayerStat stats: report.getPlayerStats()){
            if (stats.getPlayer().getGamertag().equalsIgnoreCase(gamertag))
                teamID = stats.getTeamId();
        }
        return teamID;
    }

    public String getTeamScore (int teamID, CarnageReport report) throws Exception {
        double teamScore = 0;
        double tempTeamScore = 0;
        for (TeamStat tStats : report.getTeamStats()){
            if (tStats.getTeamId() == teamID){
                tempTeamScore = tStats.getScore();
            }
        }
        for (PlayerStat stats : report.getPlayerStats()){
            if (stats.getTeamId() == teamID){
                if (stats.getPlayerScore() == null) {
                    teamScore = tempTeamScore;
                    break;
                }
                else
                    teamScore += (stats.getPlayerScore());
            }
        }
        return  Double.toString(teamScore);
    }

    public String getGameVariantName(String gameVID, String[] gameVariantsS) throws Exception {
        String gameName = null;
        GameVariant[] gameVariants = getArenaGameVariants(gameVariantsS);
        for (int i = 0; i < gameVariants.length; i++){
            if (gameVariants[i].getId().equalsIgnoreCase(gameVID))
                gameName = gameVariants[i].getName();
        }
        return gameName;
    }

    public  BaseStats getBaseStats(Enum gameType, String gamertag) throws Exception {
        JSONObject obj = getPlayerStatsJSON(gameType, gamertag);
        String var = obj.toString();

        BaseStats stats = gson.fromJson(var, BaseStats.class);
        return stats;
    }


    public  JSONObject getPlayerStatsJSON (Enum gameType) throws Exception {
        JSONObject obj = null;
        if (gameType == WARZONE)
            obj = new JSONObject(warzoneMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat");
        if (gameType == ARENA)
            obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats");
        if (gameType == CUSTOM)
            obj = new JSONObject(customMatches(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats");
        return obj;
    }

    public  JSONObject getPlayerStatsJSON (Enum gameType, String gt) throws Exception
    {
        JSONObject obj = null;
        if (gameType == WARZONE)
            obj = new JSONObject(warzoneMatches(formatString(gt))).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("WarzoneStat");
        if (gameType == ARENA)
            obj = new JSONObject(arenaStats(formatString(gt))).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats");
        if (gameType == CUSTOM)
            obj = new JSONObject(customMatches(formatString(gt))).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("CustomStats");
        return obj;
    }

    public void testImpulses() throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(ARENA);
        Impulse[] impulses = gson.fromJson(listImpulses(), Impulse[].class);
        for (int i = 0; i < 10; i++){
            for (PlayerStat ps : reports[i].getPlayerStats()){
                if (ps.getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    for (Impulse im : ps.getImpulses()){
                        for (int k = 0; k < impulses.length; k++){
                            if (im.getId() == impulses[k].getId()){
                                log.info(impulses[k].getInternalName());
                            }
                        }
                    }
                }
            }
        }
    }

    public  String testMedalStats(Enum gameType) throws Exception {
        JSONArray obj = getPlayerStatsJSON(gameType).getJSONArray("MedalAwards");
        String mostEarnedMedal = null;
        double average = 0;
        int highestMedalCount = 0;
        int topGunCount = 0;
        int extermCount = 0;
        String mostLegEarned = null;
        String var = obj.toString();
        double games = totalGames(gameType, PLAYER);
        games = (double)Math.round(games *1000d) / 1000d;

        Medal[] medals = getMedals();
        MedalAward[] medalAwards = gson.fromJson(var, MedalAward[].class);
        for (int row = 0; row < medalAwards.length; row++){
            medalAwards[row].setName(getMedalName(medalAwards[row].getMedalId(), medals));
        }
        HashMap<String, MedalAward> medalHash = new HashMap<>();
        for (int row = 0; row < medalAwards.length; row++) {
            medalHash.put(medalAwards[row].getName(), medalAwards[row]);
        }
        List<MedalAward> medalsByCount = new ArrayList<>(medalHash.values());
        Collections.sort(medalsByCount, ((o1, o2) -> o2.getCount() - o1.getCount()));

        for (int i = 0; i < medalAwards.length; i++) {
////            for (int k = i + 1; k < medalAwards.length; k++) {
//                if (medalAwards[i].getCount() > medalAwards[i].getCount() && medalAwards[i].getCount() > highestMedalCount && ! medalAwards[i].getName().equalsIgnoreCase("headshot") && getMedalDifficulty(medalAwards[i].getMedalId(), medals) <= 10) {
//                    highestMedalCount = medalAwards[i].getCount();
//                    mostEarnedMedal = medalAwards[i].getName();
//                }
////            }
            if (medalAwards[i].getName().equalsIgnoreCase("Top Gun"))
                topGunCount = medalAwards[i].getCount();
            if (medalAwards[i].getName().equalsIgnoreCase("Extermination"))
                extermCount++;
        }
        double topGunAverage = (double)Math.round((topGunCount / games) *100d);
//        mostEarnedMedal = ("Your most earned Legendary Medal is the " + mostEarnedMedal + " medal, with a total of " + highestMedalCount + " and an average of " + average + " per game");
        mostEarnedMedal = "\nYour Top 5 most earned Medals are: ";
        for (int i = 0; i < 5; i++){
            average = (double)Math.round((medalsByCount.get(i).getCount() / games) *1000d) / 1000d;
            mostEarnedMedal = mostEarnedMedal + "\n" + medalsByCount.get(i).getName() + " with a total count of: " + medalsByCount.get(i).getCount() + ". With an average of: " + average + " per game";
        }
        String otherMedalStats = "\n\nYou have earned the Top Gun medal " + topGunCount + " times. That's " + topGunAverage +"% of your games!";
        otherMedalStats = otherMedalStats + "\nYou have earned the Extermination Medal " + extermCount + " times!";
        return mostEarnedMedal + otherMedalStats;
    }

    public  void testPlayerStats() throws Exception
    {

        BaseStats stats = gson.fromJson(arenaStats(PLAYER), ArenaStat.class);
        log.info("" + stats.getEnemyKills());
    }

    public  double totalGames(Enum gameType, String gt) throws Exception
    {
        JSONObject obj = getPlayerStatsJSON(gameType, formatString(gt));
        String var = obj.toString();

        BaseStats stats = gson.fromJson(var, BaseStats.class);
        return stats.getTotalGamesCompleted();
    }

    public  double totalGamesAll() throws Exception
    {
        return totalGames(CUSTOM, PLAYER) + totalGames(WARZONE, PLAYER) + totalGames(ARENA, PLAYER);
    }

    public  String totalWins(Enum gameType) throws Exception
    {
        String gType = gameType.toString();
        BaseStats stats = getBaseStats(gameType, PLAYER);
        log.info("Total " + capitalize(gType.toString().toLowerCase()) + " games won: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
        return ("Total number of Wins: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
    }

    public  int totalKills(Enum gameType, String player) throws Exception{
        BaseStats stats = getBaseStats(gameType, player);
        int totalKills = stats.getTotalKills();
        return totalKills;
    }

    public  int totalDeaths(Enum gameType, String player) throws Exception{
        BaseStats stats = getBaseStats(gameType, player);
        int totalDeaths = stats.getTotalDeaths();
        return totalDeaths;
    }

    public  double getKD(double kills, double deaths){
        double kdRatio = (kills/deaths);
        kdRatio = (double)Math.round(kdRatio *1000d) / 1000d;
        return kdRatio;
    }

    public  void testWeaponKills(Enum gameType) throws Exception
    {
        JSONArray obj = getPlayerStatsJSON(gameType).getJSONArray("WeaponStats");
        String favWeapon = null;
        int totalKills = 0;

        String var = obj.toString();
        Weapon[] weapons = getWeapons();
        WeaponStats[] stats = gson.fromJson(var, WeaponStats[].class);
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
//        log.info("Total kills per weapon for " + PLAYER_UF);
//        for (int i = 0; i < stats.length; i++)
//        {
//            double killCount = stats[i].getTotalKills()/games;
//            killCount = (double)Math.round(killCount * 1000d) / 1000d;
//            log.info(stats[i].getName() + ": " + stats[i].getTotalKills() + "  ||  Avg kills per game: " + killCount);
//        }
        log.info("Your favorite weapon is the " + favWeapon + " with a kill total of: " + totalKills);
    }


    public  void testBaseStats(Enum gameType) throws Exception
    {
        JSONObject obj = getPlayerStatsJSON(gameType);
//        log.info(obj.toString());
        String gType = capitalize(gameType.toString().toLowerCase());
        String var = obj.toString();

        BaseStats stats = gson.fromJson(var, BaseStats.class);
        JSONObject obj2 = null;
        if (gameType.toString().equalsIgnoreCase("ARENA")){
            obj2 = getPlayerStatsJSON(CUSTOM);
        }
        else{
            obj2 = getPlayerStatsJSON(ARENA);
        }
        BaseStats stats2 = gson.fromJson(obj2.toString(), BaseStats.class);
//        BaseStats stats3 = gson.fromJson(getPlayerStatsJSON(WARZONE).toString(), BaseStats.class);
        double totalShotsFired = stats.getTotalShotsFired();
        double totalShotsLanded = stats.getTotalShotsLanded();
        int totalHeadShots = stats.getTotalHeadshots();
        double totalPowerWeapon = stats.getTotalPowerWeaponGrabs();
        double totalPowerWeaponKills = stats.getTotalPowerWeaponKills();
        String pWeaponTime = getTotalDuration(Duration.parse(stats.getTotalPowerWeaponPossessionTime()).getSeconds());
        long playSeconds = Duration.parse(stats.getTotalTimePlayed()).getSeconds();
//        log.info(playSeconds + "");

        long totalPlaySeconds = (Duration.parse(stats.getTotalTimePlayed()).getSeconds()) ;
        String playTime = getTotalDuration(playSeconds);
        String totalPlayTime = getTotalDuration(totalPlaySeconds);
//        double playDays = (double) TimeUnit.SECONDS.toDays(playSeconds);
//        double haloReleaseDays = (double) TimeUnit.SECONDS.toDays(getHaloReleaseTime());
        double averagePlayTime = (((double)totalPlaySeconds/(double)getHaloReleaseTime()));
//        log.info("" + averagePlayTime);
        double averageTimePerDay = ((((averagePlayTime * 60) * 60) * 24 ) / 60) ;
        averageTimePerDay = Math.round(averageTimePerDay * 1000d) / 1000d;
        averagePlayTime = Math.round(averagePlayTime * 100000d) /1000d;
        log.info("\nNow here's some stats that show you how much of your life has been utterly wasted diddling a controller!");
        log.info("Halo 5 has been out for " + getTotalDuration(getHaloReleaseTime()) + ". \nIn that time you've wasted" + totalPlayTime + " playing Arena, Warzone, and Custom games!");
        log.info("That means that you've played " + "Halo 5 for " + averagePlayTime + " percent of the time that it's been out!");
        log.info("That's an average of " + averageTimePerDay +  " minutes a day, EVERYDAY since it's release!");
        double totalDamageDealt = stats.getTotalGrenadeDamage() + stats.getTotalMeleeDamage() + stats.getTotalGroundPoundDamage() + stats.getTotalWeaponDamage() + stats.getTotalPowerWeaponDamage() + stats.getTotalShoulderBashDamage();
        totalDamageDealt = (double)Math.round(totalDamageDealt * 100d) / 100d;
        double accuracy = (totalShotsLanded/totalShotsFired);
        accuracy = (double)Math.round(accuracy * 100d);
        log.info("\nHere are some more Random Stats: ");
//        log.info(PLAYER_UF + " has completed " + stats.getTotalGamesCompleted()+ " " + capitalize(gType.toString().toLowerCase()) + " games.");
        log.info("You have wasted a total of: " + playTime + " playing " + gType + " games on Halo 5!");
        testWeaponKills(gameType);
        log.info("You have fired a total of: " + (int)totalShotsFired + " shots. Of those, you've landed " + (int)totalShotsLanded + " shots.");
        log.info("That's an accuracy of " + accuracy + "%");
        log.info("You have slaughtered " + totalHeadShots + " Spartans with a headshot.");
        log.info("You've stroked a power weapon in your hands for a total of " + pWeaponTime);
        log.info("You have grabbed a power weapon " + (int)totalPowerWeapon + " times, \nYou've killed " + (int)totalPowerWeaponKills + " Spartans with those power weapons.");
//        log.info("That's an average of " + powerWeaponKillAvg + " kills each time you pick up a power weapon!");
        log.info("You have Spartan Charged " + stats.getTotalShoulderBashKills() + " dumb-dumbs.");
        log.info("You have murdered " + stats.getTotalGrenadeKills() + " Spartans with grenades.");
        log.info("You have tied the stupid enemy team " + stats.getTotalGamesTied() + " time(s).");
        log.info("You have dealt " + totalDamageDealt + " damage points in your Arena career.");
        log.info("You have performed a total of " + stats.getTotalAssassinations() + " assassinations!");
        log.info(testMedalStats(gameType));
    }

    public  void testBaseResults() throws Exception
    {
        JSONObject obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result");
        String var = obj.toString();

        BaseServiceRecordResult stats = gson.fromJson(var, BaseServiceRecordResult.class);
        List<ArenaStat.ArenaPlaylistStats> arenaStats = stats.getArenaStat().getArenaPlaylistStats();
        for (ArenaStat.ArenaPlaylistStats sta : arenaStats) {
            log.info(sta.getPlaylistId() + " " + sta.getTotalGamesCompleted());
        }
    }

    public  void testActivePlaylists() throws Exception
    {
        JSONArray obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats").getJSONArray("ArenaPlaylistStats");
        String var = obj.toString();
        log.info(var);

        String playlistData = listPlaylists();
        ArenaStat.ArenaPlaylistStats[] playlistStats = gson.fromJson(var, ArenaStat.ArenaPlaylistStats[].class);
        Playlist[] playlists = gson.fromJson(playlistData, Playlist[].class);
        for (int i = 0; i < playlistStats.length; i++) {
            for (int k = 0; k < playlists.length; k++) {
                if (playlists[k].isActive() == true) {
                    log.info(playlists[k].getName());
                }
                if (playlists[k].getId().equalsIgnoreCase(playlistStats[i].getPlaylistId())) {
                    playlistStats[i].setName(playlists[k].getName());
                }
            }
        }
    }

    public  void comparePlayers(String player1, String player2, Enum gameType) throws Exception
    {
        JSONArray obj = getPlayerStatsJSON(gameType, formatString(player1)).getJSONArray("MedalAwards");
        JSONArray obj2 = getPlayerStatsJSON(gameType, formatString(player2)).getJSONArray("MedalAwards");

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
        double totalGames1 = totalGames(gameType, player1);
        double totalGames2 = totalGames(gameType, player2);
        double gamePercentage1 = 0;
        double gamePercentage2 = 0;
        long[] p1Has = null;
        long[] p2Has = null;
        for (int i = 0; i < p1Medals.length; i++) {
            allMedals1[i] = getMedalName(p1Medals[i].getMedalId(), medals);
            for (int k = 0; k < p2Medals.length; k++) {
                  allMedals2[k] = getMedalName(p2Medals[k].getMedalId(), medals);
                if (p1Medals[i].getMedalId() == p2Medals[k].getMedalId()) {
                    p1totalMedals = p1Medals[i].getCount();
                    p2totalMedals = p2Medals[k].getCount();
                    gamePercentage1 = p1totalMedals/totalGames1;
                    gamePercentage2 = p2totalMedals/totalGames2;
                    gamePercentage1 = (double)Math.round(gamePercentage1 *1000d) / 1000d;
                    gamePercentage2 = (double)Math.round(gamePercentage2 *1000d) / 1000d;
                    if (gamePercentage1 > gamePercentage2) {
                        betterMedals[i] = getMedalName(p1Medals[i].getMedalId(), medals) + " x " + p1Medals[i].getCount() + " : " + "Earned per game: " + gamePercentage1 + " " + player2 + "  only earns " + gamePercentage2 + " per game";
                    }
                    else if (gamePercentage2 > gamePercentage1) {
                        worseMedals[i] = getMedalName(p2Medals[k].getMedalId(), medals) + " x " + p2Medals[i].getCount() + " : " + "Earned per game: " + gamePercentage2 + " " + player1 + " only earns " + gamePercentage1 + " per game";
                    }}}}
        log.info(player1 + " has earned these medals more than " + player2);
        for (int i = 0; i < betterMedals.length; i++){
            if (betterMedals[i] != null) {
                log.info(betterMedals[i]);
            }
        }
        log.info("\n" + player2 + " has earned these medals more than " + player1);
        for (int i = 0; i < worseMedals.length; i++){
            if (worseMedals[i] != null){
                log.info(worseMedals[i]);
            }
        }
        Arrays.sort(allMedals1);
        Arrays.sort(allMedals2);
        p1Has = new long[allMedals1.length];
        p2Has = new long[allMedals2.length];
        for (int i = 0; i < allMedals1.length; i++){
            if (! Arrays.asList(allMedals2).contains(allMedals1[i])){
                p1Has[i] = getMedalID(allMedals1[i], medals);
            }
        }
        for (int i = 0; i < allMedals2.length; i++){
            if (! Arrays.asList(allMedals1).contains(allMedals2[i])){
                p2Has[i] = getMedalID(allMedals2[i], medals);
            }
        }
        log.info("\nMedals that " + player1 + " has earned that " + player2 + " hasn't: ");
        for (int k = 0; k < p1Medals.length; k++) {
            for (int i = 0; i < p1Has.length; i++) {
                if (p1Medals[k].getMedalId() == p1Has[i]){
                    log.info(getMedalName(p1Has[i], medals) + " : " + getMedalDescription(p1Has[i], medals) + ". Count: " + p1Medals[k].getCount());
                }}}
        log.info("\nMedals that " + player2 + " has earned that " + player1 + " hasn't: ");
        for (int k = 0; k < p2Medals.length; k++) {
            for (int i = 0; i < p2Has.length; i++) {
                if (p2Medals[k].getMedalId() == p2Has[i]){
                    log.info(getMedalName(p2Has[i], medals) + " : " + getMedalDescription(p2Has[i], medals)+ ". Count: " + p2Medals[k].getCount());
                }}}}

    public  String favoriteMap(Match[] matches, Map[] maps) throws Exception{
        String favMap = null;
        int favMapCount = 0;
        for (int i = 0; i < matches.length; i++){
            for (int k = 0; k < maps.length; k++){
                if (matches[i].getMapVariant().getResourceId().equalsIgnoreCase(maps[k].getId())){
                    maps[k].setCount(maps[k].getCount()+ 1);
                }}}
        for (int i = 0; i < maps.length; i++){
            for (int k = 0; k < maps.length; k++){
                if (maps[i].getCount() > maps[k].getCount() && maps[i].getCount() >= favMapCount){
                    favMapCount = maps[i].getCount();
                    favMap = maps[i].getName();
                }}}
        return ("Your favorite map is: " + favMap + " with a total play count of: " + favMapCount);
    }

    public  String favoriteMapVariant(Enum gameType) throws Exception{
        if (totalGames(gameType, PLAYER_UF) == 0)
            return null;
        if (gameType == CUSTOM){
            return favoriteCustomMapVariant(gameType);
        }
        String favMap = null;
        MapVariant[] maps = getArenaMapVariants();
//        MapVariant[] maps = getArenaMapVariants();
        HashMap<String, Integer> stringsCount = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
//
        Match[] matches = getPlayerMatches(gameType);
        for (int i = 0; i < matches.length; i++){
            list.add(getMapVariantName(matches[i].getMapVariant().getResourceId(), maps));
        }
//            for (int i = 0; i < maps.length; i++) {
//                list.add(maps[i].getName());
//            }
        for (String s: list){
            Integer c = stringsCount.get(s);
            if(c == null)
                c = new Integer(0);
            c++;
            stringsCount.put(s,c);
//            log.info(s);

        }
        java.util.Map.Entry<String,Integer> mostRepeated = null;
        for(java.util.Map.Entry<String, Integer> e: stringsCount.entrySet()){
            if(mostRepeated == null || mostRepeated.getValue()<e.getValue())
                mostRepeated = e;
        }
        if(mostRepeated != null)
            favMap = ("Your most played Arena map is: " + mostRepeated.getKey() + " with a total of " + mostRepeated.getValue() + " games wasted");
        return favMap;
    }

    public  String favoriteCustomMapVariant(Enum gameType) throws Exception {
        String favMap = null;
        HashMap<String, Integer> stringsCount = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
//        CustomMapVariant[] mapv = getCachedCustMapVariants();
        Match[] matches = getPlayerMatches(gameType);
        for (int i = 0; i < matches.length; i++){
            list.add(matches[i].getMapVariant().getResourceId());
        }
//        for (int i = 0; i < mapv.length; i++) {
//            list.add(mapv[i].getIdentity().getResourceId());
//        }
        for (String s: list){
            Integer c = stringsCount.get(s);
            if(c == null)
                c = new Integer(0);
            c++;
            stringsCount.put(s, c);

        }
        java.util.Map.Entry<String,Integer> mostRepeated = null;
        for(java.util.Map.Entry<String, Integer> e: stringsCount.entrySet()){
            if(mostRepeated == null || mostRepeated.getValue() < e.getValue())
                mostRepeated = e;
        }
        if(mostRepeated != null)
            favMap = ("Your most played Custom map is: " + getCustomMapName(mostRepeated.getKey()) + " with a total play count of: " + mostRepeated.getValue() + " games!");
        else
            favMap = "You don't have a favorite Custom map you nigger";
        return favMap;
    }

    public Player[] getTeamMates (Enum gameType) throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(gameType);
        List<Player> teamMates = new ArrayList<>();
        int playerTeamID = 2;
        for (int i = 0; i < reports.length; i++){
            List<PlayerStat> playerList = reports[i].getPlayerStats();
            for(PlayerStat en : playerList){
                if (en.getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF))
                    playerTeamID = en.getTeamId();
            }
            for(PlayerStat en: playerList){
                if(! en.getPlayer().getGamertag().equalsIgnoreCase((PLAYER_UF)) && en.getTeamId() == playerTeamID){
                    teamMates.add(en.getPlayer());
                }else{
                    continue;
                }
            }
        }
        Player[] matesArray = new Player[teamMates.size()];
        for (int i = 0; i < teamMates.size(); i++){
            matesArray[i] = teamMates.get(i);
        }
        return matesArray;
    }

    public Player[] getPlayersPlayedWith (Enum gameType) throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(gameType);
        List<Player> teamMates = new ArrayList<>();
//        int playerTeamID = 0;
        for (int i = 0; i < reports.length; i++){
            List<PlayerStat> playerList = reports[i].getPlayerStats();
            for(PlayerStat en : playerList){
//                if (en.getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF))
//                    playerTeamID = en.getTeamId();
                if(! en.getPlayer().getGamertag().equalsIgnoreCase((PLAYER_UF))){
                    teamMates.add(en.getPlayer());
//                    if (en.getPlayer().getGamertag().equalsIgnoreCase("That Brock Guy") && en.getTeamId() != playerTeamID){
////                        log.info("That Brock Guy " + reports[i].getMatchId() + " " + en.getTeamId() + " " + playerTeamID);
//                    }
                }}}
        Player[] matesArray = new Player[teamMates.size()];
        for (int i = 0; i < teamMates.size(); i++){
            matesArray[i] = teamMates.get(i);
        }
        return matesArray;
    }

    public String favoriteTeamMate(Enum gameType) throws Exception {
        String favPlayer = null;
        int totalMates = 0;
        double totalGames = totalGames(gameType, PLAYER);
        double numTimesWFavPlayer = 0;
        HashMap<String, Integer> playerCount = new HashMap<>();
        List<String> list = new ArrayList<>();
        Player[] teamMates = getTeamMates(gameType);

        for (int i = 0; i < teamMates.length; i++){
            list.add(teamMates[i].getGamertag());
        }
        List<String> uniquePlayers = new ArrayList<>(new HashSet<>(list));
        totalMates = uniquePlayers.size();

        for (String s: list){
            Integer c = playerCount.get(s);
            if (c == null){
                c = new Integer(0);
            }
            c++;
            playerCount.put(s, c);
        }
        java.util.Map.Entry<String,Integer> mostRepeated = null;
        for(java.util.Map.Entry<String, Integer> e: playerCount.entrySet())
        {
            if(mostRepeated == null || mostRepeated.getValue() < e.getValue())
                mostRepeated = e;
        }
        if(mostRepeated != null) {
            numTimesWFavPlayer = mostRepeated.getValue() / totalGames;
            numTimesWFavPlayer = (double)Math.round(numTimesWFavPlayer *100d);
            favPlayer = ("You've had a total of " + totalMates + " unique team mates. \n" +
                    "The player that's been on your team the most is: " + mostRepeated.getKey() + ". You've been team mates " + mostRepeated.getValue() + " times!" +
                    " That's " + numTimesWFavPlayer + "% of your games!");
        }

        return favPlayer;
    }

    public String favoritePlayer(Enum gameType) throws Exception {
        String favPlayer = null;
        int totalMates = 0;
        int playedBravo = 0;
        int playedUnyshek = 0;
        double totalGames = totalGames(gameType, PLAYER);
        double numTimesWFavPlayer = 0;
        HashMap<String, PlayerCount> playerCount = new HashMap<>();
        List<String> list = new ArrayList<>();
        Player[] teamMates = getPlayersPlayedWith(gameType);
        for (int i = 0; i < teamMates.length; i++){
            list.add(teamMates[i].getGamertag());
        }
        List<String> uniquePlayers = new ArrayList<>(new HashSet<>(list));
        totalMates = uniquePlayers.size();
        int playCount = 0;
        for (String s: list){
            PlayerCount pc = null;
            if (playerCount.get(s) == null) {
                pc = new PlayerCount(s, 1);
                playerCount.put(pc.getName(), pc);
//                continue;
            }
            else{
                pc = playerCount.get(s);
                playCount = pc.getGameCount();
                pc.setGameCount(playCount + 1);
                playerCount.put(pc.getName(), pc);
            }
            if (s.equalsIgnoreCase("B is for Bravo"))
                playedBravo++;
            if (s.equalsIgnoreCase("unyshek"))
                playedUnyshek++;
        }
        List<PlayerCount> playersByGameCount = new ArrayList<>(playerCount.values());
        Collections.sort(playersByGameCount, (o1, o2) -> o2.getGameCount() - o1.getGameCount());
        favPlayer = "Your top five players that you've played Halo 5 with are: ";
        for (int i = 0; i < 5; i++){
            numTimesWFavPlayer = playersByGameCount.get(i).getGameCount() / totalGames;
            numTimesWFavPlayer = (double)Math.round(numTimesWFavPlayer *100d);
            favPlayer = favPlayer + "\n" + playersByGameCount.get(i).getName() + " with a total of " + playersByGameCount.get(i).getGameCount() + " games played! That's " + numTimesWFavPlayer + "% of your games";
        }
//            favPlayer = ("You've played with a total of " + totalMates + " unique players. \n" +
//                    "The player that you've played the most is: " + mostRepeated.getKey() + ". You've played " + mostRepeated.getValue() + " games with each other!" +
//                    " That's " + numTimesWFavPlayer + "% of your games!");
            if (playedBravo > 0)
                favPlayer = favPlayer + "\nYou have played with Bravo " + playedBravo + " times!";
            if (playedUnyshek > 0)
                favPlayer = favPlayer + "\nYou have played with Unyshek " + playedUnyshek + " times!";

        return favPlayer;
    }

    public KilledByOpponentDetail[] getEnemyKills (Enum gameType) throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(gameType);
        List<KilledByOpponentDetail> enemiesKilledBy = new ArrayList<>();
        for (int i = 0; i < reports.length; i++){
            List<PlayerStat> playerList = reports[i].getPlayerStats();
            for(PlayerStat en : playerList){
                if(en.getPlayer().getGamertag().equalsIgnoreCase((PLAYER_UF))){
                    List<KilledByOpponentDetail> killedBy = en.getKilledByOpponentDetails();
                    for(KilledByOpponentDetail kb: killedBy){
                        enemiesKilledBy.add(kb);
                        continue;
                    }}}}
        KilledByOpponentDetail[] allEnemies = new KilledByOpponentDetail[enemiesKilledBy.size()];
        for (int i = 0; i < enemiesKilledBy.size(); i++){
            allEnemies[i] = enemiesKilledBy.get(i);
        }
        return allEnemies;
    }

    public String killedByOponent(Enum gameType) throws Exception{
        String gametype = capitalize(gameType.toString().toLowerCase());
        String worstEnemy = null;
        int totalTimesPlayedWEnemy = 0;
        String mostPlayedAgainst = null;
        int totalTimesPlayed = 0;
        int totalEnemies = 0;
        int totalKills = 0;
        HashMap<String, Integer> stringsCount = new HashMap<>();
        HashMap<String, Integer> mostKilledBy = new HashMap<>();
        List<String> list = new ArrayList<>();
        KilledByOpponentDetail[] enemies = getEnemyKills(gameType);
//        for (String s: list){
//
//        }
        for(int i = 0; i < enemies.length; i++){
            list.add(enemies[i].getGamerTag());
        }
        List<String> uniqueList = new ArrayList<>(new HashSet<>(list));
        for (String s: uniqueList){
            totalEnemies ++;
        }
        for (String s: list){
            Integer c = stringsCount.get(s);
            Integer d = mostKilledBy.get(s);
            if(c == null) {
                c = new Integer(0);
                d = new Integer(0);
            }
            c++;
            stringsCount.put(s,c);
            mostKilledBy.put(s,d);
        }
        java.util.Map.Entry<String,Integer> mostRepeated = null;
        for(java.util.Map.Entry<String, Integer> e: stringsCount.entrySet())
        {
            if(mostRepeated == null || mostRepeated.getValue() < e.getValue())
                mostRepeated = e;
        }
        if(mostRepeated != null)
            mostPlayedAgainst = ("The enemy that you've played against most is: " + mostRepeated.getKey() + " with a total play count of: " + mostRepeated.getValue());

        for(java.util.Map.Entry<String, Integer> e : mostKilledBy.entrySet()){
            for (int i = 0; i < enemies.length; i++){
                if(e.getKey().equalsIgnoreCase(enemies[i].getGamerTag())){
                    e.setValue(enemies[i].getTotalKills() + e.getValue());
                    enemies[i].setKilledEnemyTotal(enemies[i].getTotalKills() + enemies[i].getKilledEnemyTotal());
                    totalTimesPlayed++;
                }
            }
            if (e.getValue() > totalKills){
                totalKills = e.getValue();
                worstEnemy = e.getKey();
            }
        }
        log.info("You have played against " + totalEnemies + " total enemies");
        totalTimesPlayedWEnemy = stringsCount.get(worstEnemy);
        return ("The enemy that has killed you the most is: " + worstEnemy + " with a total of " + totalKills + " kills in " + gametype + " games. You have played each other " + totalTimesPlayedWEnemy + " times \n" + mostPlayedAgainst);
    }

    public String getMatchDetails(String matchID, String gamerTag) throws Exception {
        CarnageReport report = gson.fromJson(postGameCarnage(matchID), CarnageReport.class);
        String weaponsUsed = "\nWeapons used: ";
        String enemiesKilled = "\n\nEnemies killed: ";
        Weapon[] weapons = gson.fromJson(listWeapons(), Weapon[].class);
        for (int i = 0; i < report.getPlayerStats().size(); i++){
            if (report.getPlayerStats().get(i).getPlayer().getGamertag().equalsIgnoreCase(gamerTag)){
                PlayerStat stat = report.getPlayerStats().get(i);
//                log.info(report.getPlayerStats().get(i).getPlayer().getGamertag());
                List<WeaponStats> weaponStats = stat.getWeaponStats();
                for (WeaponStats weapon : weaponStats){
                    if (weapon.getTotalKills() < 1)
                        continue;
                    weaponsUsed = weaponsUsed + "\n" + getWeaponName(weapon.getWeaponId().getStockId(), weapons) + " kills: " + weapon.getTotalKills();
                }
                List<KilledOpponentDetail> killedOpponents = stat.getKilledOpponentDetails();
                for (KilledOpponentDetail ko : killedOpponents){
                    enemiesKilled = enemiesKilled + "\n" + ko.getGamerTag() + " " + ko.getTotalKills();
                }

            }
        }
        return weaponsUsed + enemiesKilled;
    }

    public void testPlayerMatches(Enum gameType) throws Exception {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] gameVariantsString =  db.getMetadataTextFromDB(dataType.ARENAGAMEVARIANTS, false, NA);
        Date completedDate = null;
        double totalGames = totalGames(gameType, PLAYER_UF);
        Medal[] medals = getMedals();
        Map[] maps = getMaps();
        double kdRatio = 0;
        double averageKills = 0;
        double averageDeaths = 0;
        double bestTotalKills = 0;
        double bestTotalDeaths = 0;
        String bestMatchDate = null;
        int matchesDNF = 0;
        double mostKills = 0;
        double mostDeaths = 0;
        int m = 0;
        String bestMatchID = null;
        String worstMatchID = null;
        String worstGameScore = null;
        BaseStats stats = getBaseStats(gameType, PLAYER_UF);
        int positiveCount = 0;

        Match[] matches = getPlayerMatches(gameType);
//        log.info("" + matches.length);
        for (int i = 0; i < matches.length; i++){
//            log.info("" + i);
            double totalKills = matches[i].getPlayers().get(0).getTotalKills();
            double totalDeaths = matches[i].getPlayers().get(0).getTotalDeaths();
            double currentKD = 0;
            if (totalDeaths == 0){
                totalDeaths = 1;
                currentKD = (totalKills / totalDeaths);
                totalDeaths = 0;
            }
            else{currentKD = (totalKills/totalDeaths);}
            if (matches[i].getPlayers().get(0).getResult() == 0){
                matchesDNF++;
            }
            String matchID = matches[i].getId().getMatchId();
            averageKills += totalKills;
            averageDeaths += totalDeaths;
            String mapName = getMapName(matches[i].getMapId(), maps);
            String gameKind = null;
            if (gameType.toString().equalsIgnoreCase("ARENA")) {
                if (getGameVariantName(matches[i].getGameVariant().getResourceId(), gameVariantsString) == null) {
                    GameVariant[] gameVariants = new GameVariant[1];
                    gameVariants[0] = (gson.fromJson(listGameVariant(matches[i].getGameVariant().getResourceId()), GameVariant.class));
                    Database.writeGameVariantsToDB(gameVariants, dataType.ARENAGAMEVARIANTS, false, NA);
                    gameVariantsString = db.getMetadataTextFromDB(dataType.ARENAGAMEVARIANTS, false, NA);
                }
                gameKind = getGameVariantName(matches[i].getGameVariant().getResourceId(), gameVariantsString);
            }else if (gameType.toString().equalsIgnoreCase("CUSTOM")){

            }

            if (! mapName.contains("Breakout Arena") && ! mapName.contains("Glacier") && ! mapName.contains("Skylark") && ! gameKind.equalsIgnoreCase("Grifball")) {
                if (currentKD > kdRatio && totalKills >= 10) {
                    kdRatio = currentKD;
                    bestMatchID = matchID;
                    bestTotalKills = totalKills;
                    bestTotalDeaths = totalDeaths;
                    completedDate = sdf.parse(matches[i].getMatchCompletedDate().getDate());
                }
                if (totalKills > mostKills)
                    mostKills = totalKills;
                if (totalDeaths > mostDeaths) {
                    mostDeaths = totalDeaths;
                    worstMatchID = matches[i].getId().getMatchId();
                }
            }
            if (currentKD >= 1){
//                log.info(matches[i].getId().getMatchId());
                positiveCount++;
            }}
        kdRatio = (double)Math.round(kdRatio *1000d) / 1000d;
        CarnageReport report = null;
        if (gameType.toString().equalsIgnoreCase("ARENA"))
            report = gson.fromJson(postGameCarnage(worstMatchID), CarnageReport.class);
        else if (gameType.toString().equalsIgnoreCase("CUSTOM"))
            report = gson.fromJson(postCustomGameCarnage(worstMatchID), CarnageReport.class);
//        log.info("" + worstMatchID);
        int worstTeamID = getTeamID(PLAYER_UF, report);
        worstGameScore = getTeamScore(worstTeamID, report);
        log.info(PLAYER_UF + " has played: " + (int)totalGames + " " + capitalize(gameType.toString().toLowerCase()) + " games");
        log.info("The most kills you've ever had in one match is: " + mostKills + " kills!");
        log.info("The most deaths that you've ever had in a match is: " + mostDeaths + " deaths! Your teams score that game was " + worstGameScore + " -- that's absolutely embarrassing");
        double percentagePositive = (positiveCount/totalGames);
        percentagePositive = (double)Math.round(percentagePositive *100d);
        log.info("Your total number of kills: " + (int)averageKills + " Your total number of deaths: " + (int)averageDeaths);
        log.info("Your average K/D spread is: " + getKD(stats.getTotalKills(),stats.getTotalDeaths()));
        double dnfPercentage = matchesDNF/totalGames;
        dnfPercentage = Math.round(dnfPercentage *1000d)/1000d;
        log.info("You have quit or been booted " + matchesDNF + " times. That's " + dnfPercentage + "% of your games that you've been a little bitch!");
        log.info("You have assisted your teammates " + stats.getTotalAssists() + " times");
        log.info("You've had a positive K/D spread " + positiveCount + " times. That's " + percentagePositive + "% of your games!");
        log.info("Your best Kill/Death ratio in any " + capitalize(gameType.toString().toLowerCase()) + " game is: " + kdRatio + " with " + (int)bestTotalKills + " kills and " + (int)bestTotalDeaths + " deaths!");
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
        }else if(gameType == WARZONE) {
            mapName = "Warzone Map";
        }
        log.info("Your best game was played on map: " + mapName + " on " + new SimpleDateFormat("MM/dd/yyyy").format(completedDate));
        log.info("Here are the medals you earned in that game: \n");
        for (int i = 0; i < playerStats.length; i++){
            for (MedalAward medal : playerStats[i].getMedalAwards()){
                for (int k = 0; k < medals.length; k++){
                    if (medal.getMedalId() == medals[k].getId()){
                        medal.setName(medals[k].getName());
                    }}
                if (playerStats[i].getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    log.info(medal.getName() + " x " + medal.getCount());
                }}}
//        log.info(getMatchDetails(bestMatchID, PLAYER_UF));
        testBaseStats(gameType);
        log.info(favoriteMapVariant(gameType));
//        log.info(favoriteCustomMapVariant(CUSTOM));
        log.info(killedByOponent(gameType));
//        log.info(favoriteTeamMate(gameType));
        log.info(favoritePlayer(gameType));
    }
}