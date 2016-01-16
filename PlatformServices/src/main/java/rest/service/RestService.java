package rest.service;

import com.google.common.collect.Lists;
import entities.abs.PersistenceEntity;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import peaseloxes.spring.annotations.LoginRequired;
import peaseloxes.spring.annotations.WrapWithLink;
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
     * Retrieve the options for this rest service.
     *
     * @return a header with allowed options.
     */
    @LoginRequired(false)
    @RequestMapping(value = "", method = RequestMethod.OPTIONS)
    public HttpEntity<String> options() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("allow", "HEAD,GET,PUT,DELETE,POST,OPTIONS");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Retrieve an entity by its id.
     *
     * @param id      the entity's id.
     * @param request the Servlet Request.
     * @return the corresponding entity.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @LoginRequired
    @WrapWithLink
    public HttpEntity<HateoasResponse> getById(@PathVariable("id") final String id,
                                               final HttpServletRequest request) {
        final T result = restRepository.findOne(id);
        return HateoasUtil.build(result);
    }

    /**
     * Retrieve all entities.
     *
     * @param request the Servlet Request.
     * @return all known entities.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @LoginRequired
    @WrapWithLink
    public HttpEntity<HateoasResponse> getAll(final HttpServletRequest request) {
        final List<T> entities = Lists.newArrayList(restRepository.findAll());
        return HateoasUtil.build(
                entities);
    }

    /**
     * Save a new entity.
     *
     * @param t       the entity to save.
     * @param request the Servlet Request.
     * @return a hateoas representation of the posted object.
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @LoginRequired
    @WrapWithLink
    public HttpEntity<HateoasResponse> post(@RequestBody final T t,
                                            final HttpServletRequest request) {
        restRepository.save(t);
        return HateoasUtil.build(t);
    }

    /**
     * Update an entity.
     *
     * @param id      the entity's id.
     * @param t       the new values as an entity.
     * @param request the Servlet Request.
     * @return a hateoas representation of the updated object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @LoginRequired
    @WrapWithLink
    public HttpEntity<HateoasResponse> update(@PathVariable("id") final String id,
                                              @RequestBody final T t,
                                              final HttpServletRequest request) {
        if (!id.equals(t.getId())) {
            // TODO freak out
            LOGGER.error("*spray with water*");
        }
        restRepository.save(t);
        return HateoasUtil.build(t);
    }

    /**
     * Delete an entity.
     *
     * @param id      the entity's id.
     * @param request the Servlet Request.
     * @return a hateoas representation of the deleted object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @LoginRequired
    @WrapWithLink
    public HttpEntity<HateoasResponse> delete(@PathVariable("id") final String id,
                                              final HttpServletRequest request) {
        restRepository.delete(id);
        return HateoasUtil.build(id);
    }
}
