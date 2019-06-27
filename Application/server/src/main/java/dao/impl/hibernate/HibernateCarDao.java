package dao.impl.hibernate;

import dao.CarDao;
import dao.UserDao;
import dao.impl.hibernate.util.HibernateUtil;
import entities.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateCarDao implements CarDao
{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Car find(long id)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        Car car = currentSession.get(Car.class, id);
        transaction.commit();
        currentSession.close();

        return car;
    }

    @Override
    public void delete(Car objectToDelete)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(objectToDelete);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void update(Car objectToUpdate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(objectToUpdate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void insert(Car objectToCreate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.merge(objectToCreate);
        transaction.commit();
        currentSession.close();
    }

    public Car findByVin(String vin)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        Car car = currentSession.byNaturalId(Car.class)
                .using("vin", vin)
                .load();
        transaction.commit();
        currentSession.close();

        return car;
    }
}
