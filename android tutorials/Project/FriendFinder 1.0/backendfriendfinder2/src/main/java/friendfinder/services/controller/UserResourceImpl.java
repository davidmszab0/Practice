package friendfinder.services.controller;

import friendfinder.api.domain.Account;
import friendfinder.api.domain.MovieGenres;
import friendfinder.api.domain.MusicGenres;
import friendfinder.api.domain.User;
import friendfinder.api.exceptions.HttpConflictException;
import friendfinder.api.exceptions.HttpNotFoundException;
import friendfinder.api.exceptions.HttpUnprocessableEntityException;
import friendfinder.persistence.MovieGenresRepository;
import friendfinder.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static friendfinder.api.domain.User.getGenderEnums;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by grace on 29/06/17.
 */
@RestController
@RequestMapping(path="/user")
public class UserResourceImpl {

    private static final Logger log = LoggerFactory.getLogger(UserResourceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        log.debug("Getting all entities");

        return userRepository.findAll();
    }

    @GetMapping(path="/getUserByNameAndGender")
    public User getUserByNameAndGender (@RequestParam(value = "name") String name,
                              @RequestParam(value = "gender") String gender) {
        log.debug("Getting the entity by name and gender.");
        User serchedEntity = null;
        HashSet hs = getGenderEnums();

        if (isBlank(name)) {
            throw new HttpUnprocessableEntityException("The name of the entity was not found.");
        }

        if (isBlank(gender) || hs.contains(gender) == false) {
            throw new HttpUnprocessableEntityException("The gender of the entity was not found.");
        }

        serchedEntity = userRepository.findUserByNameAndGender(name, User.Gender.valueOf(gender));

        if (serchedEntity == null) {
            log.debug("Entity does not exist.");
            throw new HttpNotFoundException("Entity was not found.");
        }
        return serchedEntity;
    }

    @GetMapping(value = "/{id}")
    public User getUserById (@PathVariable(value = "id") Integer id) {
        log.debug("Getting the entity by id.");
        User serchedEntity = null;
        if (id <= 0) {
            throw new HttpUnprocessableEntityException("Entity has invalid id.");
        }
        serchedEntity = userRepository.findUserById(id);

        if (serchedEntity == null) {
            log.debug("Entity does not exist.");
            throw new HttpNotFoundException("Entity was not found.");
        }
        return serchedEntity;
    }

    @GetMapping(path="/search")
    public List<User> searchEntities (@RequestParam(value = "name") String name,
                             @RequestParam(value = "gender") String gender) {
        log.debug("Getting the users by name and gender");
        List<User> usr = null;
            log.debug("name: " + name);
            log.debug("gender: " + gender);

            usr = userRepository.findByQuery(name, gender);

            log.debug("findbyQuery: " + usr);

            if (usr == null) {
                System.out.println("Entities do not exist.");
                throw new HttpNotFoundException("Entity was not found.");
            }
        return usr;
    }

    @PostMapping
    public User createUser (@RequestBody User entity) {

        User newEntity = null;
            if (entity == null) {
                throw new HttpUnprocessableEntityException("Entity is null.");
            }
            newEntity = userRepository.findUserByName(entity.getName());

            if (newEntity != null) {
                log.debug("Entity already registered.");
                throw new HttpConflictException("Entity already registered.");
            }
            log.debug("Creating an entity");

            // Fixme account email and password can't be null
            Account acc = new Account();
            acc.setUser(entity);
            entity.setAccount(acc);
            newEntity = userRepository.save(entity);
        return newEntity;
    }

    @PutMapping(value = "/{id}")
    public User updateEntity (@PathVariable(value = "id") Integer id,
                                 @RequestBody User entity) {
        log.debug("Updating the entity.");
            User entityBefore = userRepository.findUserById(id);
            if (entityBefore == null) {
                throw new HttpNotFoundException("The Entity was not found");
            }
            entity.setId(id);
            userRepository.save(entity);
        return entity;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser (@PathVariable(value = "id") Integer id) {
        log.debug("Deleting the entity.");
            User deleteEntity = userRepository.findUserById(id);
            if (deleteEntity == null) {
                throw new HttpNotFoundException("The Entity was not found.");
            }
            userRepository.delete(deleteEntity);
    }

    // ------------------------  Operations on MovieGenres ------------------------

    @GetMapping(value = "/{id}/movieGenres")
    public Set<MovieGenres> getMovieGenresByUserId (@PathVariable(value = "id") Integer id) {
        log.debug("Getting the movieGenres by user id.");

        if (id <= 0) {
            throw new HttpUnprocessableEntityException("Entity has invalid id.");
        }

        User searchedEntity = userRepository.findUserById(id);

        if (searchedEntity == null) {
            log.debug("Entity does not exist.");
            throw new HttpNotFoundException("Entity was not found.");
        }

        Set<MovieGenres> movieGenres = searchedEntity.getMovieGenres();

        return movieGenres;
    }

    // ------------------------  Operations on MusicGenres ------------------------

    @GetMapping(value = "/{id}/musicGenres")
    public Set<MusicGenres> getMusicGenresByUserId (@PathVariable(value = "id") Integer id) {
        log.debug("Getting the musicGenres by user id.");

        if (id <= 0) {
            throw new HttpUnprocessableEntityException("Entity has invalid id.");
        }

        User searchedEntity = userRepository.findUserById(id);

        if (searchedEntity == null) {
            log.debug("Entity does not exist.");
            throw new HttpNotFoundException("Entity was not found.");
        }

        Set<MusicGenres> musicGenres = searchedEntity.getMusicGenres();

        return musicGenres;
    }

    // Fixme - do the POST new genres with POST, not GET!
    @GetMapping(value = "/{id}/genre")
    public User postGenres (@PathVariable(value = "id") Integer id,
                              @RequestParam(value = "movieGenre") String movieGenre,
                              @RequestParam(value = "musicGenre") String musicGenre) {
        log.debug("Updating the entity with genres.");
        User entityBefore = userRepository.findUserById(id);
        if (entityBefore == null) {
            throw new HttpNotFoundException("The Entity was not found.");
        }

        if (isNotBlank(movieGenre)) {
            MovieGenres mvG = new MovieGenres(movieGenre);
            entityBefore.addMovieGenres(mvG);
        }
        if (isNotBlank(musicGenre)) {
            MusicGenres msG = new MusicGenres(musicGenre);
            entityBefore.addMusicGenres(msG);
        }

        userRepository.save(entityBefore);
        return entityBefore;
    }
}
