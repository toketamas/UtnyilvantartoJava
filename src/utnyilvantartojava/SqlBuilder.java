package utnyilvantartojava;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Dictionary;
import java.util.List;

public class SqlBuilder extends SqlCommands {

    public SqlBuilder(String jdbcDriver, String url, String username, String passworld) {
        super(jdbcDriver, url, username, passworld);
    }

    public String sqlStringBuilder(Object list, String queryType) {

        String sqlQuery = "";
        if (String.valueOf(queryType).toUpperCase() == "INSERT") {
            insert((DoubleList) list, sqlQuery);
            System.out.println(sqlQuery);
        } else if (String.valueOf(queryType).toUpperCase() == "UPDATE") {
            sqlQuery = update((DoubleList) list, "settings");

        }
        return sqlQuery;
    }

    private void insert(List<Object> list, String tableName) {
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

    private String update(DoubleList list, String tableName) {
        System.out.println("listaméret " + list.size());
        String sqlString = "UPDATE " + tableName + " set ";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(1).getClass() == String.class)
                sqlString = sqlString + " " + list.get(i).get(0) + "= '" + list.get(i).get(1) + "'";
            else
                sqlString = sqlString + " " + list.get(i).get(0) + "=" + list.get(i).get(1);
            if (i < list.size() - 1)
                sqlString += ",";
        }
        System.out.println("ezaz: " + sqlString);
        return sqlString;
    }
}