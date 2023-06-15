package ua.project.SafeSellSafeBuy.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @Size(min = 3, max = 30, message = "first_name should be between 3 and 30")
    @NotNull(message = "first_name should be not empty!")
    private String first_name;

    @Column(name = "last_name")
    @Size(min = 3, max = 30, message = "last_name should be between 3 and 30")
    @NotNull(message = "last_name should be not empty!")
    private String last_name;

    @Column(name = "date_of_birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date_of_birthday;

    @Email(message = "Must be right email")
    @NotNull(message = "user email should be not empty!")
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "cardNumber")
    @Size(min = 15,max = 17, message = "must be 16 numbers")
    private String cardNumber;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    private List<Product> sellProducts;

    @OneToMany(mappedBy = "buyer")
    private List<Product> buyProducts;

    @Column(name = "userLogin")
    @Size(min = 3, max = 30, message = "login should be between 3 and 30")
    @NotNull(message = "login should be not empty!")
    private String username;

    @Column(name = "userPassword")
    @NotNull(message = "password should be not empty!")
    @Size(min = 6, message = "password should be more than 6 letters")
    private String password;

    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(String first_name, String last_name, Date date_of_birthday,
                String userEmail, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birthday = date_of_birthday;
        this.userEmail = userEmail;
        this.username = username;
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

    public Date getDate_of_birthday() {
        return date_of_birthday;
    }

    public void setDate_of_birthday(Date date_of_birthday) {
        this.date_of_birthday = date_of_birthday;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String user_email) {
        this.userEmail = user_email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return first_name + " " + last_name;
    }
}
