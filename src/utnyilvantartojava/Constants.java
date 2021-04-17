package utnyilvantartojava;


public class Constants
{
    private Constants(){};

    public static class WebapiUrl{

        public static final String REMOTE_LINK_FOR_DATABASE_WEBAPI = "https://mju7nhz6bgt5vfr4cde3xsw2yaq1.tfsoft.hu/";
        public static final String TEST_LINK_FOR_DATABASE_WEBAPI ="http://localhost/utnyilvDB/";
    }

    public static  class SqliteDataBase{
        //SqLite
        public static final String JDBC_DRIVER = "org.sqlite.JDBC";
        public static final String URL = "jdbc:sqlite:routeregister.db";
        public static final String USERNAME = "";
        public static final String PASSWORD = "";
        //MySql
        public static final String JDBC_DRIVER_MYSQL = "com.mysql.jdbc.Driver";
        public static final String URLMYSQL2 = "jdbc:mysql://192.168.1.11:3306/routeregister?serverTimezone=Europe/Budapest&useUnicode=true&characterEncoding=UTF-8";
        public static final String URLMYSQL = "jdbc:mysql://b4a00aba1329.sn.mynetname.net:3306/routeregister?serverTimezone=Europe/Budapest&useUnicode=true&characterEncoding=UTF-8";
        public static final String URLMYSQL_REMOTE = "jdbc:mysql:// mysql.nethely.hu:3306/utnyilv_dieb?serverTimezone=Europe/Budapest&useUnicode=true&characterEncoding=UTF-8";
        public static final String USERNAME_MYSQL = "utnyilv_dieb";
        public static final String PASSWORD_MYSQL = "8V2lrQnq()Tb";
    }


    public  static class SqlQuery{
        public static final String INSERT = "INSERT";
        public static final String UPDATE="UPDATE";

    }


}
