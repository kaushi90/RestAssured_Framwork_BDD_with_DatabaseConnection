package crm.framework.db.connections;

import crm.framework.config.ConfigKeys;
import crm.framework.config.DefaultConfig;
import crm.framework.config.ImplementConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class OracleJDBCConnection {

    private static Connection connection;
    private static Connection connection2;

    public OracleJDBCConnection()  {
    }

    private static void openDBConnection() throws SQLException, ClassNotFoundException {
        ImplementConfig config = DefaultConfig.getConfig();
        String driverName = "oracle.jdbc.driver.OracleDriver";

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException var7) {
            System.err.println("Is There is a Oracle JDBC Driver? " + var7.getMessage());
            throw var7;
        }

        String url = config.getValue(ConfigKeys.KEY_DBURL.getKey().toString());
        String username = config.getValue(ConfigKeys.KEY_DBUSERNAME.getKey().toString());
        String password = config.getValue(ConfigKeys.KEY_DBUSERPASSWORD.getKey().toString());

        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + url, username, password);
        } catch (SQLException var6) {
            System.err.println("SQL Connection error : " + var6.getMessage());
            throw var6;
        }
    }

    private static void openDBConnection2() throws SQLException, ClassNotFoundException {
        ImplementConfig config = DefaultConfig.getConfig();
        String driverName = "oracle.jdbc.driver.OracleDriver";

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException var7) {
            System.err.println("Where is your Oracle JDBC Driver? " + var7.getMessage());
            throw var7;
        }

        String url2 = config.getValue(ConfigKeys.KEY_DBURL2.getKey().toString());
        String username2 = config.getValue(ConfigKeys.KEY_DBUSERNAME2.getKey().toString());
        String password2 = config.getValue(ConfigKeys.KEY_DBUSERPASSWORD2.getKey().toString());

        try {
            connection2 = DriverManager.getConnection("jdbc:oracle:thin:@" + url2, username2, password2);
        } catch (SQLException var6) {
            System.err.println("SQL Connection error : " + var6.getMessage());
            throw var6;
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        } else {
            openDBConnection();
            return connection;
        }
    }

    public static Connection getConnection2() throws SQLException, ClassNotFoundException {
        if (connection2 != null && !connection2.isClosed()) {
            return connection2;
        } else {
            openDBConnection2();
            return connection2;
        }
    }
}
