package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {

    public static Connection getConnection(){
        Connection conn = null;
        final String DRIVER = "org.postgresql.Driver";
        final String USER = "postgres";
        final String PASSWORD = "1234";

        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", USER, PASSWORD);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
