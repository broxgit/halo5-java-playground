package com.broxhouse.h5api;

/**
 * Created by Brock Berrett on 7/26/2016.
 */

import com.broxhouse.h5api.models.metadata.*;
import com.broxhouse.h5api.models.stats.matches.Match;
import com.broxhouse.h5api.models.stats.reports.Resource;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.broxhouse.h5api.Database.player;

enum dataType{METADATA, PLAYERMATCHDATA, PLAYERCARNAGE, PLAYERENEMIES, WEAPONS, MAPS, MEDALS, MAPVARIANTS, CUSTOMMAPVARIANTS, ARENAMATCHES, CUSTOMMATCHES, MATCHES}

enum resourceType{MAPVRESOURCES, GAMEVRESOURCES}

public class Database {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/HALO5?autoReconnect=true&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "winter12";

    static HaloApi haloApi = new HaloApi();

    static String player = haloApi.PLAYER_UF.replaceAll("\\s+", "").toUpperCase();

    public Database(Connection conn, Statement stmt, ResultSet rs) {
        this.conn = conn;
        this.stmt = stmt;
        this.rs = rs;
    }

    public Database() {

    }

//    static Connection conn = null;
//    static Statement stmt = null;

//    public static void setupDatabase(){
//        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        }catch(Exception e){}
//    }

    public static Weapon[] getWeaponsDB() {
        List<Weapon> weapons = new ArrayList<>();
        Weapon[] weaponsArr = null;
        Database db = createDatabaseConnection(dataType.WEAPONS);
        try {
            while (db.rs.next()) {
//                    System.out.println(rs.getRow());
//                    int i = 0;
//                    System.out.println(i);
                Weapon weapon = new Weapon();
                weapon.setId(db.rs.getLong("id"));
                weapon.setContentId(db.rs.getString("contentid"));
                weapon.setName(db.rs.getString("name"));
                weapon.setDescription(db.rs.getString("description"));
                weapon.setLargeIconImageUrl(db.rs.getString("largeimgurl"));
                weapon.setSmallIconImageUrl(db.rs.getString("smallimgurl"));
                weapon.setType(db.rs.getString("type"));
                weapon.setUsableByPlayer(db.rs.getBoolean("playerusable"));
                weapons.add(weapon);
            }
            weaponsArr = new Weapon[weapons.size()];
            for (int i = 0; i < weapons.size(); i++) {
                weaponsArr[i] = weapons.get(i);
            }
        } catch (Exception e) {
        }
        handleConnection(db);
        return weaponsArr;
    }

    public static Map[] getMapsDB() {
        List<Map> maps = new ArrayList<>();
        Map[] mapArr = null;
        Database db = createDatabaseConnection(dataType.MAPS);
        try {
            while (db.rs.next()) {
                Map map = new Map();
                map.setId(db.rs.getString("id"));
                map.setContentId(db.rs.getString("contentid"));
                map.setName(db.rs.getString("name"));
                map.setDescription(db.rs.getString("description"));
                map.setImageUrl(db.rs.getString("mapimgurl"));
                map.setCount(db.rs.getInt("count"));
                maps.add(map);
            }
            mapArr = new Map[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                mapArr[i] = maps.get(i);
            }
        } catch (Exception e) {
        }
        handleConnection(db);
        return mapArr;
    }

    public static MapVariant[] getArenaMapVariants() {
        List<MapVariant> maps = new ArrayList<>();
        MapVariant[] mapArr = null;
        Database db = createDatabaseConnection(dataType.MAPVARIANTS);
        try {
            while (db.rs.next()) {
                MapVariant map = new MapVariant();
                map.setId(db.rs.getString("id"));
                map.setContentId(db.rs.getString("contentid"));
                map.setName(db.rs.getString("name"));
                map.setDescription(db.rs.getString("description"));
                map.setMapImageUrl(db.rs.getString("mapimgurl"));
//                map.setCount(db.rs.getInt("count"));
                maps.add(map);
            }
            mapArr = new MapVariant[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                mapArr[i] = maps.get(i);
            }
        } catch (Exception e) {
        }
        handleConnection(db);
        return mapArr;
    }

    public static CustomMapVariant[] getCustomMapVariants() {
        List<CustomMapVariant> maps = new ArrayList<>();
        CustomMapVariant[] mapArr = null;
        Database db = createDatabaseConnection(dataType.CUSTOMMAPVARIANTS);
        try {
            while (db.rs.next()) {
                CustomMapVariant map = new CustomMapVariant();
                Identity identity = new Identity();
                identity.setResourceId(db.rs.getString("resourceid"));
                identity.setOwner(db.rs.getString("owner"));
                identity.setResourceType(9);
                identity.setOwnerType(3);
                map.setName(db.rs.getString("name"));
                map.setDescription(db.rs.getString("description"));
                map.setCount(db.rs.getInt("count"));
                map.setIdentity(identity);
                map.setBanned(db.rs.getBoolean("banned"));
                maps.add(map);
            }
            mapArr = new CustomMapVariant[maps.size()];
            for (int i = 0; i < maps.size(); i++) {
                System.out.println(maps.get(i).getName() + " " + maps.get(i).getIdentity().getResourceId());
                mapArr[i] = maps.get(i);
            }
        } catch (Exception e) {
        }
        handleConnection(db);
        return mapArr;
    }

    public static Medal[] getMedalsDB() {
        List<Medal> medals = new ArrayList<>();
        Medal[] medalArr = null;
        Database db = createDatabaseConnection(dataType.MEDALS);
        try {
            while (db.rs.next()) {
                Medal medal = new Medal();
                medal.setId(db.rs.getLong("id"));
                medal.setContentId(db.rs.getString("contentid"));
                medal.setName(db.rs.getString("name"));
                medal.setDescription(db.rs.getString("description"));
                medal.setClassification(db.rs.getString("classification"));
                medal.setDifficulty(db.rs.getInt("difficulty"));
                medals.add(medal);
            }
            medalArr = new Medal[medals.size()];
            for (int i = 0; i < medals.size(); i++) {
                medalArr[i] = medals.get(i);
            }

        } catch (Exception e) {
        }
        handleConnection(db);
        return medalArr;
    }

    public static Match[] getMatchesDB(Enum gameType) {
        List<Match> matchList = new ArrayList<>();
        Match[] matches = null;
        String tableName = player + gameType.toString();
        Database db = createDatabaseConnection(dataType.MATCHES, tableName);
        try {
            while (db.rs.next()) {
                Match match = new Match();
                Match.Id id = new Match.Id();
                Match.CompletedDate completedDate = new Match.CompletedDate();
                Resource mapv = new Resource();
                Resource gamev = new Resource();
                id.setMatchId(db.rs.getString("matchid"));
                id.setGameMode(db.rs.getInt("gamemode"));
                match.setId(id);
                completedDate.setDate(db.rs.getString("completeddate"));
                match.setMatchCompletedDate(completedDate);
                match.setHopperId(db.rs.getString("hopperid"));
                match.setMapId(db.rs.getString("mapid"));
                mapv.setResourceId(db.rs.getString("mapvresourceid"));
                mapv.setResourceType(db.rs.getInt("mapvresourcetype"));
                mapv.setOwnerType(db.rs.getString("mapvownertype"));
                mapv.setOwner(db.rs.getString("mapvowner"));
                match.setMapVariant(mapv);
                gamev.setResourceType(db.rs.getInt("gamevresourcetype"));
                gamev.setResourceId(db.rs.getString("gamevresourceid"));
                gamev.setOwnerType(db.rs.getString("gamevownertype"));
                gamev.setOwner(db.rs.getString("gamevowner"));
                match.setGameVariant(gamev);
                match.setMatchDuration(db.rs.getString("matchduration"));
                match.setTeamGame(db.rs.getBoolean("isteamgame"));
                match.setSeasonId(db.rs.getString("seasonid"));
                matchList.add(match);
            }
            matches = new Match[matchList.size()];
            for (int i = 0; i < matchList.size(); i++) {
                matches[i] = matchList.get(i);
            }
        } catch (Exception e) {
        }
        return matches;
    }

    public static Resource[] getResources(Enum resourceType){
        List<Resource> resourceList = new ArrayList<>();
        Resource[] resources = null;
        Database db = createDatabaseConnection(resourceType);
        try{
            while(db.rs.next()){
                Resource resource = new Resource();
                resource.setResourceId(db.rs.getString("resourceid"));
                resource.setOwner(db.rs.getString("owner"));
                resource.setOwnerType(db.rs.getString("ownertype"));
                resource.setResourceType(db.rs.getInt("resourcetype"));
                resourceList.add(resource);
            }
            resources = new Resource[resourceList.size()];
            for (int i = 0; i < resourceList.size(); i++){
                resources[i] = resourceList.get(i);
            }
        }catch (Exception e){}

        return resources;
    }

    public static void addWeaponstoDB(Statement stmt) {
        Weapon[] metaData = null;
        String sql = null;
        Gson gson = new Gson();
        try {
            String weaponData = haloApi.listWeapons();
            metaData = gson.fromJson(weaponData, Weapon[].class);
            for (int i = 0; i < metaData.length; i++) {
                String weaponDescription = metaData[i].getDescription();
                if (weaponDescription != null && weaponDescription.contains("'"))
                    weaponDescription = weaponDescription.replaceAll("'", "''");
                sql = "INSERT IGNORE INTO " + "WEAPONS" + " VALUES " +
                        "(" + metaData[i].getId() + ", " +
                        "'" + metaData[i].getContentId() + "', " +
                        "'" + metaData[i].getName() + "', " +
                        "'" + weaponDescription + "', " +
                        "'" + metaData[i].getLargeIconImageUrl() + "', " +
                        "'" + metaData[i].getSmallIconImageUrl() + "', " +
                        "'" + metaData[i].getType() + "', " +
                        "" + metaData[i].isUsableByPlayer() + ")";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
        }
    }

    public static void addMapsToDB(Statement stmt) {
        Map[] metaData = null;
        String sql = null;
        Gson gson = new Gson();
        try {
            String mapData = haloApi.listMaps();
            metaData = gson.fromJson(mapData, Map[].class);
            for (int i = 0; i < metaData.length; i++) {
                String mapDesc = metaData[i].getDescription();
                if (mapDesc != null && mapDesc.contains("'"))
                    mapDesc = mapDesc.replaceAll("'", "''");
                sql = "INSERT IGNORE INTO " + "MAPS" + " VALUES " +
                        "('" + metaData[i].getId() + "', " +
                        "'" + metaData[i].getContentId() + "', " +
                        "'" + metaData[i].getName() + "', " +
                        "'" + mapDesc + "', " +
                        "'" + metaData[i].getImageUrl() + "', " +
                        "" + metaData[i].getCount() + ")";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
        }
    }

    public static void addArenaMapVariantsToDB(MapVariant[] metaData) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = null;
            for (int i = 0; i < metaData.length; i++) {
                String mapDesc = metaData[i].getDescription();
                if (mapDesc != null && mapDesc.contains("'"))
                    mapDesc = mapDesc.replaceAll("'", "''");
                String mapName = metaData[i].getName();
                if (mapName != null && mapName.contains("'"))
                    mapName = mapName.replaceAll("'", "''");
                sql = "INSERT IGNORE INTO " + "MAPVARIANTS" + " VALUES " +
                        "('" + metaData[i].getId() + "', " +
                        "'" + metaData[i].getContentId() + "', " +
                        "'" + mapName + "', " +
                        "'" + mapDesc + "', " +
                        "'" + metaData[i].getMapImageUrl() + "', " +
                        "'" + metaData[i].getMapId() + "', " +
                        "" + metaData[i].getCount() + ")";
//                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }


    public static void addCustomMapVariantsToDB(CustomMapVariant[] metaData) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = null;
            for (int i = 0; i < metaData.length; i++) {
                String mapDesc = metaData[i].getDescription();
                if (mapDesc != null && mapDesc.contains("'"))
                    mapDesc = mapDesc.replaceAll("'", "''");
                sql = "INSERT IGNORE INTO " + "CUSTOMMAPVARIANTS" + " VALUES " +
                        "('" + metaData[i].getIdentity().getResourceId() + "', " +
                        "'" + metaData[i].getIdentity().getOwner() + "', " +
                        "'" + metaData[i].getName() + "', " +
                        "'" + mapDesc + "', " +
                        "" + metaData[i].getBanned() + ", " +
                        "" + metaData[i].getCount() + ")";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
        }
    }

    public static void addResources(Resource[] metaData, Enum resourceType, Enum gameType) {
        Connection conn = null;
        Statement stmt = null;
        String gameMode = gameType.toString();
        System.out.println(gameMode);
        String name = null;
        String description = null;
        MapVariant map = null;
        GameVariant game = null;
        CustomMapVariant cMap = null;
        CustomGameVariant cGame = null;

//        clearTable(resourceType.toString());
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = null;
            System.out.println(metaData.length);
            for (int i = 0; i < metaData.length; i++) {
                if (checkDBForValue("resourceid", metaData[i].getResourceId(), resourceType))
                    continue;
                if (resourceType.toString().equalsIgnoreCase("mapvresources" )) {
                    if( gameMode.equalsIgnoreCase("arena")){
//                        System.out.println(metaData[i].getResourceId());
                        map = haloApi.getMapVariant(metaData[i].getResourceId());
                        name = map.getName();
                        description = map.getDescription();
                    } else if(gameMode.equalsIgnoreCase("custom")){
                        cMap = haloApi.getCustomMapVariant(metaData[i].getResourceId(), haloApi.formatString(metaData[i].getOwner()));
                        description = cMap.getDescription();
                        name = cMap.getName();
                    }
                } else if (resourceType.toString().equalsIgnoreCase("gamevresources")) {
                    if (gameMode.equalsIgnoreCase("arena")) {
                        game = haloApi.getGameVariant(metaData[i].getResourceId());
                        name = game.getName();
                        description = game.getDescription();
                    } else if(gameMode.equalsIgnoreCase("custom")){
                        cGame = haloApi.getCustomGameVariant(metaData[i].getResourceId(), haloApi.formatString(metaData[i].getOwner()));
                        description = cGame.getDescription();
                        name = cGame.getName();
                    }
                }
                if (description != null && description.contains("'"))
                    description = description.replaceAll("'", "''");
                if (description == null)
                    description = "none";
                if (name == null)
                    name = "none";
                sql = "INSERT IGNORE INTO " + resourceType.toString() + " VALUES " +
                        "('" + metaData[i].getResourceId() + "', " +
                        "" + metaData[i].getResourceType() + ", " +
                        "'" + metaData[i].getOwnerType() + "', " +
                        "'" + metaData[i].getOwner() + "', " +
                        "'" + gameMode + "', " +
                        "'" + name + "', " +
                        "'" + description + "')";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
            conn.close();
        } catch (Exception e) {
        }
    }

    public static void addMedalsToDB(Statement stmt) {
        Medal[] medals = null;
        String sql = null;
        Gson gson = new Gson();
        try {
            String medalData = haloApi.listMedals();
            medals = gson.fromJson(medalData, Medal[].class);
            System.out.println(medals.length);
            for (int i = 0; i < medals.length; i++) {
                String mapDesc = medals[i].getDescription();
                if (mapDesc.contains("'")) {
                    mapDesc = mapDesc.replaceAll("'", "''");
                }
                sql = "INSERT IGNORE INTO " + "MEDALS" + " VALUES " +
                        "(" + medals[i].getId() + ", " +
                        "'" + medals[i].getName() + "', " +
                        "" + medals[i].getDifficulty() + ", " +
                        "'" + mapDesc + "', " +
                        "'" + medals[i].getClassification() + "', " +
                        "'" + medals[i].getContentId() + "')";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
        }
    }

    public static void createTable(String tableName) throws Exception{

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE " + tableName +
                    "(b blob);";
//            System.out.println(sql);
            stmt.executeUpdate(sql);
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
    }

    public static void createMatchesTable(Enum gameType, boolean createPlayerDB) {
        Connection conn = null;
        Statement stmt = null;
        String gameMode = gameType.toString();
        String tableName = player + gameMode + "MATCHES ";
        if (!createPlayerDB)
            tableName = gameMode + "MATCHES ";
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating Matches Table");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE " + tableName +
                    "(matchid varchar (255) primary key, " +
                    "gamemode int not null, " +
                    "completeddate varchar(255), " +
                    "hopperid varchar(255), " +
                    "mapid varchar (255), " +
                    "mapvresourceid varchar (255), " +
                    "mapvresourcetype int not null, " +
                    "mapvownertype varchar (5), " +
                    "mapvowner varchar (255), " +
                    "gamebasevid varchar (255), " +
                    "gamevresourcetype int not null, " +
                    "gamevresourceid varchar (255), " +
                    "gamevownertype varchar (5), " +
                    "gamevowner varchar (255), " +
                    "matchduration varchar (255), " +
                    "isteamgame boolean, " +
                    "seasonid varchar (255))";
//            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public static void addMatchesToDB(Match[] metaData, Enum gameType, boolean createPlayerDB) {
        Connection conn = null;
        Statement stmt = null;
        String gameMode = gameType.toString();
        String tableName = player + gameMode + "MATCHES";
        if (!createPlayerDB){
            tableName = gameMode + "MATCHES";
        }
//        clearTable(tableName);
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (!tables.next()){
                System.out.println("Table doesn't exist");
                createMatchesTable(gameType, createPlayerDB);
            }
            String sql = null;
            int i = 0;
            for (i = 0; i < metaData.length; i++) {
                sql = "INSERT IGNORE INTO " + tableName + " VALUES " +
                        "('" + metaData[i].getId().getMatchId() + "', " +
                        "" + metaData[i].getId().getGameMode() + ", " +
                        "'" + metaData[i].getMatchCompletedDate().getDate() + "', " +
                        "'" + metaData[i].getHopperId() + "', " +
                        "'" + metaData[i].getMapId() + "', " +
                        "'" + metaData[i].getMapVariant().getResourceId() + "', " +
                        "" + metaData[i].getMapVariant().getResourceType() + ", " +
                        "'" + metaData[i].getMapVariant().getOwnerType() + "', " +
                        "'" + metaData[i].getMapVariant().getOwner() + "', " +
                        "'" + metaData[i].getGameBaseVariantId() + "', " +
                        "" + metaData[i].getGameVariant().getResourceType() + ", " +
                        "'" + metaData[i].getGameVariant().getResourceId() + "', " +
                        "'" + metaData[i].getGameVariant().getOwnerType() + "', " +
                        "'" + metaData[i].getGameVariant().getOwner() + "', " +
                        "'" + metaData[i].getMatchDuration() + "', " +
                        "" + metaData[i].isTeamGame() + ", " +
                        "'" + metaData[i].getSeasonId() + "')";
//                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
            System.out.println(i);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static void writeMetadataToDB(Object[] objects, Enum dataType, boolean isPlayerDB, Enum gameType) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        String tableName = null;
        String gameMode = gameType.toString();
        if (gameMode.equalsIgnoreCase("na")){
            tableName = dataType.toString() + "blob";
        } else {
            tableName = gameMode + dataType.toString() + "blob";
        }
        if (isPlayerDB)
            tableName = player + gameMode + dataType.toString() + "blob";
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        if (!tables.next()){
            System.out.println("Table doesn't exist");
            createTable(tableName);
        }
        for (int i = 0; i < objects.length; i++){
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO " + tableName + " VALUES (?)");
            pstmt.setObject(1, objects[i]);
            pstmt.executeUpdate();
            pstmt.close();
        }
        conn.close();
        System.out.println("Goodbye!");
    }

    public static Object[] getMetadataFromDB(Enum dataType) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT b FROM TestWeapons");
        List<Object> metaList = new ArrayList<>();
        Object[] objects = null;
        while (rs.next()) {
            byte[] st = (byte[]) rs.getObject(1);
            ByteArrayInputStream baip = new ByteArrayInputStream(st);
            ObjectInputStream ois = new ObjectInputStream(baip);
            Object weapon =  ois.readObject();
            metaList.add(weapon);
        }
        objects = new Weapon[metaList.size()];
        for (int i = 0; i < metaList.size(); i++){
            objects[i] = metaList.get(i);
        }
        stmt.close();
        rs.close();
        conn.close();
        return objects;
    }

    public static void addItemsToDatabase(Enum dataType) {
        Connection conn = null;
        Statement stmt = null;
        String tableName = dataType.toString();
//        clearTable(tableName);
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            if (tableName.equalsIgnoreCase("weapons")) {
                addWeaponstoDB(stmt);
            } else if (tableName.equalsIgnoreCase("maps")) {
                addMapsToDB(stmt);
            } else if (tableName.equalsIgnoreCase("medals")) {
                addMedalsToDB(stmt);
            }
            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static Database createDatabaseConnection(Enum dataType, String... tablename) {
        Connection conn = null;
        Statement stmt = null;
        String tableName = null;
        if (tablename.length > 0) {
            tableName = tablename[0] + dataType.toString();
        } else {
            tableName = dataType.toString();
        }
        ResultSet rs = null;
        Database database = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql;
            sql = "SELECT * FROM " + tableName;
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            database = new Database(conn, stmt, rs);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return database;
    }

    public static void handleConnection(Database db) {
        try {
            db.rs.close();
            db.stmt.close();
            db.conn.close();

        } catch (Exception e) {
        } finally {
            //finally block used to close resources
            try {
                if (db.stmt != null)
                    db.stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (db.conn != null)
                    db.conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static void clearTable(String tableName) {
        String clear = "DELETE FROM " + tableName;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            int deletedRows = stmt.executeUpdate(clear);
            if (deletedRows > 0) {
                System.out.println("Deleted " + deletedRows + " Rows In The Table Successfully...");
            } else {
                System.out.println("Table already empty.");
            }
        } catch (Exception e) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public static boolean checkDatabaseValues(String column, String value, Enum dataType) {
        String data = dataType.toString();
        String sql = data + " WHERE " + column + " = '" + value + "'";
        Database db = createDatabaseConnection(dataType, sql);
        int count = 0;
        try {
            if (db.rs.next()) {
                count = db.rs.getInt(1);
            }
        } catch (Exception e) {
        }

        return count > 0;
    }


    public static boolean checkDBForValue(String column, String value, Enum dataType) throws SQLException {
        int count = 0;
        PreparedStatement stmt = null;
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        ResultSet rset = null;
        boolean hasValue = false;

        try {
            stmt = connection.prepareStatement(
                    "SELECT * from " + dataType.toString() + " WHERE "+ column +  "=?");
            stmt.setString(1, value);
//            System.out.println(stmt);
            rset = stmt.executeQuery();
            if (rset.absolute(1)){
                connection.close();
                return true;
            }else{
                connection.close();
                return false;
            }

        } finally {
            if(rset != null) {
                try {
                    rset.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

