package dao;

import entities.Car;

public interface CarDao extends Dao<Car>
{
    @Override
    Car find(long id);

    @Override
    void delete(Car objectToDelete);

    @Override
    void update(Car objectToUpdate);

    @Override
    void insert(Car objectToCreate);

    Car findByVin(String vin);
}
