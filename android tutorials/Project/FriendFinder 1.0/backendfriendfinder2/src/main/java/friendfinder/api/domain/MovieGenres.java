package friendfinder.api.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by grace on 25/06/17.
 */
@Entity
@Table(name = "movie_genres")
public class MovieGenres {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="movies_id")
    private Integer id;

    private String name;

    // @JoinColumn indicates the entity is the owner of the relationship: the corresponding table has a column with a foreign key to the referenced table.
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "movie_genres_users", joinColumns = @JoinColumn(name = "movies_id", referencedColumnName = "movies_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;

    public MovieGenres() {
    }

    public MovieGenres(String name) {
        this.name = name;
    }

    public MovieGenres(String name, Set<User> users) {
        this.name = name;
        this.users=users;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        String result = String.format(
                "MovieGenres[id=%d, name='%s']%n",
                id, name);
        if (users != null) {
            for(User mvG : users) {
                result += String.format(
                        "User [id=%d, name='%s', gender='%s']%n",
                        mvG.getId(), mvG.getName(), mvG.getGender());
            }
        }
        return result;
    }
}
