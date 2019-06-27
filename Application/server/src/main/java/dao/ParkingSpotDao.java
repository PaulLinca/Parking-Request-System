package dao;

import entities.ParkingSpot;

import java.util.List;

public interface ParkingSpotDao extends Dao<ParkingSpot>
{
    @Override
    ParkingSpot find(long id);

    @Override
    void delete(ParkingSpot objectToDelete);

    @Override
    void update(ParkingSpot objectToUpdate);

    @Override
    void insert(ParkingSpot objectToCreate);

    List<ParkingSpot> selectAll(long id);
}
