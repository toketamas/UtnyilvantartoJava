/*package utnyilvantartojava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlHelper {
    PreparedStatement preparedStatement;
    Connection conn;
    public SqlHelper(PreparedStatement preparedStatement, Connection conn){
        this.conn=conn;
        this.preparedStatement=preparedStatement;

    }

    public void insert(List<Object> list, String sqlQuery){

        //System.out.println(sqlQuery);
        if (list.size()!=0){
            for (int i = 0;i<list.size();i++){
                if (i< list.size()-1)
                    sqlQuery=sqlQuery+"?,";
                else
                    sqlQuery=sqlQuery+"?);";
            }
        }
        //System.out.println(sqlQuery);
        try {
            preparedStatement =  conn.prepareStatement(sqlQuery);
            for (int i=0; i<list.size();i++){
                //System.err.println(i+1);
                //System.err.println(list.get(i).getClass());
                preparedStatement.setObject(i+1,list.get(i));
            }
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Hiba! Nem sikerült a táblához adatot hozzáadni");
            System.out.println("" + ex);
        }
    }





}
*/