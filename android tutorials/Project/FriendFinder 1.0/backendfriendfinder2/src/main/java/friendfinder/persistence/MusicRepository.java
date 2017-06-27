package friendfinder.persistence;

import friendfinder.domain.MusicGenres;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by grace on 27/06/17.
 */
public interface MusicRepository extends CrudRepository<MusicGenres, Integer> {
}
