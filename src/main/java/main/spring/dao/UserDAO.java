package main.spring.dao;

import main.spring.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class UserDAO {

    private SessionFactory sessionFactory;
    Session session;
    List<User> userList;

    public UserDAO()
    {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                .setProperty("hibernate.connection.datasource", "java:/comp/env/jdbc/practice")
                .setProperty("hibernate.order_updates", "true")
                .setProperty("show_sql", "true");
        sessionFactory = configuration.buildSessionFactory();
    }

    public void configureEnd()
    {
        this.sessionFactory.close();
    }

    public List<User> getUserList(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            Query<User> query = session.createQuery(criteria);
            userList = query.getResultList();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return userList;
    }

//    Create user
    public void createUser(User user){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(user);
            session.getTransaction();
        }
        finally {
             session.close();
        }
    }
//    Find by id
    public User findById(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user;
        try {
            user = session.find(User.class, id);
            session.getTransaction();
        }
        finally {
            session.close();
        }
        return user;
    }

//    Find by login
    public User findByLogin(String login){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> q1 = criteriaBuilder.createQuery(User.class);
            Root<User> root = q1.from(User.class);
            Predicate userByLogin = criteriaBuilder.equal(root.get("login"), login);
            Predicate search = criteriaBuilder.or(userByLogin);
            user = session.createQuery(q1.where(search)).getSingleResult();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return user;
    }

}
