package bg.mvr.the.kiss.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.01.2021.
 * Time: 13:24.
 * Organization: DKIS MOIA.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserForAdmin {
    private Long id;

    private String email;

    private String username;

    private Set<RoleForAdmin> authorities;

    private LocalDateTime createdDate;

    private Long lastModifiedBy;

    private LocalDateTime lastModifiedDate;

}
