package com.broxhouse.h5api;

import com.broxhouse.h5api.models.metadata.CustomMapVariant;
import com.broxhouse.h5api.models.metadata.GameVariant;
import com.broxhouse.h5api.models.metadata.MapVariant;
import com.broxhouse.h5api.models.stats.common.Player;
import com.broxhouse.h5api.models.stats.matches.Match;
import com.broxhouse.h5api.models.stats.matchevents.MatchEvents;
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
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
    }


    HaloApi hApi = new HaloApi();
    Database db = new Database();
    Gson gson = new Gson();

    public List<String> populatePlayers() throws Exception{
        CarnageReport[] matches = hApi.getAllCarnageReports(ARENA);
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

    public void cacheArenaGameTypes() throws Exception {
        Match[] matches = hApi.getPlayerMatches(ARENA);
        GameVariant[] gameVariants = new GameVariant[matches.length];
        for (int i = 0; i < matches.length; i++){
            gameVariants[i] = gson.fromJson(hApi.listGameVariant(matches[i].getGameVariant().getResourceId()), GameVariant.class);
        }
        db.writeGameVariantsToDB(gameVariants, dataType.ARENAGAMEVARIANTS, false, NA);
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

    public void cacheCustomMapsRandomly() throws Exception {
        List<String> players = db.getPlayersFromDB(false);
        List<CustomMapVariant> mapList = new ArrayList<>();
        for (String s : players){
            CustomMapVariant[] maps = hApi.getCustomMapVariantsByPlayer(hApi.formatString(s));
            if (maps == null)
                continue;
            for (int i = 0; i < maps.length; i++){
                mapList.add(maps[i]);
            }
        }
        CustomMapVariant[] allMaps = new CustomMapVariant[mapList.size()];
        for (int i = 0; i < mapList.size(); i++){
            allMaps[i] = mapList.get(i);
        }

        db.writeCustomMapVariantsToDB(allMaps);

    }

    public void cacheCustomMapsRandomlyAsText() throws Exception {

        db.writeCustomMapVariantsToDBText((CustomMapVariant[]) db.getMetadataFromDB(dataType.CUSTOMMAPVARIANTS, false, NA));

    }

    public void cacheMatchesAsText() throws Exception {
        for (int i = 10; i < 100; i++){
            Match[] matches = (Match[]) db.getSomeMatchesFromDB(i*10, 100000, false, ARENA);
//            db.writeMatchesToDBText(matches, dataType.MATCHES, false, ARENA);
            int threadCount = 100;
            Thread[] threads = new Thread[threadCount];
            for (int k = 0; k < threadCount; k++){
                int finalI = k;
                int finalI1 = i;
                int div = 100000/threadCount;
                int arrayStart = finalI *div;
                int arrayEnd = (finalI + 1) *div;

                threads[i] = new Thread(){
                    @Override
                    public void run(){
                        try{
                            db.writeMatchesToDBText(Arrays.copyOfRange(matches, arrayStart, arrayEnd), dataType.MATCHES, false, ARENA);
                            log.info("Thread " + finalI + " is finished with loop " + finalI1);
                        }catch (Exception e){}
                    }
                };
                threads[i].start();
            }
            for (int k = 0; k < threadCount; k++){
                try{
                    threads[i].join();
                }catch (Exception e){}
            }
        }

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
                maps[i] = hApi.getCustomMapVariant(resources[i].getResourceId(), resources[i].getOwner());
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

    public void cacheAnyMapVariant(Enum gameType) throws Exception {
        Match[] matches = (Match[])db.getSomeMatchesFromDB(0, 50000, false, gameType);
        MapVariant[] maps = (MapVariant[]) db.getMetadataFromDB(dataType.ARENAMAPVARIANTS, false, NA);
        if (gameType.toString().equalsIgnoreCase("ARENA")){
            for (int i = 0; i < matches.length; i++) {
                if (hApi.getMapVariantName(matches[i].getMapVariant().getResourceId(), maps) == null) {
                    MapVariant[] mapsTest = new MapVariant[1];
                    mapsTest[0] = hApi.getMapVariant(matches[i].getMapVariant().getResourceId());
                    db.writeMapVariantsToDB(mapsTest);
//                log.info(getMapVariantName(matches[i].getMapVariant().getResourceId()));
                }
            }
        }
        else if (gameType.toString().equalsIgnoreCase("CUSTOM")) {
            for (int i = 0; i < matches.length; i++) {
                if (hApi.getCustomMapName(matches[i].getMapVariant().getResourceId()) == null) {
                    CustomMapVariant[] mapsTest = new CustomMapVariant[1];
                    mapsTest[0] = hApi.getCustomMapVariant(matches[i].getMapVariant().getResourceId(), matches[i].getMapVariant().getOwner());
                    db.writeCustomMapVariantsToDB(mapsTest);
                }
            }
        }

    }

    public void cacheMatches(Enum gameType, boolean cachePlayerData, String... players) throws Exception {
        List<String> oldMatchIDs = new ArrayList<>();
        GameVariant[] gameVariants = null;
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
            gameVariants = new GameVariant[matches1.length];
            for (int k = 0; k < matches1.length; k++){
                match = matches1[k];
                matchList.add(match);
//                gameVariants[k] = hApi.getGameVariant(matches1[k].getGameVariant().getResourceId());
                if (oldMatchIDs.contains(matches1[k].getId().getMatchId()) && cachePlayerData)
                    break outer;
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
//            db2.writeGameVariantsToDB(gameVariants, dataType.ARENAGAMEVARIANTS, false, NA);
//            db2.writeMatchesToDB(matches, dataType.MATCHES, false, gameType);
        }
        if (! cachePlayerData) {
            db.writeMatchesToDB(matches, dataType.MATCHES, false, gameType);
//            db2.writeMapVariantsToDB(customMaps);
        }
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

    public void cacheGameCarnage(Enum gameType, boolean cachePlayerCarnage, CarnageReport[] oldCarnageReports, Match... matches1) throws Exception {
//        System.out.println("Getting " + gameType + " game carnage for: " + hApi.PLAYER_UF);
        if (oldCarnageReports == null){
            oldCarnageReports = hApi.getPlayerCarnageReports(gameType);
        }
        if (! cachePlayerCarnage){
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
            System.out.println("There aren't any carnage reports for this player in the database, starting from scratch...");
            System.out.println("This might take a while");
            for (int i = 0; i < matches.length; i++) {
                newMatchIDs.add(matches[i].getId().getMatchId());
            }
        }

        matches = null;
        matches1 = null;
        oldCarnageReports = null;
        allMatchIDs.clear();
        oldMatchIDs.clear();

        JSONObject obj = null;
        for (int i = 0; i < newMatchIDs.size(); i++) {
            if (gameType == ARENA) {
                obj = new JSONObject(hApi.postGameCarnage(newMatchIDs.get(i)));
            } else if (gameType == CUSTOM) {
                obj = new JSONObject(hApi.postCustomGameCarnage(newMatchIDs.get(i)));
            } else if (gameType == WARZONE) {}
            carnageReport = gson.fromJson(obj.toString(), CarnageReport.class);
            carnageReport.setMatchId(newMatchIDs.get(i));
            carnageList.add(carnageReport);
            if (i % 25 == 0 && i != 0) {
                System.out.println("Finished caching the first " + i + " Carnage Reports");
                double percentComplete = (((double)i / (double)newMatchIDs.size()) * 100000d) / 1000d;
                System.out.println("Database caching is " + percentComplete + " percent complete!");
                CarnageReport[] reports = new CarnageReport[carnageList.size()];
                for (int k = 0; k < carnageList.size(); k++) {
                    reports[k] = carnageList.get(k);
                }
                if (cachePlayerCarnage == true) {
                    db.writeCarnageReportsToDB(reports, dataType.CARNAGE, true, gameType);
                }else{
                    db.writeCarnageReportsToDB(reports, dataType.CARNAGE, false, gameType);
                }
                carnageList.clear();
            }
        }
    }

    public void cacheMatchEvents(Enum gameType, boolean cachePlayerEvents, MatchEvents[] oldMatchEvents, Match... matches1) throws Exception {
//        System.out.println("Getting " + gameType + " game carnage for: " + hApi.PLAYER_UF);
        if (oldMatchEvents == null){
            oldMatchEvents = hApi.getPlayerMatchEvents(gameType, hApi.PLAYER_UF);
        }
        if (! cachePlayerEvents){
            oldMatchEvents = null;
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
            if (cachePlayerEvents) {
                matches = hApi.getPlayerMatches(gameType);
            } else {
                matches = hApi.getAllMatches(gameType);
            }
        }
//        System.out.println("Caching " + matches.length + " matches");
        MatchEvents matchEvent = null;
        List<MatchEvents> matchEventList = new ArrayList<>();
        if (oldMatchEvents != null) {
            for (int i = 0; i < oldMatchEvents.length; i++) {
                oldMatchIDs.add(oldMatchEvents[i].getMatchID());
            }
            for (int i = 0; i < matches.length; i++) {
                if (oldMatchIDs.contains(matches[i].getId().getMatchId())) {
                    allMatchIDs.add(matches[i].getId().getMatchId());
                    continue;
                }
                else {
                    if (matches[i].getMatchCompletedDate().getDate().contains("2015-10") || matches[i].getMatchCompletedDate().getDate().contains("2015-11"))
                        continue;
                    newMatchIDs.add(matches[i].getId().getMatchId());
                    allMatchIDs.add(matches[i].getId().getMatchId());
                }
            }
        } else if (oldMatchEvents == null) {
            System.out.println("There aren't any carnage reports for this player in the database, starting from scratch...");
            System.out.println("This might take a while");
            for (int i = 0; i < matches.length; i++) {
                newMatchIDs.add(matches[i].getId().getMatchId());
            }
        }

        matches = null;
        matches1 = null;
        oldMatchEvents = null;
        allMatchIDs.clear();
        oldMatchIDs.clear();

        JSONObject obj2 = null;
        for (int i = 0; i < newMatchIDs.size(); i++) {
            obj2 = new JSONObject(hApi.listEventsForMatch(newMatchIDs.get(i)));
            if (obj2.toString().equalsIgnoreCase("{}")) {
//                oldMatchIDs.add(newMatchIDs.get(i));
                continue;
            }
            matchEvent = gson.fromJson(obj2.toString(), MatchEvents.class);
            matchEvent.setMatchID(newMatchIDs.get(i));
            matchEventList.add(matchEvent);
            if (i % 25 == 0 && i != 0) {
                System.out.println("Finished caching the first " + i + " Match Events");
                double percentComplete = (((double)i / (double)newMatchIDs.size()) * 100000d) / 1000d;
                System.out.println("Database caching is " + percentComplete + " percent complete!");
                MatchEvents[] matchEvents = new MatchEvents[matchEventList.size()];
                for (int k = 0; k < matchEventList.size(); k++) {
                    matchEvents[k] = matchEventList.get(k);
                }
                matchEventList.clear();
                if (cachePlayerEvents) {
                    db.writeMatchEventsToDB(matchEvents, dataType.MATCHEVENTS, true, gameType);
                }else{
                    db.writeMatchEventsToDB(matchEvents, dataType.MATCHEVENTS, false, gameType);
                }
            }
            else if (newMatchIDs.size() - i < 25 && matchEventList.size() == (newMatchIDs.size() - i)){
                System.out.println("Finished caching the last " + i + " Match Events");
                double percentComplete = (((double)i / (double)newMatchIDs.size()) * 100000d) / 1000d;
                System.out.println("Database caching is " + percentComplete + " percent complete!");
                MatchEvents[] matchEvents = new MatchEvents[matchEventList.size()];
                for (int k = 0; k < matchEventList.size(); k++) {
                    matchEvents[k] = matchEventList.get(k);
                }
                matchEventList.clear();
                if (cachePlayerEvents) {
                    db.writeMatchEventsToDB(matchEvents, dataType.MATCHEVENTS, true, gameType);
                }else{
                    db.writeMatchEventsToDB(matchEvents, dataType.MATCHEVENTS, false, gameType);
                }
            }
        }
//        for (int i = 0; i < oldMatchIDs.size(); i++){
//            for (Match match : matches){
//                if (match.getId().getMatchId().equalsIgnoreCase(oldMatchIDs.get(i)))
//                    log.info(match.getMatchCompletedDate().getDate());
//            }
//        }
//        log.info(oldMatchIDs.size() + "");
    }

    public void cachePlayerCarnage(CarnageReport[] reports, Enum gameType) throws Exception{
        db.writeCarnageReportsToDB(reports, dataType.CARNAGE, true, gameType);
    }

    public void cachePlayerMatchEventsThreadTest(Enum gameType) throws Exception{
        Match[] matches = hApi.getPlayerMatches(gameType);
        MatchEvents[] oldMatchEvents = null;
        if (hApi.getPlayerMatchEvents(gameType, hApi.PLAYER_UF) != null)
            oldMatchEvents = hApi.getPlayerMatchEvents(gameType, hApi.PLAYER_UF);
        int matchCount = matches.length;
        int div = (int)(matches.length *10.0f/100.0f);
        log.info(div + "");
        int threadCount = (div/10);
        double totalGames = hApi.totalGames(gameType, hApi.PLAYER);
        log.info(totalGames + "");
        double unthreadedCarnage = totalGames - (threadCount*100);
        System.out.println("Starting " + threadCount + " threads");
//        System.out.println(matchCount);
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            int arrayStart = finalI * 100;
            int arrayEnd = (finalI + 1) * 100;
            System.out.println(arrayStart + " " + arrayEnd);
            Match[] finalMatches = matches;
            MatchEvents[] finalOldMatchEvents = oldMatchEvents;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        cacheMatchEvents(gameType, true, finalOldMatchEvents,Arrays.copyOfRange(finalMatches, arrayStart, arrayEnd));
                        System.out.println(db.getNumberOfRows(dataType.CARNAGE, true, gameType));
//                            for (int j = arrayStart; j < arrayEnd; j++){
//                                cacheGameCarnage(gameType, false, finalMatches[j]);
//                            }
                    }catch(OutOfMemoryError e){
                        System.out.println(Runtime.getRuntime().maxMemory());
                        System.out.println(e.getMessage() + ". Total memory: " + Runtime.getRuntime().totalMemory() + " Free memory: " + Runtime.getRuntime().freeMemory() + " " + (Runtime.getRuntime().totalMemory() - (Runtime.getRuntime().freeMemory())));
                    }
                    catch (Exception e){e.printStackTrace();}
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
        if (unthreadedCarnage > 0) {
            matches = (Match[]) db.getSomeMatchesFromDB(0, (int) unthreadedCarnage, true, gameType);
        }
        cacheMatchEvents(gameType, true, oldMatchEvents, matches);
    }

//    public void cacheCarnageThreadTest(Enum gameType) throws Exception{
//        for (int k = 0; k < 60; k++) {
//            Match[] matches = (Match[]) db.getSomeMatchesFromDB(k, 10000, false, gameType);
//            int matchCount = matches.length;
//            int div = 400;
//            System.out.println("Starting " + matchCount / div + " threads");
////        System.out.println(matchCount);
//            Thread[] threads = new Thread[matchCount / div];
//            for (int i = 0; i < matchCount / div; i++) {
//                int finalI = i;
//                int arrayStart = finalI * div;
//                int arrayEnd = (finalI + 1) * div;
//                Match[] finalMatches = matches;
//                int finalK = k;
//                threads[i] = new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            System.out.println("Creating thread: " + finalI + " in loop: " + finalK);
//                            cacheGameCarnage(gameType, false, Arrays.copyOfRange(finalMatches, arrayStart, arrayEnd));
//                            System.out.println(db.getNumberOfRows(dataType.CARNAGE, false, ARENA));
////                            for (int j = arrayStart; j < arrayEnd; j++){
////                                cacheGameCarnage(gameType, false, finalMatches[j]);
////                            }
//                            if (finalI == (matchCount / div) - 1)
//                                System.out.println("Thread " + finalI + " in loop " + finalK + " completed");
//                        }catch(OutOfMemoryError e){
//                            System.out.println(Runtime.getRuntime().maxMemory());
//                            System.out.println(e.getMessage() + ". Total memory: " + Runtime.getRuntime().totalMemory() + " Free memory: " + Runtime.getRuntime().freeMemory() + " " + (Runtime.getRuntime().totalMemory() - (Runtime.getRuntime().freeMemory())));
//                        }
//                        catch (Exception e){}
//                    }
//                };
//                threads[i].start();
//            }
//            for (int i = 0; i < matchCount / div; i++) {
//                try {
//                    threads[i].join();
//                } catch (Exception e) {
//                }
//            }
//        }
//    }

    public void cachePlayerCarnageThreadTest(Enum gameType) throws Exception{
        Match[] matches = hApi.getPlayerMatches(gameType);
        CarnageReport[] oldCarnageReports = (CarnageReport[])db.getMetadataFromDB(dataType.CARNAGE, true, gameType);
        int matchCount = matches.length;
        int div = (int)(matches.length *10.0f/100.0f);
        log.info(div + "");
        int threadCount = (div/10);
        double totalGames = hApi.totalGames(gameType, hApi.PLAYER);
        log.info(totalGames + "");
        double unthreadedCarnage = totalGames - (threadCount*100);
        System.out.println("Starting " + threadCount + " threads");
//        System.out.println(matchCount);
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            int arrayStart = finalI * 100;
            int arrayEnd = (finalI + 1) * 100;
            System.out.println(arrayStart + " " + arrayEnd);
            Match[] finalMatches = matches;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        cacheGameCarnage(gameType, true, oldCarnageReports ,Arrays.copyOfRange(finalMatches, arrayStart, arrayEnd));
                        System.out.println(db.getNumberOfRows(dataType.CARNAGE, true, gameType));
//                            for (int j = arrayStart; j < arrayEnd; j++){
//                                cacheGameCarnage(gameType, false, finalMatches[j]);
//                            }
                    }catch(OutOfMemoryError e){
                        System.out.println(Runtime.getRuntime().maxMemory());
                        System.out.println(e.getMessage() + ". Total memory: " + Runtime.getRuntime().totalMemory() + " Free memory: " + Runtime.getRuntime().freeMemory() + " " + (Runtime.getRuntime().totalMemory() - (Runtime.getRuntime().freeMemory())));
                    }
                    catch (Exception e){e.printStackTrace();}
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
        if (unthreadedCarnage > 0) {
            matches = (Match[]) db.getSomeMatchesFromDB(0, (int) unthreadedCarnage, true, gameType);
        }
        cacheGameCarnage(gameType, true, oldCarnageReports, matches);
    }

    public void cachePlayerCarnage(Enum gameType) throws Exception {
        Match[] matches = hApi.getPlayerMatches(gameType);
        CarnageReport[] oldReports = (CarnageReport[]) db.getMetadataFromDB(dataType.CARNAGE, true, gameType);
        cacheGameCarnage(gameType, true, oldReports, matches);
    }

    public void cacheMatchesThreadTest(Enum gameType) throws Exception{
        List<String> playersList = db.getPlayersFromDB(true);
        String[] players = playersList.toArray(new String[playersList.size()]);
        int playerCount = players.length;
        int div = 1;
//        if (playerCount % 500 > 1) {
//            div = 50;
//            log.info(div + " " + playerCount % 500);
//        }
        if (playerCount % 10 > 1){
            div = 10;
            log.info(div + "");
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
//        cacheGameCarnage(gameType, false);
    }

    public void cacheAShitTonOfPlayerCaranage(Enum gameType) throws Exception {
//        CarnageReport[] oldCarnageReports = hApi.getPlayerCarnageReports(gameType);
        for (int k = 0; k < hApi.totalGames(gameType, hApi.PLAYER)/300; k++) {
            Match[] matches = (Match[]) db.getSomeMatchesFromDB(k*300, 300, true, gameType);
            int matchCount = matches.length;
            int div = 5;
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
//                            System.out.println("Creating thread: " + finalI + " in loop: " + finalK);
                            cacheGameCarnage(gameType, true, null, Arrays.copyOfRange(finalMatches, arrayStart, arrayEnd));
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
