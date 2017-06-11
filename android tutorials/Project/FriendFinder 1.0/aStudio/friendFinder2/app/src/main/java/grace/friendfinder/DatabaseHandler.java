package grace.friendfinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by grace on 11/06/17.
 */

public class DatabaseHandler {

    private final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/friendfinder2?useSSL=false";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "";
    private final static String DB_DRIVER = "com.mysql.jdbc.Driver";

    /** Method to connect to database */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return c;
    }

    public boolean checkUser(String email, String password) {
        boolean check = false;

        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM friendfinder2.accounts WHERE email = \"david@szabo.com\" AND password = \"empty\";" ;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next())
                check = true;

            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("checkUser error: " + e);
        }
        return check;
    }

    public void addUser () {

    }
}
