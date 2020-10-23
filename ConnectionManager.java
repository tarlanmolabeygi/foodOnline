
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private Connection conection;

    public static ConnectionManager connectionManager = new ConnectionManager();

    private ConnectionManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinefoodordering","root","1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return conection;
    }

    @Override
    public String toString() {
        return "ConnectionManager{" +
                "conection=" + conection +
                '}';
    }
}
