package dao.impl.hibernate;

import dao.ParkingSpotDao;
import dao.impl.hibernate.util.HibernateUtil;
import entities.ParkingSpot;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateParkingSpotDao implements ParkingSpotDao
{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public ParkingSpot find(long id)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        ParkingSpot parkingSpot = currentSession.get(ParkingSpot.class, id);
        transaction.commit();
        currentSession.close();

        return parkingSpot;
    }

    @Override
    public void delete(ParkingSpot objectToDelete)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(objectToDelete);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void update(ParkingSpot objectToUpdate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(objectToUpdate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void insert(ParkingSpot objectToCreate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.merge(objectToCreate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public List<ParkingSpot> selectAll(long id)
    {
        Session currentSession = sessionFactory.openSession();
        Query query = currentSession.createQuery("SELECT r from ParkingSpot r join r.parkingLot p where p.id = :idPark");
        query.setParameter("idPark", id);
        List<ParkingSpot> parkingSpots = query.getResultList();
        currentSession.close();
        return parkingSpots;
    }
}
