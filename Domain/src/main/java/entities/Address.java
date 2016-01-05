package entities;

import entities.abs.PersistenceEntity;
import java.util.List;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tom on 5-1-2016.
 */
@Getter
@Setter
public class Address extends PersistenceEntity {
    private String city;
    private String streetname;
    private String number;
    private String zipcode;
    @OneToMany(mappedBy = "address")
    private List<Customer> customersAddress;
    @OneToMany(mappedBy = "deliveryAddress")
    private List<Customer> customersDeliveryAddress;
}
