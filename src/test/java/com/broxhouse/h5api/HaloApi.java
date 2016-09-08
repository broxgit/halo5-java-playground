package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.*;
import com.broxhouse.h5api.models.metadata.Map;
import com.broxhouse.h5api.models.stats.common.MedalAward;
import com.broxhouse.h5api.models.stats.common.Player;
import com.broxhouse.h5api.models.stats.common.WeaponStats;
import com.broxhouse.h5api.models.stats.matches.Match;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.HashSet;

import static com.broxhouse.h5api.gameType.*;


enum gameType {WARZONE, ARENA, CUSTOM, NA}


public class HaloApi {

    public String PLAYER_UF = "That Brock Guy";
    private final String PLAYER = formatString(PLAYER_UF);
    private static final String TOKEN = "ad00d31dde2c44a8b6f07c05621699d9";
    private static final String BASE_URL = "https://www.haloapi.com/";
    private static final String STATS_URL = "https://www.haloapi.com/stats/h5/";
    private static final String META_URL = "https://www.haloapi.com/metadata/h5/metadata/";
    private final String PLAYER_MATCHES = STATS_URL + "players/" + PLAYER + "/matches";
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

//    private static boolean cachingResources = false;
//    private static boolean cachingResources = true;
//    private static boolean cachingMatches = false;
//    private static boolean cachingMatches = true;
    private static boolean cachingMapVariants = false;
//    private static boolean cachingMapVariants = true;
//    private static boolean cacheMetaData = false;
////    private static boolean cacheMetaData = true;

    Database db = new Database();

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

    public void printData() throws Exception{
//        db.clearTable("CUSTOMMATCHESBLOB");
//        Map[] metaData = getMaps();
//        MapVariant[] metaData = getArenaMapVariants();
        CustomMapVariant[] metaData = (CustomMapVariant[]) db.getMetadataFromDB(dataType.CUSTOMMAPVARIANTS, false, NA);
//        Match[] metaData = getMatches(ARENA);
//        Weapon[] metaData = (Weapon[]) db.getMetadataFromDB(dataType.MEDALS);
        int i = 0;
        for (; i < metaData.length; i++){
            System.out.println(metaData[i].getName());
        }
        System.out.println(metaData.length);
    }

    public Player[] cachePlayers() throws Exception{
        Match[] matches = getMatches(ARENA);
        List<Player> playersList = new ArrayList<>();
        for (int i = 0; i < matches.length; i++){
            for (int k = 0; k < matches[i].getPlayers().size(); i++){
                playersList.add(matches[i].getPlayers().get(k).getPlayer());
                System.out.println(matches[i].getPlayers().size() + " " + matches[i].getPlayers().get(k).getPlayer().getGamertag());
            }
        }
        Player[] players = new Player[playersList.size()];
        for (int i = 0; i < playersList.size(); i++){
            players[i] = playersList.get(i);
        }
        return players;
    }

    public void cacheData() throws Exception{
        cacheAllMetaData();
        cacheMatches(ARENA);
    }

    public void test() throws Exception{
        db.addItemsToDatabase(dataType.PLAYERS);
    }

    public static void main(String[] args) throws Exception {
        try{
            HaloApi hapi = new HaloApi();
//            long startTime = System.nanoTime();
//            hapi.printData();
//            hapi.test();
//            hapi.cacheMatches(ARENA);
//            hapi.cacheData();
//            hapi.cacheAllMetaData();
//            hapi.cacheArenaMaps();
//            hapi.cacheCustomMaps();
//            hapi.getMedals();
//            hapi.getMaps();
//            hapi.getWeapons();
//            hapi.cacheData();
//            hapi.testPlayerMatches(CUSTOM);
//            hapi.cacheEnemyKills(CUSTOM);
//            hapi.cachePlayerData(CUSTOM, hapi.PLAYER_UF);
//            System.out.println(hapi.killedByOponent(ARENA));
//            hapi.cacheAllPlayerData(hapi.PLAYER_UF);
            hapi.cacheGameCarnage(ARENA);
//            System.out.println(hapi.favoriteMapVariant(ARENA));
//            System.out.println(hapi.favoriteCustomMapVariant(CUSTOM));
//            System.out.println(hapi.killedByOponent(CUSTOM));
//            hapi.getMatches(ARENA);
//            hapi.testBaseStats(ARENA);
//            hapi.postCustomGameCarnage("af5c2264-91d4-4056-a206-7c4111351d24");
//            hapi.comparePlayers("That Ax Guy", "That Brock Guy", CUSTOM);
//            long endTime = System.nanoTime();
//            long duration = (endTime - startTime);
//            System.out.println("Start time: " + startTime + " end time:" + endTime + " duration:" + duration);
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

    public  void cachePlayerData(Enum gameType, String gamerTag) throws Exception{
//        gamerTag = gamerTag.toLowerCase();
//        Thread metaDataThread = new Thread(){
//            @Override
//            public void run() {
//                try {
//                    HaloApi hapi = new HaloApi();
//                    cacheMetaData = true;
//                    hapi.getMaps();
//                    hapi.getWeapons();
//                    hapi.getMedals();
//                    cacheMetaData = false;
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }};
//        Thread axelThread = new Thread(){
//            @Override
//            public void run(){
//                try{
//                    HaloApi hapi = new HaloApi();
//                    hapi.PLAYER_UF = "That Ax Guy";
//                    hapi.cachingMatches = true;
//                    hapi.getMatches(gameType);
//                    hapi.cachingMatches = false;
//                    hapi.cachingMapVariants = true;
//                    hapi.cacheArenaMaps(gameType);
//                    hapi.cachingMapVariants = false;
//                    hapi.cacheGameCarnage(gameType);
//                    cacheEnemyKills(gameType);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//          }};
//        Thread brockThread = new Thread(){
//            @Override
//            public void run(){
//                try{
//                    HaloApi hapi = new HaloApi();
//                    hapi.PLAYER_UF = "That Brock Guy";
//                    hapi.cachingMatches = true;
//                    hapi.getMatches(gameType);
//                    hapi.cachingMatches = false;
//                    hapi.cachingMapVariants = true;
//                    hapi.cacheArenaMaps(gameType);
//                    hapi.cachingMapVariants = false;
//                    hapi.cacheGameCarnage(gameType);
//                    cacheEnemyKills(gameType);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }};
//        Thread stuartThread = new Thread(){
//            @Override
//            public void run(){
//                try{
//                    HaloApi hapi = new HaloApi();
//                    hapi.PLAYER_UF = "That Sturt Guy";
//                    hapi.cachingMatches = true;
//                    hapi.getMatches(gameType);
//                    hapi.cachingMatches = false;
//                    hapi.cachingMapVariants = true;
//                    hapi.cacheArenaMaps(gameType);
//                    hapi.cachingMapVariants = false;
//                    hapi.cacheGameCarnage(gameType);
//                    cacheEnemyKills(gameType);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }};
//        Thread trevorThread = new Thread(){
//            @Override
//            public void run(){
//                try{
//                    HaloApi hapi = new HaloApi();
//                    hapi.PLAYER_UF = "That Trev Guy";
//                    hapi.cachingMatches = true;
//                    hapi.getMatches(gameType);
//                    hapi.cachingMatches = false;
//                    hapi.cachingMapVariants = true;
//                    hapi.cacheArenaMaps(gameType);
//                    hapi.cachingMapVariants = false;
//                    hapi.cacheGameCarnage(gameType);
//                    cacheEnemyKills(gameType);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }};
//        Thread richardThread = new Thread(){
//            @Override
//            public void run(){
//                try{
//                    HaloApi hapi = new HaloApi();
//                    hapi.PLAYER_UF = "That Noah Guy";
//                    hapi.cachingMatches = true;
//                    hapi.getMatches(gameType);
//                    hapi.cachingMatches = false;
//                    hapi.cachingMapVariants = true;
//                    hapi.cacheArenaMaps(gameType);
//                    hapi.cachingMapVariants = false;
//                    hapi.cacheGameCarnage(gameType);
//                    cacheEnemyKills(gameType);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }};
//        switch(gamerTag){
//                case "that brock guy": metaDataThread.start();brockThread.start();
//                    break;
//                case "that ax guy": metaDataThread.start();axelThread.start();
//                    break;
//                case "that sturt guy": metaDataThread.start();stuartThread.start();
//                    break;
//                case "that trev guy": metaDataThread.start();trevorThread.start();
//                    break;
//                default:  metaDataThread.start();axelThread.start();brockThread.start();stuartThread.start();
//        }
////        axelThread.start();
////        brockThread.start();
////        stuartThread.start();
////        trevorThread.start();
////        richardThread.start();
    }

    public  void cacheAllPlayerData(String gamerTag) throws Exception{
        Thread customThread = new Thread() {
            @Override
            public void run() {
                try {
                    cachePlayerData(CUSTOM, gamerTag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }};
        Thread arenaThread = new Thread() {
            @Override
            public void run() {
                try {
                    cachePlayerData(ARENA, gamerTag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }};
//        Thread warzoneThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    cachePlayerData(WARZONE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }};
        customThread.start();
        arenaThread.start();
//        warzoneThread.start();
    }

    public  void cacheAllMetaData() throws Exception{
        Gson gson = new Gson();
//        db.addItemsToDatabase(dataType.WEAPONS);
//        db.addItemsToDatabase(dataType.MAPS);
//        db.addItemsToDatabase(dataType.MEDALS);
//        db.addItemsToDatabase(dataType.MAPVARIANTS);
//        db.addItemsToDatabase(dataType.CUSTOMMAPVARIANTS);
        db.writeMetadataToDB(gson.fromJson(listMedals(), Medal[].class), dataType.MEDALS, false, NA);
        db.writeMetadataToDB(gson.fromJson(listWeapons(), Weapon[].class), dataType.WEAPONS, false, NA);
        db.writeMetadataToDB(gson.fromJson(listMaps(), Map[].class), dataType.MAPS, false, NA);
        db.addItemsToDatabase(dataType.PLAYERS);
        cacheCustomMaps();
        cacheArenaMaps();

    }

    public void cacheGameCarnage(Enum gameType) throws Exception {
        System.out.println("Getting " + gameType + " game carnage for: " + PLAYER_UF);
        CarnageReport[] oldCarnageReports = (CarnageReport[]) db.getMetadataFromDB(dataType.CARNAGE, false, gameType);
        System.out.println("boobs");
        List<String> oldMatchIDs = new ArrayList<>();
        List<String> newMatchIDs = new ArrayList<>();
        List<String> allMatchIDs = new ArrayList<>();
        Gson gson = new Gson();
        Match[] matches = getMatches(gameType);
        CarnageReport carnageReport = null;
        List<CarnageReport> carnageList = new ArrayList<>();
        if (oldCarnageReports != null) {
            for (int i = 0; i < oldCarnageReports.length; i++) {
                oldMatchIDs.add(oldCarnageReports[i].getMatchId());
            }
            for (int i = 0; i < matches.length; i++) {
                if (oldMatchIDs.contains(matches[i].getId().getMatchId())) {
                    allMatchIDs.add(matches[i].getId().getMatchId());
                    continue;
                }
                else {
                    newMatchIDs.add(matches[i].getId().getMatchId());
                    allMatchIDs.add(matches[i].getId().getMatchId());
                }
            }
        } else if (oldCarnageReports == null) {
            for (int i = 0; i < matches.length; i++) {
                newMatchIDs.add(matches[i].getId().getMatchId());
            }
        }
            JSONObject obj = null;
            for (int i = 0; i < newMatchIDs.size(); i++) {
                if (gameType == ARENA) {
                    obj = new JSONObject(postGameCarnage(newMatchIDs.get(i)));
                } else if (gameType == CUSTOM) {
                    obj = new JSONObject(postCustomGameCarnage(newMatchIDs.get(i)));
                } else if (gameType == WARZONE) {}

                carnageReport = gson.fromJson(obj.toString(), CarnageReport.class);
                carnageReport.setMatchId(matches[i].getId().getMatchId());
                carnageList.add(carnageReport);

                continue;
            }
            CarnageReport[] reports = new CarnageReport[carnageList.size()];
            for (int i = 0; i < carnageList.size(); i++) {
                reports[i] = carnageList.get(i);
            }
            db.writeCarnageReportsToDB(reports, dataType.CARNAGE, false, gameType);
        oldCarnageReports = (CarnageReport[]) db.getMetadataFromDB(dataType.CARNAGE, true, gameType);
        CarnageReport[] playerReports = new CarnageReport[oldCarnageReports.length];
        int diffCount = 0;
        for (int i = 0; i < oldCarnageReports.length; i++){
            if(! allMatchIDs.contains(oldCarnageReports[i].getMatchId())){
                playerReports[i] = oldCarnageReports[i];
                diffCount++;
            }
        }
        System.out.println(diffCount);
        db.writeCarnageReportsToDB(playerReports, dataType.CARNAGE, true, gameType);
        }

    public void cacheArenaMaps() throws Exception {
//        MapVariant[] oldmaps = db.getArenaMapVariants();
//        List<Resource> oldResourceList = new ArrayList<>();
        MapVariant[] oldmaps = (MapVariant[]) db.getMetadataFromDB(dataType.ARENAMAPVARIANTS, false, NA);
        List<Resource> oldResourceList = new ArrayList<>();
        Resource[] oldResources = null;
        for (int i = 0; i < oldmaps.length; i++){
            Resource oldResource = new Resource();
            oldResource.setResourceId(oldmaps[i].getId());
            oldResourceList.add(oldResource);
        }
        oldResources = new Resource[oldResourceList.size()];
        for (int i = 0; i < oldResourceList.size(); i++){
            oldResources[i] = oldResourceList.get(i);
        }
        Set<String> oldMapIDs = getUniqueResources(oldResources);
        System.out.println("Caching Custom Maps");
        Match[] matches = getMatches(ARENA);
        Resource[] resources = null;
        List<Resource> mapList = new ArrayList<>();
        int k = 0;
        System.out.println("Creating resourceList");
        for (int i = 0; i < matches.length; i++){
            Resource mapv = matches[i].getMapVariant();
            mapList.add(mapv);
        }
        System.out.println("Creating resource array");
        resources = new Resource[mapList.size()];
        for (int i = 0; i < mapList.size(); i++){
            resources[i] = mapList.get(i);
        }
        boolean newValues = false;
        boolean firstRun = false;
        Set<String> mapIDs = getUniqueResources(resources);
        MapVariant[] maps = null;
        List<MapVariant> newMaps = new ArrayList<>();
        if (firstRun) {
            maps = new MapVariant[resources.length];
            for (int i = 0; i < resources.length; i++) {
                System.out.println(resources[i].getResourceId());
                maps[i] = getMapVariant(resources[i].getResourceId());
            }
        }
        if (!firstRun) {
            for (String s : mapIDs) {
                if (db.checkDBForValue("resourceid", s, dataType.CUSTOMMAPVARIANTS)) {
//                    System.out.println("Contains");
                    k++;
                    continue;
                } else {
//                    System.out.println("Does not contain");
                    MapVariant map = getMapVariant(s);
                    newMaps.add(map);
                    newValues = true;
                    k++;
                    continue;
                }
            }
            maps = new MapVariant[newMaps.size()];
            for (int i = 0; i < newMaps.size(); i++){
                maps[i] = newMaps.get(i);
            }
        }
        System.out.println("Adding map variants to the database");
        if (newValues == true){
            db.writeMapVariantsToDB(maps);
            System.out.println("Finished caching Arena maps");
        }else{
            System.out.println("There weren't any new values to add.");
        }



//        for (int i = 0; i < oldmaps.length; i++){
//            Resource oldResource = new Resource();
//            oldResource.setResourceId(oldmaps[i].getId());
//            oldResourceList.add(oldResource);
//        }
//        oldResources = new Resource[oldResourceList.size()];
//        for (int i = 0; i < oldResourceList.size(); i++){
//            oldResources[i] = oldResourceList.get(i);
//        }
//        Set<String> oldMapIDs = getUniqueResources(oldResources);
//        System.out.println("Caching Arena Maps");
//        Match[] matches = db.getMatchesDB(ARENA);
//        Resource[] resources = null;
//        List<Resource> mapList = new ArrayList<>();
//        int k = 0;
//        System.out.println("Creating resourceList");
//        for (int i = 0; i < matches.length; i++){
//            Resource mapv = matches[i].getMapVariant();
//            mapList.add(mapv);
//        }
//        System.out.println("Creating resource array");
//        resources = new Resource[mapList.size()];
//        Set<String> mapIDs = getUniqueResources(resources);
//        for (int i = 0; i < mapList.size(); i++){
//            resources[i] = mapList.get(i);
//        }
//        boolean newValues = false;
//        List<MapVariant> mapList2 = new ArrayList<>();
//        for (String s: mapIDs){
//            if (db.checkDBForValue("id", s, dataType.MAPVARIANTS)) {
//                k++;
//                continue;
//            }else{
//                MapVariant map = getMapVariant(s);
//                mapList2.add(map);
//                newValues = true;
//                k++;
//                continue;
//            }
//        }
//        MapVariant[] maps = new MapVariant[mapList2.size()];
//        for (int i = 0; i < mapList2.size(); i++){
//            maps[i] = mapList2.get(i);
//        }
//        System.out.println("Adding map variants to the database");
//        if (newValues == true){
//            db.addArenaMapVariantsToDB(maps);
//            System.out.println("Finished caching Arena maps");
//        }else System.out.println("There weren't any new values to add.");
    }

    public void cacheCustomMaps() throws Exception{
        CustomMapVariant[] oldmaps = (CustomMapVariant[]) db.getMetadataFromDB(dataType.CUSTOMMAPVARIANTS, false, NA);
        List<Resource> oldResourceList = new ArrayList<>();
        Resource[] oldResources = null;
        for (int i = 0; i < oldmaps.length; i++){
            Resource oldResource = new Resource();
            oldResource.setResourceId(oldmaps[i].getIdentity().getResourceId());
            oldResourceList.add(oldResource);
        }
        oldResources = new Resource[oldResourceList.size()];
        for (int i = 0; i < oldResourceList.size(); i++){
            oldResources[i] = oldResourceList.get(i);
        }
        Set<String> oldMapIDs = getUniqueResources(oldResources);
        System.out.println("Caching Custom Maps");
        Match[] matches = getMatches(CUSTOM);
        Resource[] resources = null;
        List<Resource> mapList = new ArrayList<>();
        int k = 0;
        System.out.println("Creating resourceList");
        for (int i = 0; i < matches.length; i++){
            Resource mapv = matches[i].getMapVariant();
            mapList.add(mapv);
        }
        System.out.println("Creating resource array");
        resources = new Resource[mapList.size()];
        for (int i = 0; i < mapList.size(); i++){
            resources[i] = mapList.get(i);
        }
        boolean newValues = true;
        boolean firstRun = true;
        Set<String> mapIDs = getUniqueResources(resources);
        CustomMapVariant[] maps = null;
        List<CustomMapVariant> newMaps = new ArrayList<>();
        if (firstRun) {
            maps = new CustomMapVariant[resources.length];
            for (int i = 0; i < resources.length; i++) {
                System.out.println(resources[i].getResourceId());
                maps[i] = getCustomMapVariant(resources[i].getResourceId(), formatString(resources[i].getOwner()));
            }
        }
        if (!firstRun) {
            for (String s : mapIDs) {
                if (db.checkDBForValue("resourceid", s, dataType.CUSTOMMAPVARIANTS)) {
//                    System.out.println("Contains");
                    k++;
                    continue;
                } else {
//                    System.out.println("Does not contain");
                    CustomMapVariant map = getCustomMapVariant(s, getMapOwner(s));
                    newMaps.add(map);
                    newValues = true;
                    k++;
                    continue;
                }
            }
            maps = new CustomMapVariant[newMaps.size()];
            for (int i = 0; i < newMaps.size(); i++){
                maps[i] = newMaps.get(i);
            }
        }
        System.out.println("Adding map variants to the database");
        if (newValues == true){
            db.writeCustomMapVariantsToDB(maps);
            System.out.println("Finished caching Arena maps");
        }else{
            System.out.println("There weren't any new values to add.");
        }
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
        String pURL = PLAYER_MATCHES;
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

    public String listGameVariant(String gameVariantID) throws Exception{
        return api(String.format(META_GAME_VARIANTS, gameVariantID));
    }

    public String listCustomGameVariants(String gameVariantID, String player) throws Exception{
        String url =  player + "/gamevariants/" + gameVariantID;
        return api(String.format(META_UGC, url));
    }


    public  void testJSONWeapons() throws Exception {
        Gson gson = new Gson();
        Weapon[] data = gson.fromJson(listWeapons(), Weapon[].class);
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < data.length; i++){
            System.out.println(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public  void testJSONMedals() throws Exception
    {
        Gson gson = new Gson();
        Medal[] data = gson.fromJson(listMedals(), Medal[].class);
        System.out.println(Arrays.toString(data));
        for (int i = 0; i < data.length; i++){
            System.out.println(data[i].getName() + " ID:  " + data[i].getId());
        }
    }

    public  Medal[] getMedals() throws Exception {
        return (Medal[]) db.getMetadataFromDB(dataType.MEDALS, false, NA);
    }

    public  Map[] getMaps() throws Exception{
        return (Map[]) db.getMetadataFromDB(dataType.MAPS, false, NA);
    }

    public  Weapon[] getWeapons() throws Exception {
        return (Weapon[]) db.getMetadataFromDB(dataType.WEAPONS, false, NA);
    }

    public Match[] getMatches(Enum gameType) throws Exception{
        return (Match[]) db.getMetadataFromDB(dataType.MATCHES, true, gameType);
    }

    public void cacheMatches(Enum gameType) throws Exception {
        JSONObject obj = null;
        JSONArray obj2 = null;
        Integer start = 0;
        Match[] matches = null;
        Match[] matches1 = null;
        List<Match> matchList = new ArrayList<>();
        Database db2 = new Database();
        Match match = null;
        Gson gson = new Gson();
        System.out.println("Caching " + capitalize(gameType.toString().toLowerCase()) +  " matches for " + PLAYER_UF);
        double totalGames = totalGames(gameType, PLAYER_UF);
        if(totalGames == 0) {
            System.out.println("This player hasn't played this game type");
            return;
        }
        double iterations = totalGames / 25;
        for (int i = 0; i < iterations; i++) {
            obj = new JSONObject(playerMatches(PLAYER, gameType.toString().toLowerCase(), start, 25));
            obj2 = obj.getJSONArray("Results");
            matches1 = gson.fromJson(obj2.toString(), Match[].class);
            for (int k = 0; k < matches1.length; k++){
                match = matches1[k];
                matchList.add(match);
            }
            start = start + obj.getInt("Count");
        }
        matches = new Match[matchList.size()];
        for (int i = 0; i < matchList.size(); i++){
            matches[i] = matchList.get(i);
        }
        System.out.println("Adding matches to database");
        db.writeMatchesToDB(matches, dataType.MATCHES, true, gameType);
        db.writeMatchesToDB(matches, dataType.MATCHES, false, gameType);
//        db.writeMetadataToDB(matches, dataType.MATCHES, true, gameType);
//        db.writeMetadataToDB(matches, dataType.MATCHES, false, gameType);
        System.out.println("Finished caching Matches for " + PLAYER_UF);
    }

    public  CarnageReport[] getCarnageReports(Enum gameType) throws Exception{
        return (CarnageReport[]) db.getMetadataFromDB(dataType.CARNAGE, true, gameType);
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
        Gson gson = new Gson();
        String mapData = listMapVariants(mapVariantID);
        MapVariant mapVariant = gson.fromJson(mapData, MapVariant.class);
        return mapVariant;
    }

    public GameVariant getGameVariant(String gameVariantID) throws Exception{
        Gson gson = new Gson();
        String gameData = listGameVariant(gameVariantID);
        return gson.fromJson(gameData, GameVariant.class);
    }

    public String getMapOwner(String mapID) throws Exception{

        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(getFileName(CUSTOM, PLAYER_UF, "mapMin")));
        String var3 = br.readLine();
        br.close();
        String mapOwner = null;
        MapVariantResourceId[] maps = gson.fromJson(var3, MapVariantResourceId[].class);
        for (int i = 0; i < maps.length; i++){
            if (mapID.equalsIgnoreCase(maps[i].getResourceId())) {
                mapOwner = maps[i].getOwner();
                break;
            }
        }
        if (mapOwner != null && mapOwner.contains(" "))
            mapOwner = formatString(mapOwner);
        return mapOwner;
    }

    public  CustomMapVariant getCustomMapVariant(String mapVariantID, String player) throws Exception{
        Gson gson = new Gson();
        String mapData = listCustomMapVariants(mapVariantID, player);
        return gson.fromJson(mapData, CustomMapVariant.class);
    }

    public CustomGameVariant getCustomGameVariant(String gameVariantID, String player) throws Exception{
        Gson gson = new Gson();
        String gameData = listCustomGameVariants(gameVariantID, player);
        return gson.fromJson(gameData, CustomGameVariant.class);
    }

    public  CustomMapVariant[] getCachedCustMapVariants() throws Exception{
        return  (CustomMapVariant[]) db.getMetadataFromDB(dataType.CUSTOMMAPVARIANTS, false, NA);
    }

    public MapVariant[] getArenaMapVariants() throws Exception{
        return  (MapVariant[]) db.getMetadataFromDB(dataType.ARENAMAPVARIANTS, false, NA);
    }

    public  String getCustomMapName(String mapID) throws Exception{
        CustomMapVariant[] mapVariants = getCachedCustMapVariants();
        String mapName = null;
        for (int i = 0; i < mapVariants.length; i++){
            if(mapVariants[i].getIdentity().getResourceId() != null && mapVariants[i].getIdentity().getResourceId().equalsIgnoreCase(mapID))
                mapName = mapVariants[i].getName();
        }
            return mapName;
    }


    public  String getMedalName(long medalID, Medal[] medals) throws Exception
    {
        String medalName = null;
        for (int i = 0; i < medals.length; i++) {
            if (medals[i].getId() == medalID)
                medalName = medals[i].getName();
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
        for (int i = 0; i < maps.length; i++) {
            if (maps[i].getId().equalsIgnoreCase(id))
                mapName = maps[i].getName();
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

    public  BaseStats getBaseStats(Enum gameType, String gamertag) throws Exception {
        JSONObject obj = getPlayerStatsJSON(gameType, gamertag);
        String var = obj.toString();
        Gson gson = new Gson();
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

    public  String testMedalStats(Enum gameType) throws Exception
    {
        JSONArray obj = getPlayerStatsJSON(gameType).getJSONArray("MedalAwards");
        String mostEarnedMedal = null;
        double average = 0;
        int highestMedalCount = 0;
        String var = obj.toString();
        double games = totalGames(gameType, PLAYER);
        games = (double)Math.round(games *1000d) / 1000d;
        Gson gson = new Gson();
        Medal[] medals = getMedals();
        MedalAward[] stats = gson.fromJson(var, MedalAward[].class);
        for (int row = 0; row < stats.length; row++){
            stats[row].setName(getMedalName(stats[row].getMedalId(), medals));
        }
        for (int row = 0; row < stats.length; row++) {
            double medalCount = stats[row].getCount()/games;
            medalCount = (double)Math.round(medalCount *1000d) / 1000d;
        }
        for (int i = 0; i < stats.length; i++) {
            for (int k = i + 1; k < stats.length; k++) {
                if (stats[i].getCount() > stats[k].getCount() && stats[i].getCount() > highestMedalCount) {
                    highestMedalCount = stats[i].getCount();
                    mostEarnedMedal = stats[i].getName();
                }
            }
        }
        average = highestMedalCount / games;
        average = (double)Math.round(average *1000d) / 1000d;
        return ("Your most earned medal is the " + mostEarnedMedal + " medal with a total of " + highestMedalCount + " and an average of " + average + " per game");
    }

    public  void testPlayerStats() throws Exception
    {
        Gson gson = new Gson();
        BaseStats stats = gson.fromJson(arenaStats(PLAYER), ArenaStat.class);
        System.out.println(stats.getEnemyKills());
    }

    public  double totalGames(Enum gameType, String gt) throws Exception
    {
        JSONObject obj = getPlayerStatsJSON(gameType, formatString(gt));
        String var = obj.toString();
        Gson gson = new Gson();
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
        System.out.println("Total " + capitalize(gType.toString().toLowerCase()) + " games won: " + stats.getTotalGamesWon() + " Total losses: " + stats.getTotalGamesLost());
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
        Gson gson = new Gson();
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
//        System.out.println("Total kills per weapon for " + PLAYER_UF);
//        for (int i = 0; i < stats.length; i++)
//        {
//            double killCount = stats[i].getTotalKills()/games;
//            killCount = (double)Math.round(killCount * 1000d) / 1000d;
//            System.out.println(stats[i].getName() + ": " + stats[i].getTotalKills() + "  ||  Avg kills per game: " + killCount);
//        }
        System.out.println("Your favorite weapon is the " + favWeapon + " with a kill total of: " + totalKills);
    }


    public  void testBaseStats(Enum gameType) throws Exception
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
        accuracy = (double)Math.round(accuracy * 100d);
        System.out.println("\nHere are some Random Stats: ");
        System.out.println(PLAYER_UF + " has completed " + stats.getTotalGamesCompleted()+ " " + capitalize(gType.toString().toLowerCase()) + " games.");
        System.out.println("You have wasted a total of: " + playTime[0] + " days " + playTime[1] + " hours " + playTime[2] + " minutes and "  + playTime[3] + " seconds playing Halo 5!");
        testWeaponKills(gameType);
        System.out.println("You have fired a total of: " + (int)totalShotsFired + " shots. Of those, you've landed " + (int)totalShotsLanded + " shots.");
        System.out.println("That's an accuracy of " + accuracy + "%");
        System.out.println("You have slaughtered " + totalHeadShots + " Spartans with a headshot.");
//        System.out.println("You've stroked a power weapon in your hands for a total of " + pWeaponTime[0] + " days " + pWeaponTime[1] + " hours " + pWeaponTime[2] + " minutes and "  + pWeaponTime[3] + " seconds!");
        System.out.println("You have grabbed a power weapon " + (int)totalPowerWeapon + " times, you've killed " + (int)totalPowerWeaponKills + " Spartans with those power weapons.");
        System.out.println("That's an average of " + powerWeaponKillAvg + " kills each time you pick up a power weapon!");
        System.out.println("You have Spartan Charged " + stats.getTotalShoulderBashKills() + " dumb-dumbs.");
        System.out.println("You have murdered " + stats.getTotalGrenadeKills() + " Spartans with grenades.");
        System.out.println("You have tied the stupid enemy team " + stats.getTotalGamesTied() + " time(s).");
        System.out.println("You have dealt " + totalDamageDealt + " damage points in your Arena career.");
        System.out.println("You have performed a total of " + stats.getTotalAssassinations() + " assassinations!");
        System.out.println(testMedalStats(gameType));
    }

    public  void testBaseResults() throws Exception
    {
        JSONObject obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result");
        String var = obj.toString();
        Gson gson = new Gson();
        BaseServiceRecordResult stats = gson.fromJson(var, BaseServiceRecordResult.class);
        List<ArenaStat.ArenaPlaylistStats> arenaStats = stats.getArenaStat().getArenaPlaylistStats();
        for (ArenaStat.ArenaPlaylistStats sta : arenaStats) {
            System.out.println(sta.getPlaylistId() + " " + sta.getTotalGamesCompleted());
        }
    }

    public  void testActivePlaylists() throws Exception
    {
        JSONArray obj = new JSONObject(arenaStats(PLAYER)).getJSONArray("Results").getJSONObject(0).getJSONObject("Result").getJSONObject("ArenaStats").getJSONArray("ArenaPlaylistStats");
        String var = obj.toString();
        System.out.println(var);
        Gson gson = new Gson();
        String playlistData = listPlaylists();
        ArenaStat.ArenaPlaylistStats[] playlistStats = gson.fromJson(var, ArenaStat.ArenaPlaylistStats[].class);
        Playlist[] playlists = gson.fromJson(playlistData, Playlist[].class);
        for (int i = 0; i < playlistStats.length; i++) {
            for (int k = 0; k < playlists.length; k++) {
                if (playlists[k].isActive() == true) {
                    System.out.println(playlists[k].getName());
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
                    gamePercentage1 = (double)Math.round(gamePercentage1 *100d) / 100d;
                    gamePercentage2 = (double)Math.round(gamePercentage2 *100d) / 100d;
                    if (gamePercentage1 > gamePercentage2) {
                        betterMedals[i] = getMedalName(p1Medals[i].getMedalId(), medals) + " x " + p1Medals[i].getCount() + " : " + "Earned per game: " + gamePercentage1 + " " + player2 + "  only earns " + gamePercentage2 + " per game";
                    }
                    else if (gamePercentage2 > gamePercentage1) {
                        worseMedals[i] = getMedalName(p2Medals[k].getMedalId(), medals) + " x " + p2Medals[i].getCount() + " : " + "Earned per game: " + gamePercentage2 + " " + player1 + " only earns " + gamePercentage1 + " per game";
                    }}}}
        System.out.println(player1 + " has earned these medals more than " + player2);
        for (int i = 0; i < betterMedals.length; i++){
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
        System.out.println("\nMedals that " + player1 + " has earned that " + player2 + " hasn't: ");
        for (int k = 0; k < p1Medals.length; k++) {
            for (int i = 0; i < p1Has.length; i++) {
                if (p1Medals[k].getMedalId() == p1Has[i]){
                    System.out.println(getMedalName(p1Has[i], medals) + " : " + getMedalDescription(p1Has[i], medals) + " count: " + p1Medals[k].getCount());
                }}}
        System.out.println("\nMedals that " + player2 + " has earned that " + player1 + " hasn't: ");
        for (int k = 0; k < p2Medals.length; k++) {
            for (int i = 0; i < p2Has.length; i++) {
                if (p2Medals[k].getMedalId() == p2Has[i]){
                    System.out.println(getMedalName(p2Has[i], medals) + " : " + getMedalDescription(p2Has[i], medals)+ " count: " + p2Medals[k].getCount());
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
        Gson gson = new Gson();
        String var = null;
        String fileName3 = getFileName(gameType, PLAYER_UF, "mapNames");
        BufferedReader br = new BufferedReader(new FileReader(fileName3));
        var = br.readLine();
        br.close();
        MapVariantResourceId[] maps = gson.fromJson(var, MapVariantResourceId[].class);
        HashMap<String, Integer> stringsCount = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < maps.length; i++) {
                list.add(maps[i].getName());
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
            if(mostRepeated == null || mostRepeated.getValue()<e.getValue())
                mostRepeated = e;
        }
        if(mostRepeated != null)
            favMap = ("Your most played Arena map is: " + mostRepeated.getKey() + " with a total of " + mostRepeated.getValue() + " games wasted");
        return favMap;
    }

    public  String favoriteCustomMapVariant(Enum gameType) throws Exception {
        String var = null;
        String favMap = null;
        String fileName2 = getFileName(gameType, PLAYER_UF, "mapMin");
        BufferedReader br = new BufferedReader(new FileReader(fileName2));
        var = br.readLine();
        br.close();
        Gson gson = new Gson();
        HashMap<String, Integer> stringsCount = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        MapVariantResourceId[] mapv = gson.fromJson(var, MapVariantResourceId[].class);
        for (int i = 0; i < mapv.length; i++) {
            list.add(mapv[i].getResourceId());
        }
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
            favMap = ("Your most played Custom map is: " + getCustomMapVariant(mostRepeated.getKey(), getMapOwner(mostRepeated.getKey())).getName() + " with a total play count of: " + mostRepeated.getValue() + " games!");
        return favMap;
    }

    public void cacheEnemyKills(Enum gameType) throws Exception{
        CarnageReport[] reports = getCarnageReports(gameType);
        String fileName = getFileName(gameType, PLAYER_UF, "carnage");
        String fileName2 = getFileName(gameType, PLAYER_UF, "enemyKills");
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        br.close();
        StringBuilder sb = new StringBuilder();
        String var2 = null;
        for (int i = 0; i < reports.length; i++){
            List<PlayerStat> playerList = reports[i].getPlayerStats();
            for(PlayerStat en : playerList){
                if(en.getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    List<KilledByOpponentDetail> killedBy = en.getKilledByOpponentDetails();
                    for(KilledByOpponentDetail kb: killedBy){
//                        System.out.println(i);
                        sb.append("{\"GamerTag\":\"" + kb.getGamerTag() + "\",\"TotalKills\":" + kb.getTotalKills() + "}");
                        sb.append(",");
                        continue;
                    }}}}
        var2 = sb.toString();
        var2 = var2.substring(0, var2.length() - 1);
        var2 = "[" + var2 + "]";
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName2), "utf-8"));
        writer.write(var2);
        writer.close();
    }

    public String killedByOponent(Enum gameType) throws Exception{
        Gson gson = new Gson();
        String gametype = capitalize(gameType.toString().toLowerCase());
        String worstEnemy = null;
        int totalTimesPlayedWEnemy = 0;
        String mostPlayedAgainst = null;
        int totalTimesPlayed = 0;
        int totalEnemies = 0;
        String fileName = getFileName(gameType, PLAYER_UF, "enemyKills");
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String var2 = br.readLine();
        br.close();
        int totalKills = 0;
        HashMap<String, Integer> stringsCount = new HashMap<>();
        HashMap<String, Integer> mostKilledBy = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        KilledByOpponentDetail[] enemies = gson.fromJson(var2, KilledByOpponentDetail[].class);
        Collections.sort(list);
//        for (String s: list){
//
//        }
        for(int i = 0; i < enemies.length; i++){
            list.add(enemies[i].getGamerTag());
        }
        for (String s: list){
            totalEnemies++;
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
        System.out.println("You have played against " + totalEnemies + " total enemies");
        totalTimesPlayedWEnemy = stringsCount.get(worstEnemy);
        return ("The enemy that has killed you the most is: " + worstEnemy + " with a total of " + totalKills + " kills in " + gametype + " games. You have played each other " + totalTimesPlayedWEnemy + " times \n" + mostPlayedAgainst);
    }

    public void testPlayerMatches(Enum gameType) throws Exception
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
        int m = 0;
        String bestMatchID = null;
        BaseStats stats = getBaseStats(gameType, PLAYER_UF);
        int positiveCount = 0;
        Gson gson = new Gson();
        Match[] matches = getMatches(gameType);
//        System.out.println(matches.length);
        for (int i = 0; i < matches.length; i++){
//            System.out.println(i);
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
            if (! getMapName(matches[i].getMapId(), maps).equalsIgnoreCase("Breakout Arena")) {
                if (currentKD > kdRatio && totalKills > 4) {
                    kdRatio = currentKD;
                    bestMatchID = matchID;
                    bestTotalKills = totalKills;
                    bestTotalDeaths = totalDeaths;
                }}
            if (currentKD >= 1){
                positiveCount++;
            }}
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
        System.out.println("Your best game was played on map: " + mapName);
        System.out.println("Here are the medals you earned in that game: \n");
        for (int i = 0; i < playerStats.length; i++){
            for (MedalAward medal : playerStats[i].getMedalAwards()){
                for (int k = 0; k < medals.length; k++){
                    if (medal.getMedalId() == medals[k].getId()){
                        medal.setName(medals[k].getName());
                    }}
                if (playerStats[i].getPlayer().getGamertag().equalsIgnoreCase(PLAYER_UF)){
                    System.out.println(medal.getName() + " x " + medal.getCount());
                }}}
        testBaseStats(gameType);
        System.out.println(favoriteMapVariant(ARENA));
        System.out.println(favoriteCustomMapVariant(CUSTOM));
        System.out.println(killedByOponent(gameType));
    }
}