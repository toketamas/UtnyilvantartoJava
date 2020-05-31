package utnyilvantarto;

import java.sql.*;

public class DbModel {

    final String JDBC_DRIVER = "org.sqlite.JDBC";
    final String URL = "jdbc:sqlite:routeregister.db";
    final String USERNAME = "";
    final String PASSWORD = "";


    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmeta = null;
    ResultSet rs1 = null;
    PreparedStatement prep = null;

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
            if (!rs1.next())             {
                createStatement.execute("create table routes(" +
                        "routeid integer primary key autoincrement," +
                        "date text," +
                        "depart text," +
                        "arrive text," +
                        "distance integer," +
                        "client text," +
                        "private integer," +
                        "backandforth integer," +
                        "sites integer);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

        try {
            rs1 = dbmeta.getTables(null, "APP", "", null);
            if (!rs1.next())             {
                createStatement.execute("create table routes(" +
                        "routeid integer primary key autoincrement," +
                        "date text," +
                        "depart text," +
                        "arrive text," +
                        "distance integer," +
                        "client text," +
                        "private integer," +
                        "backandforth integer," +
                        "sites integer);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }

        try {
            rs1 = dbmeta.getTables(null, "APP", "ROUTES", null);
            if (!rs1.next())             {
                createStatement.execute("create table routes(" +
                        "routeid integer primary key autoincrement," +
                        "date text," +
                        "depart text," +
                        "arrive text," +
                        "distance integer," +
                        "client text," +
                        "private integer," +
                        "backandforth integer," +
                        "sites integer);");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba!");
            System.out.println("" + ex);
        }
    }

    public void addUser1(String name, String address, int age) {

        try {
            String sqlQuery = "insert into users values ('" + name + "','" + address + "'," + age + ")";
            createStatement.execute(sqlQuery);
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    public void addRoute(String datum, String indulas, String erkezes, int tavolsag, String ugyfel, int magan, int odaVissza, int telephelyrol) {
        String sqlQuery = "insert into Routes values (?,?,?,?,?,?,?,?,?)";
        try {
            prep = conn.prepareStatement(sqlQuery);
            prep.setString(1,null);
            prep.setString(2, datum);
            prep.setString(3, indulas);
            prep.setString(4, erkezes);
            prep.setInt(5,tavolsag );
            prep.setString(6, ugyfel);
            prep.setInt(7, magan);
            prep.setInt(8, odaVissza);
            prep.setInt(9, telephelyrol);
            prep.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisba írni");
            System.out.println("" + ex);
        }
    }

    public void getUser() {
        try {
            String sqlQuery = "select * from users";
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                String name = rs1.getString("name");
                String address = rs1.getString("address");
                int age = rs1.getInt("age");
                System.out.println(name + " | " + address + " | " + age);
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
    }
    public void getUsersMetadata() {
        ResultSetMetaData rs1MetaData = null;
        String sqlQuery = "select * from users";

        try {
            rs1 = createStatement.executeQuery(sqlQuery);
            rs1MetaData = rs1.getMetaData();
            for (int i = 1; i <= rs1MetaData.getColumnCount(); i++) {
                System.out.print(rs1MetaData.getColumnName(i) + " | ");
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
    }

   /* public ArrayList getAllUsers() {
        ArrayList<User> users=null;
        try {
            String sqlQuery = "select * from users";
            users = new ArrayList<>();
            rs1 = createStatement.executeQuery(sqlQuery);
            while (rs1.next()) {
                String name = rs1.getString("name");
                String address = rs1.getString("address");
                int age = rs1.getInt("age");
                users.add(new User(name, address, age));
            }
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
            System.out.println("" + ex);
        }
        return users;
    }*/



    }
