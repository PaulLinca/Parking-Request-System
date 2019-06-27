package dao.impl.hibernate;

import dao.*;

public class HibernateDaoFactory extends DaoFactory
{
    @Override
    public CarDao getCarDao() {
        return new HibernateCarDao();
    }

    @Override
    public ParkingLotDao getParkingLotDao() {
        return new HibernateParkingLotDao();
    }

    @Override
    public ParkingSpotDao getParkingSpotDao() {
        return new HibernateParkingSpotDao();
    }

    @Override
    public RequestDao getRequestDao() {
        return new HibernateRequestDao();
    }

    @Override
    public UserDao getUserDao() {
        return new HibernateUserDao();
    }
}
