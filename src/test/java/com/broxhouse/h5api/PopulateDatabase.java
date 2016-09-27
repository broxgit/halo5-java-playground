package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.CustomMapVariant;
import com.broxhouse.h5api.models.metadata.MapVariant;
import com.broxhouse.h5api.models.stats.common.Player;
import com.broxhouse.h5api.models.stats.matches.Match;
import com.broxhouse.h5api.models.stats.reports.CarnageReport;
import com.broxhouse.h5api.models.stats.reports.PlayerStat;
import com.broxhouse.h5api.models.stats.reports.Resource;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Logger;

import static com.broxhouse.h5api.gameType.*;

/**
 * Created by Brock Berrett on 9/8/2016.
 */
public class PopulateDatabase {

    static Logger log = Logger.getLogger("Logging");

    public PopulateDatabase() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s %n");
    }


    HaloApi hApi = new HaloApi();
    Database db = new Database();

    public List<String> populatePlayers() throws Exception{
        CarnageReport[] matches = hApi.getPlayerCarnageReports(ARENA);
        List<Player> playersList = new ArrayList<>();
        List<List<PlayerStat>> playerStatList = new ArrayList<>();
        for (int i = 0; i < matches.length; i++){
            for (int k = 0; k < matches[i].getPlayerStats().size(); k++){
                playerStatList.add(matches[i].getPlayerStats());
//                playersList.add(matches[i].getPlayerStats().get(k).getPlayer());
//                System.out.println(matches[i].getPlayerStats().size() + " " + matches[i].getPlayerStats() + " " + k);
            }
        }
        System.out.println(playerStatList.size());
        List<String> players = new ArrayList<>();
//        Player[] players = new Player[playersList.size()];
        for (int i = 0; i < playerStatList.size(); i++){
//            System.out.println(i);
            for (int k = 0; k < playerStatList.get(i).size(); k++){
                players.add(playerStatList.get(i).get(k).getPlayer().getGamertag());
//                System.out.println(playerStatList.get(i).get(k).getPlayer().getGamertag());
            }
        }
        List<String> uniquePlayers = new ArrayList<>(new HashSet<>(players));
        return uniquePlayers;
    }

    public void cacheNewPlayers() throws Exception{
        List<String> newPlayers = populatePlayers();
        List<String> allPlayers = db.getPlayersFromDB(false);
        System.out.println("New players: " + newPlayers.size() + " Old Players: " + allPlayers.size());
        for (String s: allPlayers){
            if (newPlayers.contains(s))
                newPlayers.remove(s);
        }
        db.clearTable("players");
        db.addPlayersToDB(newPlayers, false);
        db.addPlayersToDB(newPlayers, true);
        System.out.println("New size of the New Players list: " + newPlayers.size());
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
        Set<String> oldMapIDs = hApi.getUniqueResources(oldResources);
        System.out.println("Caching Custom Maps");
        Match[] matches = hApi.getPlayerMatches(ARENA);
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
        Set<String> mapIDs = hApi.getUniqueResources(resources);
        MapVariant[] maps = null;
        List<MapVariant> newMaps = new ArrayList<>();
        if (firstRun) {
            maps = new MapVariant[resources.length];
            for (int i = 0; i < resources.length; i++) {
                System.out.println(resources[i].getResourceId());
                maps[i] = hApi.getMapVariant(resources[i].getResourceId());
            }
        }
        if (!firstRun) {
            for (String s : mapIDs) {
                if (oldMapIDs.contains(s)) {
                    System.out.println("Contains " + s);
                    k++;
                    continue;
                } else {
                    System.out.println("Does not contain " + s);
                    MapVariant map = hApi.getMapVariant(s);
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
        System.out.println("Adding " + maps.length +  " map variants to the database");
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
        Set<String> oldMapIDs = hApi.getUniqueResources(oldResources);
        System.out.println("Caching Custom Maps");
        Match[] matches = hApi.getPlayerMatches(CUSTOM);
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
        Set<String> mapIDs = hApi.getUniqueResources(resources);
        CustomMapVariant[] maps = null;
        List<CustomMapVariant> newMaps = new ArrayList<>();
        if (firstRun) {
            maps = new CustomMapVariant[resources.length];
            for (int i = 0; i < resources.length; i++) {
                System.out.println(resources[i].getResourceId());
                maps[i] = hApi.getCustomMapVariant(resources[i].getResourceId(), hApi.formatString(resources[i].getOwner()));
            }
        }
        if (!firstRun) {
            for (String s : mapIDs) {
                if (db.checkDBForValue("resourceid", s, dataType.CUSTOMMAPVARIANTS)) {
                    System.out.println("Contains " + s);
                    k++;
                    continue;
                } else {
                    System.out.println("Does not contain " + s);
                    CustomMapVariant map = hApi.getCustomMapVariant(s, hApi.getMapOwner(s));
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

    public void cacheMatches(Enum gameType, boolean cachePlayerData, String... players) throws Exception {
        List<String> oldMatchIDs = new ArrayList<>();
        CustomMapVariant[] customMaps = null;
        if ((Match[]) db.getMetadataFromDB(dataType.MATCHES, true, gameType) != null) {
            Match[] oldMatches = hApi.getPlayerMatches(gameType);
            for (int i = 0; i < oldMatches.length; i++) {
                oldMatchIDs.add(oldMatches[i].getId().getMatchId());
            }
        }
        JSONObject obj = null;
        JSONArray obj2 = null;
        String gamertag = null;
        if (players.length > 0){
            gamertag = players[0];
        }
        else{
            gamertag = hApi.PLAYER_UF;
        }
        Integer start = 0;
        Match[] matches = null;
        Match[] matches1 = null;
        List<Match> matchList = new ArrayList<>();
        Database db2 = new Database();
        Match match = null;
        Gson gson = new Gson();
        double totalGames = hApi.totalGames(gameType, gamertag);
        if(totalGames == 0) {
            System.out.println("This player hasn't played this game type");
            return;
        }
        double iterations = totalGames / 25;
        double newGames = (totalGames - oldMatchIDs.size());
        outer:
        for (int i = 0; i < iterations; i++) {
            obj = new JSONObject(hApi.playerMatches(gamertag, gameType.toString().toLowerCase(), start, 25));
            obj2 = obj.getJSONArray("Results");
            matches1 = gson.fromJson(obj2.toString(), Match[].class);
            customMaps = new CustomMapVariant[matches1.length];
            for (int k = 0; k < matches1.length; k++){
                match = matches1[k];
                matchList.add(match);
                if (matches1[k].getMapVariant().getOwner().equalsIgnoreCase("") || matches1[k].getMapVariant().getOwner() == null)
                    continue;
                customMaps[k] = hApi.getCustomMapVariant(matches1[k].getMapVariant().getResourceId(), hApi.formatString(matches1[k].getMapVariant().getOwner()));
                if (oldMatchIDs.contains(matches1[k].getId().getMatchId()) && cachePlayerData){}
//                    break outer;
            }
            start = start + obj.getInt("Count");
        }
        matches = new Match[matchList.size()];
        for (int i = 0; i < matchList.size(); i++){
            matches[i] = matchList.get(i);
        }
        System.out.println("Adding " + newGames +  " matches to database");
        if (cachePlayerData) {
            db.writeMatchesToDB(matches, dataType.MATCHES, true, gameType);
            db2.writeCustomMapVariantsToDB(customMaps);
//            db2.writeMatchesToDB(matches, dataType.MATCHES, false, gameType);
        }
        if (! cachePlayerData)
            db.writeMatchesToDB(matches, dataType.MATCHES, false, gameType);
        System.out.println("Finished caching Matches for " + gamertag);
    }

    public void cachePlayerData(Enum gameType, String gamerTag) throws Exception{
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
//                    hapi.getPlayerMatches(gameType);
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
//                    hapi.getPlayerMatches(gameType);
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
//                    hapi.getPlayerMatches(gameType);
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
//                    hapi.getPlayerMatches(gameType);
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
//                    hapi.getPlayerMatches(gameType);
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

    public void cacheAllPlayerData(String gamerTag) throws Exception{
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

    public void cacheGameCarnage(Enum gameType, boolean cachePlayerCarnage, Match... matches1) throws Exception {
//        System.out.println("Getting " + gameType + " game carnage for: " + hApi.PLAYER_UF);
        CarnageReport[] oldCarnageReports = null;
        if (cachePlayerCarnage){
            oldCarnageReports = (CarnageReport[]) db.getMetadataFromDB(dataType.CARNAGE, true, gameType);
        }
        else {
            oldCarnageReports = null;
        }
//        System.out.println("boobs");
        List<String> oldMatchIDs = new ArrayList<>();
        List<String> newMatchIDs = new ArrayList<>();
        List<String> allMatchIDs = new ArrayList<>();
        Gson gson = new Gson();
        Match[] matches = null;
        if (matches1.length > 0){
            matches = matches1;
        }
        else {
            if (cachePlayerCarnage) {
                matches = hApi.getPlayerMatches(gameType);
            } else {
                matches = hApi.getAllMatches(gameType);
            }
        }
//        System.out.println("Caching " + matches.length + " matches");
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
        int diffCount = allMatchIDs.size() - newMatchIDs.size();
//        System.out.println("Getting carnage reports for " + newMatchIDs.size() + " games that aren't in the database");
        JSONObject obj = null;
        for (int i = 0; i < newMatchIDs.size(); i++) {
            if (gameType == ARENA) {
                obj = new JSONObject(hApi.postGameCarnage(newMatchIDs.get(i)));
            } else if (gameType == CUSTOM) {
                obj = new JSONObject(hApi.postCustomGameCarnage(newMatchIDs.get(i)));
            } else if (gameType == WARZONE) {}

            if (i % 100 == 0){
//                System.out.println(i);
            }
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
        if (cachePlayerCarnage == true) {
            cachePlayerCarnage(reports, gameType);
        }
    }

    public void cachePlayerCarnage(CarnageReport[] reports, Enum gameType) throws Exception{
        db.writeCarnageReportsToDB(reports, dataType.CARNAGE, true, gameType);
    }

    public void cacheCarnageThreadTest(Enum gameType) throws Exception{
        for (int k = 0; k < 60; k++) {
            Match[] matches = (Match[]) db.getSomeMatchesFromDB(k, 10000, false, gameType);
            int matchCount = matches.length;
            int div = 400;
            System.out.println("Starting " + matchCount / div + " threads");
//        System.out.println(matchCount);
            Thread[] threads = new Thread[matchCount / div];
            for (int i = 0; i < matchCount / div; i++) {
                int finalI = i;
                int arrayStart = finalI * div;
                int arrayEnd = (finalI + 1) * div;
                Match[] finalMatches = matches;
                int finalK = k;
                threads[i] = new Thread() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("Creating thread: " + finalI + " in loop: " + finalK);
                            cacheGameCarnage(gameType, false, Arrays.copyOfRange(finalMatches, arrayStart, arrayEnd));
                            System.out.println(db.getNumberOfRows(dataType.CARNAGE, false, ARENA));
//                            for (int j = arrayStart; j < arrayEnd; j++){
//                                cacheGameCarnage(gameType, false, finalMatches[j]);
//                            }
                            if (finalI == (matchCount / div) - 1)
                                System.out.println("Thread " + finalI + " in loop " + finalK + " completed");
                        }catch(OutOfMemoryError e){
                            System.out.println(Runtime.getRuntime().maxMemory());
                            System.out.println(e.getMessage() + ". Total memory: " + Runtime.getRuntime().totalMemory() + " Free memory: " + Runtime.getRuntime().freeMemory() + " " + (Runtime.getRuntime().totalMemory() - (Runtime.getRuntime().freeMemory())));
                        }
                        catch (Exception e){}
                    }
                };
                threads[i].start();
            }
            for (int i = 0; i < matchCount / div; i++) {
                try {
                    threads[i].join();
                } catch (Exception e) {
                }
            }
        }
    }

    public void cacheMatchesThreadTest(Enum gameType) throws Exception{
        List<String> playersList = db.getPlayersFromDB(true);
        String[] players = playersList.toArray(new String[playersList.size()]);
        int playerCount = players.length;
        int div = 0;
        if (playerCount % 100 > 1) {
            div = 50;
        }
        else if (playerCount % 10 > 1){
            div = 5;
        }
        int threadCount = playerCount/div;
        System.out.println("Starting " + threadCount + " threads with " + playerCount + " players");
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++){
            int finalI = i;
            int arrayStart = finalI *div;
            int arrayEnd = (finalI + 1) *div;
            threads[i] = new Thread(){
                @Override
                public void run(){
                    try{
                        String[] tempPlayers = Arrays.copyOfRange(players, arrayStart, arrayEnd);
                        Arrays.sort(tempPlayers);
                        System.out.println("Thread " + finalI + " is caching " + tempPlayers.length + " players.");
                        for (int k = 0; k < tempPlayers.length; k++){
                            cacheMatches(gameType, false, tempPlayers[k]);
                            if (k % 100 == 0){
                                System.out.println("Thread " + finalI + " is on it's " + k + "th player.");
                            }
                        }
                        System.out.println("Thread " + finalI + " completed");
                    }catch (Exception e){}
                }
            };
            threads[i].start();
        }
        for (int i = 0; i < playerCount/div; i++){
            try{
                threads[i].join();
            }catch (Exception e){}
        }
    }


    public void cacheAShitTonOfMatches(Enum gameType) throws Exception {
        List<String> players = db.getPlayersFromDB(false);
        for (String s : players){
            hApi.PLAYER_UF = s;
            cacheMatches(gameType, false, s);
        }
    }

    public void cacheAShitTonOfCarnage(Enum gameType) throws Exception {
        cacheGameCarnage(gameType, false);
    }

    public void cacheAShitTonOfPlayerCaranage(Enum gameType) throws Exception {
        for (int k = 0; k < hApi.totalGames(gameType, hApi.PLAYER)/100; k++) {
            Match[] matches = (Match[]) db.getSomeMatchesFromDB(k*100, 100, true, gameType);
            int matchCount = matches.length;
            int div = 5;
//            System.out.println("Starting " + matchCount / div + " threads");
//        System.out.println(matchCount);
            Thread[] threads = new Thread[matchCount / div];
            for (int i = 0; i < matchCount / div; i++) {
                int finalI = i;
                int arrayStart = finalI * div;
                int arrayEnd = (finalI + 1) * div;
                Match[] finalMatches = matches;
                int finalK = k;
                threads[i] = new Thread() {
                    @Override
                    public void run() {
                        try {
//                            System.out.println("Creating thread: " + finalI + " in loop: " + finalK);
                            cacheGameCarnage(gameType, true, Arrays.copyOfRange(finalMatches, arrayStart, arrayEnd));
//                            System.out.println(db.getNumberOfRows(dataType.CARNAGE, true, gameType));
//                            for (int j = arrayStart; j < arrayEnd; j++){
//                                cacheGameCarnage(gameType, false, finalMatches[j]);
//                            }
                            if (finalI == (matchCount / div) - 1)
                                System.out.println("Thread " + finalI + " in loop " + finalK + " completed");
                        }catch(OutOfMemoryError e){
                            System.out.println(Runtime.getRuntime().maxMemory());
                            System.out.println(e.getMessage() + ". Total memory: " + Runtime.getRuntime().totalMemory() + " Free memory: " + Runtime.getRuntime().freeMemory() + " " + (Runtime.getRuntime().totalMemory() - (Runtime.getRuntime().freeMemory())));
                        }
                        catch (Exception e){}
                    }
                };
                threads[i].start();
            }
            for (int i = 0; i < matchCount / div; i++) {
                try {
                    threads[i].join();
                } catch (Exception e) {
                }
            }
        }
    }
}
