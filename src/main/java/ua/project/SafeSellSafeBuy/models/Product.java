package ua.project.SafeSellSafeBuy.models;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "loginInGame")
    private String loginInGame;

    @Column(name = "passwordInGame")
    private String passwordInGame;

    @Column(name = "emailInGame")
    private String emailInGame;

    @Column(name = "secretQuestion")
    private String secretQuestion;

    @Column(name = "secretAnswer")
    private String secretAnswer;

    @ManyToOne
    @JoinColumn(name = "seller", referencedColumnName = "id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer", referencedColumnName = "id")
    private User buyer;

    public Product() {
    }

    public Product(String title, int price, String shortDescription,
                   String longDescription, String loginInGame,
                   String passwordInGame, String emailInGame,
                   String secretQuestion, String secretAnswer) {
        this.title = title;
        this.price = price;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.loginInGame = loginInGame;
        this.passwordInGame = passwordInGame;
        this.emailInGame = emailInGame;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
    }

    public String getLoginInGame() {
        return loginInGame;
    }

    public void setLoginInGame(String loginInGame) {
        this.loginInGame = loginInGame;
    }

    public String getPasswordInGame() {
        return passwordInGame;
    }

    public void setPasswordInGame(String passwordInGame) {
        this.passwordInGame = passwordInGame;
    }

    public String getEmailInGame() {
        return emailInGame;
    }

    public void setEmailInGame(String emailInGame) {
        this.emailInGame = emailInGame;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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
