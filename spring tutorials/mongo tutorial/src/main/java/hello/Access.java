package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by grace on 22/02/17.
 */
@Document(collection="accessAttributes")
public class Access {

    @Id
    public String accessId;

    public String access;

    public String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }
    public String getAccessId() {
        return accessId;
    }

    public Access(String accessId, String organizationId, String access) {
        this.accessId = accessId;
        this.organizationId = organizationId;
        this.access = access;
    }

    public void setAccessAttribute(String access) {
        this.access = access;
    }

/*    @Override
    public String toString() {
        return String.format(
                "AccessAttributeId[id=%s, accessAttribute='%s']",
                accessAttributeId, accessAttribute);
    }*/
}
