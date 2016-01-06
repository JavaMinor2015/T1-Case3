package entities;

import entities.abs.PersistenceEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
@Entity
public class Product extends PersistenceEntity {
    private static final long serialVersionUID = -6842446943022957399L;
    private String name;
    private double price;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
