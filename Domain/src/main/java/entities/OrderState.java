package entities;

/**
 * Created by Tom on 5-1-2016.
 */
public enum OrderState {
    RUNNING("Openstaand");

    private String name;

    OrderState(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}