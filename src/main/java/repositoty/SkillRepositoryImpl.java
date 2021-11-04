package repositoty;

import lombok.SneakyThrows;
import model.Skill;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;
import java.util.Collection;

public class SkillRepositoryImpl implements BaseRepository<Skill, Long> {

    private final SessionFactory getSessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public Collection<Skill> findAll() {
        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Skill", Skill.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete Skill s where s.id=:id")
                    .setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
    }

    @Override
    public Skill findById(Long id) {
        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Skill s where s.id=:id", Skill.class)
                    .setParameter("id", id).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @SneakyThrows
    @Override
    public Skill create(Skill skill) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(skill);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return skill;
    }

    @SneakyThrows
    @Override
    public Skill update(Long id, Skill skill) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(skill);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return skill;
    }

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
