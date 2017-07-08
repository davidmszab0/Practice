package friendfinder.services.controller;

import friendfinder.domain.Account;
import friendfinder.domain.User;
import friendfinder.exceptions.HttpUnprocessableEntityException;
import friendfinder.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import java.util.List;

/**
 * Created by grace on 29/06/17.
 */
@RestController
@RequestMapping(path="/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/all")
    public Iterable<User> getAllEntities() {
        log.debug("Getting all entities");

        return userRepository.findAll();
    }

    @GetMapping(path="/getUserByNameAndGender")
    public String getEntity (@RequestParam(value = "name") String name,
                              @RequestParam(value = "gender") String gender) {
        log.debug("Getting the user by name and gender");
        String id = "";
        try {
            if (isBlank(name)) {
                return "User not found";
            }
            User serchedEntity = userRepository.findUserByNameAndGender(name, gender);

            if (serchedEntity == null) {
                log.debug("Entity does not exist.");
                return "Entity does not exist.";
            }
            id = String.valueOf(serchedEntity.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error getting the User: " + ex.toString();
        }
        return "The user id is: " + id + " ";
    }

    @GetMapping(path="/search")
    public String searchEntitys (@RequestParam(value = "name") String name,
                             @RequestParam(value = "gender") String gender) {
        log.debug("Getting the users by name and gender");
        List<User> usr;
        try {
            log.debug("name: " + name);
            log.debug("gender: " + gender);

            usr = userRepository.findByQuery(name, gender);

            log.debug("findbyQuery: " + usr);

            if (usr == null) {
                System.out.println("Entities do not exist.");
                return "Entities do not exist.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error getting the User: " + ex.toString();
        }
        return "The entities are: " + usr + " ";
    }

    @PostMapping
    public String createEntity (@RequestBody User entity) {

        String id = "";
        try {
            if (entity == null) {
                throw new HttpUnprocessableEntityException("Entity is null.");
            }
            User newEntity = userRepository.findUserByName(entity.getName());

            if (newEntity != null) {
                log.debug("Entity already registered.");
                return "Entity already registered.";
            }
            log.debug("Creating an entity");

            Account acc = new Account(entity);
            entity.setAccount(acc);
            userRepository.save(entity);
            id = String.valueOf(entity.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error creating the entity: " + ex.toString();
        }
        return "Entity successfully created with id = " + id + " ";
    }

    @PutMapping(value = "/{id}")
    public User updateEntity (@PathVariable(value = "id") Integer id,
                                 @RequestBody User entity) {
        log.debug("Updating an entity");
        try {
            User entityBefore = userRepository.findUserById(id);
            entity.setId(id);
            userRepository.save(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug("Error updating the Entity: " + ex.toString());
        }
        return entity;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEntity (@PathVariable(value = "id") Integer id) {
        log.debug("Deleting an entity");
        try {
            User deleteEntity = userRepository.findUserById(id);
            userRepository.delete(deleteEntity);
        } catch (Exception ex) {
            log.debug("Error deleting the Entity: " + ex.toString());
        }
    }
}
