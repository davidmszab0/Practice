package hello;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by grace on 22/02/17.
 */
public interface OrganizationRepository extends MongoRepository<Organization, String> {
    public Organization findByorganizationId (String organizationId);
}
