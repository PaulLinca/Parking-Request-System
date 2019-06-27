package dao.impl.hibernate;

import dao.ParkingLotDao;
import dao.impl.hibernate.util.HibernateUtil;
import entities.ParkingLot;
import entities.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;

public class HibernateParkingLotDao implements ParkingLotDao
{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public ParkingLot find(long id)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        ParkingLot parkingLot = currentSession.get(ParkingLot.class, id);
        transaction.commit();
        currentSession.close();

        return parkingLot;
    }

    @Override
    public void delete(ParkingLot objectToDelete)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(objectToDelete);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void update(ParkingLot objectToUpdate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(objectToUpdate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void insert(ParkingLot objectToCreate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.merge(objectToCreate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public List<ParkingLot> selectAll()
    {
        Session currentSession = sessionFactory.openSession();
        Query query = currentSession.createQuery("from ParkingLot");
        List<ParkingLot> parkingLots = query.getResultList();
        currentSession.close();
        return parkingLots;
    }
}
