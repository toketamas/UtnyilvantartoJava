package utnyilvantartojava;


import java.util.List;

public class SqlHelper {

    public SqlHelper() {
    }

    //query type UPDATE, INSERT
    public String sqlStringBuilder(List<Object> list, String queryType) {
            String sqlQuery="";
        if (String.valueOf(queryType).toUpperCase() == "INSERT") {
            sqlQuery=insert(list);
        } else if (String.valueOf(queryType).toUpperCase() == "UPDATE") {
            sqlQuery=update(list);
        }
        return sqlQuery;
    }

    private String insert(List<Object> list) {
        String sqlQuery="INSERT INTO Routes VALUES (";
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1)
                    sqlQuery = sqlQuery + "?,";
                else
                    sqlQuery = sqlQuery + "?);";
            }
        }
        return "";
    }

    private String update(List<Object> list) {
        return "";
    }
}





