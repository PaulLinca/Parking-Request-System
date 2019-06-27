package output;

import lombok.Data;

@Data
public class ParkingSpotDTO implements DTO
{
    public final int parkingNo;
    public final long lotId;
    public boolean isFree = true;
}
