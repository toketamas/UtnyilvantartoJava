



//sqLite

package utnyilvantartojava;

import java.sql.*;
import java.util.ArrayList;

public class DbModel {

    final String JDBC_DRIVER = "org.sqlite.JDBC";
    final String URL = "jdbc:sqlite:routeregister.db";
    final String USERNAME = "";
    final String PASSWORD = "";

    final String JDBC_DRIVER_MYSQL = "com.mysql.jdbc.Driver";
    final String URLMYSQL2 = "jdbc:mysql://192.168.1.11:3306/routeregister?serverTimezone=Europe/Budapest&useUnicode=true&characterEncoding=UTF-8";
    final String URLMYSQL = "jdbc:mysql://b4a00aba1329.sn.mynetname.net:3306/routeregister?serverTimezone=Europe/Budapest&useUnicode=true&characterEncoding=UTF-8";
    final String USERNAME_MYSQL = "diebold";
    final String PASSWORD_MYSQL = "8V2lrQnq()Tb";


    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmeta = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    Connection conn1 = null;
    Statement createStatement1 = null;
    DatabaseMetaData dbmeta1 = null;
    ResultSet rs1 = null;
    PreparedStatement preparedStatement1 = null;

    public DbModel() {

      /////////////////////////////////////////////////////////////////////////////////////
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A kapcsolat létrejött az sqlite adatbázissal,");
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

     ///////////////////////////////////////////////////////////////////////////////////////

        try {
            conn1 = DriverManager.getConnection(URLMYSQL, USERNAME_MYSQL, PASSWORD_MYSQL);
            System.out.println("A kapcsolat létrejött a távoli mysql adatbázissal,");

        }catch (Exception e) {
            System.out.println("Hiba! A távoli mysql server nem érhető el!");
            System.out.println("" + e);
            try {
                conn1 = DriverManager.getConnection(URLMYSQL2, USERNAME_MYSQL, PASSWORD_MYSQL);
                System.out.println("A kapcsolat létrejött a helyi mysql adatbázissal,");

            }catch (Exception ex) {
                System.out.println("Hiba! A helyi mysql server nem érhető el!");
                System.out.println("" + ex);
            }
        }

        if (conn1 != null) {
            try {
                createStatement1 = conn1.createStatement();
            } catch (SQLException ex) {
                System.out.println("Hiba!");
                System.out.println("" + ex);
            }
        }
        if (conn1 != null) {
            try {
                dbmeta1 = conn1.getMetaData();
            } catch (SQLException ex) {
                System.out.println("Hiba!");
                System.out.println("" + ex);
            }
        }

        try {
            rs = dbmeta.getTables(null, "APP", "ROUTES", null);
            if (!rs.next()) {
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
            rs = dbmeta.getTables(null, "APP", "CLIENTS", null);
            if (!rs.next()) {
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
            rs = dbmeta.getTables(null, "APP", "DISTANCES", null);
            if (!rs.next()) {
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
            rs = dbmeta.getTables(null, "APP", "SETTINGS", null);
            if (!rs.next()) {
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
                        "lezarva integer);");
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
            preparedStatement.setInt(11,settings.getLezarva());
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
            rs = createStatement.executeQuery(sqlQuery);
            while (rs.next()) {
               settings = new Settings(
                        rs.getString("nev"),
                        rs.getString("varos"),
                        rs.getString("cim"),
                        rs.getString("auto"),
                        rs.getString("rendszam"),
                        rs.getString("loketterfogat"),
                        rs.getString("fogyasztas"),
                        rs.getInt("elozo_zaro"),
                        rs.getString("aktualis_honap"),
                        rs.getString("utolso_ugyfel"),
                        rs.getInt("lezarva")
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
            rs = createStatement.executeQuery(sqlQuery);
            while (rs.next()) {
                Integer routeId = rs.getInt("routeid");
                String date = rs.getString("date");
                boolean priv = convertBool(rs.getInt("private"));
                String depart = rs.getString("depart");
                String arrive = rs.getString("arrive");
                String client = rs.getString("client");
                int spedometer = rs.getInt("spedometer");
                double fueling = rs.getDouble("fueling");
                int distance = rs.getInt("distance");
                boolean backandforth = convertBool(rs.getInt("backandforth"));
                int cellId = rs.getInt("cellId");


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
            rs = createStatement.executeQuery(sqlQuery);
            value = rs.getInt("sum(distance)");
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
            rs = createStatement.executeQuery(sqlQuery);
            value = rs.getString("date");
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
            System.out.println(sqlQuery);
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
            rs =preparedStatement.executeQuery();

            distance.setDistance(rs.getInt("distance"));

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
            rs = createStatement.executeQuery(sqlQuery);
            while (rs.next()) {
                client = new Client(
                        rs.getString("client"),
                        rs.getString("clientnumber"),
                        rs.getString("type"),
                        rs.getString("factorynumber"),
                        rs.getInt("zipcode"),
                        rs.getString("city"),
                        rs.getString("address"),
                        convertBool(rs.getInt("exist")),
                        rs.getInt("maintenanceperyear"),
                        rs.getString("field")
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
            rs = createStatement.executeQuery(sqlQuery);
            while (rs.next()) {
                clients.add(rs.getString("clientnumber"));
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
            rs = createStatement.executeQuery(sqlQuery);
            while (rs.next()) {
                clients.add(rs.getString("city"));
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return clients;
    }

    public void addDistanceToMySql(String clientId1, String clientId2, int distance) {
        String sqlQuery = "insert into distances  values (?,?,?)";
        try {
            preparedStatement1 = conn1.prepareStatement(sqlQuery);
            preparedStatement1.setString(1, clientId1);
            preparedStatement1.setString(2, clientId2);
            preparedStatement1.setInt(3, distance);
            preparedStatement1.execute();
            System.out.println(sqlQuery);
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült a mysql distances táblába írni írni");
            System.out.println("" + ex);
        }
    }

    public Distance getDistanceFromMySql(String client1,String client2) {      // a distances listából két ügyfél távolságát adja vissza
        Distance distance=new Distance(client1,client2);
        String sqlQuery = "select distance from distances where clientid1='" + client1 + "' and clientid2='" + client2 + "';";
        try {
            preparedStatement1=conn1.prepareStatement(sqlQuery);
            rs1 =preparedStatement1.executeQuery();

            distance.setDistance(rs1.getInt("distance"));

        } catch (SQLException ex) {
            System.out.println("Nem sikerűlt a mysql distances táblából olvasni!");;
        }

        return distance;
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





