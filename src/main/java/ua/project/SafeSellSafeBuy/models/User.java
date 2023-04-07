package ua.project.SafeSellSafeBuy.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "year_of_birthday")
    private Date year_of_birthday;

    @Column(name = "cardNumber")
    private String cardNumber;

    @OneToMany
    @Column(name = "sellProducts")
    private List<Product> sellProducts;

    @OneToMany
    @Column(name = "buyProducts")
    private List<Product> buyProducts;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public User() {
    }

    public User(String first_name, String last_name, Date year_of_birthday,
                List<Product> sellProducts, List<Product> buyProducts, String login, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.year_of_birthday = year_of_birthday;
        this.sellProducts = sellProducts;
        this.buyProducts = buyProducts;
        this.login = login;
        this.password = password;
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

    public Date getYear_of_birthday() {
        return year_of_birthday;
    }

    public void setYear_of_birthday(Date year_of_birthday) {
        this.year_of_birthday = year_of_birthday;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
