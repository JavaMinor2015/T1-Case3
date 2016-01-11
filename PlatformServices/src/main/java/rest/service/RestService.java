package rest.service;

import entities.abs.PersistenceEntity;
import global.Globals;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import rest.repository.RestRepository;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;

/**
 * Created by alex on 1/5/16.
 *
 * @param <T> the abstract entity for this service.
 */
@CrossOrigin
@RestController
public abstract class RestService<T extends PersistenceEntity> {

    private static final Logger LOGGER = LogManager.getLogger(RestService.class.getName());

    @Setter
    private RestRepository<T> restRepository;

    /**
     * Use this method to set the repository.
     */
    public abstract void initRepository();

    /**
     * Use this method to return the classname of the instance.
     *
     * @return class of the instance.
     */
    public abstract Class<? extends RestService<T>> getClazz();

    /**
     * Retrieve an entity by its id.
     *
     * @param id the entity's id.
     * @return the corresponding entity.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<HateoasResponse> getById(@PathVariable("id") final String id) {
        final T result = restRepository.findOne(id);
        return HateoasUtil.build(
                result,
                HateoasUtil.makeLink(getClazz(), Globals.SELF, id),
                HateoasUtil.makeLink(getClazz(), Globals.NEXT, id),
                HateoasUtil.makeLink(getClazz(), Globals.PREV, id),
                HateoasUtil.makeLink(getClazz(), Globals.UPDATE, id),
                HateoasUtil.makeLink(getClazz(), Globals.DELETE, id)
        );
    }

    /**
     * Retrieve all entities.
     *
     * @return all known entities.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<HateoasResponse> getAll() {
        final List<T> entities = restRepository.findAll();
        final List<HateoasResponse> result = new ArrayList<>(entities.size());

        // do not convert to lambda while Hateoas workaround is in place
        for (T entity : entities) {
            result.add(HateoasUtil.toHateoas(
                    entity,
                    // eww
                    new Link(HateoasUtil.makeLink(getClazz()).getHref() + "/" + entity.getId(), Globals.SELF),
                    new Link(HateoasUtil.makeLink(getClazz()).getHref() + "/" + entity.getId(), Globals.NEXT),
                    new Link(HateoasUtil.makeLink(getClazz()).getHref() + "/" + entity.getId(), Globals.PREV),
                    new Link(HateoasUtil.makeLink(getClazz()).getHref() + "/" + entity.getId(), Globals.UPDATE),
                    new Link(HateoasUtil.makeLink(getClazz()).getHref() + "/" + entity.getId(), Globals.DELETE)
            ));
        }

        return HateoasUtil.build(
                result,
                HateoasUtil.makeLink(getClazz(), Globals.SELF),
                HateoasUtil.makeLink(getClazz(), Globals.NEXT),
                HateoasUtil.makeLink(getClazz(), Globals.PREV),
                HateoasUtil.makeLink(getClazz(), Globals.UPDATE),
                HateoasUtil.makeLink(getClazz(), Globals.DELETE)
        );
    }

    /**
     * Save a new entity.
     *
     * @param t the entity to save.
     * @return a hateoas representation of the posted object.
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<HateoasResponse> post(@RequestBody final T t) {
        restRepository.save(t);
        return HateoasUtil.build(
                t,
                HateoasUtil.makeLink(getClazz(), Globals.SELF, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.NEXT, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.PREV, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.UPDATE, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.DELETE, t.getId())
        );
    }

    /**
     * Update an entity.
     *
     * @param id the entity's id.
     * @param t  the new values as an entity.
     * @return a hateoas representation of the updated object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<HateoasResponse> update(@PathVariable("id") final String id, @RequestBody final T t) {
        if (!id.equals(t.getId())) {
            // TODO freak out
            LOGGER.error("*slap with newspaper*");
        }
        restRepository.save(t);
        return HateoasUtil.build(
                t,
                // id = t.getId()
                HateoasUtil.makeLink(getClazz(), Globals.SELF, id, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.NEXT, id, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.PREV, id, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.UPDATE, id, t.getId()),
                HateoasUtil.makeLink(getClazz(), Globals.DELETE, id, t.getId())
        );
    }

    /**
     * Delete an entity.
     *
     * @param id the entity's id.
     * @return a hateoas representation of the deleted object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity<HateoasResponse> delete(@PathVariable("id") final String id) {
        restRepository.delete(id);
        return HateoasUtil.build(
                id,
                HateoasUtil.makeLink(getClazz(), Globals.SELF, id),
                HateoasUtil.makeLink(getClazz(), Globals.NEXT, id),
                HateoasUtil.makeLink(getClazz(), Globals.PREV, id),
                HateoasUtil.makeLink(getClazz(), Globals.UPDATE, id),
                HateoasUtil.makeLink(getClazz(), Globals.DELETE, id)
        );
    }
}
