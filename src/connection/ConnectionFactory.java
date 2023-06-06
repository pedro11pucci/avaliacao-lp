package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

    protected Connection conn;
    
    public ConnectionFactory(){
        this.conn = getConnection();
    }

    public Connection getConnection(){
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost/avaliacao", "root", "1234");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
