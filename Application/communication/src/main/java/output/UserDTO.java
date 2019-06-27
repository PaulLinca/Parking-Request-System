package output;

import lombok.Data;

@Data
public class UserDTO implements DTO
{
    private final String mail;
    private String name;
    private String message;
    private boolean isAdmin;
}
