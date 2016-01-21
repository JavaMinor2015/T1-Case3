package util;

import entities.Address;
import entities.BusinessKey;
import entities.Customer;
import entities.VaultEntity;
import entities.abs.PersistenceEntity;
import entities.rest.CustomerOrder;
import entities.rest.CustomerProduct;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by alex on 1/19/16.
 */
public final class EntityConverter {

    private EntityConverter() {
        // hidden constructor
    }

    /**
     * Convert an entity to its VaultEntity.
     *
     * @param entity the persistence entity.
     * @return a corresponding VaultEntity.
     */
    public static VaultEntity convert(final PersistenceEntity entity) {
        if (entity instanceof CustomerProduct) {
            VaultEntity product = convertProduct((CustomerProduct) entity);
            entity.setBusinessKey(product.getBusinessKey());
            return product;
        }
        if (entity instanceof CustomerOrder) {
            VaultEntity order = convertOrder((CustomerOrder) entity);
            for (CustomerProduct customerProduct : ((CustomerOrder) entity).getProducts()) {
                VaultEntity product = convertProduct(customerProduct);
                customerProduct.setBusinessKey(product.getBusinessKey());
                order.addSub(product);
            }
            entity.setBusinessKey(order.getBusinessKey());
            return order;
        }
        if (entity instanceof Customer) {
            VaultEntity customer = convertCustomer((Customer) entity);
            customer.addSub(convertAddress(((Customer) entity).getAddress()));
            customer.addSub(convertAddress(((Customer) entity).getDeliveryAddress()));
            // TODO address business keys
            entity.setBusinessKey(customer.getBusinessKey());
            return customer;
        }
        return new VaultEntity("");
    }

    private static VaultEntity convertAddress(final Address address) {
        return new VaultEntity(BusinessKey.ADDRESS.key(DigestUtils.sha1Hex(combineAddress(address))));
    }

    private static VaultEntity convertCustomer(final Customer customer) {
        String hash = String.valueOf(customer.getClass())
                + customer.getFirstName()
                + customer.getInitials()
                + customer.getLastName()
                + combineAddress(customer.getAddress())
                + combineAddress(customer.getDeliveryAddress());
        return new VaultEntity(BusinessKey.CUSTOMER.key(DigestUtils.sha1Hex(hash)));
    }

    private static VaultEntity convertProduct(final CustomerProduct product) {
        String hash = combineProduct(product);
        return new VaultEntity(BusinessKey.PRODUCT.key(DigestUtils.sha1Hex(hash)));
    }

    private static VaultEntity convertOrder(final CustomerOrder customerOrder) {
        StringBuilder hashBuilder = new StringBuilder();
        hashBuilder.append(String.valueOf(customerOrder.getClass()))
                .append(customerOrder.getTimestamp())
                .append(customerOrder.getOrderStatus())
                .append(customerOrder.getCustomerId())
                .append(customerOrder.getTotalPrice());
        for (CustomerProduct product : customerOrder.getProducts()) {
            hashBuilder.append(combineProduct(product));
        }
        return new VaultEntity(BusinessKey.ORDER.key(DigestUtils.sha1Hex(hashBuilder.toString())));
    }

    private static String combineAddress(final Address address) {
        return address.getClass()
                + address.getCity()
                + address.getNumber()
                + address.getStreetname()
                + address.getZipcode();
    }

    private static String combineProduct(CustomerProduct product) {
        return String.valueOf(product.getClass())
                // products should have id's already, include them
                + product.getId()
                + product.getVersion()
                + product.getName()
                + product.getAmount()
                + product.getPrice();
    }
}
