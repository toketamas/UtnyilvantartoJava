package utnyilvantartojava;

import java.sql.*;

    abstract class Connect {


        String jdbcDriver;
        String url;
        String username;
        String passworld;


        /////////////////////////////////////////////////////////////////////////////////////////////////
        Connection conn = null;
        Statement createStatement = null;
        DatabaseMetaData dbmeta = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;

        /////////////////////////////////////////////////////////////////////////////////////////////////
        protected Connect(String jdbcDriver, String url, String username, String passworld) {
            this.jdbcDriver = jdbcDriver;
            this.url = url;
            this.username = username;
            this.passworld = passworld;
            connectDb(jdbcDriver,url,username,passworld);
        }

        private void connectDb(String jdbcDriver, String url, String username, String passworld) {

            try {
                conn = DriverManager.getConnection(url, username, passworld);
                System.out.println("A kapcsolat létrejött az adatbázissal,");
            } catch (SQLException ex) {
                System.out.println("Hiba nem sikerült kapcsolódni az adatbázishoz!");
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

        }


    }

