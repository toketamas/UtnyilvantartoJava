package utnyilvantartojava;

import java.sql.*;

  public  class RemoteDb {


        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://192.168.1.11/routeregister?serverTimezone=Europe/Budapest";
        final String USERNAME = "admin";//"diebold";
        final String PASSWORD = "gizi666";//"Af6Be5Cd4";


        Connection conn = null;
        Statement createStatement = null;
        DatabaseMetaData dbmeta = null;
        ResultSet rs1 = null;
        PreparedStatement preparedStatement = null;

        public RemoteDb(){
              try {
                    conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
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

        }
        public void addDistance(String clientId1, String clientId2, int distance) {
              String sqlQuery = "insert into distances values (?,?,?)";
              try {
                    preparedStatement = conn.prepareStatement(sqlQuery);
                    preparedStatement.setString(1, clientId1);
                    preparedStatement.setString(2, clientId2);
                    preparedStatement.setInt(3, distance);
                    preparedStatement.execute();
              } catch (SQLException ex) {
                    System.out.println("Hiba! Nem sikerült a Distance táblába írni írni");
                    System.out.println("" + ex);
              }
        }

        public Distance getDistance(String client1,String client2) {      // a distances listából két ügyfél távolságát adja vissza
              Distance distance=new Distance(client1,client2);
              String sqlQuery = "select distance from distances where clientid1='" + client1 + "' and clientid2='" + client2 + "';";
              try {
                    preparedStatement=conn.prepareStatement(sqlQuery);
                    rs1=preparedStatement.executeQuery();

                    distance.setDistance(rs1.getInt("distance"));

              } catch (SQLException ex) {
                    System.out.println("Nem sikerűlt a Distances táblából olvasni!");;
              }

              return distance;
        }

    }


