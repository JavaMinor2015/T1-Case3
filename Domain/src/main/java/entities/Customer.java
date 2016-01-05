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
public class Customer extends PersistenceEntity {
    private String firstName;
    private String lastName;
    private String initials;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Address deliveryAddress;
    @OneToMany(mappedBy = "customer")
    private List<PurchaseOrder> orders;
}
