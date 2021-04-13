package utnyilvantartojava;

import java.sql.SQLException;
import java.util.List;

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
    protected void insertStm(List<Object> list, String sqlQuery,String tableName) {
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            for (int i = 0; i < list.size(); i++) {
                preparedStatement.setObject(i + 1, list.get(i));
            }
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.err.println("Hiba! Nem sikerült a "+tableName+" táblához adatot hozzáadni");
            System.err.println(sqlQuery);
            System.err.println("" + ex);
        }
    }
}
