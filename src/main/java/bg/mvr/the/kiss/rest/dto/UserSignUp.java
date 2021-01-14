package bg.mvr.the.kiss.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 06.10.2020.
 * Time: 09:43.
 * Organization: DKIS MOIA.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSignUp {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    @Size(min = 8, max = 30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
