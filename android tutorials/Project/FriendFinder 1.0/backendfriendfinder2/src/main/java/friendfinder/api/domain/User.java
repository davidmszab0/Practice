package friendfinder.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User implements Serializable {

    /**
     * Created by grace on 23/06/17.
     */
    public enum Gender {Male, Female}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // mappedBy maps the Entity of this class and not the table
    @OneToOne(fetch=FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore // to avoid infinite recursion
    private Account account;

    // https://stackoverflow.com/questions/19280121/spring-and-or-hibernate-saving-many-to-many-relations-from-one-side-after-form
    // @JoinColumn indicates the entity is the owner of the relationship: foreign key is here to the referenced table: user.
    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_genres_users", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_genres_id", referencedColumnName = "id"))
    private Set<MovieGenres> movieGenres = new HashSet<>();

    // if I want to update the fields from both sides, put @JoinColumn to the MusicGenres and avoid mapping table
    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "music_genres_users", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_genres_id", referencedColumnName = "id"))
    private Set<MusicGenres> musicGenres = new HashSet<>();

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

    public User(String name, Gender gender, Account account, Set<MovieGenres> movieGenres, Set<MusicGenres> musicGenres) {
        this.name = name;
        this.gender = gender;
        this.account = account;
        this.movieGenres = movieGenres;
        this.musicGenres = musicGenres;
    }

    public User(String name, Gender gender, Account account, Set<MusicGenres> musicGenres) {
        this.name = name;
        this.gender = gender;
        this.account = account;
        this.musicGenres = musicGenres;
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

    // https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
    public void addMovieGenres(MovieGenres mvG) {
        movieGenres.add(mvG);
        mvG.getUsers().add(this);
    }

    public void removeBook(MovieGenres mvG) {
        movieGenres.remove(mvG);
        mvG.getUsers().remove(this);
    }

    public void remove() {
        for(MovieGenres mvG : new HashSet<MovieGenres>(movieGenres)) {
            removeBook(mvG);
        }
    }

    public void addAllMovieGenres(Set<MovieGenres> movieGenresInput) {
        for(MovieGenres mvG : movieGenresInput) {
            addMovieGenres(mvG);
        }
    }

    // Fixme: failed to lazily initialize a collection
    @Override
    public String toString() {
        String result = String.format(
                "User [id=%d, name='%s', gender='%s', account='%s']%n",
                id, name, gender, account);
        if (!movieGenres.isEmpty()) {
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
