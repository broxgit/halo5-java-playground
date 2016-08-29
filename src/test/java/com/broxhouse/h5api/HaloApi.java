package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.*;
import com.broxhouse.h5api.models.metadata.Map;
import com.broxhouse.h5api.models.stats.common.MedalAward;
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
import org.jvnet.hk2.annotations.*;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;
import java.util.logging.Logger;

import static com.broxhouse.h5api.gameType.ARENA;
import static com.broxhouse.h5api.gameType.CUSTOM;
import static com.broxhouse.h5api.gameType.WARZONE;


enum gameType {WARZONE, ARENA, CUSTOM}


public class HaloApi {

    private String PLAYER_UF = "That Ax Guy";
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
    private static final String META_CUSTOM_MAPS = BASE_URL + "ugc/h5/players/%s";
    private static final String POST_GAME_CARNAGE = BASE_URL + "stats/h5/arena/matches/%s";
    private static final String POST_GAME_CARNAGE_CUST = BASE_URL + "stats/h5/custom/matches/%s";

    private static boolean cachingResources = false;
//    private static boolean cachingResources = true;
    private static boolean cachingMatches = false;
//    private static boolean cachingMatches = true;
    private static boolean cachingMapVariants = false;
//    private static boolean cachingMapVariants = true;
//    private static boolean cacheMetaData = false;
    private static boolean cacheMetaData = true;

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
        Medal[] weapons = getMedals();
        System.out.println(weapons.length);
        for (int i = 0; i < weapons.length; i++){
            System.out.println(weapons[i].getName());
        }
    }

    public static void main(String[] args) throws Exception {
        try{
            HaloApi hapi = new HaloApi();
//            long startTime = System.nanoTime();
//            hapi.printData();
            hapi.getMedals();
//            hapi.getWeapons();
//            hapi.testPlayerMatches(CUSTOM);
//            hapi.cacheEnemyKills(CUSTOM);
//            hapi.cachePlayerData(CUSTOM, hapi.PLAYER_UF);
//            System.out.println(hapi.killedByOponent(ARENA));
//            hapi.cacheAllPlayerData(hapi.PLAYER_UF);
//            hapi.cacheGameCarnage(CUSTOM);
//            System.out.println(hapi.favoriteMapVariant(ARENA));
//            System.out.println(hapi.favoriteCustomMapVariant(CUSTOM));
//            System.out.println(hapi.killedByOponent(CUSTOM));
//            hapi.getMatches(CUSTOM);
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

    public static String getFileName(String metaDataItem) throws Exception{
        metaDataItem = metaDataItem.toLowerCase();
        String fileName = metaDataItem + "_metadata.txt";
        return fileName;
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
        gamerTag = gamerTag.toLowerCase();
        Thread metaDataThread = new Thread(){
            @Override
            public void run() {
                try {
                    HaloApi hapi = new HaloApi();
                    cacheMetaData = true;
                    hapi.getMaps();
                    hapi.getWeapons();
                    hapi.getMedals();
                    cacheMetaData = false;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }};
        Thread axelThread = new Thread(){
            @Override
            public void run(){
                try{
                    HaloApi hapi = new HaloApi();
                    hapi.PLAYER_UF = "That Ax Guy";
                    hapi.cachingMatches = true;
                    hapi.getMatches(gameType);
                    hapi.cachingMatches = false;
                    hapi.cachingMapVariants = true;
                    hapi.cacheArenaMaps(gameType);
                    hapi.cachingMapVariants = false;
                    hapi.cacheGameCarnage(gameType);
                    cacheEnemyKills(gameType);
                }catch (Exception e){
                    e.printStackTrace();
                }
          }};
        Thread brockThread = new Thread(){
            @Override
            public void run(){
                try{
                    HaloApi hapi = new HaloApi();
                    hapi.PLAYER_UF = "That Brock Guy";
                    hapi.cachingMatches = true;
                    hapi.getMatches(gameType);
                    hapi.cachingMatches = false;
                    hapi.cachingMapVariants = true;
                    hapi.cacheArenaMaps(gameType);
                    hapi.cachingMapVariants = false;
                    hapi.cacheGameCarnage(gameType);
                    cacheEnemyKills(gameType);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }};
        Thread stuartThread = new Thread(){
            @Override
            public void run(){
                try{
                    HaloApi hapi = new HaloApi();
                    hapi.PLAYER_UF = "That Sturt Guy";
                    hapi.cachingMatches = true;
                    hapi.getMatches(gameType);
                    hapi.cachingMatches = false;
                    hapi.cachingMapVariants = true;
                    hapi.cacheArenaMaps(gameType);
                    hapi.cachingMapVariants = false;
                    hapi.cacheGameCarnage(gameType);
                    cacheEnemyKills(gameType);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }};
        Thread trevorThread = new Thread(){
            @Override
            public void run(){
                try{
                    HaloApi hapi = new HaloApi();
                    hapi.PLAYER_UF = "That Trev Guy";
                    hapi.cachingMatches = true;
                    hapi.getMatches(gameType);
                    hapi.cachingMatches = false;
                    hapi.cachingMapVariants = true;
                    hapi.cacheArenaMaps(gameType);
                    hapi.cachingMapVariants = false;
                    hapi.cacheGameCarnage(gameType);
                    cacheEnemyKills(gameType);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }};
        Thread richardThread = new Thread(){
            @Override
            public void run(){
                try{
                    HaloApi hapi = new HaloApi();
                    hapi.PLAYER_UF = "That Noah Guy";
                    hapi.cachingMatches = true;
                    hapi.getMatches(gameType);
                    hapi.cachingMatches = false;
                    hapi.cachingMapVariants = true;
                    hapi.cacheArenaMaps(gameType);
                    hapi.cachingMapVariants = false;
                    hapi.cacheGameCarnage(gameType);
                    cacheEnemyKills(gameType);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }};
        switch(gamerTag){
                case "that brock guy": metaDataThread.start();brockThread.start();
                    break;
                case "that ax guy": metaDataThread.start();axelThread.start();
                    break;
                case "that sturt guy": metaDataThread.start();stuartThread.start();
                    break;
                case "that trev guy": metaDataThread.start();trevorThread.start();
                    break;
                default:  metaDataThread.start();axelThread.start();brockThread.start();stuartThread.start();
        }
//        axelThread.start();
//        brockThread.start();
//        stuartThread.start();
//        trevorThread.start();
//        richardThread.start();
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

//    public  void cacheAllMetaData() throws Exception{
//        cacheMetaData = true;
//        getMaps();
//        getWeapons();
//        getMedals();
//        cacheMetaData = false;
//    }

    public  void cacheGameCarnage(Enum gameType) throws Exception{
        System.out.println("Getting " + gameType +  " game carnage for: " + PLAYER_UF);
        double totalGames = totalGames(gameType, PLAYER_UF);
        Match[] matches = getMatches(gameType);
        String var3 = "";
        JSONObject obj = null;
        String fileName = getFileName(gameType, PLAYER_UF, "carnage");
        StringBuilder strBuilder = new StringBuilder("");
        for (int i = 0; i < matches.length; i++) {
            if (gameType == ARENA) {
                obj = new JSONObject(postGameCarnage(matches[i].getId().getMatchId()));
            }
            else if(gameType == CUSTOM) {
                obj = new JSONObject(postCustomGameCarnage(matches[i].getId().getMatchId()));
            }
            else if(gameType == WARZONE){}

            strBuilder.append(obj.toString());
            strBuilder.append(",");
            continue;
        }
        var3 = strBuilder.substring(0, strBuilder.length() -1);
        var3 = "[" + var3 + "]";
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"));
        writer.write(var3);
        writer.close();
        System.out.println("Finished caching " + capitalize(gameType.toString().toLowerCase()) + " carnage reports");
    }

    public void cacheCustomMaps() throws Exception {
        int iterations = 0;
        Set<String> mapIDs = getResources(CUSTOM);
        String fileName = getFileName(CUSTOM, PLAYER_UF, "mapMax");
        String fileName2 = getFileName(CUSTOM, PLAYER_UF, "mapMin");
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(fileName2));
        String var3 = br.readLine();
        br.close();
        JSONArray jsonArray = new JSONArray(var3);
        String var2 = "";
        if (cachingMapVariants) {
            System.out.println("Caching favorite Custom map variants for: " + PLAYER_UF);
            for (String s : mapIDs) {
                if ((jsonArray.getJSONObject(iterations).getString("Owner")).isEmpty() || (jsonArray.getJSONObject(iterations).getString("Owner")) == "") {
                    iterations++;
                    continue;
                }
                CustomMapVariant mv = getCustomMapVariant(jsonArray.getJSONObject(iterations).getString("ResourceId"), formatString(jsonArray.getJSONObject(iterations).getString("Owner")));
                sb.append("{\"BaseMap\":{\"ResourceType\":" + mv.getBaseMap().getResourceType() + ",\"ResourceId\":\"" + mv.getBaseMap().getResourceId() + "\",\"OwnerType\":" + mv.getBaseMap().getOwnerType() + ",\"Owner\":" + mv.getBaseMap().getOwner() + "},\"Name\":\"" + mv.getName() + "\",\"Description\":\"" + mv.getDescription() + "\",\"AccessControl\":" + mv.getAccessControl() + ",\"Links\":{},\"CreationTimeUtc\":{\"ISO8601Date\":\"" + mv.getCreationTimeUtc() + "\"},\"LastModifiedTimeUtc\":{\"ISO8601Date\":\"" + mv.getLastModifiedTimeUtc() + "\"},\"Banned\":" + mv.getBanned() + ",\"Identity\":{\"ResourceType\":" + mv.getIdentity().getResourceType() + ",\"ResourceId\":\"" + mv.getIdentity().getResourceId() + "\",\"OwnerType\":" + mv.getIdentity().getOwnerType() + ",\"Owner\":\"" + mv.getIdentity().getOwner() + "\"},\"Stats\":{\"BookmarkCount\":" + mv.getStats().getBookmarkCount() + ",\"HasCallerBookmarked\":" + mv.getStats().getHasCallerBookmarked() + "}}");
                sb.append(",");
                iterations++;
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            var2 = sb.substring(0, sb.length() - 1);
            var2 = "[" + var2 + "]";
            writer.write(var2);
            writer.close();
            System.out.println("Finished Caching Custom Map Data");
        }
    }

    public void cacheArenaMaps(Enum gameType) throws Exception{
        if (gameType == CUSTOM){
            cacheCustomMaps();
            return;
        }
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Set<String> mapIDs = getResources(ARENA);
        String fileName = getFileName(ARENA, PLAYER_UF, "mapMax");
        String fileName2 = getFileName(ARENA, PLAYER_UF, "mapMin");
        String fileName3 = getFileName(ARENA, PLAYER_UF, "mapNames");
        BufferedReader br = new BufferedReader(new FileReader(fileName2));
        String var3 = br.readLine();
        br.close();
        MapVariantResourceId[] maps = gson.fromJson(var3, MapVariantResourceId[].class);
        String var2 = "";
        if(cachingMapVariants){
            System.out.println("Caching favorite map variants for: " + PLAYER_UF);
            for (String s: mapIDs){
                if (s != null){
                    MapVariant mv = getMapVariant(s.toLowerCase());
                    for (int i = 0; i < maps.length; i++){
//                        System.out.println(mv.getId() + " " + maps[i].getResourceId());
                        if (mv.getContentId().equalsIgnoreCase(maps[i].getResourceId())){
//                            System.out.println(mv.getName());
                            maps[i].setName(mv.getName());
                            sb2.append("{\"OwnerType\":" + maps[i].getOwnerType() + ",\"ResourceId\":\"" + maps[i].getResourceId() + "\",\"ResourceType\":" + maps[i].getResourceType() + ",\"Name\":\"" + mv.getName() +  "\"}");
                            sb2.append(",");
                        }
                    }
                    sb.append("{\"name\":\"" +mv.getName()+ "\",\"description\":\"" + mv.getDescription() + "\",\"mapImageUrl\":\"" + mv.getMapImageUrl() +  "\",\"mapId\":\"" + mv.getMapId() + "\",\"id\":\"" + mv.getId() +  "\",\"contentId\":\"" + mv.getContentId() + "\"}");
                    sb.append(",");
                }
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
            var2 = sb.substring(0, sb.length() - 1);
            var2 = "[" + var2 + "]";
            writer.write(var2);
            writer.close();
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName3), "utf-8"));
            var2 = sb2.substring(0, sb2.length() - 1);
            var2 = "[" + var2 + "]";
            writer.write(var2);
            writer.close();
            System.out.println("Finished Caching Arena Map Data");
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

    public  String listMaps() throws Exception
    {
        return api(META_MAPS);
    }

    public  String listMapVariants(String mapVariantID) throws Exception
    {
        return api(String.format(META_MAP_VARIANTS, mapVariantID));
    }

    public  String listCustomMapVariants(String mapVariantID, String player) throws Exception{
        String url =  player + "/mapvariants/" + mapVariantID;
        return api(String.format(META_CUSTOM_MAPS, url));
    }


    public  void testJSONWeapons() throws Exception
    {
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

    public  Medal[] getMedals() throws Exception
    {
        Medal[] medals = null;
        Database db = new Database();
        if (cacheMetaData) {
            db.addItemsToDatabase(dataType.MEDALS);
        }else{
            medals = db.getMedalsDB();
        }
        return medals;
    }

    public  Map[] getMaps() throws Exception
    {
        Gson gson = new Gson();
        String fileName = getFileName("maps");
        String mapData = null;
        if (cacheMetaData) {
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

    public  Weapon[] getWeapons() throws Exception
    {
        Weapon[] weapons = null;
        Database db = new Database();
        if (cacheMetaData) {
            db.addItemsToDatabase(dataType.WEAPONS);
//            Writer writer1 = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(fileName), "utf-8"));
//            writer1.write(weaponData);
//            writer1.close();
        }else{
            weapons = db.getWeaponsDB();
        }
        return weapons;
    }

    public  Match[] getMatches(Enum gameType) throws Exception {
        JSONObject obj = null;
        JSONArray obj2 = null;
        JSONObject obj3 = null;
        String var2 = null;
        Integer start = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String fileName = getFileName(gameType, PLAYER_UF, "matches");
        String fileName2 = getFileName(gameType, PLAYER_UF, "mapMin");
        if (cachingMatches){
            System.out.println("Caching " + capitalize(gameType.toString().toLowerCase()) +  " matches for " + PLAYER_UF);
            double totalGames = totalGames(gameType, PLAYER_UF);
           if(totalGames == 0)
               return null;
            String var3 = "";
            double iterations = totalGames / 25;
            for (int i = 1; i < iterations; i++) {
                obj = new JSONObject(playerMatches(PLAYER, gameType.toString().toLowerCase(), start, 25));
                obj2 = obj.getJSONArray("Results");
                System.out.println(obj2.toString());
                for (int k = 0; k < obj2.length(); k++) {
                    obj3 = obj2.getJSONObject(k).getJSONObject("MapVariant");
                    if (k < obj2.length()) {
                        sb.append(obj3.toString());
                        sb.append(",");
                    } else {
                        sb.append(obj3.toString());
                    }
                }
                if (i < iterations) {
                    sb2.append(obj2.toString().substring(1, obj2.toString().length() -1));
                    sb2.append(",");
                } else {
                    sb2.append(obj2.toString());
                }
                start = start + obj.getInt("Count");
            }
        var3 = sb.substring(0, sb.length() - 1);
        var3 = "[" + var3 + "]";
        Writer writer1 = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName2), "utf-8"));
        writer1.write(var3);
        writer1.close();
        var2 = sb2.substring(0, sb2.length() - 1);
        var2 = "[" + var2 + "]";

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"));
        writer.write(var2);
        writer.close();
            System.out.println("Finished Caching " + gameType +  " Match Data");
    }else{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        var2 = br.readLine();
        br.close();
    }
        Gson gson = new Gson();
        Match[] matches = gson.fromJson(var2, Match[].class);
        return matches;

//        JSONObject obj = null;
//        String var = null;
//        String var2 = "";
//        Integer start = 0;
//        String fileName = getFileName(gameType, PLAYER_UF, "matches");
//        String fileName2 = getFileName(gameType, PLAYER_UF, "mapMin");
//        if (cachingMatches){
//            double totalGames = totalGames(gameType, PLAYER_UF);
//            if(totalGames == 0)
//                return null;
//            TimeUnit.SECONDS.sleep(8);
//            int m = 0;
//            String var3 = "";
//            double iterations = totalGames / 25;
//            for (int i = 1; i < iterations; i++) {
//                obj = new JSONObject(playerMatches(PLAYER, gameType.toString().toLowerCase(), start, 25));
//                JSONArray obj2 = obj.getJSONArray("Results");
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
//                var = obj2.toString();
//                if (i < iterations) {
//                    var = var.substring(1, var.length() - 1);
//                    var2 = var2.concat(var);
//                    var2 = var2 + ",";
//                } else {
//                    var2 = var2.concat(var);
//                }
//                start = start + obj.getInt("Count");
//                if (i % 10 == 0) {
//                    TimeUnit.SECONDS.sleep(10);
//                }
//            }
//            var3 = var3.substring(0, var3.length() - 1);
//            var3 = "[" + var3 + "]";
////            System.out.println(var3);
//            Writer writer1 = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(fileName2), "utf-8"));
//            writer1.write(var3);
//            writer1.close();
//            var2 = var2.substring(0, var2.length() - 1);
//            var2 = "[" + var2 + "]";
//
//            Writer writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(fileName), "utf-8"));
//            writer.write(var2);
//            writer.close();
//        }else{
//            BufferedReader br = new BufferedReader(new FileReader(fileName));
//            var2 = br.readLine();
//            br.close();
//        }
//        Gson gson = new Gson();
//        Match[] matches = gson.fromJson(var2, Match[].class);
//        return matches;
    }

    public  CarnageReport[] getCarnageReports(Enum gameType) throws Exception{
        Gson gson = new Gson();
        String fileName = getFileName(gameType, PLAYER_UF, "carnage");
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String carnageData = br.readLine();
        br.close();
        CarnageReport[] carnageReports = gson.fromJson(carnageData, CarnageReport[].class);
        return carnageReports;
    }

    public  Set<String> getResources(Enum gameType) throws Exception
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
//                if (i % 200 == 0) {
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
        JSONArray obj3 = new JSONArray(var3);
        Set<String> mapIDs = new HashSet<String>();
        for (int i = 0; i < obj3.length(); i++){
            String mapID = obj3.getJSONObject(i).getString("ResourceId");
            if(mapIDs.contains(mapID))
                continue;
            else
                mapIDs.add(mapID);
        }
//        System.out.println("mapSimpleData size " + mapIDs.size());
        return mapIDs;
    }

    public  MapVariant getMapVariant(String mapVariantID) throws Exception
    {
        Gson gson = new Gson();
        String mapData = listMapVariants(mapVariantID);
        MapVariant mapVariant = gson.fromJson(mapData, MapVariant.class);
        return mapVariant;
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
        return mapOwner;
    }

    public  CustomMapVariant getCustomMapVariant(String mapVariantID, String player) throws Exception{
        Gson gson = new Gson();
        String mapData = listCustomMapVariants(mapVariantID, player);
        CustomMapVariant mapVariant = gson.fromJson(mapData, CustomMapVariant.class);
        return mapVariant;
    }

    public  CustomMapVariant[] getCachedCustMapVariants() throws Exception{
        Gson gson = new Gson();
        String fileName = getFileName(CUSTOM, PLAYER_UF, "mapMax");
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String var3 = br.readLine();
        br.close();
        CustomMapVariant[] mapVariant = gson.fromJson(var3, CustomMapVariant[].class);
        return mapVariant;
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