package utnyilvantartojava;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class SqlCommands extends Connect {

    public SqlCommands(String jdbcDriver, String url, String username, String passworld) {
        super(jdbcDriver, url, username, passworld);
    }

    //Create
    protected void createStm(String tableName, String sqlQuery) {

        try {
            rs = dbmeta.getTables(null, "APP", tableName, null);
            if (!rs.next()) {
                createStatement.execute(sqlQuery);
                System.out.println("A " + tableName + " tábla létrehozva.");
            }
        } catch (SQLException ex) {
            System.out.println("Nem sikerült létrehozni a " + tableName + " táblát!");
            System.out.println("" + ex);
        }
    }



    //Insert
    protected void insertStm(List<Object> list, String sqlQuery, String tableName) {
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            for (int i = 0; i < list.size(); i++) {
                preparedStatement.setObject(i + 1, list.get(i));
                System.out.println((i + 1) + " : " + list.get(i).getClass());
            }
            preparedStatement.execute();

        } catch (SQLException ex) {
            System.err.println("Hiba! Nem sikerült a " + tableName + " táblához adatot hozzáadni");
            System.err.println(sqlQuery);
            System.err.println("" + ex);
        }
    }

// egy adatbázis cella értékét adja vissza megadandó a lekérdezés és az oszlop neve amin végre kell hajtani
    public Object query(String sqlQuery) {
        Object value = null;
        try {
            rs = createStatement.executeQuery(sqlQuery);
            value = rs.getObject(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

 //nem ad vissza értéket
    public void nonQuery(String sqlQuery){
    try {
        preparedStatement = conn.prepareStatement(sqlQuery);
        preparedStatement.execute();
    } catch (SQLException throwables) {
        System.out.println("Nem sikerült a lekérdezés: "+sqlQuery);
        throwables.printStackTrace();
    }}


//Ez egy listát ad vissza a táblából(objektumok listája), ezt használjuk akkor is ha egy sort (objektumot) akarunk lekérdezni
//Az objektumnak kell egy olyan konstruktor ahol egy listábol szedi ki az értékeket
    public List<Object> queryObjectList(String sqlQuery){
        List<Object> resultList=null;
        List<Object> list =new ArrayList<>();
        try {
            resultList = new ArrayList<>();
            rs = createStatement.executeQuery(sqlQuery);
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                    list.add(rs.getObject(i+1));
                   // System.err.println(i+" "+list.get(i));
                }
                resultList.add(list);
            }
            } catch (SQLException ex) {
                System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
                System.out.println("" + ex);
            }
            return resultList;
        }


///////////////////////////////////////////Régi átirandó lekérdezések!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    //<editor-fold desc="Régi át kell írni">




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
    //</editor-fold>


}

