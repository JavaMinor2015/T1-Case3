package service;

import entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import rest.repository.RestRepository;
import rest.service.RestService;

/**
 * Created by alex on 1/5/16.
 */
public class ShoppingService extends RestService<Product> {

    @Autowired
    private RestRepository<Product> repository;

    @Override
    public void initRepository() {
        setRestRepository(repository);
    }
}
