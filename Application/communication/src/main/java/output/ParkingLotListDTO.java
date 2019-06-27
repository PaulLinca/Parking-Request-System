package output;

import lombok.Data;

import java.util.List;

@Data
public class ParkingLotListDTO implements DTO
{
    private final List<ParkingLotDTO> list;
}
