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
public class Address extends PersistenceEntity {
    private static final long serialVersionUID = -6059929196973281627L;
    private String city;
    private String streetname;
    private String number;
    private String zipcode;
    @OneToMany(mappedBy = "address")
    private List<Customer> customersAddress;
    @OneToMany(mappedBy = "deliveryAddress")
    private List<Customer> customersDeliveryAddress;
}
