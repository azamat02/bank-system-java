package main.spring.dao;

import main.spring.models.Card;
import main.spring.models.Operation;
import main.spring.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class OperationDAO {
    private SessionFactory sessionFactory;
    Session session;
    List<javax.smartcardio.Card> cardList;

    @Autowired
    public OperationDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }



    public void configureEnd()
    {
        this.sessionFactory.close();
    }

//    Get operations by user id
    public List<Operation> findOperationsByUserId(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Operation> userOperationList = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Operation> q1 = criteriaBuilder.createQuery(Operation.class);
            Root<Operation> root = q1.from(Operation.class);
            Predicate operationsByUserId = criteriaBuilder.equal(root.get("user_id"), id);
            Predicate search = criteriaBuilder.or(operationsByUserId);
            userOperationList = session.createQuery(q1.where(search)).getResultList();
            session.getTransaction().commit();
        }
        catch (NoResultException e){

        }
        finally {
            session.close();
        }
        return userOperationList;
    }

//    Create operation
    public void createOperation(Operation operation){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(operation);
            session.getTransaction();
        }
        finally {
            session.close();
        }
    }

}
