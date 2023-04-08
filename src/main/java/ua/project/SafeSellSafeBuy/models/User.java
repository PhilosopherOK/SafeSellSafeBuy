package ua.project.SafeSellSafeBuy.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "date_of_birthday")
    private Date date_of_birthday;

    @Column(name = "cardNumber")
    private String cardNumber;

    @OneToMany(mappedBy = "seller")
    private List<Product> sellProducts;

    @OneToMany(mappedBy = "buyer")
    private List<Product> buyProducts;

    @Column(name = "userLogin")
    private String userLogin;

    @Column(name = "userPassword")
    private String userPassword;

    public User() {
    }

    public User(String first_name, String last_name, Date date_of_birthday,
                String userLogin, String userPassword) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birthday = date_of_birthday;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDate_of_birthday() {
        return date_of_birthday;
    }

    public void setDate_of_birthday(Date date_of_birthday) {
        this.date_of_birthday = date_of_birthday;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<Product> getSellProducts() {
        return sellProducts;
    }

    public void setSellProducts(Product product) {
        sellProducts.add(product);
    }

    public List<Product> getBuyProducts() {
        return buyProducts;
    }

    public void setBuyProducts(Product product) {
        buyProducts.add(product);
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String login) {
        this.userLogin = login;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }
}
