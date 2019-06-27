package output;

import lombok.Data;

@Data
public class AssignSpotDTO implements DTO
{
    private final long requestId;
    private final long parkingSpotId;
    private String message;
}
