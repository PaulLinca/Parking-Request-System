package dao.impl.jdbc;

import dao.CarDao;
import dao.impl.jdbc.util.JdbcUtil;
import entities.Car;
import entities.User;
import entities.builders.CarBuilder;
import entities.builders.UserBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

public class JdbcCarDao implements CarDao
{
    private Connection connection;

    private static Connection connectToDB()
    {
        JdbcUtil connectionFactory = new JdbcUtil();
        connectionFactory.createConnection();

        return (Connection) connectionFactory.getConnection();
    }

    @Override
    public Car find(long id)
    {
        return null;
    }

    @Override
    public void delete(Car objectToDelete)
    {
        connection = connectToDB();
        String deleteStatement = "DELETE FROM a1_example.car WHERE car_id = " + objectToDelete.getId();
        PreparedStatement delete = null;
        ResultSet rSet = null;

        try
        {
            delete = connection.prepareStatement(deleteStatement);
            delete.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Car objectToUpdate)
    {
        throw new NotImplementedException();
    }

    @Override
    public void insert(Car objectToCreate)
    {
        connection = connectToDB();
        String insertStatement = "INSERT INTO a1_example.car (pti, vin, user_id) values (?, ?, ?)";
        PreparedStatement insert = null;
        ResultSet rSet = null;

        try
        {
            java.sql.Date date = new java.sql.Date(objectToCreate.getPti().getTime());
            insert = connection.prepareStatement(insertStatement);

            insert.setDate(1, date);
            insert.setString(2, objectToCreate.getVin());
            insert.setLong(3, objectToCreate.getUser().getId());

            insert.execute();
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
        }
    }

    @Override
    public Car findByVin(String vin)
    {
        connection = connectToDB();
        String findStatement = "SELECT * FROM a1_example.car WHERE vin = '" + vin + "'";
        PreparedStatement find = null;
        ResultSet rSet = null;
        Car car;
        try
        {
            find = connection.prepareStatement(findStatement);
            rSet = find.executeQuery();
            rSet.next();
            car = CarBuilder.createCarBuilder().pti(rSet.getDate(2)).vin(rSet.getString(3)).build();
            car.setId(rSet.getLong(1));

            return car;

        }
        catch (SQLException e)
        {
            System.out.println("Car not in DB.");
        }

        return null;
    }
}
