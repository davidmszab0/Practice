package friendfinder.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by grace on 23/06/17.
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User implements Serializable {

    private Integer userId;

    private String name;

    private String gender;

    private Account account;

    public User () {}

    public User(int userId) {
        this.userId = userId;
    }

    public User(String name, String gender, Account account) {
        this.name = name;
        this.gender = gender;
        this.account = account;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "user")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
