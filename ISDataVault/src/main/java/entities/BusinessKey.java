package entities;

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

    /**
     * Generate a key.
     *
     * @param id the entity id.
     * @return a key. Warning: won't open doors.
     */
    public String key(final String id) {
        return prod;
    }
}
