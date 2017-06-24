package friendfinder.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by grace on 24/06/17.
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private Date createdAt;

    private Date modifiedAt;

    public Account () {}

    public Account (String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Integer getId() {
        return id;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
