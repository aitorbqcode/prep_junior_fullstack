package DataBase;

import java.sql.*;

public class DatabaseConnection {

    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    //Data Base connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

//Con el connection abrimos el canal de comunicación entre Java y PostgreeSQL
// El flujo para ello es el getConnection() para abrir canal de comunicacion
// PrepareStatement para la query con las lineas
// Y luego el resultado