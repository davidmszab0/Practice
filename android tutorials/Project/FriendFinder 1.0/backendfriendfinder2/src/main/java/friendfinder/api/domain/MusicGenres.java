package friendfinder.api.domain;

import javax.persistence.*;

/**
 * Created by grace on 25/06/17.
 */
@Entity
@Table(name = "music_genres")
public class MusicGenres {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="music_id")
    private Integer id;

    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="music_user_id") // foreign key will be here
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
