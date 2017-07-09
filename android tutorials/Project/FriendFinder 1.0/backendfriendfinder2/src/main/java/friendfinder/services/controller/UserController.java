package friendfinder.services.controller;

import friendfinder.domain.Account;
import friendfinder.domain.User;
import friendfinder.exceptions.HttpConflictException;
import friendfinder.exceptions.HttpNotFoundException;
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
    public User getEntity (@RequestParam(value = "name") String name,
                              @RequestParam(value = "gender") String gender) {
        log.debug("Getting the user by name and gender");
        User serchedEntity = null;
        try {
            if (isBlank(name)) {
                throw new HttpUnprocessableEntityException("Entity was not found");
            }
            serchedEntity = userRepository.findUserByNameAndGender(name, gender);

            if (serchedEntity == null) {
                log.debug("Entity does not exist.");
                throw new HttpNotFoundException("Entity was not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return serchedEntity;
    }

    @GetMapping(path="/search")
    public List<User> searchEntitys (@RequestParam(value = "name") String name,
                             @RequestParam(value = "gender") String gender) {
        log.debug("Getting the users by name and gender");
        List<User> usr = null;
        try {
            log.debug("name: " + name);
            log.debug("gender: " + gender);

            usr = userRepository.findByQuery(name, gender);

            log.debug("findbyQuery: " + usr);

            if (usr == null) {
                System.out.println("Entities do not exist.");
                throw new HttpNotFoundException("Entity was not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return usr;
    }

    @PostMapping
    public User createEntity (@RequestBody User entity) {

        User newEntity = null;
        try {
            if (entity == null) {
                throw new HttpUnprocessableEntityException("Entity is null.");
            }
            newEntity = userRepository.findUserByName(entity.getName());

            if (newEntity != null) {
                log.debug("Entity already registered.");
                throw new HttpConflictException("Entity already registered.");
            }
            log.debug("Creating an entity");

            Account acc = new Account(entity);
            entity.setAccount(acc);
            userRepository.save(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newEntity;
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
