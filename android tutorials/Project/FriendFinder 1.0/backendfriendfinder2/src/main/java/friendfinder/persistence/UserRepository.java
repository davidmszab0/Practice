package friendfinder.persistence;

import friendfinder.api.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by grace on 23/06/17.
 */

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer>, UserRepositoryCustom {
    User findUserById (Integer id);
    User findUserByName (String name);
    User findUserByNameAndGender (String name, User.Gender gender);
}
