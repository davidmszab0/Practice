package friendfinder.persistence;

import friendfinder.api.domain.User;

import java.util.List;

/**
 * Created by grace on 29/06/17.
 */
public interface UserRepositoryCustom {

    /*@Query(value = "SELECT * FROM todos t WHERE t.title = 'title'",
            nativeQuery=true
    )*/
    List<User> findByQuery(String name, User.Gender gender);
}
