package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleConnect {
    private Connection connection;
    private static final String host = "localhost";
    private static final String dbname = "orcl";
    private static final String username = "pdb";
    private static final String password = "pdb";
    private static final String port = "1521";

    public OracleConnect() throws Exception {
        String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + dbname;
        this.connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connected With Oracle");
    }

    public int updateDB(String query) throws Exception {
        Statement statment = this.connection.createStatement();
        return statment.executeUpdate(query);
    }

    public ResultSet searchDB(String query) throws Exception {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public void close() throws Exception {
        this.connection.close();
    }
}
