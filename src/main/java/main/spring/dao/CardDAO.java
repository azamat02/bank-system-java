package main.spring.dao;

import main.spring.models.Card;
import main.spring.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CardDAO {
    private SessionFactory sessionFactory;
    Session session;
    List<javax.smartcardio.Card> cardList;

    public CardDAO()
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

//    Get cards by user id
    public List<Card> findByUserId(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Card> userCardList;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Card> q1 = criteriaBuilder.createQuery(Card.class);
            Root<Card> root = q1.from(Card.class);
            Predicate cardByUserId = criteriaBuilder.equal(root.get("user_id"), id);
            Predicate search = criteriaBuilder.or(cardByUserId);
            userCardList = session.createQuery(q1.where(search)).getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return userCardList;
    }

//    Get card by number
    public Card findCardByNumber(String card_number){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Card card;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Card> q1 = criteriaBuilder.createQuery(Card.class);
            Root<Card> root = q1.from(Card.class);
            Predicate cardByUserId = criteriaBuilder.equal(root.get("card_number"), card_number);
            Predicate search = criteriaBuilder.or(cardByUserId);
            card = session.createQuery(q1.where(search)).getSingleResult();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return card;
    }

//    Create card
    public void createCard(Card card){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(card);
            session.getTransaction();
        }
        finally {
            session.close();
        }
    }

}
