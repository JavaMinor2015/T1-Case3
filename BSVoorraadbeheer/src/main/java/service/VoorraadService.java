package service;

import entities.Product;
import entity.BuildStatus;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import peaseloxes.spring.annotations.WrapWithLink;
import peaseloxes.spring.annotations.WrapWithLinks;
import repository.BuildRepository;
import repository.ProductRepository;
import rest.service.RestService;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;

/**
 * Created by alex on 1/12/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/stock/api")
@EnableScheduling
public class VoorraadService extends RestService<Product> {

    @Autowired
    @Setter
    private ProductRepository productRepository;

    @Autowired
    @Setter
    private BuildRepository buildRepository;

    @Autowired
    @Setter
    private DBWriter<Product> writer;

    @Override
    public void initRepository() {
        setRestRepository(productRepository);
    }

    @Override
    @WrapWithLink
    public HttpEntity<HateoasResponse> getAll(final HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @WrapWithLink
    public HttpEntity<HateoasResponse> getById(@PathVariable("id") final String id,
                                               final HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @WrapWithLink
    public HttpEntity<HateoasResponse> post(@RequestBody final Product product,
                                            final HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @WrapWithLink
    public HttpEntity<HateoasResponse> delete(@PathVariable("id") final String id,
                                              final HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @WrapWithLink
    public HttpEntity<HateoasResponse> update(@PathVariable("id") final String id,
                                              @RequestBody final Product product,
                                              final HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Request a build of the database.
     *
     * @param request the Servlet Request.
     * @return the acceptance of the build and a status identifier token.
     */
    @RequestMapping(value = "/request")
    @WrapWithLinks(links = {
            @WrapWithLink(rel = WrapWithLink.Type.SELF),
    })
    public HttpEntity<HateoasResponse> requestBuild(final HttpServletRequest request) {
        final BuildStatus status = new BuildStatus(
                String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond())
        );
        buildRepository.save(status);
        writer.write(productRepository, Product.class, status.getId());

        HttpEntity<HateoasResponse> response = HateoasUtil.build(status.getId());
        response.getBody().addAll(
                WrapWithLink.Type.STATUS.link(request, "/status/" + status.getId()),
                WrapWithLink.Type.NEXT.link(request, "/status/" + status.getId())
        );
        return response;
    }

    /**
     * Given a status identifier request the status of the requested build.
     *
     * @param id      the status identifier.
     * @param request the Servlet Request.
     * @return true if done, false otherwise.
     */
    @RequestMapping(value = "/request/status/{identifier}")
    @WrapWithLink
    public HttpEntity<HateoasResponse> requestStatus(@PathVariable("identifier") final String id,
                                                     final HttpServletRequest request) {
        if (buildRepository.exists(id)) {
            return HateoasUtil.build(buildRepository.findOne(id).isReady());
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public Class<? extends RestService<Product>> getClazz() {
        return VoorraadService.class;
    }
}
