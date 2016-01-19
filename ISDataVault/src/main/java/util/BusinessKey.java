package util;

import entities.abs.PersistenceEntity;
import entities.rest.CustomerProduct;

/**
 * Created by alex on 1/19/16.
 */
public enum BusinessKey {


    PRODUCT(CustomerProduct.class, "PROD");
    private String prod;
    private Class<? extends PersistenceEntity> clazz;

    BusinessKey(final Class<? extends PersistenceEntity> customerProductClass, final String prod) {
        this.prod = prod;
        this.clazz = customerProductClass;
    }
}
