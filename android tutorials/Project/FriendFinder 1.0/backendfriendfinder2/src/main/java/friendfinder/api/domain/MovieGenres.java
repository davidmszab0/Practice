package friendfinder.api.domain;

import javax.persistence.*;

/**
 * Created by grace on 25/06/17.
 */
@Entity
@Table(name = "movie_genres")
public class MovieGenres {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="movies_id")
    private Integer moviesId;

    private String movieGenres;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="movies_user_id") // foreign key will be here
    private User user;

    public MovieGenres() {
    }

    public MovieGenres(String movieGenres) {
        this.movieGenres = movieGenres;
    }

    public Integer getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(Integer moviesId) {
        this.moviesId = moviesId;
    }

    public String getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(String movieGenres) {
        this.movieGenres = movieGenres;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
