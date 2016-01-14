package repository;

import entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/14/16.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, String>, RestRepository<Address> {

    /**
     * Retrieve an address by its zipcode and number.
     *
     * @param zipcode the zip code.
     * @param number  street nunmber.
     * @return the corresponding address, or null.
     */
    Address findByZipcodeAndNumber(final String zipcode, final String number);
}
