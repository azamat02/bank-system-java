package main.spring.models;


import javax.persistence.*;

@Entity(name = "CardEntity")
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    public Integer user_id;

    @Column(name = "card_number")
    public String card_number;

    @Column(name = "expired_date")
    public String expired_date;

    @Column(name = "cvv")
    public Integer cvv;

    @Column(name = "pin")
    public Integer pin;

    @Column(name = "card_type")
    public String card_type;

    @Column(name = "balance")
    public Integer balance;

    public Card() {
    }

    public Card(Integer id, Integer user_id, String card_number, String expired_date, Integer cvv, Integer pin, String card_type, Integer balance) {
        this.id = id;
        this.user_id = user_id;
        this.card_number = card_number;
        this.expired_date = expired_date;
        this.cvv = cvv;
        this.pin = pin;
        this.card_type = card_type;
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

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
