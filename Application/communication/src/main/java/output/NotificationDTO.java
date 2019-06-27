package output;

import lombok.Data;

@Data
public class NotificationDTO implements DTO
{
    private final String message;
}
