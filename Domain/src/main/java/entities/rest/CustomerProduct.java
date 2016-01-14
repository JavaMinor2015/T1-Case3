package entities.rest;

import entities.Product;
import entities.abs.PersistenceEntity;
import javax.persistence.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author peaseloxes
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CustomerProduct")
public class CustomerProduct extends PersistenceEntity {
    private static final long serialVersionUID = -7699374375063811525L;
    private String name;
    private int amount;
    private double price;

    /**
     * CustomerProduct constructor.
     *
     * @param id     the product id.
     * @param name   the product name.
     * @param price  the product price.
     * @param amount the amount in stock.
     */
    public CustomerProduct(final String id, final String name, final int amount, final double price) {
        this.setId(id);
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    /**
     * CustomerProduct constructor.
     *
     * @param product the product to extract data from.
     */
    public CustomerProduct(final Product product) {
        this.setId(product.getId());
        this.name = product.getName();
        this.amount = product.getStock();
        this.price = product.getPrice();
    }
}
