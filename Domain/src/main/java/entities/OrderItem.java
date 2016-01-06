package entities;

import entities.abs.PersistenceEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
@Entity
public class OrderItem extends PersistenceEntity {
    private static final long serialVersionUID = 2430048350928299321L;
    @ManyToOne
    private Product product;

    @ManyToOne
    private PurchaseOrder order;
    private int amount;
}
