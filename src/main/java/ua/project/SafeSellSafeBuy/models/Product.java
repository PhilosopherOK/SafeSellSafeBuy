package ua.project.SafeSellSafeBuy.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @Column(name = "id")
    private int id;
    @Size(min = 8, max = 30, message = "title should be between 8 and 30")
    @NotNull(message = "title should be not empty!")
    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Size(min = 8, max = 100, message = "shortDescription should be between 8 and 100")
    @NotNull(message = "shortDescription should be not empty!")
    @Column(name = "short_description")
    private String short_description;

    @Size(min = 8, max = 500, message = "long_description should be between 8 and 500")
    @NotNull(message = "long_description should be not empty!")
    @Column(name = "long_description")
    private String long_description;

    @Column(name = "login_in_game")
    private String login_in_game;

    @Size(min = 8, max = 30, message = "passwordInGame should be between 8 and 30")
    @NotNull(message = "passwordInGame should be not empty!")
    @Column(name = "password_in_game")
    private String password_in_game;
    @Email
    @Size(min = 8, max = 30, message = "emailInGame should be between 8 and 30")
    @NotNull(message = "emailInGame should be not empty!")
    @Column(name = "email_in_game")
    private String email_in_game;

    @Size(min = 8, max = 30, message = "secretQuestion should be between 8 and 30")
    @NotNull(message = "secretQuestion should be not empty!")
    @Column(name = "secret_question")
    private String secret_question;

    @Size(min = 8, max = 30, message = "secretAnswer should be between 8 and 30")
    @NotNull(message = "secretAnswer should be not empty!")
    @Column(name = "secret_answer")
    private String secret_answer;

    @ManyToOne
    @JoinColumn(name = "seller", referencedColumnName = "id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer", referencedColumnName = "id")
    private User buyer;

    public Product() {
    }

    public Product(String title, int price, String shortDescription,
                   String long_description, String login_in_game,
                   String password_in_game, String email_in_game,
                   String secret_question, String secret_answer) {
        this.title = title;
        this.price = price;
        this.short_description = shortDescription;
        this.long_description = long_description;
        this.login_in_game = login_in_game;
        this.password_in_game = password_in_game;
        this.email_in_game = email_in_game;
        this.secret_question = secret_question;
        this.secret_answer = secret_answer;
    }

    public String getLogin_in_game() {
        return login_in_game;
    }

    public void setLogin_in_game(String loginInGame) {
        this.login_in_game = loginInGame;
    }

    public String getPassword_in_game() {
        return password_in_game;
    }

    public void setPassword_in_game(String passwordInGame) {
        this.password_in_game = passwordInGame;
    }

    public String getEmail_in_game() {
        return email_in_game;
    }

    public void setEmail_in_game(String emailInGame) {
        this.email_in_game = emailInGame;
    }

    public String getSecret_question() {
        return secret_question;
    }

    public void setSecret_question(String secretQuestion) {
        this.secret_question = secretQuestion;
    }

    public String getSecret_answer() {
        return secret_answer;
    }

    public void setSecret_answer(String secretAnswer) {
        this.secret_answer = secretAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String shortDescription) {
        this.short_description = shortDescription;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String longDescription) {
        this.long_description = longDescription;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
