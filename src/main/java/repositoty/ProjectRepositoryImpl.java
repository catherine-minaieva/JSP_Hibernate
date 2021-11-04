package repositoty;

import lombok.SneakyThrows;
import model.Project;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;
import java.util.Collection;

public class ProjectRepositoryImpl implements BaseRepository<Project, Long> {

    private final SessionFactory getSessionFactory = HibernateSessionFactory.getSessionFactory();

    @SneakyThrows
    @Override
    public Collection<Project> findAll() {
        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Project", Project.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Project findById(Long id) {

        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Project p where p.id=:id", Project.class)
                    .setParameter("id", id).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Project create(Project project) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return project;
    }

    @SneakyThrows
    @Override
    public Project update(Long id, Project project) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return project;
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete Project p where p.id=:id")
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
