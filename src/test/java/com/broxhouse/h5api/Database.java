package com.broxhouse.h5api;

/**
 * Created by Brock Berrett on 7/26/2016.
 */

import com.broxhouse.h5api.models.metadata.Map;
import com.broxhouse.h5api.models.metadata.MapVariant;
import com.broxhouse.h5api.models.metadata.Medal;
import com.broxhouse.h5api.models.metadata.Weapon;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

enum dataType{METADATA, PLAYERMATCHDATA, PLAYERCARNAGE, PLAYERENEMIES, WEAPONS, MAPS, MEDALS}

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

    public Database(Connection conn, Statement stmt, ResultSet rs){
        this.conn = conn;
        this.stmt = stmt;
        this.rs = rs;
    }

    public Database(){

    }

//    static Connection conn = null;
//    static Statement stmt = null;

//    public static void setupDatabase(){
//        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        }catch(Exception e){}
//    }

    public static Weapon[] getWeaponsDB(){
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
        }catch(Exception e){}
        handleConnection(db);
        return weaponsArr;
    }

    public static Map[] getMapsDB(){
        List<Map> maps = new ArrayList<>();
        Map[] mapArr = null;
        Database db = createDatabaseConnection(dataType.MAPS);
        try{
            while(db.rs.next()){
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
            for (int i = 0; i < maps.size(); i++){
                mapArr[i] = maps.get(i);
            }
        }catch (Exception e){}
        handleConnection(db);
        return mapArr;
    }

    public static Medal[] getMedalsDB(){
        List<Medal> medals = new ArrayList<>();
        Medal[] medalArr = null;
        Database db = createDatabaseConnection(dataType.MEDALS);
        try{
            while(db.rs.next()){
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
            for(int i = 0; i < medals.size(); i++) {
                medalArr[i] = medals.get(i);
            }

        }catch (Exception e){}
        handleConnection(db);
        return medalArr;
        }

    public static void addWeaponstoDB(Statement stmt){
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
                    sql = "INSERT INTO " + "WEAPONS" + " VALUES " +
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
            }catch (Exception e){}
    }

    public static void addArenaMapVariantsToDB(Statement stmt){
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
                sql = "INSERT INTO " + "WEAPONS" + " VALUES " +
                        "('" + metaData[i].getId() + "', " +
                        "'" + metaData[i].getContentId() + "', " +
                        "'" + metaData[i].getName() + "', " +
                        "'" + mapDesc + "', " +
                        "'" + metaData[i].getImageUrl() + "', " +
                        "" + metaData[i].getCount() + ")";
                stmt.executeUpdate(sql);
            }
        }catch (Exception e){}
    }

    public static void addMedalsToDB(Statement stmt){
        Medal[] medals = null;
        String sql = null;
        Gson gson = new Gson();
        try{
            String medalData = haloApi.listMedals();
            medals = gson.fromJson(medalData, Medal[].class);
            System.out.println(medals.length);
            for (int i = 0; i < medals.length; i++){
                String mapDesc = medals[i].getDescription();
                if (mapDesc.contains("'")) {
                    mapDesc = mapDesc.replaceAll("'", "''");
                }
                sql = "INSERT INTO " + "MEDALS" + " VALUES " +
                        "(" + medals[i].getId() + ", " +
                        "'" + medals[i].getName() + "', " +
                        "" + medals[i].getDifficulty() + ", " +
                        "'" + mapDesc + "', " +
                        "'" + medals[i].getClassification() + "', " +
                        "'" + medals[i].getContentId() + "')";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            }
        }catch (Exception e){}
    }

    public static void addItemsToDatabase(Enum dataType){
        Connection conn = null;
        Statement stmt = null;
        String tableName = dataType.toString();
        clearTable(tableName);
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            if (tableName.equalsIgnoreCase("weapons")){
                addWeaponstoDB(stmt);
            }else if (tableName.equalsIgnoreCase("maps")){
                addArenaMapVariantsToDB(stmt);
            }else if(tableName.equalsIgnoreCase("medals")){
                addMedalsToDB(stmt);
            }
//            sql = "INSERT INTO Registration " +
//                    "VALUES (101, 'Mahnaz', 'Fatma', 25)";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Registration " +
//                    "VALUES (102, 'Zaid', 'Khan', 30)";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Registration " +
//                    "VALUES(103, 'Sumit', 'Mittal', 28)";
//            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static Database createDatabaseConnection(Enum dataType){
        Connection conn = null;
        Statement stmt = null;
        String tableName = dataType.toString();
        ResultSet rs = null;
        Database database = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql;
            sql = "SELECT * FROM " + tableName;
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            database = new Database(conn, stmt, rs);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return database;
    }

    public static void handleConnection(Database db){
        try{
            db.rs.close();
            db.stmt.close();
            db.conn.close();

        }catch(Exception e){}
        finally{
            //finally block used to close resources
            try{
                if(db.stmt!=null)
                    db.stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(db.conn!=null)
                    db.conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static void clearTable(String tableName){
        String clear = "DELETE FROM Weapons";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            int deletedRows = stmt.executeUpdate(clear);
            if (deletedRows > 0) {
                System.out.println("Deleted " + deletedRows +  " Rows In The Table Successfully...");
            } else {
                System.out.println("Table already empty.");
            }
        }catch(Exception e){}
        finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }
}

class databaseHandler{


    }
