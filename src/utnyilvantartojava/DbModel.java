



//sqLite

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
                        "backandforth integer not null," +
                        "cellid integer not null);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }


        try {
            rs1 = dbmeta.getTables(null, "APP", "CLIENTS", null);
            if (!rs1.next()) {
                createStatement.execute("create table clients(" +
                        "client text not null," +
                        "clientnumber text primary key not null ," +
                        "type text," +
                        "factorynumber text," +
                        "zipcode integer," +
                        "city text not null," +
                        "address text," +
                        "exist integer," +
                        "maintenanceperyear integer," +
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

        try {
            rs1 = dbmeta.getTables(null, "APP", "SETTINGS", null);
            if (!rs1.next()) {
                createStatement.execute("create table settings(" +

                        "nev text," +
                        "varos text," +
                        "cim text," +
                        "auto text," +
                        "rendszam text," +
                        "loketterfogat text," +
                        "fogyasztas text," +
                        "elozo_zaro integer," +
                        "aktualis_honap text primary key not null," +
                        "utolso_ugyfel text," +
                        "lezarva boolean);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }
    }

    //settings táblához tartozó lekérdezések
    public void addSettings(Settings settings) {

        String sqlQuery = "insert into settings values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, settings.getNev());
            preparedStatement.setString(2, settings.getVaros());
            preparedStatement.setString(3, settings.getCim());
            preparedStatement.setString(4, settings.getAuto());
            preparedStatement.setString(5, settings.getRendszam());
            preparedStatement.setString(6, settings.getLoketterfogat());
            preparedStatement.setString(7, settings.getFogyasztas());
            preparedStatement.setInt(8, settings.getElozo_zaro());
            preparedStatement.setString(9, settings.getAktualis_honap());
            preparedStatement.setString(10, settings.getUtolso_ugyfel());
            preparedStatement.setBoolean(11,settings.getLezarva());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    public void updateSettings(Settings settings,String dateValue) {
        String sqlQuery = "update settings set " +
                "nev= '" + settings.getNev() +"',"+
                "varos='" +settings.getVaros()+"',"+
                "cim='" +settings.getCim() +"',"+
                "auto='" +settings.getAuto() +"',"+
                "rendszam='" + settings.getRendszam() +"',"+
                "loketterfogat='" + settings.getLoketterfogat() +"',"+
                "fogyasztas='" + settings.getFogyasztas() +"',"+
                "elozo_zaro=" + settings.getElozo_zaro() +","+
                "utolso_ugyfel ='" + settings.getUtolso_ugyfel() + "',"+
                "lezarva ='" + settings.getLezarva() + "'"+
                " where aktualis_honap = '"+dateValue+"';";

        try {
            System.out.println(sqlQuery);
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Settings getSettings(String month) {
        String sqlQuery = "select * from settings where aktualis_honap='" + month + "'";
        return querySettings(sqlQuery);
    }

    public Settings getMinSpedometer(String rendszam){
        String sqlQuery = "select min(elozo_zaro),* from settings where rendszam='"+rendszam+"' ;";
        return querySettings(sqlQuery);
    }

    public Settings querySettings(String sqlQuery) {
                Settings settings = null;
        try {
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
               settings = new Settings(
                        rs1.getString("nev"),
                        rs1.getString("varos"),
                        rs1.getString("cim"),
                        rs1.getString("auto"),
                        rs1.getString("rendszam"),
                        rs1.getString("loketterfogat"),
                        rs1.getString("fogyasztas"),
                        rs1.getInt("elozo_zaro"),
                        rs1.getString("aktualis_honap"),
                        rs1.getString("utolso_ugyfel"),
                        rs1.getBoolean("lezarva")
                );
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return settings;
    }



    public void addRoute(Route route) {

        String sqlQuery = "insert into Routes values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, route.getDatum());
            preparedStatement.setInt(3, convertBool(route.isMagan()));
            preparedStatement.setString(4, route.getIndulas());
            preparedStatement.setString(5, route.getErkezes());
            preparedStatement.setString(6, route.getUgyfel());
            preparedStatement.setInt(7, route.getSpedometer());
            preparedStatement.setDouble(8, route.getFueling());
            preparedStatement.setInt(9, route.getTavolsag());
            preparedStatement.setInt(10, convertBool(route.isVissza()));
            preparedStatement.setInt(11, route.getCellId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    public ArrayList getRoutes(String workDate) {      //A kiválasztott hónap utjait adja vissza
        ArrayList<Route> routes = null;
        System.out.println(workDate);
        try {
            String sqlQuery = "select * from routes where date like '" + workDate + "-%%' order by date , routeid";

            routes = new ArrayList<>();
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                Integer routeId = rs1.getInt("routeid");
                String date = rs1.getString("date");
                boolean priv = convertBool(rs1.getInt("private"));
                String depart = rs1.getString("depart");
                String arrive = rs1.getString("arrive");
                String client = rs1.getString("client");
                int spedometer = rs1.getInt("spedometer");
                double fueling = rs1.getDouble("fueling");
                int distance = rs1.getInt("distance");
                boolean backandforth = convertBool(rs1.getInt("backandforth"));
                int cellId = rs1.getInt("cellId");


                routes.add(new Route(routeId, date, priv, depart, arrive, client, fueling, spedometer, distance, backandforth, cellId));
                System.out.println(date);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return routes;
    }

    public void delRoute(int routeId) {
        String sqlQuery = "delete from routes where routeid='" + routeId + "';";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getTotalDistanceTravelled(String workDate){
        String sqlQuery = "select sum(distance) from routes where date < '"+workDate+"-01';";
        return querySpedometer(sqlQuery);
    }

    public int getSpedometer(String workDate) {
        String sqlQuery = "select sum(distance) from routes where date like '" + workDate + "-%%'; ";
        return querySpedometer(sqlQuery);
    }

    public int querySpedometer(String sqlQuery) {      // a routes listából a havi összes távolságot adja vissza
        int value = 0;
        try {
            rs1 = createStatement.executeQuery(sqlQuery);
            value = rs1.getInt("sum(distance)");
            System.out.println(value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    public Object getLastRoute(){
        Object value=null ;
        String sqlQuery = "select * from routes\n" +
                "where routeid = (select max (routeid) from routes);\n" +
                "; ";
        try {
            rs1 = createStatement.executeQuery(sqlQuery);
            value = rs1.getString("date");
            System.out.println(value.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    public void updateRoute(Route route, int routeId) {
        String sqlQuery = "update routes set " +
                "date='" + route.getDatum() +"',"+
                "private=" + convertBool(route.isMagan()) +","+
                "depart='" + route.getIndulas() +"',"+
                "arrive='" + route.getErkezes() +"',"+
                "client='" + route.getUgyfel() +"',"+
                "spedometer=" + route.getSpedometer() +","+
                "fueling=" + route.getFueling() +","+
                "distance=" + route.getTavolsag() +","+
                "backandforth=" + convertBool(route.isVissza()) +","+
                "cellid=" + route.getCellId() +" "+
                "where routeid = " + routeId + ";";
        System.out.println(sqlQuery);

        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //distances táblához tartozó lekérdezések
    public void addDistance(String clientId1, String clientId2, int distance) {
        String sqlQuery = "insert into distances values (?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, clientId1);
            preparedStatement.setString(2, clientId2);
            preparedStatement.setInt(3, distance);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült a Distance táblába írni írni");
            System.out.println("" + ex);
        }
    }

    public Distance getDistance(String client1,String client2) {      // a distances listából két ügyfél távolságát adja vissza
        Distance distance=new Distance(client1,client2);
        String sqlQuery = "select distance from distances where clientid1='" + client1 + "' and clientid2='" + client2 + "';";
        try {
            preparedStatement=conn.prepareStatement(sqlQuery);
            rs1=preparedStatement.executeQuery();

            distance.setDistance(rs1.getInt("distance"));

            } catch (SQLException ex) {
            System.out.println("Nem sikerűlt a Distances táblából olvasni!");;
        }

        return distance;
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


    //  client táblához kapcsolódó lekérdezések
    public void addClient(String client, String clientnumber, String type, String factorynumber, int zipcode, String city, String address, Boolean exist, int maintinanceperyear, String field) {
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

    public Client getClient(String value) {
        return queryClient("select * from clients where clientnumber='" + value + "';");
    }


    public Client getClientFromAddress(String value) {
        return queryClient("select * from clients where city || ' ' || address='" + value + "';");
    }

    private Client queryClient(String sqlQuery) {      //visszaad egy ügyfelet
        Client client = null;
        try {
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

    public void updateClient(Client client, String clientNumber) {
        String sqlQuery = "update clients set " +
                "client='" + client.getClient() +"',"+
                //"clientnumber='" + client.getClientNumber() +"',"+
                "type='" + client.getType() + "',"+
                "factorynumber='" + client.getFactoryNumber() +"',"+
                "zipcode=" + client.getZipCode() +","+
                "city='" + client.getCity() +"',"+
                "address='" + client.getAddress() +"',"+
                "exist=" + convertBool(client.getExist()) +","+
                "maintenanceperyear=" + client.getMaintenancePerYear() +","+
                "field='" + client.getField() + "' " +
                "where clientnumber ='"+clientNumber+"';";

        System.out.println(sqlQuery);
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void delClient(String value) {
        String sqlQuery = "delete from clients where clientnumber='" + value + "'";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
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

    // convertBool átalakítás Boolean->int int->Boleean mert az SQLite nem ismeri a Booleant true=1 false=0
    public int convertBool(Boolean value) {
        int convertedValue;
        if (value)
            return 1;
        else
            return 0;
    }

    private Boolean convertBool(int value) {
        if (value == 1)
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





