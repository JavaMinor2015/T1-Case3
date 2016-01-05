package entities;

import entities.abs.PersistenceEntity;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
public class OrderItem extends PersistenceEntity {
    @OneToMany(mappedBy = "orderItems")
    private Product product;
    @OneToMany(mappedBy = "orderItems")
    private PurchaseOrder order;
    private int amount;
}
