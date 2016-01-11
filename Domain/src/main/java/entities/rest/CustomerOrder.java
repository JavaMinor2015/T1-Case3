package entities.rest;

import entities.abs.PersistenceEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.*;

/**
 * @author peaseloxes
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CustomerOrder extends PersistenceEntity {
    // TODO mongodb persistence

    private String orderId;
    private String customerId;
    private String orderStatus;
    private String deliveryStatus;
    private double totalPrice;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
    private List<CustomerProduct> products = new ArrayList<>();




}
