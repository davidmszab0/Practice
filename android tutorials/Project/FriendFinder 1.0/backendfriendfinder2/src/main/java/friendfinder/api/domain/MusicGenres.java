package friendfinder.api.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by grace on 25/06/17.
 */
@Entity
@Table(name = "music_genres")
public class MusicGenres {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToMany(fetch=FetchType.LAZY, mappedBy = "musicGenres", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users = new HashSet<>();

    public MusicGenres() {
    }

    public MusicGenres(String name) {
        this.name = name;
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

    public Set<User> getUser() {
        return users;
    }

    public void setUser(Set<User>  user) {
        this.users = user;
    }
}
