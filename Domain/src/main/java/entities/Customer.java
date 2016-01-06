package entities;

import entities.abs.PersistenceEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
@Entity
public class Customer extends PersistenceEntity {
    private static final long serialVersionUID = 2987510524408242766L;
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
