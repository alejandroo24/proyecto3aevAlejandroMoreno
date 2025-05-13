import utils.Utilidades;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.1.100:3306/foreverdamned";
        String user = "root";
        String password = "root";

        try{
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM productos");
            Utilidades.muestraMensaje(String.valueOf(rs));
            Utilidades.muestraMensaje("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
