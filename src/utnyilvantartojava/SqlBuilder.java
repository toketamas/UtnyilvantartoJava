package utnyilvantartojava;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class SqlBuilder extends SqlCommands {





    public SqlBuilder(String jdbcDriver, String url, String username, String passworld) {
        super(jdbcDriver, url, username, passworld);
    }


    public void insert(List<Object> list, String tableName) {
        String sqlQuery = "INSERT INTO " + tableName + " VALUES (";
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1)
                    sqlQuery = sqlQuery + "?,";
                else
                    sqlQuery = sqlQuery + "?);";
            }
        }
        insertStm(list, sqlQuery, tableName);
    }



    public void update(DoubleList list, String tableName, String condition) {
        System.out.println("listaméret " + list.size());
        List<Object> objList = new ArrayList<>();
        String sqlString = "UPDATE " + tableName + " SET ";
        for (int i = 0; i < list.size(); i++) {

            sqlString += " " + list.get(i).get(0) + " = ?";
            objList.add(list.get(i).get(1));
            if (i < list.size() - 1)
                sqlString += ",";
            else
                sqlString+=" WHERE "+condition;
        }
        System.out.println("ezaz: " + sqlString);
        for (int j=0; j<objList.size(); j++)
            System.out.println(objList.get(j)+" , "+objList.get(j).getClass());
        insertStm(objList,sqlString,tableName);
    }


    //<editor-fold desc="Egyetlen érték lekérdezés">
    public Client getClient(String value) {
        return (Client) query("select * from clients where clientnumber='" + value + "';");
    }

    public Client getSajatClient(String value) {
        return (Client) query("select * from sajat_cimek where clientnumber='" + value + "';");
    }

    public Client getClientFromAddress(String value) {
        return (Client) query("select * from clients where city || ' ' || address='" + value + "';");
    }

    public Client getClientFromClientNumber(String value) {
        return (Client) query("select * from clients where clientnumber='" + value + "';");
    }

    public int getMaxKmFromMonth(String workDate) {
        String sqlQuery = "select max(routeid) and max(date),spedometer from routes where date like '" + workDate + "-%%';";
        return (int) query(sqlQuery);
    }


    // a routes listából a havi összes tankolást adja vissza
    public double getFueling(String workDate, String rendszam) {
        String sqlQuery = "select sum(fueling) from routes where date like '" + workDate + "-%%' and rendszam='" + rendszam + "'; ";
        return (double) query(sqlQuery);
    }
    // a routes listából a havi összes távolságot adja vissza

    public int getSpedometer(String workDate, String rendszam) {
        String sqlQuery = "select sum(distance) from routes where date like '" + workDate + "-%%' and rendszam='" + rendszam + "'; ";
        System.out.println(sqlQuery);
        return (int) query(sqlQuery);
    }
    // a routes listából a havi összes távolságot adja vissza

    public int getMaganut(String workDate, String rendszam) {
        String sqlQuery = "select sum(distance) from routes where client='Magánhasználat' and  date like '" + workDate + "-%%' and rendszam='" + rendszam + "'; ";
        System.out.println(sqlQuery);
        return (int) query(sqlQuery);
    }

    public Distance getDistance(String client1, String client2) {      // a distances listából két ügyfél távolságát adja vissza
        Distance distance = new Distance(client1, client2);
        String sqlQuery = "select distance from distances where clientid1='" + client1 + "' and clientid2='" + client2 + "';";
        return (Distance) query(sqlQuery);
    }

    public Settings getSettings(String settingsId) {
        String sqlQuery = "select * from settings where id='" + settingsId + "'";
        return (Settings) query(sqlQuery);
    }

    public Settings getLastSettings() {
        String sqlQuery = "SELECT MAX(sorszam), * FROM settings WHERE active='true' ;";
        return (Settings) query(sqlQuery);
    }

    public Settings getLastSettingsIfActiveNullAll() {
        String sqlQuery = "SELECT MAX(sorszam), * FROM settings  ;";
        return (Settings) query(sqlQuery);
    }
    public String getDateOfLastRoute() {
        String value = null;
        String sqlQuery = "select * from routes\n"
                + "where routeid = (select max (routeid) from routes);\n"
                + "; ";
        return (String) query(sqlQuery);
    }

    //</editor-fold>

    //<editor-fold desc="Nincs visszaadott érték">

    public void addAllSajatClientToClients() {
        nonQuery("create table clients as select * from sajat_cimek");
    }

    public void delRoute(int routeId) {
        String sqlQuery = "delete from routes where routeid='" + routeId + "';";
        nonQuery(sqlQuery);
    }


    public void delClient(String value, boolean sajatKliens) {

        String sqlQuery;
        if (sajatKliens) {
            sqlQuery = "delete from sajat_cimek where clientnumber='" + value + "'";
        } else {
            sqlQuery = "delete from clients where clientnumber='" + value + "'";
        }
        nonQuery(sqlQuery);

    }

    public void updateDistanceRev(String clientid1, String clientid2, int distance) {
        String sqlQuery = "update distances set "
                + "distance=" + distance + " "
                + "where clientid1 = '" + clientid2 + "' and clientid2='" + clientid1 + "';";
        nonQuery(sqlQuery);
    }

    public void updateDistance(String clientid1, String clientid2, int distance) {
        String sqlQuery = "update distances set "
                + "distance=" + distance + " "
                + "where clientid1 = '" + clientid1 + "' and clientid2='" + clientid2 + "';";
        nonQuery(sqlQuery);
    }
    //</editor-fold>


}