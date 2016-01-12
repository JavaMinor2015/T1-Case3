package service;

import entities.Product;
import entity.BuildStatus;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
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
    private ProductRepository productRepository;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private DBWriter<Product> writer;

    @Override
    public void initRepository() {
        setRestRepository(productRepository);
    }

    @Override
    public HttpEntity<HateoasResponse> getAll() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public HttpEntity<HateoasResponse> getById(@PathVariable("id") final String id) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public HttpEntity<HateoasResponse> post(@RequestBody final Product product) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public HttpEntity<HateoasResponse> delete(@PathVariable("id") final String id) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public HttpEntity<HateoasResponse> update(@PathVariable("id") final String id, @RequestBody final Product product) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(value = "/request")
    public HttpEntity<HateoasResponse> requestBuild() {
        final BuildStatus status = new BuildStatus(String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()));
        buildRepository.save(status);
        writer.write(productRepository, Product.class, status.getId());
        return HateoasUtil.build(
                status.getId(),
                new Link(HateoasUtil.makeLink(getClazz()).getHref() + "/" + status.getId(), "status")
        );
    }

    @RequestMapping(value = "/request/{identifier}")
    public HttpEntity<HateoasResponse> requestStatus(@PathVariable("identifier") final String id) {
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
