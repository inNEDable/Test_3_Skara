package db_shit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    // jdbc:mysql://localhost:3306/mydb

    private static DBManager instance;
    private Connection connection;
    private static final String DRIVER = "mysql";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATA_BASE_NAME = "mydb";
    private static final String USER = "root";
    private static final String PASS = "root";


    private DBManager (){
        try {
            connection = DriverManager.getConnection("jdbc:"+DRIVER+"://"+HOST+":"+PORT+"/"+ DATA_BASE_NAME, USER, PASS);
        } catch (SQLException e) {
            System.err.println("FAILED TO CREATE CONNECTION OBJECT!!!!");
            e.printStackTrace();
        }
    }

    public static DBManager getInstance() {
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
