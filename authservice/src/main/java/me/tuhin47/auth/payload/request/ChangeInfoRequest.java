package me.tuhin47.auth.payload.request;

import lombok.Data;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.constraints.ValidPassword;

import javax.validation.constraints.Email;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link User}
 */

@Data
public class ChangeInfoRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    @Email
    String email;
    String displayName;
    Boolean enabled;

    @ValidPassword(message = "{Size.userDto.password}")
    byte[] password;

    public void setPassword(String password) {
        this.password = password != null ? password.getBytes() : null;
    }
}