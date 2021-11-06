package repositoty;

import model.Company;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;
import java.util.List;

public class CompanyRepositoryImpl implements BaseRepository<Company, Long> {

    private final SessionFactory getSessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public Company create(Company company) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return company;
    }

    @Override
    public Company findById(Long id) {
        try (Session session = getSessionFactory.openSession()) {
            return session.get(Company.class, id);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public List<Company> findAll() {
        try (Session session = getSessionFactory.openSession()) {
            return session.createQuery("from Company", Company.class).list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public Company update(Long id, Company company) {
        Transaction transaction = null;
        try (Session session = getSessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(company);
            transaction.commit();
        } catch (HibernateException e) {
            transactionRollback(transaction);
            throw new HibernateException(e);
        }
        return company;
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = getSessionFactory.openSession()) {
            session.delete(String.valueOf(Company.class), id);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    private void transactionRollback(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }





//    @SneakyThrows
//    @Override
//    public Collection<Company> findAll() {
//        ResultSet resultSet = CONNECTION.createStatement().executeQuery("SELECT*FROM companies");
//        final List<Company> companyList = new ArrayList<>();
//        while (resultSet.next()) {
//            final Company company = Company.builder()
//                    .id(resultSet.getLong("id"))
//                    .name(resultSet.getString("name"))
//                    .headOffice(resultSet.getString("head_office"))
//                    .build();
//            companyList.add(company);
//        }
//        return companyList;
//    }
//
//    @SneakyThrows
//    @Override
//    public void deleteById(Long id) {
//        CONNECTION
//                .createStatement()
//                .execute("DELETE FROM " + SCHEMA_NAME + ".companies" +
//                         " WHERE id=" + id);
//    }
//
//    @SneakyThrows
//    @Override
//    public Company findById(Long id) {
//        Company company = new Company();
//        final ResultSet resultSet = CONNECTION.createStatement().executeQuery("SELECT * FROM companies WHERE id=" + id + ";");
//        while (resultSet.next()) {
//            company = Company.builder()
//                    .id(resultSet.getLong("id"))
//                    .name(resultSet.getString("name"))
//                    .headOffice(resultSet.getString("head_office"))
//                    .build();
//        }
//        return company;
//    }
//
//    @SneakyThrows
//    @Override
//    public Company create(Company company) {
//        PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO " + SCHEMA_NAME + ".companies (id, name,head_office) VALUES (?,?,?) ;");
//        preparedStatement.setLong(1, company.getId());
//        preparedStatement.setString(2, company.getName());
//        preparedStatement.setString(3, company.getHeadOffice());
//        preparedStatement.executeUpdate();
//        return company;
//    }
//
//    @SneakyThrows
//    @Override
//    public Company update(Long id, Company company) {
//        PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE  " + SCHEMA_NAME + ".companies SET name=?,head_office=?  WHERE id=" + id + ";");
//        preparedStatement.setString(1, company.getName());
//        preparedStatement.setString(2, company.getHeadOffice());
//        preparedStatement.execute();
//        return company;
//    }

}
