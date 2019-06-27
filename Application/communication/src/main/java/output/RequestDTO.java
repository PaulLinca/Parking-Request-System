package output;

import lombok.Data;

import java.util.Date;

@Data
public class RequestDTO implements DTO
{
    private final Date date;
    private String status;
    private String vin;
    private int pLot;
    private String message;
    private int pSpot = 0;
}
