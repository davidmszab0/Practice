package hello;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by grace on 22/02/17.
 */
public interface AccessRepository extends MongoRepository<Access, String> {


    public List<Access> findByOrganizationIdIn(List<String> organizationId);
}
