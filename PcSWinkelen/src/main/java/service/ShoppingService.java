package service;

import entities.Product;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ProductRepository;
import rest.service.RestService;

/**
 * Created by alex on 1/5/16.
 */
@RestController
@RequestMapping(value = "/products")
@EnableJpaRepositories(value = "repository")
public class ShoppingService extends RestService<Product> {

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
    }
}
