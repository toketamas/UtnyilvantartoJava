package utnyilvantarto;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbModel {



    /**
     *
     * @author Tamas
     */


        final String JDBC_DRIVER = "org.apache.jdbc.EmbeddedDriver";
        final String URL = "jdbc:h2:A:\\TT_GIT\\Útnyilvántartó Projekt\\UtnyilvantartoJava\\H2\\RoadRegistry.db";
        final String USERNAME = "sa";
        final String PASSWORD = "";

        //Létrehozzuk a kapcsolatot
        Connection conn = null;
        //Létrehozzuk a teherautót
        Statement createStatement = null;
        //Létrehozzuk a metaadatot
        DatabaseMetaData dbmeta = null;
        //Létrehozunk egy result setet
        ResultSet rs1 = null;
        //Létrehozzuk a biztonságosabb kocsit
        PreparedStatement prep = null;

        public DbModel() {
            //Létrehozzuk a kapcsolatot az adatbázissal
            //megpróbáljuk életre kelteni
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("A híd létrejött");
            } catch (SQLException ex) {
                System.out.println("Valami baj van");
                System.out.println("" + ex);
            }
            //Ha életre kel beizzítjuk teherautót amivel elküldhetjük a lekérdezéseket

            if (conn != null) {
                try {
                    createStatement = conn.createStatement();
                } catch (SQLException ex) {
                    System.out.println("Valami baj van");
                    System.out.println("" + ex);
                }
            }
            //Megnézzük üres e az adatbázis

            try {
                //Lekérjük a metaadatokat
                dbmeta = conn.getMetaData();
            } catch (SQLException ex) {
                System.out.println("Valami baj van");
                System.out.println("" + ex);
            }
            //a ResultSet visszaadja a kért adatokat táblázatos formában

            try {
                rs1 = dbmeta.getTables(null, "APP", "Routes", null);
                //ha nem létezik következő adat(üres az adatbázis)
                if (!rs1.next()) //Akkor létrehozzuk a táblákat
                {
                    createStatement.execute("CREATE TABLE Routes(routeID int," +
                            "date varchar(20)," +
                            "depart varchar(20)," +
                            "arrive varchar(20)," +
                            "distance int," +
                            "client varchar(20)," +
                            "private int," +
                            "backAndForth int, " +
                            "sites int);");
                }
            } catch (SQLException ex) {
                System.out.println("Valami baj van");
                System.out.println("" + ex);
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        /*public void addUser1(String name, String address, int age) {

            try {
                //!!FONTOS a Statementbe nem írunk sql utasítást csak a változót adhatjuk át!!
                String sqlQuery = "insert into users values ('" + name + "','" + address + "'," + age + ")";
                createStatement.execute(sqlQuery);
            } catch (SQLException ex) {
                System.out.println("Valami baj van, nem sikerült az adatbázisba írni");
                System.out.println("" + ex);
            }
        }*/

        public void addRoute(String datum, String indulas, String erkezes, int tavolsag, String ugyfel, int magan, int odaVissza, int telephelyrol) {
            //Biztonságosabb lekérdezés
            //A stringben az sql utasítást írjuk meg a kérdőjelek jelentik az adatokat
            String sqlQuery = "insert into Routes values (?,?,?,?,?,?,?,?,?)";
            try {
                //Átadjuk a stringet
                prep = conn.prepareStatement(sqlQuery);
                //Az első kérdőjel helyére tartozó adat megadása
                prep.setInt(1,0);
                prep.setString(2, datum);
                //A második kérdőjel helyére tartozó adat megadása
                prep.setString(3, indulas);
                //A harmadik kérdőjel helyére tartozó adat megadása
                prep.setString(4, erkezes);
                prep.setInt(5,tavolsag );
                prep.setString(6, ugyfel);
                prep.setInt(7, magan);
                prep.setInt(8, odaVissza);
                prep.setInt(9, telephelyrol);

                prep.execute();
            } catch (SQLException ex) {
                System.out.println("Hiba, nem sikerült az adatbázisba írni");
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
                System.out.println("Valami baj van, nem sikerült az adatbázisból olvasni");
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
                System.out.println("Valami baj van, nem sikerült az adatbázisból olvasni");
                System.out.println("" + ex);
            }
        }

        /*public ArrayList getAllUsers() {

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
                System.out.println("Valami baj van, nem sikerült az adatbázisból olvasni");
                System.out.println("" + ex);
            }
            return users;
        }*/

    ArrayList<String> list = new ArrayList<String>() ;


    public ArrayList<String> readFile(String path) {

        File file = new File(path);
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                list.add(read.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        // list.sort(Comparator.naturalOrder());
        return list;
    }

    }
