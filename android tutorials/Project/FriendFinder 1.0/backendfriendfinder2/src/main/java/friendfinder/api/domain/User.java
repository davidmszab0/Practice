package friendfinder.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User implements Serializable {

    /**
     * Created by grace on 23/06/17.
     */
    public enum Gender {Male, Female}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // mappedBy maps the Entity of this class and not the table
    @OneToOne(fetch=FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MovieGenres> movies;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MusicGenres> music;

    public User () {}

    public User(Account account) {
        this.account = account;
    }

    public User(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public User(String name, Gender gender, Account account) {
        this.name = name;
        this.gender = gender;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<MovieGenres> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieGenres> movies) {
        this.movies = movies;
    }

    public Set<MusicGenres> getMusic() {
        return music;
    }

    public void setMusic(Set<MusicGenres> music) {
        this.music = music;
    }

    @Override
    public String toString() {
        return "[id= "+ id + ", name= " + name + ", gender= " + gender + "]";
    }

    public static HashSet<String> getGenderEnums() {

        HashSet<String> values = new HashSet<String>();

        for (User.Gender g : User.Gender.values()) {
            values.add(g.name());
        }
        return values;
    }
}
