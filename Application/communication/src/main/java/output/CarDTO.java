package output;

import lombok.Data;

import java.util.Date;

@Data
public class CarDTO implements DTO
{
    private final String vin;
    private Date pti;
    private String message;
}
