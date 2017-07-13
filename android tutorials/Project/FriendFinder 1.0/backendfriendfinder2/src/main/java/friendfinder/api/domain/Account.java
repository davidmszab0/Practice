package friendfinder.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by grace on 24/06/17.
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "accounts")
public class Account {

    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="account_id")
    private Integer id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="modified_at")
    private Date modifiedAt;

    @OneToOne(fetch=FetchType.LAZY)
    //@JoinColumn(name="account_user_id") // foreign key will be here
    @MapsId // for shared primary key
    @JsonIgnore
    private User user;

    public Account () {}

    public Account (String email, String password) {
        this.email = email;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
