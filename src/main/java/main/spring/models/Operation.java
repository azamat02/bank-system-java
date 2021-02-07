package main.spring.models;


import javax.persistence.*;
import java.util.Date;

@Entity(name = "OperationEntity")
@Table(name = "operation_history")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    public Integer user_id;

    @Column(name = "date")
    public Date date;

    @Column(name = "operation")
    public String operation;

    @Column(name = "from_card")
    public String from_card;

    @Column(name = "status")
    public String status;

    @Column(name = "balance")
    public String balance;

    public Operation() {
    }

    public Operation(Integer id, Integer user_id, Date date, String operation, String from_card, String status, String balance) {
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.operation = operation;
        this.from_card = from_card;
        this.status = status;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getFrom_card() {
        return from_card;
    }

    public void setFrom_card(String from_card) {
        this.from_card = from_card;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
