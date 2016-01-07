package service;

import entities.Product;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ProductRepository;
import rest.service.RestService;




/**
 * Created by alex on 1/5/16.
 */
@RestController
@RequestMapping(value = "/products")
public class ShoppingService extends RestService<Product> {

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
        repository.save(new Product("1", "bicycle", 1200.99, 50));
        repository.save(new Product("2", "tricycle", 400.15, 50));
        repository.save(new Product("3", "handbrake", 15.50, 50));
        repository.save(new Product("4", "headlight", 5.95, 50));
    }

    @Override
    public Class<? extends RestService<Product>> getClazz() {
        return this.getClass();
    }
}
