package output;

import java.util.Date;

public class RequestDTOBuilder implements DTO
{
    private Date date;
    private String status;
    private String vin;
    private int pLot;
    private String message;

    public static RequestDTOBuilder createRequestDTO()
    {
        return new RequestDTOBuilder();
    }

    public RequestDTOBuilder date(Date date)
    {
        this.date = date;
        return this;
    }

    public RequestDTOBuilder status(String status)
    {
        this.status = status;
        return this;
    }

    public RequestDTOBuilder vin(String vin)
    {
        this.vin = vin;
        return this;
    }

    public RequestDTOBuilder pLot(int pLot)
    {
        this.pLot = pLot;
        return this;
    }

    public RequestDTOBuilder message(String message)
    {
        this.message = message;
        return this;
    }

    public RequestDTO build()
    {
        RequestDTO requestDTO = new RequestDTO(date);
        requestDTO.setPLot(pLot);
        requestDTO.setVin(vin);
        requestDTO.setMessage(message);
        requestDTO.setStatus(status);

        return requestDTO;
    }
}
