package auth.repository;

import entities.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.repository.RestRepository;

/**
 * @author peaseloxes
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, String>, RestRepository<Token> {
    /**
     * Retrieve a token by its token value.
     *
     * @param token the token value.
     * @return a token, or null.
     */
    Token findByToken(final String token);
}
