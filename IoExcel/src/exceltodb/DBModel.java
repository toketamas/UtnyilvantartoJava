package exceltodb;

import java.sql.*;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

    public class DBModel {

        final String JDBC_DRIVER = "org.sqlite.JDBC";
        final String URL = "jdbc:sqlite:routeregister.db";
        final String USERNAME = "";
        final String PASSWORD = "";


        Connection conn = null;
        Statement createStatement = null;
        DatabaseMetaData dbmeta = null;
        ResultSet rs1 = null;
        PreparedStatement prep = null;

        public DBModel() {
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
                rs1 = dbmeta.getTables(null, "APP", "ROUTEREGISTER", null);
                if (!rs1.next()) {
                    createStatement.execute("create table clients(" +
                            "client text not null,"+
                            "clientnumber text primary key not null ," +
                            "type text,"+
                            "factorynumber text,"+
                            "zipcode integer,"+
                            "city text not null," +
                            "address text," +
                            "exist integer,"+
                            "maintenanceperyear integer,"+
                            "field text);");
                }
            } catch (SQLException ex) {
                System.out.println("Hiba!");
                System.out.println("" + ex);
            }



        }





        // hozzáad egy új ügyfelet(gépet) a client táblához
        public void addClient(String client, String clientnumber,String type, String factorynumber, int zipcode,  String city,  String address, Boolean exist,int maintinanceperyear, String field) {
            String sqlQuery = "insert into clients values (?,?,?,?,?,?,?,?,?,?)";
            try {
                prep = conn.prepareStatement(sqlQuery);
                prep.setString(1, client);
                prep.setString(2, clientnumber);
                prep.setString(3, type);
                prep.setString(4, factorynumber);
                prep.setInt(5, zipcode);
                prep.setString(6, city);
                prep.setString(7, address);
                prep.setInt(8, convertBool(exist));
                prep.setInt(9, maintinanceperyear);
                prep.setString(10, field);
                prep.execute();
            } catch (SQLException ex) {
                System.out.println("Hiba! Nem sikerült az adatbázisba írni");
                System.out.println("" + ex);
            }
        }












        // convertBool átalakítás Boolean->int int->Boleean mert az SQLite nem ismeri a Booleant true=1 false=0
        public int convertBool(Boolean value){
            int convertedValue;
            if(value)
                return 1;
            else
                return  0;
        }
        private Boolean convertBool(int value) {
            if (value==1)
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
//Lekérdezéseket írni a javításokhoz


