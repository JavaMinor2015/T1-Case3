package entities.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.abs.PersistenceEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.*;

/**
 * @author peaseloxes
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProduct extends PersistenceEntity {
    private String name;
    private int amount;
    private double price;

    @JsonIgnore
    @ManyToOne
    private CustomerOrder customerOrder;

    public CustomerProduct(final String id, final String name, final int amount, final double price) {
        this.setId(id);
        this.name = name;
        this.amount = amount;
        this.price = price;
    }
}