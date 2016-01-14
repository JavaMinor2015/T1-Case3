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
    Address findByZipcodeAndNumber(final String zipcode, final String number);
}
