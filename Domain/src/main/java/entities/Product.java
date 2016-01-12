package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.abs.PersistenceEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Tom on 5-1-2016.
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends PersistenceEntity {
    private static final long serialVersionUID = -6842446943022957399L;
    private String name;
    private double price;
    private int stock;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    /**
     * Product constructor.
     *
     * @param id    the product id.
     * @param name  the product name.
     * @param price the product price.
     * @param stock the amount in stock.
     */
    public Product(final String id, final String name, final double price, final int stock) {
        this.setId(id);
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
