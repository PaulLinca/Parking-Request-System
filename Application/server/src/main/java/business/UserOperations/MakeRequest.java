package business.UserOperations;

import dao.DaoFactory;
import dao.RequestDao;
import entities.Car;
import entities.ParkingLot;
import entities.Request;
import entities.builders.RequestBuilder;
import output.RequestDTO;


import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class MakeRequest
{
    private final DaoFactory daoFactory;

    public MakeRequest(DaoFactory daoFactory)
    {
        this.daoFactory = daoFactory;
    }

    public RequestDTO registerRequest(Car car, Set<ParkingLot> parkingLots)
    {
        //initialize DAO
        RequestDao requestDao = daoFactory.getRequestDao();

        Request request;

        Date currentTime = Calendar.getInstance().getTime();
        RequestDTO requestDto = new RequestDTO(currentTime);

        //build request
        request = RequestBuilder.createRequestBuilder()
                .date(Calendar.getInstance().getTime())
                .status("Pending approval")
                .car(car)
                .build();

        //add desired parking lots to the request
        for(ParkingLot pLot: parkingLots)
        {
            request.addParkingLot(pLot);
        }

        //add this request to its parking lots' lists
        for(ParkingLot pLot: parkingLots)
        {
            pLot.getRequests().add(request);
        }

        //add request in DB
        requestDao.insert(request);
        //update DTO with needed data
        requestDto.setStatus("Pending approval");
        requestDto.setMessage("The parking request for car " + car.getVin() + " has been registered.\nParking lots applied for:");

        for(ParkingLot pLot: request.getParkingLots())
        {
            requestDto.setMessage(requestDto.getMessage() + " " + pLot.getId());
        }
        requestDto.setMessage(requestDto.getMessage() + ".");

        return requestDto;
    }
}