package friendfinder.persistence;

import friendfinder.api.domain.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by grace on 24/06/17.
 */
public interface AccountRepository extends CrudRepository<Account, Integer> {

    public Account findByAccountId(Integer accountId);
    public Account findByEmail(String email);
    public Account findByEmailAndPassword (String email, String password);
}
