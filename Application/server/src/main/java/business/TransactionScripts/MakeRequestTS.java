package business.TransactionScripts;

import dao.DaoFactory;
import entities.Car;
import entities.ParkingLot;
import entities.Request;
import entities.User;
import entities.builders.RequestBuilder;
import output.RequestDTO;
import output.RequestDTOBuilder;

import java.util.Calendar;
import java.util.Date;

public class MakeRequestTS implements TransactionScript
{
    private final DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);
    private String mail;
    private String vin;
    private long lotId;

    public MakeRequestTS(String mail, String vin, long lotId)
    {
        this.mail = mail;
        this.vin = vin;
        this.lotId = lotId;
    }

    @Override
    public RequestDTO execute()
    {
        RequestDTO toReturn;
        Car car = daoFactory.getCarDao().findByVin(vin);
        ParkingLot parkingLot = daoFactory.getParkingLotDao().find(lotId);

        Date currentTime = Calendar.getInstance().getTime();

        Request request = RequestBuilder.createRequestBuilder().car(car).date(currentTime).status("Pending").build();
        request.addParkingLot(parkingLot);

        daoFactory.getRequestDao().insert(request);

        toReturn = RequestDTOBuilder.createRequestDTO().date(currentTime).pLot((int)lotId).status("Pending").vin(vin).build();
        return toReturn;
    }
}
