package utnyilvantartojava;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DbModel {

    final String JDBC_DRIVER = "org.sqlite.JDBC";
    final String URL = "jdbc:sqlite:routeregister.db";
    final String USERNAME = "";
    final String PASSWORD = "";


    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmeta = null;
    ResultSet rs1 = null;
    PreparedStatement prep = null;

    public DbModel() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A kapcsolat létrejött az adatbázissal,");
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Hiba!");
                System.out.println("" + ex);
            }
        }
        try {
            dbmeta = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

        try {
            rs1 = dbmeta.getTables(null, "APP", "ROUTES", null);
            if (!rs1.next()) {
                createStatement.execute("create table routes(" +
                        "routeid integer primary key autoincrement," +
                        "date text," +
                        "depart text," +
                        "arrive text," +
                        "distance integer," +
                        "client text," +
                        "private integer," +
                        "backandforth integer," +
                        "sites integer);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

        try {
            rs1 = dbmeta.getTables(null, "APP", "SETTINGS", null);
            if (!rs1.next()) {
                createStatement.execute("create table settings(" +
                        "id integer primary key," +
                        "nev text," +
                        "telephely text," +
                        "autoTipusa text," +
                        "rendszam text," +
                        "loketterfogat integer," +
                        "fogyasztas integer," +
                        "elozoZaroKm integer);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

        try {
            rs1 = dbmeta.getTables(null, "APP", "CLIENTS", null);
            if (!rs1.next()) {
                createStatement.execute("create table clients(" +
                        "client text not null,"+
                        "clientnumber text primary key not null ," +
                        "type text,"+
                        "factorynumber text,"+
                        "zipcode integer,"+
                        "city text not null," +
                        "address text," +
                        "exist integer,"+
                        "maintenanceperyear integer,"+
                        "field text);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

        try {
            rs1 = dbmeta.getTables(null, "APP", "DISTANCES", null);
            if (!rs1.next()) {
                createStatement.execute("create table distances(" +
                        "distid integer primary key autoincrement," +
                        "clientid1 text," +
                        "clientid2 text," +
                        "distance integer);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

    }
    public void addUser1(String name, String address, int age) {

        try {
            String sqlQuery = "insert into users values ('" + name + "','" + address + "'," + age + ")";
            createStatement.execute(sqlQuery);
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

   public void delSettings(){
       String sqlQuery="delete from settings where id ='1'";
       try {
           createStatement.execute(sqlQuery);
       } catch (SQLException throwables) {
           System.out.println("Hiba az adatok törlésekor"+throwables);;
       }
   }


    public void addSetting(int id,String nev,String telephely,String autoTip,String rendsz,int lokett, int fogy, int zaroKm) {
        String sqlQuery = "insert into settings values (?,?,?,?,?,?,?,?)";
        try {
            prep = conn.prepareStatement(sqlQuery);
            prep.setInt(1,id);
            prep.setString(2, nev);
            prep.setString(3, telephely );
            prep.setString(4, autoTip);
            prep.setString(5, rendsz);
            prep.setInt(6, lokett);
            prep.setInt(7, fogy);
            prep.setInt(8, zaroKm);
            prep.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    // hozzáad egy új utat a routes táblához
    public void addRoute(String datum, String indulas, String erkezes, int tavolsag, String ugyfel, Boolean magan, Boolean odaVissza, Boolean telephelyrol) {
        String sqlQuery = "insert into Routes values (?,?,?,?,?,?,?,?,?)";
        try {
            prep = conn.prepareStatement(sqlQuery);
            prep.setString(1,null);
            prep.setString(2, datum.toString());
            prep.setString(3, indulas);
            prep.setString(4, erkezes);
            prep.setInt(5,tavolsag );
            prep.setString(6, ugyfel);
            prep.setInt(7, convertBool(magan));
            prep.setInt(8, convertBool(odaVissza));
            prep.setInt(9, convertBool(telephelyrol));
            prep.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }


    // hozzáad egy új ügyfelet(gépet) a client táblához
    public void addClient(String client, String clientnumber,String type, String factorynumber, int zipcode,  String city,  String address, Boolean exist,int maintinanceperyear, String field) {
        String sqlQuery = "insert into clients values (?,?,?,?,?,?,?,?,?,?)";
        try {
            prep = conn.prepareStatement(sqlQuery);
            prep.setString(1, client);
            prep.setString(2, clientnumber);
            prep.setString(3, type);
            prep.setString(4, factorynumber);
            prep.setInt(5, zipcode);
            prep.setString(6, city);
            prep.setString(7, address);
            prep.setInt(8, convertBool(exist));
            prep.setInt(9, maintinanceperyear);
            prep.setString(10, field);
            prep.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    //hozzáad két ügyfél közti távolságot a distances táblához
    public void addDistance(String clientId1, String clientId2, int distance) {
        String sqlQuery = "insert into distances values (?,?,?,?)";
        try {
            prep = conn.prepareStatement(sqlQuery);
            prep.setString(1, null);
            prep.setString(2, clientId1);
            prep.setString(3, clientId2);
            prep.setInt(4, distance);
            prep.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    /*public void getUser() {
        try {
            String sqlQuery = "select * from users";
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                String name = rs1.getString("name");
                String address = rs1.getString("address");
                int age = rs1.getInt("age");
                System.out.println(name + " | " + address + " | " + age);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
    }
    public void getUsersMetadata() {
        ResultSetMetaData rs1MetaData = null;
        String sqlQuery = "select * from users";

        try {
            rs1 = createStatement.executeQuery(sqlQuery);
            rs1MetaData = rs1.getMetaData();
            for (int i = 1; i <= rs1MetaData.getColumnCount(); i++) {
                System.out.print(rs1MetaData.getColumnName(i) + " | ");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
    }*/

    public ArrayList getRoutes(String startDate, String endDate) {      //két dátum közötti utakat adja vissza
        ArrayList<Route> routes = null;

        try {
            String sqlQuery = "select * from routes where date between \"" + startDate + "\" and \"" + endDate + "\" order by date";
            routes = new ArrayList<>();
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                String date = rs1.getString("date");
                String depart = rs1.getString("depart");
                String arrive = rs1.getString("arrive");
                int distance = rs1.getInt("distance");
                String client = rs1.getString("client");
                boolean priv = convertBool(rs1.getInt("private"));
                boolean backandforth = convertBool(rs1.getInt("backandforth"));
                boolean sites = convertBool(rs1.getInt("sites"));

                routes.add(new Route(date, depart, arrive, distance, client, priv, backandforth, sites));
                System.out.println(date);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return routes;
    }

    public int getDistance(String client1, String Client2) {      // a distances listából két ügyfél távolságát adja vissza
        int distance = 0;

        return distance;
    }

    public ArrayList availableDest(String startClient) {// az összes célt amihez megvan a távolság
        ArrayList<Distance> distances = null;
        try {
            String sqlQuery = "select * from distances where distance is not null";
            distances = new ArrayList<>();
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                String clientid1 = rs1.getString("clientid1");
                String clientid2 = rs1.getString("clientid2");
                int distance = rs1.getInt("distance");
                distances.add(new Distance(clientid1, clientid2, distance));
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return distances;
    }

    public void setDistance(String clientid1, String clientid2, int distance) { //Beállítja a távolságot a két meglévő helyszín között
        String sqlQuery = "update distances set distance=" + distance + " where clientid1="
                + clientid1 + " and clientid2=" + clientid2 + " (?,?,?,?)";
        try {
            prep = conn.prepareStatement(sqlQuery);
            prep.setString(1, null);
            prep.setString(2, clientid1);
            prep.setString(3, clientid2);
            prep.setInt(4, distance);
            prep.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    public ArrayList availableClient(String targetClient) {      //visszaadja  az összes lehetséges célt egy városban
        ArrayList<String> clients = null;
        try {
            String sqlQuery = "select clientnumber from clients where city=" + targetClient;
            clients = new ArrayList<>();
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                clients.add(rs1.getString("clientnumber"));
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return clients;
    }



    public ArrayList availableCity(String startClient) {             // az összes várost ahol
        return null;
    }


    // convertBool átalakítás Boolean->int int->Boleean mert az SQLite nem ismeri a Booleant true=1 false=0
    public int convertBool(Boolean value){
        int convertedValue;
        if(value)
            return 1;
        else
            return  0;
    }
    private Boolean convertBool(int value) {
        if (value==1)
            return true;
        else
            return false;
    }

    public Boolean convertBool(String value) {
        if (value.contentEquals("ok"))
            return true;
        else
            return false;
    }
}
//Lekérdezéseket írni a javításokhoz





