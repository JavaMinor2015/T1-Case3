package entities;

import entities.abs.PersistenceEntity;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
public class PurchaseOrder extends PersistenceEntity {

    private OrderState orderState;
    @ManyToOne
    private List<OrderItem> orderItems;
    @OneToMany(mappedBy = "orders")
    private Customer customer;
}
