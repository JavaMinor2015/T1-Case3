package entities;

import entities.abs.PersistenceEntity;
import java.util.List;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
public class Product extends PersistenceEntity {
    private String name;
    private double price;
    @ManyToOne
    private List<OrderItem> orderItems;
}
