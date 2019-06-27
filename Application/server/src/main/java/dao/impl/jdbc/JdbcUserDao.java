package dao.impl.jdbc;

import dao.UserDao;
import dao.impl.jdbc.util.JdbcUtil;
import entities.Car;
import entities.User;
import entities.builders.UserBuilder;
import org.springframework.jdbc.support.xml.SqlXmlFeatureNotImplementedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JdbcUserDao implements UserDao
{
    private Connection connection;

    private static Connection connectToDB()
    {
        JdbcUtil connectionFactory = new JdbcUtil();
        connectionFactory.createConnection();

        return (Connection) connectionFactory.getConnection();
    }

    @Override
    public User find(long id)
    {
        connection = connectToDB();
        String findStatement = "SELECT * FROM a1_example.user WHERE user_id = " + id;
        PreparedStatement find = null;
        ResultSet rSet = null;
        User user;
        try
        {
            find = connection.prepareStatement(findStatement);
            rSet = find.executeQuery();
            rSet.next();
            user = UserBuilder.createUserBuilder().address(rSet.getString(2)).admin(rSet.getBoolean(3))
                    .mail(rSet.getString(4)).name(rSet.getString(5)).password(rSet.getString(6))
                    .build();
            user.setId(rSet.getLong(1));

            return user;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(User objectToDelete)
    {
        throw new NotImplementedException();
    }

    @Override
    public void update(User objectToUpdate)
    {
        throw new NotImplementedException();
    }

    @Override
    public void insert(User objectToCreate)
    {
        connection = connectToDB();
        String insertStatement = "INSERT INTO a1_example.user (address, is_admin, mail, name, password) values(";
        PreparedStatement insert = null;
        ResultSet rSet = null;

        insertStatement += "'" + objectToCreate.getAddress() + "'" + ", ";
        insertStatement += objectToCreate.isAdmin() + ", ";
        insertStatement += "'" + objectToCreate.getMail() + "'" + ", ";
        insertStatement += "'" + objectToCreate.getName() + "'" + ", ";
        insertStatement += "'" + objectToCreate.getPassword() + "'" + ")";

        try
        {
            insert = connection.prepareStatement(insertStatement);
            insert.execute();
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
        }
    }

    @Override
    public User findByMail(String mail)
    {
        connection = connectToDB();
        String findStatement = "SELECT * FROM a1_example.user WHERE mail = '" + mail + "'";
        PreparedStatement find = null;
        ResultSet rSet = null;
        User user;
        try
        {
            find = connection.prepareStatement(findStatement);
            rSet = find.executeQuery();
            rSet.next();
            user = UserBuilder.createUserBuilder().address(rSet.getString(2)).admin(rSet.getBoolean(3))
                    .mail(rSet.getString(4)).name(rSet.getString(5)).password(rSet.getString(6))
                    .build();
            user.setId(rSet.getLong(1));

            return user;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
