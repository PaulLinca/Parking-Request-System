package output;

import lombok.Data;

@Data
public class ParkingLotDTO implements DTO
{
    public final long id;
    public String address;
}
