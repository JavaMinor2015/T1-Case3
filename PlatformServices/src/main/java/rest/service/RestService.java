package rest.service;

import entities.abs.PersistenceEntity;
import java.util.List;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/5/16.
 */
public abstract class RestService<T extends PersistenceEntity> {

    private static final Logger LOGGER = LogManager.getLogger(RestService.class.getName());

    @Setter
    private RestRepository<T> restRepository;

    /**
     * Use this method to set the repository.
     */
    public abstract void initRepository();

    /**
     * Retrieve an entity by its id.
     *
     * @param id the entity's id.
     * @return the corresponding entity.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T getById(@PathVariable("id") final String id) {
        return restRepository.findOne(id);
    }

    /**
     * Retrieve all entities.
     *
     * @return all known entities.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<T> getAll() {
        return restRepository.findAll();
    }

    /**
     * Save a new entity.
     *
     * @param t the entity to save.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void post(@RequestBody final T t) {
        restRepository.save(t);
    }

    /**
     * Update an entity.
     *
     * @param id the entity's id.
     * @param t  the new values as an entity.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") final String id, @RequestBody final T t) {
        if (!id.equals(t.getId())) {
            // TODO freak out
            LOGGER.error("*slap with newspaper*");
        }
        restRepository.save(t);
    }

    /**
     * Delete an entity.
     *
     * @param id the entity's id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void delete(@PathVariable("id") final String id) {
        restRepository.delete(id);
    }

}
