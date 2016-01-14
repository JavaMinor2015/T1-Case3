package service;

import entities.Address;
import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.AddressRepository;
import rest.service.RestService;

/**
 * Created by alex on 1/14/16.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/addresses")
public class AddressService extends RestService<Address> {

    @Autowired
    @Setter
    private AddressRepository addressRepository;

    @PostConstruct
    @Override
    public void initRepository() {
        setRestRepository(addressRepository);
        Address sample = new Address();
        sample.setId("1");
        sample.setZipcode("1234 AB");
        sample.setStreetname("Elm");
        sample.setNumber("5A");
        sample.setCity("Sin");
        addressRepository.save(sample);
    }

    @Override
    public Class<? extends RestService<Address>> getClazz() {
        return AddressService.class;
    }
}
