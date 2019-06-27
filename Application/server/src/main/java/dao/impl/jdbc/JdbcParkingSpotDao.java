package dao.impl.jdbc;

import dao.ParkingSpotDao;
import entities.ParkingSpot;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class JdbcParkingSpotDao implements ParkingSpotDao
{
    @Override
    public ParkingSpot find(long id)
    {
        throw new NotImplementedException();
    }

    @Override
    public void delete(ParkingSpot objectToDelete)
    {
        throw new NotImplementedException();
    }

    @Override
    public void update(ParkingSpot objectToUpdate)
    {
        throw new NotImplementedException();
    }

    @Override
    public void insert(ParkingSpot objectToCreate)
    {
        throw new NotImplementedException();
    }

    @Override
    public List<ParkingSpot> selectAll(long id) {
        return null;
    }
}
