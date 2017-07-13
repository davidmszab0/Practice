package friendfinder.persistence;

import friendfinder.api.domain.MovieGenres;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by grace on 27/06/17.
 */
public interface MovieGenresRepository extends CrudRepository<MovieGenres, Integer> {
}
