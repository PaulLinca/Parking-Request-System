package dao;

import entities.ParkingLot;
import entities.Request;

import java.util.List;

public interface ParkingLotDao extends Dao<ParkingLot>
{
    @Override
    ParkingLot find(long id);

    @Override
    void delete(ParkingLot objectToDelete);

    @Override
    void update(ParkingLot objectToUpdate);

    @Override
    void insert(ParkingLot objectToCreate);

    public List<ParkingLot> selectAll();
}
