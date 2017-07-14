package friendfinder.persistence;

import friendfinder.api.domain.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by grace on 24/06/17.
 */
public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findById(Integer id);
    Account findByEmail(String email);
    Account findByEmailAndPassword (String email, String password);
}
