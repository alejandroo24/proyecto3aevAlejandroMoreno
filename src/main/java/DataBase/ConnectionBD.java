package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import dataAccess.XMLManager;

public class ConnectionBD {
    private static Connection con;
    private static ConnectionBD _instance;

    private ConnectionBD() {
        ConnectionProperties connectionProperties = new ConnectionProperties("localhost","3306","foreverDamned","root","root");

        ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), ConnectionProperties.getFile());
        try {
            con = DriverManager.getConnection(properties.getURL(),properties.getUser(),properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            con = null;
        }
    }

    public static Connection getConnection() {
        if(_instance == null) {
            _instance = new ConnectionBD();
        }
        return con;
    }
}
