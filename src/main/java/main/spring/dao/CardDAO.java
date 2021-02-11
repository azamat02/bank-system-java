package main.spring.dao;

import main.spring.models.Card;
import main.spring.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleInfoNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@Scope(scopeName = "prototype")

public class CardDAO {
    private SessionFactory sessionFactory;
    Session session;
    List<Card> cardList;

    @Autowired
    public CardDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public void configureEnd()
    {
        this.sessionFactory.close();
    }

//    Get all cards list
    public List<Card> getAllCardsList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                try
                {
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    CriteriaQuery<Card> criteria = builder.createQuery(Card.class);
                    Root<Card> root = criteria.from(Card.class);
                    criteria.select(root);
                    Query<Card> query = session.createQuery(criteria);
                    cardList = query.getResultList();
                    session.getTransaction().commit();
                }
                finally
                {
                    session.close();
                }
            }
        }).start();
        return cardList;
    }

//    Update balance
    public void updateCardBalance(Card card){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.update(card);
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
    }

//    Get cards by user id
    public List<Card> findByUserId(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Card> userCardList = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Card> q1 = criteriaBuilder.createQuery(Card.class);
            Root<Card> root = q1.from(Card.class);
            Predicate cardByUserId = criteriaBuilder.equal(root.get("user_id"), id);
            Predicate search = criteriaBuilder.or(cardByUserId);
            userCardList = session.createQuery(q1.where(search)).getResultList();
            session.getTransaction().commit();
        }
        catch (NoResultException e){

        }
        finally {
            session.close();
        }
        return userCardList;
    }

//    Get card by id
    public Card findCardById(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Card card = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Card> q1 = criteriaBuilder.createQuery(Card.class);
            Root<Card> root = q1.from(Card.class);
            Predicate cardById = criteriaBuilder.equal(root.get("id"), id);
            Predicate search = criteriaBuilder.or(cardById);
            card = session.createQuery(q1.where(search)).getSingleResult();
            session.getTransaction().commit();
        }
        catch (NoResultException e){

        }
        finally {
            session.close();
        }
        return card;
    }

//    Get card by number
    public Card findCardByNumber(String card_number){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Card card = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Card> q1 = criteriaBuilder.createQuery(Card.class);
            Root<Card> root = q1.from(Card.class);
            Predicate cardByUserId = criteriaBuilder.equal(root.get("card_number"), card_number);
            Predicate search = criteriaBuilder.or(cardByUserId);
            card = session.createQuery(q1.where(search)).getSingleResult();
            session.getTransaction().commit();
        }
        catch (NoResultException e){

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
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
    }

}
