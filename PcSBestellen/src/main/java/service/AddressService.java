package service;

import entities.Address;
import global.Globals;
import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.AddressRepository;
import rest.service.RestService;
import rest.util.HateoasResponse;
import rest.util.HateoasUtil;

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
    public HttpEntity<HateoasResponse> post(@RequestBody final Address address) {
        Address existing = addressRepository.findByZipcodeAndNumber(address.getZipcode(), address.getNumber());
        if (existing == null) {
            return super.post(address);
        } else {
            return HateoasUtil.build(
                    existing,
                    HateoasUtil.makeLink(getClazz(), Globals.SELF, existing.getId()),
                    HateoasUtil.makeLink(getClazz(), Globals.NEXT, existing.getId()),
                    HateoasUtil.makeLink(getClazz(), Globals.PREV, existing.getId()),
                    HateoasUtil.makeLink(getClazz(), Globals.UPDATE, existing.getId()),
                    HateoasUtil.makeLink(getClazz(), Globals.DELETE, existing.getId())
            );
        }
    }

    @Override
    public Class<? extends RestService<Address>> getClazz() {
        return AddressService.class;
    }
}
