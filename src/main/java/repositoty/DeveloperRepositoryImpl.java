package repositoty;

import lombok.SneakyThrows;
import model.Developer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;
import java.util.Collection;

public class DeveloperRepositoryImpl implements BaseRepository<Developer, Long> {

    private final SessionFactory getSessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public Collection<Developer> findAll() {
        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Developer", Developer.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @SneakyThrows
    @Override
    public Developer findById(Long id) {
        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Developer d where d.id=:id", Developer.class)
                    .setParameter("id", id).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Developer create(Developer developer) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(developer);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return developer;
    }

    @Override
    public Developer update(Long id, Developer developer) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(developer);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return developer;
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete Developer d where d.id=:id")
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
