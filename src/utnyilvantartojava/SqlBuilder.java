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


    public int getMaxKmFromMonth(String workDate) {
        String sqlQuery = "select max(routeid) and max(date),spedometer from routes where date like '" + workDate + "-%%';";
        return queryIntValueFromRoute(sqlQuery, "spedometer");
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


}