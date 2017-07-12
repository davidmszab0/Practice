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
    private Integer musicId;

    private String musicGenres;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="music_user_id") // foreign key will be here
    private User user;

    public MusicGenres() {
    }

    public MusicGenres(String musicGenres) {
        this.musicGenres = musicGenres;
    }

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public String getMusicGenres() {
        return musicGenres;
    }

    public void setMusicGenres(String musicGenres) {
        this.musicGenres = musicGenres;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
