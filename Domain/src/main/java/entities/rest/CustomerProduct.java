package entities.rest;

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

    public CustomerProduct(final String id, final String name, final int amount, final double price) {
        this.setId(id);
        this.name = name;
        this.amount = amount;
        this.price = price;
    }
}