package entities;

/**
 * Created by alex on 1/19/16.
 */
public enum BusinessKey {

    PRODUCT("PROD"),
    ORDER("ORD"),
    CUSTOMER("CUST"),
    ADDRESS("ADDR");

    private final String prod;

    BusinessKey(final String prod) {
        this.prod = prod;
    }

    /**
     * Generate a key.
     *
     * @param hash the entity hash.
     * @return a key. Warning: won't open doors.
     */
    public String key(final String hash) {
        return prod + "_" + hash;
    }
}
