package ua.project.SafeSellSafeBuy.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_for_check")
public class ProductForCheck {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 3, max = 30, message = "title should be between 3 and 30")
    @NotNull(message = "title should be not empty!")
    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Size(min = 3, max = 100, message = "shortDescription should be between 3 and 100")
    @NotNull(message = "shortDescription should be not empty!")
    @Column(name = "short_description")
    private String short_description;

    @Size(min = 3, max = 500, message = "long_description should be between 3 and 500")
    @NotNull(message = "long_description should be not empty!")
    @Column(name = "long_description")
    private String long_description;

    @Column(name = "login_in_game")
    private String login_in_game;

    @Size(min = 3, max = 30, message = "passwordInGame should be between 3 and 30")
    @NotNull(message = "passwordInGame should be not empty!")
    @Column(name = "password_in_game")
    private String password_in_game;
    @Email
    @Size(min = 3, max = 30, message = "emailInGame should be between 3 and 30")
    @NotNull(message = "emailInGame should be not empty!")
    @Column(name = "email_in_game")
    private String email_in_game;

    @Size(min = 3, max = 30, message = "secretQuestion should be between 3 and 30")
    @NotNull(message = "secretQuestion should be not empty!")
    @Column(name = "secret_question")
    private String secret_question;

    @Size(min = 3, max = 30, message = "secretAnswer should be between 3 and 30")
    @NotNull(message = "secretAnswer should be not empty!")
    @Column(name = "secret_answer")
    private String secret_answer;

    @ManyToOne
    @JoinColumn(name = "seller", referencedColumnName = "id")
    private User seller;


    public ProductForCheck(String title, int price, String short_description,
                           String long_description, String login_in_game,
                           String password_in_game, String email_in_game,
                           String secret_question, String secret_answer) {
        this.title = title;
        this.price = price;
        this.short_description = short_description;
        this.long_description = long_description;
        this.login_in_game = login_in_game;
        this.password_in_game = password_in_game;
        this.email_in_game = email_in_game;
        this.secret_question = secret_question;
        this.secret_answer = secret_answer;
    }

    public ProductForCheck() {
    }

    public int getId() {
        return id;
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

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getLogin_in_game() {
        return login_in_game;
    }

    public void setLogin_in_game(String login_in_game) {
        this.login_in_game = login_in_game;
    }

    public String getPassword_in_game() {
        return password_in_game;
    }

    public void setPassword_in_game(String password_in_game) {
        this.password_in_game = password_in_game;
    }

    public String getEmail_in_game() {
        return email_in_game;
    }

    public void setEmail_in_game(String email_in_game) {
        this.email_in_game = email_in_game;
    }

    public String getSecret_question() {
        return secret_question;
    }

    public void setSecret_question(String secret_question) {
        this.secret_question = secret_question;
    }

    public String getSecret_answer() {
        return secret_answer;
    }

    public void setSecret_answer(String secret_answer) {
        this.secret_answer = secret_answer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "ProductForCheck{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", short_description='" + short_description + '\'' +
                ", long_description='" + long_description + '\'' +
                ", login_in_game='" + login_in_game + '\'' +
                ", password_in_game='" + password_in_game + '\'' +
                ", email_in_game='" + email_in_game + '\'' +
                ", secret_question='" + secret_question + '\'' +
                ", secret_answer='" + secret_answer + '\'' +
                ", seller=" + seller +
                '}';
    }
}
