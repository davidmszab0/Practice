package friendfinder.persistence;

import friendfinder.api.domain.User;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;

import static friendfinder.api.domain.User.getGenderEnums;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

        HashSet hs = getGenderEnums();

        if(isNotBlank(name)) {
            cq.where(cb.equal(root.get("name"), name));
        }

        if(isNotBlank(gender) && hs.contains(gender) == true) {
            cq.where(cb.equal(root.get("gender"), User.Gender.valueOf(gender)));
        }
        TypedQuery<User> q = em.createQuery(cq); // prepare a query for execution and specifying the type of the query result
        List<User> allPets = q.getResultList(); // execute a query

        return allPets;
    }


}
