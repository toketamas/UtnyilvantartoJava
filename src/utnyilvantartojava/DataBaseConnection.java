//sqLite
package utnyilvantartojava;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {

    final String JDBC_DRIVER = Constants.SqliteDataBase.JDBC_DRIVER;
    final String URL = Constants.SqliteDataBase.URL;
    final String USERNAME = Constants.SqliteDataBase.USERNAME;
    final String PASSWORD = Constants.SqliteDataBase.PASSWORD;


    /////////////////////////////////////////////////////////////////////////////////////////////////
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmeta = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    /*Connection conn1 = null;
    Statement createStatement1 = null;
    DatabaseMetaData dbmeta1 = null;
    ResultSet rs1 = null;
    PreparedStatement preparedStatement1 = null;

     */

    /////////////////////////////////////////////////////////////////////////////////////////////////
    public DataBaseConnection() {

        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A kapcsolat létrejött az sqlite adatbázissal,");
        } catch (SQLException ex) {
            System.out.println("Hiba nem sikerült kapcsolódni az sqlite adatbázishoz!");
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

        //////////////////////////////////////////////////////////////////////////////////////////////


        //sajat_cimek tábla létrehozása
        try {
            rs = dbmeta.getTables(null, "APP", "SAJAT_CIMEK", null);
            if (!rs.next()) {
                createStatement.execute(
                        "CREATE TABLE sajat_cimek("
                                + "client text,"
                                + "clientnumber text primary key,"
                                + "type text,factorynumber text,"
                                + "zipcode integer,"
                                + "city text,"
                                + "address text,"
                                + "exist integer,"
                                + "maintenanceperyear integer,"
                                + "field text);"
                );
                System.out.println("A sajat_cimek tábla létrehozva.");
            }
        } catch (SQLException ex) {
            System.out.println("Nem sikerült létrehozni a sajat_cimek táblát!");
            System.out.println("" + ex);
        }
    }

    public void renColToActive() {

        try {
            rs = dbmeta.getColumns(null, null, "settings", null);
            while (rs.next()) {
                if (rs.getString("COLUMN_NAME").startsWith("lezart_tabla")) {
                    System.out.println(rs.getString("COLUMN_NAME"));
                    String sqlQuery = "ALTER TABLE settings RENAME COLUMN 'lezart_tabla' TO 'active';";
                    preparedStatement = conn.prepareStatement(sqlQuery);
                    preparedStatement.execute();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    //SqlHelper sqlHelper=new SqlHelper();
    //settings táblához tartozó lekérdezések
    public void addSettings(Settings settings) {
        //Itt valami string buildert kellene használni a lekérdezés összeállításához!
        String sqlQuery = "insert into settings values (";
        insertUpdate(settings.list(), sqlQuery);
    }



    public void insertUpdate(List<Object> list, String sqlQuery) {
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1)
                    sqlQuery = sqlQuery + "?,";
                else
                    sqlQuery = sqlQuery + "?);";
            }
        }
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            for (int i = 0; i < list.size(); i++) {
                preparedStatement.setObject(i + 1, list.get(i));
            }
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült a táblához adatot hozzáadni");
            System.out.println("" + ex);
        }
    }

   public void updateSettings(Settings settings, String idValue) {
        System.out.println("update: " + settings.getAktualis_honap());
        String sqlQuery = "update settings set "
                + "nev= '" + settings.getNev() + "',"
                + "varos='" + settings.getVaros() + "',"
                + "cim='" + settings.getCim() + "',"
                + "auto='" + settings.getAuto() + "',"
                + "rendszam='" + settings.getRendszam() + "',"
                + "loketterfogat='" + settings.getLoketterfogat() + "',"
                + "fogyasztas='" + settings.getFogyasztas() + "',"
                + "elozo_zaro=" + settings.getElozo_zaro() + ","
                + "aktualis_honap='" + settings.getAktualis_honap() + "',"
                + "utolso_ugyfel ='" + settings.getUtolso_ugyfel() + "',"
                + "zaro_km ='" + settings.getZaroKm() + "',"
                + "utolso_szerkesztes='" + LocalDateTime.now().toString() + "',"
                + "active='" + settings.getActive() + "'"
                + " where id = '" + idValue + "';";
        System.out.println(sqlQuery);
        try {
            // System.out.println(sqlQuery);
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public Settings getSettings(String settingsId) {
        String sqlQuery = "select * from settings where id='" + settingsId + "'";
        return querySettings(sqlQuery);
    }

    public Settings getLastSettings() {
        String sqlQuery = "SELECT MAX(sorszam), * FROM settings WHERE active='true' ;";
        return querySettings(sqlQuery);
    }

    public Settings getLastSettingsIfActiveNullAll() {
        String sqlQuery = "SELECT MAX(sorszam), * FROM settings  ;";
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
                        rs.getInt("zaro_km"),
                        rs.getString("id"),
                        rs.getInt("sorszam"),
                        rs.getString("utolso_szerkesztes"),
                        rs.getBoolean("active")
                );
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return settings;
    }

    // routes táblához tartozó lekérdezések
    public void addRoute(Route route, String rendszam) {

        String sqlQuery = "insert into Routes values (?,?,?,?,?,?,?,?,?,?,?,?)";
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
            preparedStatement.setString(12, rendszam);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült a routes táblába írni");
            System.out.println("" + ex);
        }
    }

    public ArrayList getRoutes(String workDate, String rendszam) {      //A kiválasztott hónap utjait adja vissza
        ArrayList<Route> routes = null;
        // System.out.println(workDate);
        try {
            String sqlQuery = "select * from routes where date like '" + workDate + "-%%'  and rendszam='" + rendszam + "' order by date;";

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
                // System.out.println(date);
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

    // a routes listából a havi összes tankolást adja vissza
    public double getFueling(String workDate, String rendszam) {
        String sqlQuery = "select sum(fueling) from routes where date like '" + workDate + "-%%' and rendszam='" + rendszam + "'; ";
        return queryDoubleValueFromRoute(sqlQuery, "sum(fueling)");
    }
    // a routes listából a havi összes távolságot adja vissza

    public int getSpedometer(String workDate, String rendszam) {
        String sqlQuery = "select sum(distance) from routes where date like '" + workDate + "-%%' and rendszam='" + rendszam + "'; ";
        System.out.println(sqlQuery);
        return queryIntValueFromRoute(sqlQuery, "sum(distance)");
    }
    // a routes listából a havi összes távolságot adja vissza

    public int getMaganut(String workDate, String rendszam) {
        String sqlQuery = "select sum(distance) from routes where client='Magánhasználat' and  date like '" + workDate + "-%%' and rendszam='" + rendszam + "'; ";
        System.out.println(sqlQuery);
        return queryIntValueFromRoute(sqlQuery, "sum(distance)");
    }
// egy int értékkel tér vissza megadandó a lekérdezés és az oszlop neve amin végre kell hajtani

    public int queryIntValueFromRoute(String sqlQuery, String returnColumn) {
        int value = 0;
        try {
            rs = createStatement.executeQuery(sqlQuery);
            value = rs.getInt(returnColumn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    // egy double értéket ad vissza megadandó a lekérdezés és az oszlop neve amin végre kell hajtani
    public double queryDoubleValueFromRoute(String sqlQuery, String returnColumn) {
        double value = 0;
        try {
            rs = createStatement.executeQuery(sqlQuery);
            value = rs.getDouble(returnColumn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }
// a legutolsó dátumot adja vissza

    public String getDateOfLastRoute() {
        String value = null;
        String sqlQuery = "select * from routes\n"
                + "where routeid = (select max (routeid) from routes);\n"
                + "; ";
        try {
            rs = createStatement.executeQuery(sqlQuery);
            value = rs.getString("date");
            //System.out.println(value.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

    //     
    public int getMaxKmFromMonth(String workDate) {
        String sqlQuery = "select max(routeid) and max(date),spedometer from routes where date like '" + workDate + "-%%';";
        return queryIntValueFromRoute(sqlQuery, "spedometer");
    }

    /* Az összes tankolás az adott hónapban
     public double getFuelingMonth(String workDate){
        String sqlQuery = "SELECT sum(fueling) as value FROM routes where date like '"+workDate+"-%%';";
        return queryDoubleValueFromRoute(sqlQuery,"value");
    }*/
    public void updateRoute(Route route, int routeId) {
        String sqlQuery = "update routes set "
                + "date='" + route.getDatum() + "',"
                + "private=" + convertBool(route.isMagan()) + ","
                + "depart='" + route.getIndulas() + "',"
                + "arrive='" + route.getErkezes() + "',"
                + "client='" + route.getUgyfel() + "',"
                + "spedometer=" + route.getSpedometer() + ","
                + "fueling=" + route.getFueling() + ","
                + "distance=" + route.getTavolsag() + ","
                + "backandforth=" + convertBool(route.isVissza()) + ","
                + "cellid=" + route.getCellId() + " "
                + "where routeid = " + routeId + ";";
        //System.out.println(sqlQuery);

        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    SqlHelper sqlHelper=new SqlHelper();
    //distances táblához tartozó lekérdezések
    public void addDistance(Distance distance){
        sqlHelper.sqlStringBuilder(distance.list(),Constants.SqlQuery.INSERT);
    }

    public Distance getDistance(String client1, String client2) {      // a distances listából két ügyfél távolságát adja vissza
        Distance distance = new Distance(client1, client2);
        String sqlQuery = "select distance from distances where clientid1='" + client1 + "' and clientid2='" + client2 + "';";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            rs = preparedStatement.executeQuery();

            distance.setDistance(rs.getInt("distance"));

        } catch (SQLException ex) {
            System.out.println("Nem sikerűlt a Distances táblából olvasni!");

        }

        return distance;
    }

    public void updateDistanceRev(String clientid1, String clientid2, int distance) {
        String sqlQuery = "update distances set "
                + "distance=" + distance + " "
                + "where clientid1 = '" + clientid2 + "' and clientid2='" + clientid1 + "';";
        updateDist(sqlQuery);
    }

    public void updateDistance(String clientid1, String clientid2, int distance) {
        String sqlQuery = "update distances set "
                + "distance=" + distance + " "
                + "where clientid1 = '" + clientid1 + "' and clientid2='" + clientid2 + "';";
        updateDist(sqlQuery);
    }

    private void updateDist(String sqlQuery) {


        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerűlt a distance táblát frissíteni");
            System.out.println("" + ex);
        }
    }

    //  client táblához kapcsolódó lekérdezések
    //ha a sajatKliens=true a sajat_cimek táblába teszi ha fals akkor a clients táblába
    public void addClient(Client client, boolean sajatKliens) {
        String sqlQuery;
        if (sajatKliens) {
            sqlQuery = "insert into sajat_cimek values (";
        } else {
            sqlQuery = "insert into clients values (";
        }
        insertUpdate(client.list(),sqlQuery);
    }

    public void addAllSajatClientToClients() {
        queryClient("create table clients as select * from sajat_cimek");
    }

    public Client getClient(String value) {
        return queryClient("select * from clients where clientnumber='" + value + "';");
    }

    public Client getSajatClient(String value) {
        return queryClient("select * from sajat_cimek where clientnumber='" + value + "';");
    }

    public Client getClientFromAddress(String value) {
        return queryClient("select * from clients where city || ' ' || address='" + value + "';");
    }

    public Client getClientFromClientNumber(String value) {
        return queryClient("select * from clients where clientnumber='" + value + "';");
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
        System.out.println("client:" + client);
        System.out.println("cn:" + clientNumber);
        String sqlQuery = "update clients set "
                + "client='" + client.getClient() + "',"
                + //"clientnumber='" + client.getClientNumber() +"',"+
                "type='" + client.getType() + "',"
                + "factorynumber='" + client.getFactoryNumber() + "',"
                + "zipcode=" + client.getZipCode() + ","
                + "city='" + client.getCity() + "',"
                + "address='" + client.getAddress() + "',"
                + "exist=" + convertBool(client.getExist()) + ","
                + "maintenanceperyear=" + client.getMaintenancePerYear() + ","
                + "field='" + client.getField() + "' "
                + "where clientnumber ='" + clientNumber + "';";

        System.out.println(sqlQuery);
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delClient(String value, boolean sajatKliens) {

        String sqlQuery;
        if (sajatKliens) {
            sqlQuery = "delete from sajat_cimek where clientnumber='" + value + "'";
        } else {
            sqlQuery = "delete from clients where clientnumber='" + value + "'";
        }
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList getAllClient(boolean sajatKliens) {      //visszaadja  az összes gépszámot
        ArrayList<String> clients = null;
        String sqlQuery;
        if (sajatKliens) {
            sqlQuery = "select clientnumber from sajat_cimek";
        } else {
            sqlQuery = "select clientnumber from clients";
        }
        try {
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

    // convertBool átalakítás Boolean->int int->Boleean mert az SQLite nem ismeri a Booleant true=1 false=0
    public int convertBool(Boolean value) {
        int convertedValue;
        if (value) {
            return 1;
        } else {
            return 0;
        }
    }

    public Boolean convertBool(int value) {
        if (value == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean convertBool(String value) {
        if (value.contentEquals("ok")) {
            return true;
        } else {
            return false;
        }
    }
}
