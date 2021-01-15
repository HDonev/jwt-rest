package bg.mvr.the.kiss.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.01.2021.
 * Time: 11:20.
 * Organization: DKIS MOIA.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeRole {
    @NotNull
    @Email
    private String email;

    @NotNull
    private Set<RoleForAdmin> authorities;

}
