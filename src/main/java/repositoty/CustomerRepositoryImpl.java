package repositoty;


import model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;
import java.util.Collection;

public class CustomerRepositoryImpl implements BaseRepository<Customer, Long> {

    private final SessionFactory getSessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public Collection<Customer> findAll() {
        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Customer ", Customer.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Customer findById(Long id) {
        try (Session session = getSessionFactory.openSession()) {
            return session.get(Customer.class, id);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Customer create(Customer customer) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return customer;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete Customer c where c.id=:id")
                    .setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}

