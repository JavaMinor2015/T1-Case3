package util;

/**
 * Created by alex on 1/19/16.
 */
public enum BusinessKey {

    PRODUCT("PROD"),
    ORDER("ORD"),
    CUSTOMER("CUST");

    private final String prod;

    BusinessKey(final String prod) {
        this.prod = prod;
    }

    public String key(final String id) {
        return prod;
    }
}
