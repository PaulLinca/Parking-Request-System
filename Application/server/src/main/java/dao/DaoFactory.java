package dao;

import dao.impl.hibernate.HibernateDaoFactory;
import dao.impl.jdbc.JdbcDaoFactory;

public abstract class DaoFactory
{
    private static final DaoFactory HIBERNATE_DAO_FACTORY = new HibernateDaoFactory();
    private static final DaoFactory JDBC_DAO_FACTORY      = new JdbcDaoFactory();

    public enum Type
    {
        HIBERNATE,
        JDBC
    }

    protected DaoFactory() {}

    public static DaoFactory getInstance(Type factoryType)
    {
        switch (factoryType)
        {
            case HIBERNATE:
                return HIBERNATE_DAO_FACTORY;
            case JDBC:
                return JDBC_DAO_FACTORY;
            default:
                throw new IllegalArgumentException("Invalid factory");
        }
    }

    public abstract CarDao getCarDao();
    public abstract ParkingLotDao getParkingLotDao();
    public abstract ParkingSpotDao getParkingSpotDao();
    public abstract RequestDao getRequestDao();
    public abstract UserDao getUserDao();



}
