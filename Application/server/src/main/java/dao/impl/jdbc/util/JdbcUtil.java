package dao.impl.jdbc.util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.logging.Logger;


public class JdbcUtil
{

    private static final Logger LOGGER = Logger.getLogger(JdbcUtil.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/a1_example";
    private static final String USER = "root";
    private static final String PASS = "password";

    private Connection connection;
    private static JdbcUtil singleInstance = new JdbcUtil();

    public  JdbcUtil()
    {
        try
        {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    //creates a connection to the database
    public void createConnection()
    {
        try
        {
            connection = (Connection) DriverManager.getConnection(DBURL + "?autoReconnect=true&useSSL=false", USER, PASS);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if(connection == null)
        {
            System.out.println("Couldn't connect to the database.");
        }
        else
        {
            System.out.println("Connected to the database successfully.");
        }
    }

    //closes the statements, connections and result sets
    public static void close(Connection connection)
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void close(Statement statement)
    {
        try
        {
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet)
    {
        try
        {
            resultSet.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //connection getter
    public Connection getConnection()
    {
        return connection;
    }

}