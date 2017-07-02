package friendfinder.persistence;

import friendfinder.domain.User;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by grace on 29/06/17.
 */
public interface UserRepositoryCustom {

    /*@Query(value = "SELECT * FROM todos t WHERE t.title = 'title'",
            nativeQuery=true
    )*/
    List<User> findByQuery(String name, String gender);
}
