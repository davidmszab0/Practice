package friendfinder.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "users")
    private Set<MovieGenres> movieGenres = new HashSet<>();

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MusicGenres> musicGenres;

    public User () {}

    public User(String name, Set<MovieGenres> movieGenres) {
        this.name = name;
        this.movieGenres=movieGenres;
    }

    public User(String name, Gender gender, Account account) {
        this.name = name;
        this.gender = gender;
        this.account = account;
    }

    public User(String name, Gender gender, Account account, Set<MovieGenres> movieGenres) {
        this.name = name;
        this.gender = gender;
        this.account = account;
        this.movieGenres=movieGenres;
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

    public Set<MovieGenres> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(Set<MovieGenres> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public Set<MusicGenres> getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(Set<MusicGenres> musicGenres) {
        this.musicGenres = musicGenres;
    }

    @Override
    public String toString() {
        String result = String.format(
                "User [id=%d, name='%s', gender='%s']%n",
                id, name, gender);
        if (movieGenres != null) {
            for(MovieGenres mvG : movieGenres) {
                result += String.format(
                        "MovieGenres[id=%d, name='%s']%n",
                        mvG.getId(), mvG.getName());
            }
        }
        return result;
    }

    public static HashSet<String> getGenderEnums() {

        HashSet<String> values = new HashSet<String>();

        for (User.Gender g : User.Gender.values()) {
            values.add(g.name());
        }
        return values;
    }
}
