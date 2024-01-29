package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
    }

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createUsersTable() {
        String createTable = """
                create table if not exists users (
                id serial primary key,
                name text,
                last_name text,
                age int
                );
                """;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(createTable).executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void dropUsersTable() {
        String dropTable = """
                drop table if exists users;
                """;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(dropTable).executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(User.builder()
                .name(name)
                .lastName(lastName)
                .age(age)
                .build());
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from User u where u.id = :param")
                .setParameter("param", id)
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> userList = session.createQuery("select u from User u", User.class)
                .list();
        session.getTransaction().commit();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from User")
                .executeUpdate();
        session.getTransaction().commit();
    }
}
