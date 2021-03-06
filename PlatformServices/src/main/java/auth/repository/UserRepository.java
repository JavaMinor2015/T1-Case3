package auth.repository;

import entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/13/16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, RestRepository<User> {

    /**
     * Find a user by his/her email.
     *
     * @param email the email address.
     * @return a user or null.
     */
    User findByEmail(final String email);

    /**
     * Find a user by his/her customer id.
     *
     * @param id the id.
     * @return a user or null.
     */
    User getByCustomerId(final String id);
}
