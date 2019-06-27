package dao.impl.hibernate;

import dao.RequestDao;
import dao.impl.hibernate.util.HibernateUtil;
import entities.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateRequestDao implements RequestDao
{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Request find(long id)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        Request request = currentSession.get(Request.class, id);
        transaction.commit();
        currentSession.close();

        return request;
    }

    @Override
    public void delete(Request objectToDelete)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(objectToDelete);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void update(Request objectToUpdate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(objectToUpdate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void insert(Request objectToCreate) {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.merge(objectToCreate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public List<Request> selectAllRequests(long id)
    {
        Session currentSession = sessionFactory.openSession();
        Query query = currentSession.createQuery("SELECT r from Request r join r.parkingLots p where p.id = :idPark");
        query.setParameter("idPark", id);
        List<Request> parkingLotRequests = query.getResultList();
        currentSession.close();
        return parkingLotRequests;
    }

    public List<Request> selectAll()
    {
        Session currentSession = sessionFactory.openSession();
        Query query = currentSession.createQuery("from Request");
        List<Request> parkingLotRequests = query.getResultList();
        currentSession.close();
        return parkingLotRequests;
    }

    public Request selectByVin(String vin)
    {
        Session currentSession = sessionFactory.openSession();
        Query query = currentSession.createQuery("SELECT r from Request r join r.car p where p.vin = :vin");
        query.setParameter("vin", vin);
        Request request = (Request) query.getSingleResult();
        currentSession.close();
        return request;
    }
}
