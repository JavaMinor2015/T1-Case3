package util;

import entities.BusinessKey;
import entities.VaultEntity;
import entities.abs.PersistenceEntity;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;

/**
 * Created by alex on 1/19/16.
 */
public class EntityConverter {

    /**
     * Convert an entity to its VaultEntity.
     *
     * @param entity the persistence entity.
     * @return a corresponding VaultEntity.
     */
    public static VaultEntity convert(final PersistenceEntity entity) {
        if (entity instanceof CustomerProduct) {
            return convertProduct((CustomerProduct) entity);
        }
        if (entity instanceof CustomerOrder) {
            VaultEntity order =  convertOrder((CustomerOrder)entity);
            for (CustomerProduct customerProduct : ((CustomerOrder) entity).getProducts()) {
                order.addSub(convertProduct(customerProduct));
            }
        }
        return new VaultEntity("");
    }

    private static VaultEntity convertProduct(final CustomerProduct product) {
        return new VaultEntity(BusinessKey.PRODUCT.key(product.getId()));
    }

    private static VaultEntity convertOrder(final CustomerOrder customerOrder) {
        return new VaultEntity(BusinessKey.ORDER.key(customerOrder.getOrderId()));
    }
}
