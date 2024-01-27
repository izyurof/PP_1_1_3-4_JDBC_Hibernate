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
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "select u from User u";
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> userList = session.createQuery(hql, User.class).list();
        session.getTransaction().commit();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String hql = "delete from User";
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery(hql).executeUpdate();
        session.getTransaction().commit();
    }
}
