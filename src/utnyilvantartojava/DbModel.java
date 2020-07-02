package utnyilvantartojava;

import java.sql.*;
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
    PreparedStatement preparedStatement = null;

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
                        "routeid integer primary key autoincrement not null," +
                        "date text not null," +
                        "private integer," +
                        "depart text not null," +
                        "arrive text not null," +
                        "client text not null," +
                        "spedometer integer not null," +
                        "fueling double not null," +
                        "distance integer not null," +
                        "backandforth intege not null);");
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
                        "clientid1 text," +
                        "clientid2 text," +
                        "distance integer);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

    }

    // hozzáad egy új utat a routes táblához
    public void addRoute(String datum, Boolean magan, String indulas, String erkezes,String ugyfel,int spedometer,double fuelig, int tavolsag, boolean odaVissza) {
        String sqlQuery = "insert into Routes values (?,?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1,null);
            preparedStatement.setString(2, datum.toString());
            preparedStatement.setInt(3, convertBool(magan));
            preparedStatement.setString(4, indulas);
            preparedStatement.setString(5,erkezes);
            preparedStatement.setString(6, ugyfel);
            preparedStatement.setInt(7, spedometer);
            preparedStatement.setDouble(8,fuelig );
            preparedStatement.setInt(9, tavolsag);
            preparedStatement.setInt(10,convertBool(odaVissza));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    public ArrayList getRoutes(String workDate) {      //két dátum közötti utakat adja vissza
        ArrayList<Route> routes = null;
        System.out.println(workDate);
        try {
            String sqlQuery = "select * from routes where date like " + workDate + " order by spedometer";

            routes = new ArrayList<>();
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                String date = rs1.getString("date");
                boolean priv = convertBool(rs1.getInt("private"));
                String depart = rs1.getString("depart");
                String arrive = rs1.getString("arrive");
                String client = rs1.getString("client");
                int spedometer=rs1.getInt("spedometer");
                double fueling=rs1.getDouble("fueling");
                int distance = rs1.getInt("distance");
                boolean backandforth = convertBool(rs1.getInt("backandforth"));


                routes.add(new Route(date,priv, depart, arrive,client,fueling,spedometer, distance, backandforth));
                System.out.println(date);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return routes;
    }
    public int getSpedometer(String workDate) {      // a routes listából a havi összes távolságot adja vissza
        int value = 0;
        String sqlQuery = "select sum(distance) from routes where date like '"+workDate+"-%%' " ;
        System.out.println(sqlQuery);
        try {
            rs1=createStatement.executeQuery(sqlQuery);
        value=rs1.getInt("sum(distance)");
            System.out.println(value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }


    //hozzáad két ügyfél közti távolságot a distances táblához
    public void addDistance(String clientId1, String clientId2, int distance) {
        String sqlQuery = "insert into distances values (?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, clientId1);
            preparedStatement.setString(2, clientId2);
            preparedStatement.setInt(3, distance);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }
    public int getDistance(String client1, String client2) {      // a distances listából két ügyfél távolságát adja vissza
        int distance = 0;
        String sqlQuery = "select distance from distances where clientid1='"+client1+"' and clientid2='"+client2+"'";
        try {
            rs1=createStatement.executeQuery(sqlQuery);
            distance=rs1.getInt("distance");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        String sqlQuery = "insert into distance values (?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, clientid1);
            preparedStatement.setString(2, clientid2);
            preparedStatement.setInt(3, distance);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }



    public ArrayList getAllCitys() {      //visszaadja  az összes gépszámot
        ArrayList<String> clients = null;
        try {
            String sqlQuery = "select distinct city from clients";
            clients = new ArrayList<>();
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                clients.add(rs1.getString("city"));
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return clients;
    }

    // hozzáad egy új ügyfelet(gépet) a client táblához
    public void addClient(String client, String clientnumber,String type, String factorynumber, int zipcode,  String city,  String address, Boolean exist,int maintinanceperyear, String field) {
        String sqlQuery = "insert into clients values (?,?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, client);
            preparedStatement.setString(2, clientnumber);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, factorynumber);
            preparedStatement.setInt(5, zipcode);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, address);
            preparedStatement.setInt(8, convertBool(exist));
            preparedStatement.setInt(9, maintinanceperyear);
            preparedStatement.setString(10, field);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    public Client getClient(String value) {      //visszaad egy ügyfelet
        Client client = null;
        try {
            String sqlQuery = "select * from clients where clientnumber='"+value+"'";
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                 client = new Client(
                        rs1.getString("client"),
                        rs1.getString("clientnumber"),
                        rs1.getString("type"),
                        rs1.getString("factorynumber"),
                        rs1.getInt("zipcode"),
                        rs1.getString("city"),
                        rs1.getString("address"),
                        convertBool(rs1.getInt("exist")),
                        rs1.getInt("maintenanceperyear"),
                        rs1.getString("field")
                        );
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return client;
    }

    public void delClient(String value){
        String sqlQuery = "delete from clients where clientnumber='"+value+"'";
        try {
            preparedStatement=conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public ArrayList getAllClient() {      //visszaadja  az összes gépszámot
        ArrayList<String> clients = null;
        try {
            String sqlQuery = "select clientnumber from clients";
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

    public ArrayList getAvailableClient(String targetClient) {      //visszaadja  az összes lehetséges célt egy városban
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



    public ArrayList getAvailableCity(String startClient) {             // az összes várost ahol
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





