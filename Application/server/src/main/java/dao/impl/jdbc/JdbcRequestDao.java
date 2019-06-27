package dao.impl.jdbc;

import dao.RequestDao;
import dao.impl.jdbc.util.JdbcUtil;
import entities.Car;
import entities.Request;
import entities.builders.RequestBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcRequestDao implements RequestDao
{
    private Connection connection;

    private static Connection connectToDB()
    {
        JdbcUtil connectionFactory = new JdbcUtil();
        connectionFactory.createConnection();

        return (Connection) connectionFactory.getConnection();
    }

    @Override
    public Request find(long id)
    {
        connection = connectToDB();
        String findStatement = "SELECT * FROM a1_example.request WHERE request_id = " + id;
        PreparedStatement find = null;
        ResultSet rSet = null;
        Request request;
        try
        {
            find = connection.prepareStatement(findStatement);
            rSet = find.executeQuery();
            rSet.next();
            Car car = new Car();
            car.setId(rSet.getLong(3));
            request = RequestBuilder.createRequestBuilder().date(rSet.getDate(2)).status(rSet.getString(4)).car(car).build();
            request.setId(rSet.getLong(1));

            return request;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Request objectToDelete)
    {
        connection = connectToDB();
        String deleteStatement = "DELETE FROM a1_example.request WHERE request_id = " + objectToDelete.getId();
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
    public void update(Request objectToUpdate)
    {
        connection = connectToDB();
        String updateStatement = "UPDATE a1_example.request SET date = ? WHERE request_id = ?";
        PreparedStatement update = null;
        ResultSet rSet = null;

        try
        {
            java.sql.Date date = new java.sql.Date(objectToUpdate.getDate().getTime());
            update = connection.prepareStatement(updateStatement);

            update.setDate(1, date);
            update.setLong(2, objectToUpdate.getId());
            update.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Request objectToCreate)
    {
        connection = connectToDB();
        String insertStatement = "INSERT INTO a1_example.request (date, parking_spot, status, car_id) values (?, ?, ?, ?)";
        PreparedStatement insert = null;
        ResultSet rSet = null;

        try
        {
            java.sql.Date date = new java.sql.Date(objectToCreate.getDate().getTime());
            insert = connection.prepareStatement(insertStatement);

            insert.setDate(1, date);
            insert.setLong(2, objectToCreate.getParkingSpot());
            insert.setString(3, objectToCreate.getStatus());
            insert.setLong(4, objectToCreate.getCar().getId());

            insert.execute();
        }
        catch (SQLException e1)
        {
            e1.printStackTrace();
        }
    }

    @Override
    public List<Request> selectAllRequests(long id) {
        return null;
    }

    @Override
    public List<Request> selectAll() {
        return null;
    }

    @Override
    public Request selectByVin(String vin) {
        return null;
    }

}
