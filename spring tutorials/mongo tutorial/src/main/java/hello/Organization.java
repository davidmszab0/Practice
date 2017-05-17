package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by grace on 22/02/17.
 */
@Document(collection="organizations")
public class Organization {

    @Id
    public String organizationId;

    public String organizationName;

    public Organization(String organizationId, String organizationName) {}

    public void setOrganizationName (String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String toString() {
        return String.format(
                "Organization[id=%s, organizationName='%s']",
                organizationId, organizationName);
    }
}
