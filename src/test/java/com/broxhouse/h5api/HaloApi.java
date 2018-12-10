package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.*;
import com.broxhouse.h5api.models.stats.common.*;
import com.broxhouse.h5api.models.metadata.Map;
import com.broxhouse.h5api.models.stats.common.Impulse;
import com.broxhouse.h5api.models.stats.matches.Match;
import com.broxhouse.h5api.models.stats.matchevents.GameEvent;
import com.broxhouse.h5api.models.stats.matchevents.MatchEvents;
import com.broxhouse.h5api.models.stats.reports.*;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.*;
import java.util.HashSet;

import static com.broxhouse.h5api.gameType.*;


enum gameType {WARZONE, ARENA, CUSTOM, NA}


public class HaloApi {

    static Logger log = Logger.getLogger("Logging");

    public static String PLAYER_UF = "That Sturt Guy";
    public static String PLAYER;
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
    private static final String MATCH_EVENTS = BASE_URL + "stats/h5/matches/%s/events";
    private static final String POST_GAME_CARNAGE_CUST = BASE_URL + "stats/h5/custom/matches/%s";
    private static final long HALO_RELEASE_DAYS = 0;

    static Database db = new Database();
    static PopulateDatabase pd = new PopulateDatabase();
    static Gson gson = new Gson();

    public HaloApi() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
    }

    private static String api(String url) throws Exception {
        String getResponse = null;
        StringBuilder sb = new StringBuilder();
        boolean callSuccessful = false;
        String[] token = new String[]{"ad00d31dde2c44a8b6f07c05621699d9", "a2cb028867254a79b5783c3786d19bfe", "293bb4a86da743bdb983b97efa5bb265"};
        while (!callSuccessful) {
            try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", token[0]);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((getResponse = br.readLine()) != null){
                sb.append(getResponse);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
        log.info(getResponse);
        return getResponse;
    }

    public void testDocApi() throws Exception {
        System.out.println(api("http://10.204.131.48:8080/doc/service.json"));
    }

    public void printData() throws Exception{
//        db.clearTable("CUSTOMMATCHESBLOB");
//        Map[] metaData = getMaps();
//        MapVariant[] metaData = getArenaMapVariants();
//        CustomMapVariant[] metaData = (CustomMapVariant[]) db.getMetadataFromDB(dataType.CUSTOMMAPVARIANTS, false, NA);
//        String[] metaData = Database.getMetadataTextFromDB(dataType.CARNAGE, true, ARENA);
//        CarnageReport[] metaData = getPlayerCarnageReports(ARENA);
        String[] metaDataString = db.getMetadataTextFromDB(dataType.CUSTOMMAPVARIANTS, false, NA);
        CustomMapVariant[] metaData = new CustomMapVariant[metaDataString.length];
        for (int i = 0; i < metaData.length; i++){
            metaData[i] = gson.fromJson(metaDataString[i], CustomMapVariant.class);
        }
        db.writeCustomMapVariantsToDB(metaData);
//        Weapon[] metaData = (Weapon[]) db.getMetadataFromDB(dataType.MEDALS);
    }

    public void test() throws Exception {
//        List<String> players = db.getPlayersFromDB();
//        log.info(players.size());
//        Match[] matches = getAllMatches(ARENA);
//        log.info("" + matches.length);
//        CarnageReport[] carnageReports = getAllCarnageReports(ARENA);
        MatchEvents[] matchEventsB = getPlayerMatchEvents(ARENA, "That Brock Guy");
//        MatchEvents[] matchEventsA = getPlayerMatchEvents(ARENA, "That Ax Guy");
//        MatchEvents[] matchEventsS = getPlayerMatchEvents(ARENA, "That Sturt Guy");
//        MatchEvents[] matchEventsT = getPlayerMatchEvents(ARENA, "That Trev Guy");
//        MatchEvents[] matchEventsN = getPlayerMatchEvents(ARENA, "That Noah Guy");

//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Brock Guy", "Top Gun"));
//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Brock Guy", "Killing Spree"));
//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Brock Guy", "Killing Frenzy"));
//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Brock Guy", "Overkill") + "\n");
        fastestMedalWasEarned(matchEventsB, "That Brock Guy");
//
//        log.info(fastestSpecificMedalEarned(matchEventsA, "That Ax Guy", "Top Gun"));
//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Ax Guy", "Killing Spree"));
//        log.info(fastestSpecificMedalEarned(matchEventsA, "That Ax Guy", "Killing Frenzy"));
//        log.info(fastestSpecificMedalEarned(matchEventsA, "That Ax Guy", "Overkill") + "\n");
//        fastestMedalWasEarned(matchEventsA, "That Ax Guy");
//
//        log.info(fastestSpecificMedalEarned(matchEventsS, "That Sturt Guy", "Top Gun"));
//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Sturt Guy", "Killing Spree"));
//        log.info(fastestSpecificMedalEarned(matchEventsS, "That Sturt Guy", "Killing Frenzy"));
//        log.info(fastestSpecificMedalEarned(matchEventsS, "That Sturt Guy", "Overkill") + "\n");
//        fastestMedalWasEarned(matchEventsS, "That Sturt Guy");
//
//        log.info(fastestSpecificMedalEarned(matchEventsT, "That Trev Guy", "Top Gun"));
//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Trev Guy", "Killing Spree"));
//        log.info(fastestSpecificMedalEarned(matchEventsT, "That Trev Guy", "Killing Frenzy"));
//        log.info(fastestSpecificMedalEarned(matchEventsT, "That Trev Guy", "Overkill") + "\n");
//        fastestMedalWasEarned(matchEventsT, "That Trev Guy");
//
//        log.info(fastestSpecificMedalEarned(matchEventsN, "That Noah Guy", "Top Gun"));
//        log.info(fastestSpecificMedalEarned(matchEventsB, "That Noah Guy", "Killing Spree"));
//        log.info(fastestSpecificMedalEarned(matchEventsN, "That Noah Guy", "Killing Frenzy"));
//        fastestMedalWasEarned(matchEventsN, "That Noah Guy");


//        log.info(fastestSpecificMedalEarned(matchEventsN, "That Noah Guy", "Overkill") + "\n");
//        log.info(fastestMedalWasEarned(matchEventsB, "That Brock Guy"));


//        log.info(getBetrayals(ARENA, matchEvents, "", true));
//        log.info(getBetrayals(matchEventsB, "That Brock Guy", true));
//        log.info(getBetrayals(matchEventsA, "That Ax Guy", true));
//        log.info(getBetrayals(matchEventsS, "That Sturt Guy", true));
//        log.info(getBetrayals(matchEventsT, "That Trev Guy", true));
//        log.info(getBetrayals(matchEventsN, "That Noah Guy", true));
//        log.info(getBetrayals(ARENA, matchEvents, "That Ax Guy"));
//        log.info(getBetrayals(ARENA, matchEvents, "That Noah Guy"));
////        log.info(getBetrayals(ARENA, matchEvents, "That Trev Guy"));
//        log.info(getBetrayals(ARENA, matchEvents, "That Sturt Guy"));
//        db.clearTable("arenamatchesblob");
//        MapVariant[] maps = new MapVariant[1];
//        maps[0] = getMapVariant("17f171aa-1464-4c2e-8cad-b4f4ccab9238");
//        db.writeMapVariantsToDB(maps);
    }




    public static void main(String[] args) throws Exception {
        try{
            long start = System.currentTimeMillis()/1000;
            HaloApi hapi = new HaloApi();
            hapi.cacheThatOneSpartanCompany();
//            hapi.testDocApi();

//            PLAYER = hapi.formatString(PLAYER_UF);
//            db.player = PLAYER_UF.replaceAll("\\s+", "").toUpperCase();
//            hapi.printData();
//            hapi.test();
//            log.info(hapi.getBetrayals(ARENA, "That Ax Guy"));
//            pd.cacheMatches(CUSTOM, true);
//            pd.cachePlayerCarnageThreadTest(CUSTOM);
//            hapi.testPlayerMatches(CUSTOM);
//            pd.cacheMatches(ARENA, true);
//            pd.cachePlayerCarnageThreadTest(ARENA);
//            pd.cachePlayerMatchEventsThreadTest(ARENA);
//            pd.cachePlayerCarnage(ARENA);
//            hapi.test();
//            MatchEvents[] matchEvents = hapi.getPlayerMatchEvents(ARENA, PLAYER_UF);
//            Match[] playerMatches = hapi.getPlayerMatches(ARENA);
//            pd.cacheMatchEvents(ARENA, true, matchEvents, playerMatches);
//            matchEvents = hapi.getPlayerMatchEvents(ARENA, PLAYER_UF);
//            log.info(hapi.fastestSpecificMedalEarned(matchEvents, PLAYER_UF, "Top Gun"));
//            hapi.testPlayerMatches(ARENA, 0);
//            hapi.top5Stats(ARENA);
            long endTime = System.currentTimeMillis()/1000;
            long duration = (endTime - start);
            log.info("\n\nduration:" + duration);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testJSONStuff() throws Exception {
        String test = arenaStats(formatString("That Brock Guy"));
    }

    public void runAppInCommandLine() throws Exception {
        Scanner reader = new Scanner(System.in);
        System.out.println("What is your gamertag?");
        String gamertag = reader.nextLine();
        PLAYER_UF = gamertag;
        PLAYER = formatString(PLAYER_UF);
        db.player = PLAYER;
        MatchEvents[] matchEvents = getPlayerMatchEvents(ARENA, gamertag);
        log.info(fastestSpecificMedalEarned(matchEvents, gamertag, "Top Gun"));
    }

    public void cacheThatOneSpartanCompany() throws Exception {
        pd.cacheMatches(ARENA, true);
        pd.cachePlayerCarnageThreadTest(ARENA);
        pd.cacheMatches(CUSTOM, true);
        pd.cacheGameCarnage(CUSTOM, true, null);
    }

    public String getPlayer() {
        return PLAYER_UF;
    }

    public void clearSparanCompanyTables() throws Exception {
        db.clearTable("THATNOAHGUYARENACARNAGEBLOB");
        db.clearTable("THATBROCKGUYARENACARNAGEBLOB");
        db.clearTable("THATTREVGUYARENACARNAGEBLOB");
        db.clearTable("THATAXGUYARENACARNAGEBLOB");
        db.clearTable("THATSTURTGUYARENACARNAGEBLOB");
        db.clearTable("THATNOAHGUYCUSTOMCARNAGEBLOB");
        db.clearTable("THATBROCKGUYCUSTOMCARNAGEBLOB");
        db.clearTable("THATTREVGUYCUSTOMCARNAGEBLOB");
        db.clearTable("THATAXGUYCUSTOMCARNAGEBLOB");
        db.clearTable("THATSTURTGUYCUSTOMCARNAGEBLOB");
        db.clearTable("THATNOAHGUYARENAMATCHEVENTSBLOB");
        db.clearTable("THATBROCKGUYARENAMATCHEVENTSBLOB");
        db.clearTable("THATTREVGUYARENAMATCHEVENTSBLOB");
        db.clearTable("THATAXGUYARENAMATCHEVENTSBLOB");
        db.clearTable("THATSTURTGUYARENAMATCHEVENTSBLOB");
//            db.clearTable("THATNOAHGUYCUSTOMMATCHESTEXT");
//            db.clearTable("THATBROCKGUYCUSTOMMATCHESTEXT");
//            db.clearTable("THATTREVGUYCUSTOMMATCHESTEXT");
//            db.clearTable("THATAXGUYCUSTOMMATCHESTEXT");
//            db.clearTable("THATSTURTGUYCUSTOMMATCHESTEXT");
//            db.clearTable("THATNOAHGUYARENAMATCHESTEXT");
//            db.clearTable("THATBROCKGUYARENAMATCHESTEXT");
//            db.clearTable("THATTREVGUYARENAMATCHESTEXT");
//            db.clearTable("THATAXGUYARENAMATCHESTEXT");
//            db.clearTable("THATSTURTGUYARENAMATCHESTEXT");
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
        if (days == 0 && hours == 0 && minutes ==0){
            return " " + seconds + " " + second;
        }
        if (days == 0 && hours == 0){
            return " " + minutes + " " + minute + ", and " + seconds + " " + second;
        }
        if (days == 0){
            return " " + hours + " " + hour + ", " + minutes + " " + minute + ", and " + seconds + " " + second;
        }
        else {
            return " " + days + " " + day + ", " + hours + " " + hour + ", " + minutes + " " + minute + ", and " + seconds + " " + second;
        }
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

    public String listEventsForMatch(String matchID) throws Exception {
        return api(String.format(MATCH_EVENTS, matchID));
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
        String url =  formatString(player) + "/gamevariants/" + gameVariantID;
        return api(String.format(META_UGC, url));
    }

    public String listImpulses() throws Exception {
        return api(META_IMPULSES);
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

    public Match[] getSomePLayerMatches(Enum gameType, int numberOfRecentMatches, double offset) throws Exception {
        if (Database.getMetadataFromDB(dataType.MATCHES, true, gameType) == null)
            pd.cacheMatches(gameType, true);
        return (Match[]) db.getSomeMatchesFromDB((int) offset, numberOfRecentMatches, true, gameType);
    }

    public MatchEvents[] getPlayerMatchEvents(Enum gameType, String player) throws Exception {
        Database.player = player.replaceAll("\\s+", "").toUpperCase();
        if (Database.getMetadataTextFromDB(dataType.MATCHEVENTS, true, gameType, true) == null)
            return null;
        String[] matchEvents = Database.getMetadataTextFromDB(dataType.MATCHEVENTS, true, gameType, true);
        MatchEvents[] events = new MatchEvents[matchEvents.length];
        for (int i = 0; i < events.length; i++){
            events[i] = gson.fromJson(matchEvents[i], MatchEvents.class);
        }
        return events;
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
        CustomMapVariant[] maps = getCustomMapVariants();
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

    public CustomMapVariant getCustomMapVariant(String mapVariantID, String player) throws Exception{
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

    public  CustomMapVariant[] getCustomMapVariants() throws Exception{
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
        CustomMapVariant[] mapVariants = getCustomMapVariants();
        String mapName = null;
//        log.info(mapID);
        for (int i = 0; i < mapVariants.length; i++){
            if(mapVariants[i].getIdentity().getResourceId() != null && mapVariants[i].getIdentity().getResourceId().equalsIgnoreCase(mapID)) {
                mapName = mapVariants[i].getName();
                if (mapName.endsWith("(Copy)"))
                    mapName = mapName.replace("(Copy", "");
                else if (mapName.endsWith("(copy)"))
                    mapName = mapName.replace("(copy)", "");
                break;
            }
        }
            return mapName;
    }

    public  String getCustomMapName(String mapID, CustomMapVariant[] mapVariants) throws Exception{
        String mapName = null;
        for (int i = 0; i < mapVariants.length; i++){
            if(mapVariants[i].getIdentity().getResourceId() != null && mapVariants[i].getIdentity().getResourceId().equalsIgnoreCase(mapID)) {
                mapName = mapVariants[i].getName();
                break;
            }
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

    public  String getMapName(String id, Map[] maps) throws Exception {
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
                break;
            }
        }
        return mapName;
    }

    public String getGameVariantName(String id, GameVariant[] gameVariants) throws Exception {
        String gameName = null;
        for (int i = 0; i < gameVariants.length; i++){
            if (gameVariants[i].getId().equalsIgnoreCase(id)){
                gameName = gameVariants[i].getName();
                break;
            }
        }
        return gameName;
    }

    public GameVariant[] getArenaGameVariants() throws Exception {
        String[] gameVariantsString = db.getMetadataTextFromDB(dataType.ARENAGAMEVARIANTS, false, NA);
        GameVariant[] gameVariants  = new GameVariant[gameVariantsString.length];
        for (int i = 0; i < gameVariants.length; i++){
            gameVariants[i] = gson.fromJson(gameVariantsString[i], GameVariant.class);
        }
        return gameVariants;
//        return (GameVariant[]) Database.getMetadataFromDB(dataType.ARENAGAMEVARIANTS, false, NA);
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
        for (int i = 0; i < reports.length; i++){
            for (PlayerStat ps : reports[i].getPlayerStats()){
                if (ps.getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    for (Impulse im : ps.getImpulses()){
                        for (int k = 0; k < ps.getImpulses().size(); k++){
                            if (im.getId() == ps.getImpulses().get(k).getId()){
                                log.info(impulses[k].getInternalName());
                            }
                        }
                    }
                }
            }
        }
    }

    public String getAverageLifeSpan(Enum gameType) throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(gameType);
        long totalAverageLifeSpan = 0;
        long[] allSpans = new long[reports.length];
        for (int i = 0; i < reports.length; i++){
            for (PlayerStat ps : reports[i].getPlayerStats()){
                if (ps.getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    allSpans[i] = Duration.parse(ps.getAvgLifeTimeOfPlayer()).getSeconds();
                    totalAverageLifeSpan += Duration.parse(ps.getAvgLifeTimeOfPlayer()).getSeconds();
                }
            }
        }
        Arrays.sort(allSpans);
//        for (int i = reports.length-1; i > -1; i--) {
//            log.info(getTotalDuration(allSpans[i]) + "");
//        }
        return "Your average lifespan in all " + gameType + " games is:" + getTotalDuration(totalAverageLifeSpan/reports.length) + ". Your longest life was" + getTotalDuration(allSpans[reports.length-1]);
    }

    public String getFavoriteTeamColor(Enum gameType, boolean allColors) throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(gameType);
        HashMap<String, PlayerCount> teamColors = new HashMap<>();
        double totalGames = totalGames(gameType, PLAYER);
        double numberOfTimesAsTeam = 0;
        String teams = "";
        int red = 0, blue = 0, yellow = 0, green = 0, purple = 0, magenta = 0, orange = 0, cyan = 0;
        for (int i = 0; i < reports.length; i++){
            for (PlayerStat ps : reports[i].getPlayerStats()){
                if (ps.getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    switch(ps.getTeamId()){
                        case 0: {
                            red++;
                            teamColors.put("Red", new PlayerCount("Red", red));
                        }
                            break;
                        case 1: {
                            blue++;
                            teamColors.put("Blue", new PlayerCount("Blue", blue));
                        }
                            break;
                        case 2: {
                            yellow++;
                            teamColors.put("Yellow", new PlayerCount("Yellow", yellow));
                        }
                            break;
                        case 3: {
                            green++;
                            teamColors.put("Green", new PlayerCount("Green", green));
                        }
                            break;
                        case 4: {
                            purple++;
                            teamColors.put("Purple", new PlayerCount("Purple", purple));
                        }
                            break;
                        case 5: {
                            magenta++;
                            teamColors.put("Magenta", new PlayerCount("Magenta", magenta));
                        }
                            break;
                        case 6: {
                            orange++;
                            teamColors.put("Orange", new PlayerCount("Orange", orange));
                        }
                            break;
                        case 7: {
                            cyan++;
                            teamColors.put("Cyan", new PlayerCount("Cyan", cyan));
                        }
                            break;
                        default: break;
                    }
                }
            }
        }
        List<PlayerCount> teamsByPlayCount = new ArrayList<>(teamColors.values());
        Collections.sort(teamsByPlayCount, (o1, o2) -> o2.getGameCount() - o1.getGameCount());
        if (allColors) {
            teams += "\nHere are the number of games that you've played as each Team Color:";
            for (int i = 0; i < teamsByPlayCount.size(); i++){
                numberOfTimesAsTeam = teamsByPlayCount.get(i).getGameCount()/totalGames;
                numberOfTimesAsTeam = (double)Math.round(numberOfTimesAsTeam *10000d)/100d;
                teams += "\n" + teamsByPlayCount.get(i).getName() + " Team: " + teamsByPlayCount.get(i).getGameCount() + "  (" + numberOfTimesAsTeam + "% of your games)";
            }
        }
        else {
            numberOfTimesAsTeam = teamsByPlayCount.get(0).getGameCount()/totalGames;
            numberOfTimesAsTeam = (double)Math.round(numberOfTimesAsTeam *10000d)/100d;
            teams += "\nYou have been the " + teamsByPlayCount.get(0).getName() + " team the most! " + teamsByPlayCount.get(0).getGameCount() + " times to be exact! " + "That's " + numberOfTimesAsTeam + "% of your games!";
        }
        return teams;
    }

    public  String testMedalStats(Enum gameType, boolean top5) throws Exception {
        JSONArray obj = getPlayerStatsJSON(gameType).getJSONArray("MedalAwards");
        String mostEarnedMedal = null;
        double average = 0;
        String otherMedalStats = null;
        int topGunCount = 0;
        int extermCount = 0;
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
             if (getMedalDifficulty(medalAwards[row].getMedalId(), medals) > 20)
                 continue;
            medalHash.put(medalAwards[row].getName(), medalAwards[row]);
        }
        List<MedalAward> medalsByCount = new ArrayList<>(medalHash.values());
        Collections.sort(medalsByCount, ((o1, o2) -> o2.getCount() - o1.getCount()));

        for (int i = 0; i < medalAwards.length; i++) {
            if (medalAwards[i].getName().equalsIgnoreCase("Top Gun"))
                topGunCount = medalAwards[i].getCount();
            if (medalAwards[i].getName().equalsIgnoreCase("Extermination"))
                extermCount = medalAwards[i].getCount();
        }
        double topGunAverage = (double)Math.round((topGunCount / games) *1000d)/1000d;
//        mostEarnedMedal = ("Your most earned Legendary Medal is the " + mostEarnedMedal + " medal, with a total of " + highestMedalCount + " and an average of " + average + " per game");
        if (top5) {
            mostEarnedMedal = "\nYour Top 5 most earned Medals (Rare or better) are: ";
            for (int i = 0; i < 5; i++) {
                average = (double) Math.round((medalsByCount.get(i).getCount() / games) * 1000d) / 1000d;
                mostEarnedMedal = mostEarnedMedal + "\n" + medalsByCount.get(i).getName() + " with a total count of: " + medalsByCount.get(i).getCount() + ". With an average of: " + average + " per game";
                otherMedalStats = "";
            }
        }else{
            mostEarnedMedal = "\nYour Most Earned Medal (Rare or better) is: ";
            average = (double) Math.round((medalsByCount.get(0).getCount() / games) * 1000d) / 1000d;
            mostEarnedMedal = mostEarnedMedal + "\n" + medalsByCount.get(0).getName() + " with a total count of: " + medalsByCount.get(0).getCount() + ". With an average of: " + average + " per game";
            otherMedalStats = "\n\nYou have earned the Top Gun medal " + topGunCount + " times. That's " + topGunAverage +"% of your games!";
            otherMedalStats = otherMedalStats + "\nYou have earned the Extermination Medal " + extermCount + " times!";
        }
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

    public WeaponStats[] getWeaponStats (Enum gameType) throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(gameType);

        Weapon[] weapons = gson.fromJson(listWeapons(), Weapon[].class);
        List<List<WeaponStats>> weaponStatsList = new ArrayList<>();
        for (int i = 0; i < reports.length; i++){
            for (int k = 0; k < reports[i].getPlayerStats().size(); k++) {
                if (reports[i].getPlayerStats().get(k).getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)) {
                    weaponStatsList.add(reports[i].getPlayerStats().get(k).getWeaponStats());
                }
            }
        }
        HashMap<String, WeaponStats> statsMap = new HashMap<>();
        WeaponStats stat = null;
        for (int i = 0; i < weaponStatsList.size(); i++){
            for (int k = 0; k < weaponStatsList.get(i).size(); k++){
                if (statsMap.get(getWeaponName(weaponStatsList.get(i).get(k).getWeaponId().getStockId(), weapons)) == null){
                    stat = weaponStatsList.get(i).get(k);
                    statsMap.put(getWeaponName(stat.getWeaponId().getStockId(), weapons), stat);
                }else{
                    stat = statsMap.get(getWeaponName(weaponStatsList.get(i).get(k).getWeaponId().getStockId(), weapons));
                    stat.setTotalKills(stat.getTotalKills() + weaponStatsList.get(i).get(k).getTotalKills());
                    statsMap.put(getWeaponName(stat.getWeaponId().getStockId(), weapons), stat);
                }
            }
        }
        int i = 0;
        WeaponStats[] stats = new WeaponStats[statsMap.size()];
        for (WeaponStats weapon : statsMap.values()){
            stats[i] = weapon;
            i++;
        }
        return stats;
    }

    public String testWeaponKills(Enum gameType, boolean top5) throws Exception {
        Weapon[] weapons = getWeapons();
        WeaponStats[] stats = getWeaponStats(gameType);
        String favWeapons = null;
        double totalGames = totalGames(gameType, PLAYER);
        double numberOfTimesPlayedWith = 0;
        HashMap<String, PlayerCount> playerCount = new HashMap<>();
        PlayerCount pc = null;
        for (int i = 0; i < stats.length; i++){
            if (playerCount.get(getWeaponName(stats[i].getWeaponId().getStockId(), weapons)) == null){
                pc = new PlayerCount(getWeaponName(stats[i].getWeaponId().getStockId(), weapons), 1);
                pc.setKillCount(stats[i].getTotalKills());
                playerCount.put(pc.getName(), pc);
            }
        }
        List<PlayerCount> playersByKillCount = new ArrayList<>(playerCount.values());
        Collections.sort(playersByKillCount, (o1, o2) -> o2.getKillCount() - o1.getKillCount());
        if (top5) {
            favWeapons = "\nYour top five weapons that you've played with the most are: ";
            for (int i = 0; i < 5; i++) {
                numberOfTimesPlayedWith = playersByKillCount.get(i).getKillCount()/totalGames;
                numberOfTimesPlayedWith = (double)Math.round(numberOfTimesPlayedWith *1000d)/1000d;
                favWeapons += "\n" + playersByKillCount.get(i).getName() + " with a total of " + playersByKillCount.get(i).getKillCount() + " kills! And an average of " + numberOfTimesPlayedWith + " kills per game!";
            }
        }else{
            favWeapons = "\nThe the weapon that you've used the most is: ";
            numberOfTimesPlayedWith = playersByKillCount.get(0).getKillCount()/totalGames;
            numberOfTimesPlayedWith = (double)Math.round(numberOfTimesPlayedWith *1000d)/1000d;
            favWeapons += "\n" + playersByKillCount.get(0).getName() + " with a total of " + playersByKillCount.get(0).getKillCount() + " kills! And an average of " + numberOfTimesPlayedWith + " kills per game!";
        }
        return favWeapons;
//        }
    }

    public String getBetrayals(MatchEvents[] matchEvents, boolean top5) throws Exception {
        return getBetrayals(matchEvents, PLAYER_UF, top5);
    }

    public String getBetrayals(MatchEvents[] matchEvents, String player, boolean top5) throws Exception {
        int totalSuicides = 0;
        int totalBetrayals = 0;
        HashMap<String, PlayerCount> playerCount = new HashMap<>();
        PlayerCount pc = null;
        for (int i = 0; i < matchEvents.length; i++){
            if (matchEvents[i] == null)
                continue;
            if (matchEvents[i].getGameEvents().size() < 1)
                continue;
            for (GameEvent event : matchEvents[i].getGameEvents()) {
                if (event.getKiller() == null)
                    continue;
                if (event.getKiller().getGamertag().equalsIgnoreCase(player) && event.getDeathDisposition() == 0) {
//                    if (event.getVictim() != null) {
//                        log.info(event.getKiller().getGamertag() + " " + event.getVictim().getGamertag());
//                    }
                    if (event.getVictim() == null)
                        continue;
                    if(event.getVictim().getGamertag().equalsIgnoreCase(player)) {
                        totalSuicides++;
                        continue;
                    }
                    totalBetrayals++;
                    if (playerCount.get(event.getVictim().getGamertag()) == null){
                        pc = new PlayerCount(event.getVictim().getGamertag(), 1);
                        pc.setKillCount(1);
                        playerCount.put(event.getVictim().getGamertag(), pc);
                    }else {
                        pc = playerCount.get(event.getVictim().getGamertag());
                        pc.setKillCount(pc.getKillCount() + 1);
                        playerCount.put(event.getVictim().getGamertag(), pc);
                    }
                }
            }
        }
        List<PlayerCount> playersByKillCount = new ArrayList<>(playerCount.values());
        Collections.sort(playersByKillCount, (o1, o2) -> o2.getKillCount() - o1.getKillCount());
        String betrayals = "\n" + player+ " has " + totalBetrayals +  " total betrayals and has betrayed " + playersByKillCount.size() + " Unique Spartans while playing Arena games!";
        betrayals += "\n" + player + " has killed himself " + totalSuicides + " times! What a dumb-dumb!";
        if (top5) {
            betrayals += "\nThe Top Five Spartans that " + player + " has betrayed are: ";
            for (int i = 0; i < 5; i++) {
                betrayals += "\n" + playersByKillCount.get(i).getName() + " with a total of " + playersByKillCount.get(i).getKillCount() + " betrayals!";
            }
        }else {
            betrayals += "\nThe Spartan that " + player + " has betrayed the most is: " + playersByKillCount.get(0).getName() + " with a total of " + playersByKillCount.get(0).getKillCount() + " betrayals!";
        }
        return betrayals;
    }

    public void fastestMedalWasEarned(MatchEvents[] matchEvents, String player) throws Exception {
        ArrayList<Long> times = new ArrayList<>();
        String matchID = null;
        long fastestTime = 0;
        Medal[] medals = getMedals();
        ArrayList<Medal> medalList = new ArrayList<>();
        for (Medal medal : medals) {
            medalList.add(medal);
        }
        Collections.sort(medalList, Comparator.comparing(Medal::getName));
        for (Medal medal : medalList){
            String medalName = medal.getName();
            long topGunID = medal.getId();
            fastestTime = 10000;
            for (int i = 0; i < matchEvents.length; i++) {
                long actualStartTime = 0;
                long time = 0;
                if (matchEvents[i] == null)
                    continue;
                if (matchEvents[i].getGameEvents().size() < 2)
                    continue;
                for (int k = 0; k < matchEvents[i].getGameEvents().size(); k++){
                    GameEvent event = matchEvents[i].getGameEvents().get(k);
                    if (event.getEventName().contains("RoundStart")){
                        GameEvent event2 = matchEvents[i].getGameEvents().get(k + 1);
                        if (event2.getEventName().contains("WeaponPickup") || event2.getEventName().contains("PlayerSpawn")) {
                            actualStartTime = Duration.parse(matchEvents[i].getGameEvents().get(k + 1).getTimeSinceStart()).getSeconds();
                        }
                    }
                    if (event.getMedalId() == null)
                        continue;
                    if (event.getMedalId() == topGunID && event.getPlayer().getGamertag().equalsIgnoreCase(player)) {
                        time = Duration.parse(event.getTimeSinceStart()).getSeconds() - actualStartTime;
                        times.add(time);
                        if (time < fastestTime) {
                            fastestTime = time;
                            medal.setTime(time);
                            medal.setMatchId(matchEvents[i].getMatchID());
                        }
                    }
                }
            }
            if (fastestTime == 10000)
                continue;
        }
        Collections.sort(medalList, Comparator.comparingInt(o -> (int) o.getTime()));
        for (Medal medal: medalList){
            if (medal.getTime() == 0)
                continue;
            log.info("The quickest " + player + " has ever achieved the " + medal.getName() + " medal was in" + getTotalDuration(medal.getTime()) + " seconds!  Match ID: (" + medal.getMatchId() + ")");
        }
    }

    public String fastestSpecificMedalEarned(MatchEvents[] matchEvents, String player, String medalName) throws Exception {
        long fastestTime = 10000;
        String matchID = null;
        String date = null;
        long time = 0;
        ArrayList<Long> times = new ArrayList<>();
        long topGunID = getMedalID(medalName, getMedals());
        for (int i = 0; i < matchEvents.length; i++){
            long actualStartTime = 0;
            if (matchEvents[i] == null)
                continue;
            if (matchEvents[i].getGameEvents().size() < 2)
                continue;
            for (int k = 0; k < matchEvents[i].getGameEvents().size(); k++){
                GameEvent event = matchEvents[i].getGameEvents().get(k);
                if (event.getEventName().contains("RoundStart")){
                    GameEvent event2 = matchEvents[i].getGameEvents().get(k + 1);
                    if (event2.getEventName().contains("WeaponPickup") || event2.getEventName().contains("PlayerSpawn")) {
                        actualStartTime = Duration.parse(matchEvents[i].getGameEvents().get(k + 1).getTimeSinceStart()).getSeconds();
                    }
                }
                if (event.getMedalId() == null)
                    continue;
                if (event.getMedalId() == topGunID && event.getPlayer().getGamertag().equalsIgnoreCase(player)){
                    time = Duration.parse(event.getTimeSinceStart()).getSeconds() - actualStartTime;
                    times.add(time);
                    if (time < fastestTime) {
                        fastestTime = time;
                        matchID = matchEvents[i].getMatchID();
                    }
                }
            }
        }
        return "The quickest " + player + " has ever achieved the " + medalName + " medal was in" + getTotalDuration(fastestTime) + " seconds!";
    }

    public void mostMedalWasEarned(MatchEvents[] matchEvents, String player) throws Exception {
        ArrayList<Long> times = new ArrayList<>();
        String matchID = null;
        long fastestTime = 0;
        Medal[] medals = getMedals();
        for (Medal medal : medals) {
            String medalName = medal.getName();
            long topGunID = medal.getId();
            fastestTime = 10000;
            for (int i = 0; i < matchEvents.length; i++) {
                long time = 0;
                if (matchEvents[i] == null)
                    continue;
                if (matchEvents[i].getGameEvents().size() < 1)
                    continue;
                for (GameEvent event : matchEvents[i].getGameEvents()) {
                    if (event.getMedalId() == null)
                        continue;
                    if (event.getMedalId() == topGunID && event.getPlayer().getGamertag().equalsIgnoreCase(player)) {
                        time = Duration.parse(event.getTimeSinceStart()).getSeconds();
                        times.add(time);
                        if (time < fastestTime) {
                            fastestTime = time;
                            matchID = matchEvents[i].getMatchID();
                        }
                    }
                }
            }
            if (fastestTime == 10000)
                continue;
            log.info("The quickest that " + player + " has ever achieved the " + medalName + " medal was in " + getTotalDuration(fastestTime) + " seconds!");
        }
    }

    public long getTotalTimePlayedAllModes() throws Exception {
        JSONObject obj = null;
        BaseStats stats = null;
        long playSeconds = 0;
        long totalPlaySeconds = 0;
        if (totalGames(ARENA, PLAYER_UF) > 0) {
            obj = getPlayerStatsJSON(ARENA);
            stats = gson.fromJson(obj.toString(), BaseStats.class);
            playSeconds = Duration.parse(stats.getTotalTimePlayed()).getSeconds();
            totalPlaySeconds += playSeconds;
        }
        if (totalGames(CUSTOM, PLAYER_UF) > 0) {
            obj = getPlayerStatsJSON(CUSTOM);
            stats = gson.fromJson(obj.toString(), BaseStats.class);
            playSeconds = Duration.parse(stats.getTotalTimePlayed()).getSeconds();
            totalPlaySeconds += playSeconds;
        }
        if (totalGames(WARZONE, PLAYER_UF) > 0) {
            obj = getPlayerStatsJSON(WARZONE);
            stats = gson.fromJson(obj.toString(), BaseStats.class);
            playSeconds = Duration.parse(stats.getTotalTimePlayed()).getSeconds();
            totalPlaySeconds += playSeconds;
        }
        return totalPlaySeconds;
    }

    public String favoriteGameVariants(Enum gameType, boolean top5) throws Exception {
        Match[] matches = getPlayerMatches(gameType);
        List<String> gameVNames = new ArrayList<>();
        String favGameV = null;
        double numTimesPlayedGameV = 0;
        double totalGames = totalGames(gameType, PLAYER);
        List<PlayerCount> timesPlayedMode = new ArrayList<>();
        GameVariant[] gameVs = getArenaGameVariants();
        HashMap<String, PlayerCount> gameVCount = new HashMap<>();
        String gameName = null;
        for (int i = 0; i < matches.length; i++){
            gameName = getGameVariantName(matches[i].getGameVariant().getResourceId(), gameVs);
            if (gameName == null)
                continue;
            if (gameName.equalsIgnoreCase("DMR SWAT") || gameName.equalsIgnoreCase("SWATnums"))
                gameName = "SWAT";
            gameVNames.add(gameName);
        }
        int playCount = 0;
        for (String s: gameVNames){
            PlayerCount pc = null;
            if (gameVCount.get(s) == null) {
                pc = new PlayerCount(s, 1);
                gameVCount.put(pc.getName(), pc);
            }
            else{
                pc = gameVCount.get(s);
                playCount = pc.getGameCount();
                pc.setGameCount(playCount + 1);
                gameVCount.put(pc.getName(), pc);
            }
        }
        List<PlayerCount> gameVByNameCount = new ArrayList<>(gameVCount.values());
        Collections.sort(gameVByNameCount, (o1, o2) -> o2.getGameCount() - o1.getGameCount());
        if (top5) {
            favGameV = "\nYour top five game variants that you've played in Halo 5 are: ";
            for (int i = 0; i < 5; i++) {
                numTimesPlayedGameV = gameVByNameCount.get(i).getGameCount() / totalGames;
                numTimesPlayedGameV = (double) Math.round(numTimesPlayedGameV * 100d);
                favGameV = favGameV + "\n" + gameVByNameCount.get(i).getName() + " with a total of " + gameVByNameCount.get(i).getGameCount() + " games played! That's " + numTimesPlayedGameV + "% of your games";
            }
        }else{
            favGameV = "\nYour Most Played Game Variant is: ";
                numTimesPlayedGameV = gameVByNameCount.get(0).getGameCount() / totalGames;
                numTimesPlayedGameV = (double) Math.round(numTimesPlayedGameV * 100d);
                favGameV = favGameV + "\n" + gameVByNameCount.get(0).getName() + " with a total of " + gameVByNameCount.get(0).getGameCount() + " games played! That's " + numTimesPlayedGameV + "% of your games";
        }
        return favGameV + "";
    }


    public void testBaseStats(Enum gameType) throws Exception
    {
        JSONObject obj = getPlayerStatsJSON(gameType);
        String gType = capitalize(gameType.toString().toLowerCase());
        String var = obj.toString();
        BaseStats stats = gson.fromJson(var, BaseStats.class);
        double totalShotsFired = stats.getTotalShotsFired();
        double totalShotsLanded = stats.getTotalShotsLanded();
        int totalHeadShots = stats.getTotalHeadshots();
        double totalPowerWeapon = stats.getTotalPowerWeaponGrabs();
        double totalPowerWeaponKills = stats.getTotalPowerWeaponKills();
        DecimalFormat formatter = new DecimalFormat("#,###");
        String pWeaponTime = getTotalDuration(Duration.parse(stats.getTotalPowerWeaponPossessionTime()).getSeconds());
        long playSeconds = Duration.parse(stats.getTotalTimePlayed()).getSeconds();
        long totalPlaySeconds = getTotalTimePlayedAllModes();
        String playTime = getTotalDuration(playSeconds);
        String totalPlayTime = getTotalDuration(totalPlaySeconds);
        double averagePlayTime = (((double)totalPlaySeconds/(double)getHaloReleaseTime()));
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
        accuracy = (double)Math.round(accuracy * 100000d) /1000d;
        log.info("\nHere are some more Random Stats: ");
//        log.info(PLAYER_UF + " has completed " + stats.getTotalGamesCompleted()+ " " + capitalize(gType.toString().toLowerCase()) + " games.");
        log.info("You have wasted a total of: " + playTime + " playing " + gType + " games on Halo 5!");
        testWeaponKills(gameType, false);
        log.info("You have fired a total of: " + formatter.format(totalShotsFired) + " shots. Of those, you've landed " + formatter.format(totalShotsLanded) + " shots.");
        log.info("That's an accuracy of " + accuracy + "%");
        log.info("You have slaughtered " + totalHeadShots + " Spartans with a headshot.");
        log.info("You've stroked a power weapon in your hands for a total of " + pWeaponTime);
//        log.info("You have grabbed a power weapon " + (int)totalPowerWeapon + " times, \nYou've killed " + (int)totalPowerWeaponKills + " Spartans with those power weapons.");
//        log.info("That's an average of " + powerWeaponKillAvg + " kills each time you pick up a power weapon!");
        log.info("You have Spartan Charged " + stats.getTotalShoulderBashKills() + " dumb-dumbs.");
        log.info("You have murdered " + stats.getTotalGrenadeKills() + " Spartans with grenades.");
        log.info("You have tied the stupid enemy team " + stats.getTotalGamesTied() + " time(s).");
//        log.info("You have dealt " + totalDamageDealt + " damage points in your Arena career.");
        log.info("You have performed a total of " + stats.getTotalAssassinations() + " assassinations!");
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

    public  void testActivePlaylists() throws Exception {
        String playlistData = listPlaylists();
        Playlist[] playlists = gson.fromJson(playlistData, Playlist[].class);
        for (Playlist p : playlists){
            log.info(p.getName() + " Active: " + p.isActive());
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
        HashMap<String, Integer> stringsCount = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        Match[] matches = getPlayerMatches(gameType);
        MapVariant[] unKnownMaps = new MapVariant[1];
        for (int i = 0; i < matches.length; i++){
            if (getMapVariantName(matches[i].getMapVariant().getResourceId(), maps) == null){
                unKnownMaps[0] = getMapVariant(matches[i].getMapVariant().getResourceId());
                db.writeMapVariantsToDB(unKnownMaps);
            }
        }
        maps = getArenaMapVariants();
        for (int i = 0; i < matches.length; i++){
            list.add(getMapVariantName(matches[i].getMapVariant().getResourceId(), maps));
        }

        for (String s: list){
            Integer c = stringsCount.get(s);
            if(c == null)
                c = new Integer(0);
            c++;
            stringsCount.put(s,c);
        }

        java.util.Map.Entry<String,Integer> mostRepeated = null;
        for(java.util.Map.Entry<String, Integer> e: stringsCount.entrySet()){
            if (mostRepeated == null || mostRepeated.getValue() < e.getValue())
                mostRepeated = e;
        }
        if(mostRepeated != null && mostRepeated.getKey() != null)
            favMap = ("\nYour most played Arena map is: " + mostRepeated.getKey() + " with a total of " + mostRepeated.getValue() + " games wasted");
        return favMap;
    }

    public  String favoriteCustomMapVariant(Enum gameType) throws Exception {
        String favMap = null;
        HashMap<String, Integer> stringsCount = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
//        CustomMapVariant[] mapv = getCustomMapVariants();
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
        for (java.util.Map.Entry<String, Integer> e: stringsCount.entrySet()){
            if (mostRepeated == null || mostRepeated.getValue() < e.getValue())
                mostRepeated = e;
        }
        if(mostRepeated != null)
            favMap = ("\nYour most played Custom map is: \n" + getCustomMapName(mostRepeated.getKey()) + " with a total play count of: " + mostRepeated.getValue() + " games!");
        else
            favMap = "You don't have a favorite Custom map you nigger";
        return favMap;
    }

    public  String top5MapVariant(Enum gameType) throws Exception{
        if (totalGames(gameType, PLAYER_UF) == 0)
            return null;
        if (gameType == CUSTOM){
            return top5CustomMapVariant(gameType);
        }
        Match[] matches = getPlayerMatches(gameType);
        List<String> gameVNames = new ArrayList<>();
        String favGameV = null;
        double numTimesPlayedGameV = 0;
        double totalGames = totalGames(gameType, PLAYER);
        MapVariant[] gameVs = getArenaMapVariants();
        HashMap<String, PlayerCount> gameVCount = new HashMap<>();
        String gameName = null;
        for (int i = 0; i < matches.length; i++){
            gameName = getMapVariantName(matches[i].getMapVariant().getResourceId(), gameVs);
            gameVNames.add(gameName);
        }
        int playCount = 0;
        for (String s: gameVNames){
            PlayerCount pc = null;
            if (gameVCount.get(s) == null) {
                pc = new PlayerCount(s, 1);
                gameVCount.put(pc.getName(), pc);
            }
            else{
                pc = gameVCount.get(s);
                playCount = pc.getGameCount();
                pc.setGameCount(playCount + 1);
                gameVCount.put(pc.getName(), pc);
            }
        }
        List<PlayerCount> gameVByNameCount = new ArrayList<>(gameVCount.values());
        Collections.sort(gameVByNameCount, (o1, o2) -> o2.getGameCount() - o1.getGameCount());
        favGameV = "\nYour top five map variants that you've played in Halo 5 are: ";
        for (int i = 0; i < 5; i++){
            numTimesPlayedGameV = gameVByNameCount.get(i).getGameCount() / totalGames;
            numTimesPlayedGameV = (double)Math.round(numTimesPlayedGameV *100d);
            favGameV = favGameV + "\n" + gameVByNameCount.get(i).getName() + " with a total of " + gameVByNameCount.get(i).getGameCount() + " games played! That's " + numTimesPlayedGameV + "% of your games";
        }
        return favGameV + "";
    }

    public  String top5CustomMapVariant(Enum gameType) throws Exception {
        Match[] matches = getPlayerMatches(gameType);
        List<String> gameVNames = new ArrayList<>();
        String favGameV = null;
        double numTimesPlayedGameV = 0;
        double totalGames = totalGames(gameType, PLAYER);
        CustomMapVariant[] gameVs = getCustomMapVariants();
        HashMap<String, PlayerCount> gameVCount = new HashMap<>();
        String gameName = null;
        for (int i = 0; i < matches.length; i++){
            gameName = getCustomMapName(matches[i].getMapVariant().getResourceId(), gameVs);
            if (gameName == null) {
                continue;
            }
            if (gameName.contains("The Sink")) {
                gameName = "The Sink";
            }
            gameVNames.add(gameName);
        }
        int playCount = 0;
        for (String s: gameVNames){
            PlayerCount pc = null;
            if (gameVCount.get(s) == null) {
                pc = new PlayerCount(s, 1);
                gameVCount.put(pc.getName(), pc);
            }
            else{
                pc = gameVCount.get(s);
                playCount = pc.getGameCount();
                pc.setGameCount(playCount + 1);
                gameVCount.put(pc.getName(), pc);
            }
        }
        List<PlayerCount> gameVByNameCount = new ArrayList<>(gameVCount.values());
        Collections.sort(gameVByNameCount, (o1, o2) -> o2.getGameCount() - o1.getGameCount());
        favGameV = "\nYour top five map variants that you've played in Halo 5 are: ";
        for (int i = 0; i < 5; i++){
            numTimesPlayedGameV = gameVByNameCount.get(i).getGameCount() / totalGames;
            numTimesPlayedGameV = (double)Math.round(numTimesPlayedGameV *100d);
            favGameV = favGameV + "\n" + gameVByNameCount.get(i).getName() + " with a total of " + gameVByNameCount.get(i).getGameCount() + " games played! That's " + numTimesPlayedGameV + "% of your games";
        }
        return favGameV + "";
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

    public String favoritePlayer(Enum gameType, boolean top5) throws Exception {
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
        if (top5) {
            favPlayer = "\nYour top five players that you've played Halo 5 with are: ";
            for (int i = 0; i < 5; i++) {
                numTimesWFavPlayer = playersByGameCount.get(i).getGameCount() / totalGames;
                numTimesWFavPlayer = (double) Math.round(numTimesWFavPlayer * 100d);
                favPlayer = favPlayer + "\n" + playersByGameCount.get(i).getName() + " with a total of " + playersByGameCount.get(i).getGameCount() + " games played! That's " + numTimesWFavPlayer + "% of your games";
            }
        }else{
            favPlayer = "\nThe player that you've played the most games with is: ";

                numTimesWFavPlayer = playersByGameCount.get(0).getGameCount() / totalGames;
                numTimesWFavPlayer = (double) Math.round(numTimesWFavPlayer * 100d);
                favPlayer = favPlayer + "\n" + playersByGameCount.get(0).getName() + " with a total of " + playersByGameCount.get(0).getGameCount() + " games played! That's " + numTimesWFavPlayer + "% of your games";
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

    public KilledOpponentDetail[] getEnemiesKilled (Enum gameType) throws Exception {
        CarnageReport[] reports = getPlayerCarnageReports(gameType);
        List<KilledOpponentDetail> enemiesKilledBy = new ArrayList<>();
        for (int i = 0; i < reports.length; i++){
            List<PlayerStat> playerList = reports[i].getPlayerStats();
            for(PlayerStat en : playerList){
                if(en.getPlayer().getGamertag().equalsIgnoreCase((PLAYER_UF))){
                    List<KilledOpponentDetail> killedBy = en.getKilledOpponentDetails();
                    for(KilledOpponentDetail kb: killedBy){
                        enemiesKilledBy.add(kb);
                        continue;
                    }}}}
        KilledOpponentDetail[] allEnemies = new KilledOpponentDetail[enemiesKilledBy.size()];
        for (int i = 0; i < enemiesKilledBy.size(); i++){
            allEnemies[i] = enemiesKilledBy.get(i);
        }
        return allEnemies;
    }

    public String killedOponent(Enum gameType, boolean top5) throws Exception{
        String favPlayer = null;
        HashMap<String, PlayerCount> playerCount = new HashMap<>();
        List<String> list = new ArrayList<>();
        KilledOpponentDetail[] teamMates = getEnemiesKilled(gameType);
        PlayerCount pc = null;
        for (int i = 0; i < teamMates.length; i++){
            list.add(teamMates[i].getGamerTag());
            if (playerCount.get(teamMates[i].getGamerTag()) == null){
                pc = new PlayerCount(teamMates[i].getGamerTag(), 1);
                pc.setKillCount(teamMates[i].getTotalKills());
                playerCount.put(pc.getName(), pc);
            }
            else{
                pc = playerCount.get(teamMates[i].getGamerTag());
                pc.setGameCount(pc.getGameCount() + 1);
                pc.setKillCount(pc.getKillCount() + teamMates[i].getTotalKills());
            }
        }
        List<PlayerCount> playersByGameCount = new ArrayList<>(playerCount.values());
        Collections.sort(playersByGameCount, (o1, o2) -> o2.getGameCount() - o1.getGameCount());
        List<PlayerCount> playersByKillCount = new ArrayList<>(playerCount.values());
        Collections.sort(playersByKillCount, (o1, o2) -> o2.getKillCount() - o1.getKillCount());
        if (top5) {
            favPlayer = "\nThe Top 5 Players that you've killed the most are: ";
            for (int i = 0; i < 5; i++) {
                favPlayer += "\nYou have killed " + playersByKillCount.get(i).getName() + " a total of " + playersByKillCount.get(i).getKillCount() + " times!";
            }
        }else{
            favPlayer = "\nThe player that you killed the most is: ";
            favPlayer += "\n" + playersByKillCount.get(0).getName() + " you've killed them " + playersByKillCount.get(0).getKillCount() + " times!";
        }

        return favPlayer;
    }

    public String killedByOponent(Enum gameType, boolean top5) throws Exception{
        String favPlayer = null;
        HashMap<String, PlayerCount> playerCount = new HashMap<>();
        List<String> list = new ArrayList<>();
        KilledByOpponentDetail[] teamMates = getEnemyKills(gameType);
        PlayerCount pc = null;
        for (int i = 0; i < teamMates.length; i++){
            list.add(teamMates[i].getGamerTag());
            if (playerCount.get(teamMates[i].getGamerTag()) == null){
                pc = new PlayerCount(teamMates[i].getGamerTag(), 1);
                pc.setKillCount(teamMates[i].getTotalKills());
                playerCount.put(pc.getName(), pc);
            }
            else{
                pc = playerCount.get(teamMates[i].getGamerTag());
                pc.setGameCount(pc.getGameCount() + 1);
                pc.setKillCount(pc.getKillCount() + teamMates[i].getTotalKills());
                playerCount.put(pc.getName(), pc);
            }
        }        List<PlayerCount> playersByGameCount = new ArrayList<>(playerCount.values());
        Collections.sort(playersByGameCount, (o1, o2) -> o2.getGameCount() - o1.getGameCount());
        List<PlayerCount> playersByKillCount = new ArrayList<>(playerCount.values());
        Collections.sort(playersByKillCount, (o1, o2) -> o2.getKillCount() - o1.getKillCount());
        if (top5) {
            favPlayer = "\nYour top five enemies that you've played against the most are: ";
            for (int i = 0; i < 5; i++) {
                favPlayer = favPlayer + "\n" + playersByGameCount.get(i).getName() + " with a total of " + playersByGameCount.get(i).getGameCount() + " games played!";
            }
        }else{
            favPlayer = "\nThe enemy that you've played the most games with is: ";
            favPlayer = favPlayer + "\n" + playersByGameCount.get(0).getName() + " with a total of " + playersByGameCount.get(0).getGameCount() + " games played!";
        }
        if (top5) {
            favPlayer += "\n\nThe Top 5 Players that have killed you the most are: ";
            for (int i = 0; i < 5; i++) {
                favPlayer += "\n" + playersByKillCount.get(i).getName() + " has killed you a total of " + playersByKillCount.get(i).getKillCount() + " times! You've been enemies " + playersByKillCount.get(i).getGameCount() + " times";
            }
        }else{
            favPlayer += "\n\nThe player that has killed you the most is: ";
                favPlayer += "\n" + playersByKillCount.get(0).getName() + " has killed you a total of " + playersByKillCount.get(0).getKillCount() + " times! You've been enemies " + playersByKillCount.get(0).getGameCount() + " times";
        }

        return favPlayer;
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

    public void testPlayerMatches(Enum gameType, int numberOfRecentMatches) throws Exception {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GameVariant[] gameVariantsString =  getArenaGameVariants();
        Date completedDate = null;
        Map[] maps = getMaps();
        double kdRatio = 0;
        double averageKills = 0;
        double averageDeaths = 0;
        double bestTotalKills = 0;
        double bestTotalDeaths = 0;
        int matchesDNF = 0;
        double mostKills = 0;
        double mostDeaths = 0;
        String bestMatchID = null;
        String worstMatchID = null;
        String worstGameScore = null;
        double totalGames = totalGames(gameType, PLAYER_UF);
        BaseStats stats = getBaseStats(gameType, PLAYER_UF);
        int positiveCount = 0;
        Match[] matches;
        if (numberOfRecentMatches > 0)
            matches = getSomePLayerMatches(gameType, numberOfRecentMatches, (totalGames - numberOfRecentMatches));
        else
            matches = getPlayerMatches(gameType);
        totalGames = matches.length;
        for (int i = 0; i < matches.length; i++){
//            log.info("" + matches[i].getMatchCompletedDate().getDate());
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
                    gameVariantsString = getArenaGameVariants();
                }
                gameKind = getGameVariantName(matches[i].getGameVariant().getResourceId(), gameVariantsString);
            }else if (gameType.toString().equalsIgnoreCase("CUSTOM")){
                gameKind = "Custom";
//                if (getGameVariantName(matches[i].getGameVariant().getResourceId(), gameVariantsString) == null) {
//                    GameVariant[] gameVariants = new GameVariant[1];
//                    gameVariants[0] = (gson.fromJson(listCustomGameVariants(matches[i].getGameVariant().getResourceId(), matches[i].getGameVariant().getOwner()), GameVariant.class));
//                    if (gameVariants[0] != null) {
//                        Database.writeGameVariantsToDB(gameVariants, dataType.ARENAGAMEVARIANTS, false, NA);
//                        gameVariantsString = getArenaGameVariants();
//                    }
//                }
            }
            if (! mapName.contains("Breakout Arena") && ! mapName.contains("Glacier") && ! mapName.contains("Skylark")) {
                if (gameType.toString().equalsIgnoreCase("ARENA"))
                    if (gameKind.equalsIgnoreCase("Grifball"))
                        continue;
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
                positiveCount++;
            }}
        kdRatio = (double)Math.round(kdRatio *1000d) / 1000d;
        CarnageReport report = null;
        if (gameType.toString().equalsIgnoreCase("ARENA"))
            report = gson.fromJson(postGameCarnage(worstMatchID), CarnageReport.class);
        else if (gameType.toString().equalsIgnoreCase("CUSTOM"))
            report = gson.fromJson(postCustomGameCarnage(worstMatchID), CarnageReport.class);
        int worstTeamID = getTeamID(PLAYER_UF, report);
        worstGameScore = getTeamScore(worstTeamID, report);
        log.info(PLAYER_UF + " has played: " + (int)totalGames + " " + capitalize(gameType.toString().toLowerCase()) + " games");
        log.info("The most kills you've ever had in one match is: " + mostKills + " kills!");
        log.info("The most deaths that you've ever had in a match is: " + mostDeaths + " deaths! Your team's score that game was " + worstGameScore + "");
        double percentagePositive = (positiveCount/totalGames);
        percentagePositive = (double)Math.round(percentagePositive *100d);
        log.info("Your total number of kills: " + (int)averageKills + " Your total number of deaths: " + (int)averageDeaths);
        double averageKills1 = Math.round((averageKills/totalGames)*100d)/100d;
        double averageDeaths1 = Math.round((averageDeaths/totalGames)*100d)/100d;
        log.info("Your average K/D spread is: " + getKD(averageKills, averageDeaths) + ". " + "With an average of " + averageKills1 + " kills and " + averageDeaths1 + " deaths per game.");
        log.info(getAverageLifeSpan(gameType));
        log.info(getFavoriteTeamColor(gameType, false));
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
//        log.info("Here are the medals you earned in that game: \n");
//        for (int i = 0; i < playerStats.length; i++){
//            for (MedalAward medal : playerStats[i].getMedalAwards()){
//                for (int k = 0; k < medals.length; k++){
//                    if (medal.getMedalId() == medals[k].getId()){
//                        medal.setName(medals[k].getName());
//                    }}
//                if (playerStats[i].getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
//                    log.info(medal.getName() + " x " + medal.getCount());
//                }}}
        testBaseStats(gameType);
        log.info(testMedalStats(gameType, false));
        log.info(favoriteMapVariant(gameType));
        log.info(favoriteGameVariants(gameType, false));
        log.info(killedByOponent(gameType, false));
        log.info(killedOponent(gameType, false));
        log.info(favoritePlayer(gameType, false));
        log.info(getBetrayals(getPlayerMatchEvents(gameType, PLAYER_UF), false));
    }

    public void top5Stats(Enum gameType) throws Exception {
        log.info("Here are " + PLAYER_UF + "'s " + capitalize(gameType.toString().toLowerCase()) + " Games Top 5 Stats!");
        log.info(top5MapVariant(gameType));
        log.info(favoriteGameVariants(gameType, true));
        log.info(favoritePlayer(gameType, true));
//        log.info(killedByOponent(gameType, true));
        log.info(killedOponent(gameType, true));
        log.info(testMedalStats(gameType, true));
        log.info(testWeaponKills(gameType, true));
        log.info(getFavoriteTeamColor(gameType, true));
        log.info(getBetrayals(getPlayerMatchEvents(gameType, PLAYER_UF), true));
    }

    public void customGamesComparison(Enum gameType, String comparePlayer) throws Exception {
        Match[] playerMatches = getPlayerMatches(gameType);
//        Match[] cPlayerMatches = getPlayerMat;
    }
}