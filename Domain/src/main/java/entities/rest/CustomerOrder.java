package entities.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entities.abs.PersistenceEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author peaseloxes
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Document(collection = "CustomerOrder")
public class CustomerOrder extends PersistenceEntity {
    private static final long serialVersionUID = -359500118765361885L;

    private String orderId;
    private String customerId;
    private String orderStatus;
    private String deliveryStatus;
    private double totalPrice;

    @JsonIgnore
    private long timestamp;

    @ElementCollection(targetClass = CustomerProduct.class)
    private List<CustomerProduct> products = new ArrayList<>();
}
