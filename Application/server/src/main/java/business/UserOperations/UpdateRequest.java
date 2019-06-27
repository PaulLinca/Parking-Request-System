package business.UserOperations;

import dao.DaoFactory;
import dao.RequestDao;
import entities.ParkingLot;
import entities.Request;
import output.RequestDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class UpdateRequest
{
    private final DaoFactory daoFactory;
    private long id;

    public UpdateRequest(DaoFactory daoFactory, long id)
    {
        this.daoFactory = daoFactory;
        this.id = id;
    }

    public RequestDTO updateRequest(Set<ParkingLot> parkingLots)
    {
        //initialize dao
        RequestDao requestDao = daoFactory.getRequestDao();
        //search for the request in the DB
        Request request = requestDao.find(id);
        Date currentTime = Calendar.getInstance().getTime();

        RequestDTO requestDTO = new RequestDTO(currentTime);

        //check if the request exits
        if(request != null)
        {
            request.setDate(currentTime);
            for(ParkingLot p: request.getParkingLots())
            {
                request.removeParkingLot(p);
            }

            //add desired parking lots to the request
            for(ParkingLot pLot: parkingLots)
            {
                request.addParkingLot(pLot);
            }

            requestDao.update(request);

            requestDTO.setStatus("Pending approval");
            requestDTO.setMessage("The parking request for car " + request.getCar().getVin() + " has been updated.");
        }

        return requestDTO;
    }
}
