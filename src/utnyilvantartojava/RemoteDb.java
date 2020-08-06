/*package utnyilvantartojava;

import java.sql.*;
import java.util.ArrayList;

public class RemoteDb {

      final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      final String URL = "jdbc:mysql://192.168.1.11:3306/routeregister?serverTimezone=Europe/Budapest";
      final String USERNAME = "diebold";
      final String PASSWORD = "Af6Be5Cd4";


      Connection conn = null;
      Statement createStatement = null;
      DatabaseMetaData dbmeta = null;
      ResultSet rs1 = null;
      PreparedStatement preparedStatement = null;

      public RemoteDb() {
            try {
                  conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                  System.out.println("A kapcsolat létrejött a mysql adatbázissal,");
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


      //distances táblához tartozó lekérdezések
      public void addDistance(String clientId1, String clientId2, int distance) {
            String sqlQuery = "insert into distances values (?,?,?)";
            try {
                  preparedStatement = conn.prepareStatement(sqlQuery);
                  preparedStatement.setString(1, clientId1);
                  preparedStatement.setString(2, clientId2);
                  preparedStatement.setInt(3, distance);
                  preparedStatement.execute();
                  System.out.println(sqlQuery);
            } catch (SQLException ex) {
                  System.out.println("Hiba! Nem sikerült a mysql distance táblába írni írni");
                  System.out.println("" + ex);
            }
      }

      public Distance getDistance(String client1,String client2) {      // a distances listából két ügyfél távolságát adja vissza
            Distance distance=new Distance(client1,client2);
            String sqlQuery = "select distance from distances where clientid1='" + client1 + "' and clientid2='" + client2 + "';";
            System.out.println(sqlQuery);
            try {
                  preparedStatement=conn.prepareStatement(sqlQuery);
                  rs1=preparedStatement.executeQuery();

                  distance.setDistance(rs1.getInt("distance"));

            } catch (SQLException ex) {
                  System.out.println("Nem sikerűlt a mysql distances táblából olvasni!");;
            }

            return distance;
      }

      public void setDistance(String clientid1, String clientid2, int distance) { //Beállítja a távolságot a két meglévő helyszín között
            String sqlQuery = "insert into distance values (?,?,?)";
            try {
                  preparedStatement = conn.prepareStatement(sqlQuery);
                  preparedStatement.setString(1, clientid1);
                  preparedStatement.setString(2, clientid2);
                  preparedStatement.setInt(3, distance);
                  preparedStatement.execute();
            } catch (SQLException ex) {
                  System.out.println("Hiba! Nem sikerült a mysql adatbázisba írni");
                  System.out.println("" + ex);
            }
      }


      //  client táblához kapcsolódó lekérdezések
      public void addClient(String client, String clientnumber, String type, String factorynumber, int zipcode, String city, String address, Boolean exist, int maintinanceperyear, String field) {
            String sqlQuery = "insert into clients values (?,?,?,?,?,?,?,?,?,?)";
            try {
                  preparedStatement = conn.prepareStatement(sqlQuery);
                  preparedStatement.setString(1, client);
                  preparedStatement.setString(2, clientnumber);
                  preparedStatement.setString(3, type);
                  preparedStatement.setString(4, factorynumber);
                  preparedStatement.setInt(5, zipcode);
                  preparedStatement.setString(6, city);
                  preparedStatement.setString(7, address);
                  preparedStatement.setInt(8, convertBool(exist));
                  preparedStatement.setInt(9, maintinanceperyear);
                  preparedStatement.setString(10, field);
                  preparedStatement.execute();
            } catch (SQLException ex) {
                  System.out.println("Hiba! Nem sikerült az adatbázisba írni");
                  System.out.println("" + ex);
            }
      }

      public Client getClient(String value) {
            return queryClient("select * from clients where clientnumber='" + value + "';");
      }


      public Client getClientFromAddress(String value) {
            return queryClient("select * from clients where city || ' ' || address='" + value + "';");
      }

      private Client queryClient(String sqlQuery) {      //visszaad egy ügyfelet
            Client client = null;
            try {
                  rs1 = createStatement.executeQuery(sqlQuery);
                  while (rs1.next()) {
                        client = new Client(
                                rs1.getString("client"),
                                rs1.getString("clientnumber"),
                                rs1.getString("type"),
                                rs1.getString("factorynumber"),
                                rs1.getInt("zipcode"),
                                rs1.getString("city"),
                                rs1.getString("address"),
                                convertBool(rs1.getInt("exist")),
                                rs1.getInt("maintenanceperyear"),
                                rs1.getString("field")
                        );
                  }
            } catch (SQLException ex) {
                  System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
                  System.out.println("" + ex);
            }
            return client;
      }

      public void updateClient(Client client, String clientNumber) {
            String sqlQuery = "update clients set " +
                    "client='" + client.getClient() +"',"+
                    //"clientnumber='" + client.getClientNumber() +"',"+
                    "type='" + client.getType() + "',"+
                    "factorynumber='" + client.getFactoryNumber() +"',"+
                    "zipcode=" + client.getZipCode() +","+
                    "city='" + client.getCity() +"',"+
                    "address='" + client.getAddress() +"',"+
                    "exist=" + convertBool(client.getExist()) +","+
                    "maintenanceperyear=" + client.getMaintenancePerYear() +","+
                    "field='" + client.getField() + "' " +
                    "where clientnumber ='"+clientNumber+"';";

            System.out.println(sqlQuery);
            try {
                  preparedStatement = conn.prepareStatement(sqlQuery);
                  preparedStatement.execute();
            } catch (SQLException throwables) {
                  throwables.printStackTrace();
            }
      }


      public void delClient(String value) {
            String sqlQuery = "delete from clients where clientnumber='" + value + "'";
            try {
                  preparedStatement = conn.prepareStatement(sqlQuery);
                  preparedStatement.execute();
            } catch (SQLException throwables) {
                  throwables.printStackTrace();
            }
      }

      public ArrayList getAllClient() {      //visszaadja  az összes gépszámot
            ArrayList<String> clients = null;
            try {
                  String sqlQuery = "select clientnumber from clients";
                  clients = new ArrayList<>();
                  rs1 = createStatement.executeQuery(sqlQuery);
                  while (rs1.next()) {
                        clients.add(rs1.getString("clientnumber"));
                  }
            } catch (SQLException ex) {
                  System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
                  System.out.println("" + ex);
            }
            return clients;
      }

      public ArrayList getAllCitys() {      //visszaadja  az összes gépszámot
            ArrayList<String> clients = null;
            try {
                  String sqlQuery = "select distinct city from clients";
                  clients = new ArrayList<>();
                  rs1 = createStatement.executeQuery(sqlQuery);
                  while (rs1.next()) {
                        clients.add(rs1.getString("city"));
                  }
            } catch (SQLException ex) {
                  System.out.println("Hiba! Nem sikerült az adatbázisból olvasni");
                  System.out.println("" + ex);
            }
            return clients;
      }

      // convertBool átalakítás Boolean->int int->Boleean mert az SQLite nem ismeri a Booleant true=1 false=0
      public int convertBool(Boolean value) {
            int convertedValue;
            if (value)
                  return 1;
            else
                  return 0;
      }

      private Boolean convertBool(int value) {
            if (value == 1)
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









package utnyilvantartojava;

import java.sql.*;

  public  class RemoteDb {


        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://192.168.1.11/routeregister?serverTimezone=Europe/Budapest";
        final String USERNAME = "diebold";
        final String PASSWORD = "Af6Be5Cd4";


        Connection conn1 = null;
        Statement createStatement1 = null;
        DatabaseMetaData dbmeta1 = null;
        ResultSet rs2 = null;
        PreparedStatement preparedStatement1 = null;

        public RemoteDb(){
              try {
                    conn1 = DriverManager.getConnection(URL,USERNAME,PASSWORD);
                    System.out.println("A kapcsolat létrejött a mysql adatbázissal,");
              } catch (SQLException ex) {
                    System.out.println("Hiba a mysql kapcsolatban!");
                    System.out.println("" + ex);
              }

              if (conn1 != null) {
                    try {
                          createStatement1 = conn1.createStatement();
                    } catch (SQLException ex) {
                          System.out.println("Hiba!");
                          System.out.println("" + ex);
                    }
              }
              try {
                    dbmeta1 = conn1.getMetaData();
              } catch (SQLException ex) {
                    System.out.println("Hiba!");
                    System.out.println("" + ex);
              }

        }
        public void addDistance(String clientId1, String clientId2, int distance) {
              String sqlQuery = "INSERT INTO distances (clientid1 ,clientid2 ,distance) " +
                      "VALUES ( ?, ?, ?);";
              try {
                    preparedStatement1 = conn1.prepareStatement(sqlQuery);
                    preparedStatement1.setString(1, clientId1);
                    preparedStatement1.setString(2, clientId2);
                    preparedStatement1.setInt(3, distance);
                    preparedStatement1.execute();
                    System.out.println(sqlQuery);
              } catch (SQLException ex) {
                    System.out.println("Hiba! Nem sikerült a távoli Distance táblába írni írni");
                    System.out.println("" + ex);
              }
        }

        public Distance getDistance(String client1,String client2) {      // a distances listából két ügyfél távolságát adja vissza
              Distance distance=new Distance(client1,client2);
              String sqlQuery = "select distance from distances where clientid1='" + client1 + "' and clientid2='" + client2 + "';";
              System.out.println(sqlQuery);
              try {
                    preparedStatement1=conn1.prepareStatement(sqlQuery);
                    rs2=preparedStatement1.executeQuery();

                    distance.setDistance(rs2.getInt("distance"));

              } catch (SQLException ex) {
                    System.out.println("Nem sikerűlt a Mysql Distances táblából olvasni!");;
              }

              return distance;
        }

    }


*/