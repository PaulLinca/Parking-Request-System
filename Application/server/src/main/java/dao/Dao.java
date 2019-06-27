package dao;

public interface Dao<T>
{
    T find(long id);

    void delete(T objectToDelete);

    void update(T objectToUpdate);

    void insert(T objectToCreate);
}
