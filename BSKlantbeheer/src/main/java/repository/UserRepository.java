package repository;

import entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * Created by alex on 1/13/16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, RestRepository<User> {
    User findByEmail(final String email);

    User getByDisplayName(final String name);
}
