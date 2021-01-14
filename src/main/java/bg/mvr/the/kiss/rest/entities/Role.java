package bg.mvr.the.kiss.rest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 23.09.2020.
 * Time: 09:46.
 * Organization: DKIS MOIA.
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority{

    public Role(String authority) {
        this.authority = authority;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "authority",unique = true,updatable = false,nullable = false)
    private String authority;

}
