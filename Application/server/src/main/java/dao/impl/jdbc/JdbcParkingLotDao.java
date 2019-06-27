package dao.impl.jdbc;

import dao.ParkingLotDao;
import dao.impl.jdbc.util.JdbcUtil;
import entities.ParkingLot;
import entities.builders.ParkingLotBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcParkingLotDao implements ParkingLotDao
{
    private Connection connection;

    private static Connection connectToDB()
    {
        JdbcUtil connectionFactory = new JdbcUtil();
        connectionFactory.createConnection();

        return (Connection) connectionFactory.getConnection();
    }
    @Override
    public ParkingLot find(long id)
    {
        connection = connectToDB();
        String findStatement = "SELECT * FROM a1_example.parking_lot WHERE parking_lot_id = " + id;
        PreparedStatement find = null;
        ResultSet rSet = null;
        ParkingLot parkingLot;
        try
        {
            find = connection.prepareStatement(findStatement);
            rSet = find.executeQuery();
            rSet.next();
            parkingLot = ParkingLotBuilder.createParkingLotBuilder().address(rSet.getString(2)).build();
            parkingLot.setId(rSet.getLong(1));

            return parkingLot;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(ParkingLot objectToDelete)
    {
        throw new NotImplementedException();
    }

    @Override
    public void update(ParkingLot objectToUpdate)
    {
        throw new NotImplementedException();
    }

    @Override
    public void insert(ParkingLot objectToCreate)
    {
        connection = connectToDB();
        String insertStatement = "INSERT INTO a1_example.parking_lot (address) values(";
        PreparedStatement insert = null;
        ResultSet rSet = null;

        insertStatement += "'" + objectToCreate.getAddress() + "'" + ")";

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
    public List<ParkingLot> selectAll()
    {
        connection = connectToDB();
        List<ParkingLot> parkingLots = new ArrayList<>();
        String selectStatement = "SELECT * FROM a1_example.parking_lot";
        PreparedStatement find = null;
        ResultSet rSet = null;

        try
        {
            find = connection.prepareStatement(selectStatement);
            rSet = find.executeQuery();
            int i = 1;
            while(rSet.next())
            {
                ParkingLot parkingLot = new ParkingLot();
                parkingLot.setId(rSet.getLong(1));
                parkingLot.setAddress(rSet.getString(2));
                parkingLots.add(parkingLot);
            }

            return parkingLots;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
