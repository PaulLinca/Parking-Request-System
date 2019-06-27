package dao;

import entities.Request;

import java.util.List;

public interface RequestDao extends Dao<Request>
{
    @Override
    Request find(long id);

    @Override
    void delete(Request objectToDelete);

    @Override
    void update(Request objectToUpdate);

    @Override
    void insert(Request objectToCreate);

    public List<Request> selectAllRequests(long id);

    List<Request> selectAll();

    Request selectByVin(String vin);
}
