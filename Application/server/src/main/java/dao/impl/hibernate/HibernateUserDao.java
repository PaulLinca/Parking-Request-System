package dao.impl.hibernate;

import dao.UserDao;
import dao.impl.hibernate.util.HibernateUtil;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateUserDao implements UserDao
{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public User find(long id)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        User user = currentSession.get(User.class, id);
        transaction.commit();
        currentSession.close();

        return user;
    }

    @Override
    public void delete(User objectToDelete)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.delete(objectToDelete);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void update(User objectToUpdate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.update(objectToUpdate);
        transaction.commit();
        currentSession.close();
    }

    @Override
    public void insert(User objectToCreate)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        currentSession.merge(objectToCreate);
        transaction.commit();
        currentSession.close();
    }

    public User findByMail(String mail)
    {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();
        User user = currentSession.byNaturalId(User.class)
                .using("mail", mail)
                .load();
        transaction.commit();
        currentSession.close();

        return user;
    }
}
