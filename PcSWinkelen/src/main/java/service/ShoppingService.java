package service;

import entities.Product;
import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaseloxes.toolbox.util.RandUtil;
import repository.ProductRepository;
import rest.service.RestService;


/**
 * Created by alex on 1/5/16.
 */
@RestController
@RequestMapping(value = "/products")
public class ShoppingService extends RestService<Product> {

    private static final int HUNDRED = 100;
    private static final int THOUSAND = 1000;

    @Autowired
    @Setter
    private ProductRepository repository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(repository);
        repository.save(new Product("1", "bicycle", RandUtil.rInt(THOUSAND), RandUtil.rInt(HUNDRED)));
        repository.save(new Product("2", "tricycle", RandUtil.rInt(THOUSAND), RandUtil.rInt(HUNDRED)));
        repository.save(new Product("3", "handbrake", RandUtil.rInt(THOUSAND), RandUtil.rInt(HUNDRED)));
        repository.save(new Product("4", "headlight", RandUtil.rInt(THOUSAND), RandUtil.rInt(HUNDRED)));
    }

    @Override
    public Class<? extends RestService<Product>> getClazz() {
        return this.getClass();
    }
}
