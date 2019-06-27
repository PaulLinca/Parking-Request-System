package dao.impl.jdbc;

import dao.*;

public class JdbcDaoFactory extends DaoFactory
{
    @Override
    public CarDao getCarDao()
    {
        return new JdbcCarDao();
    }

    @Override
    public ParkingLotDao getParkingLotDao()
    {
        return new JdbcParkingLotDao();
    }

    @Override
    public ParkingSpotDao getParkingSpotDao()
    {
        return new JdbcParkingSpotDao();
    }

    @Override
    public RequestDao getRequestDao()
    {
        return new JdbcRequestDao();
    }

    @Override
    public UserDao getUserDao()
    {
        return new JdbcUserDao();
    }
}
