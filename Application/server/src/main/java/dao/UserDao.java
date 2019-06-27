package dao;

import entities.User;

public interface UserDao extends Dao<User>
{
    @Override
    User find(long id);

    @Override
    void delete(User objectToDelete);

    @Override
    void update(User objectToUpdate);

    @Override
    void insert(User objectToCreate);

    User findByMail(String mail);
}
