package friendfinder.persistence;

import friendfinder.domain.User;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by grace on 29/06/17.
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<User> findByQuery(String name, String gender) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class); // create a query object
        Root<User> root = cq.from(User.class); // called to set the query root
        cq.select(root); // set the result list type

        if(name!=null) {
            cq.where(cb.equal(root.get("name"), name));
        }
        if(gender!=null) {
            cq.where(cb.equal(root.get("gender"), gender));
        }
        TypedQuery<User> q = em.createQuery(cq); // prepare a query for execution and specifying the type of the query result
        List<User> allPets = q.getResultList(); // execute a query

        return allPets;
    }
}
