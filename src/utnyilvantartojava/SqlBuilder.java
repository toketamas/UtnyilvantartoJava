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

    /*public String sqlStringBuilder(Object list, String queryType) {

        String sqlQuery = "";
        if (String.valueOf(queryType).toUpperCase() == "INSERT") {
            insert((DoubleList) list, sqlQuery);
            System.out.println(sqlQuery);
        } else if (String.valueOf(queryType).toUpperCase() == "UPDATE") {
            sqlQuery = update((DoubleList) list, "settings");

        }
        return sqlQuery;
    }*/

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
        String sqlString = "UPDATE " + tableName + " set ";
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
}