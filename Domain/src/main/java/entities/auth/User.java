package entities.auth;

import entities.abs.PersistenceEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by alex on 1/13/16.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends PersistenceEntity {
    private static final long serialVersionUID = 6706564855734236928L;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "displayName")
    private String displayName;
}
