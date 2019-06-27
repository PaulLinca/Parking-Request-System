package business.AdminOperations;

import dao.DaoFactory;
import dao.ParkingSpotDao;
import dao.RequestDao;
import entities.ParkingSpot;
import entities.Request;
import output.AssignSpotDTO;


public class RetractParkingSpot
{
    private final DaoFactory daoFactory;
    private long parkingSpotId;
    private long requestId;

    public RetractParkingSpot(DaoFactory daoFactory, long parkingSpotId, long requestId)
    {
        this.daoFactory = daoFactory;
        this.parkingSpotId = parkingSpotId;
        this.requestId = requestId;
    }

    public AssignSpotDTO execute()
    {
        ParkingSpotDao parkingSpotDao = daoFactory.getParkingSpotDao();
        RequestDao requestDao = daoFactory.getRequestDao();

        ParkingSpot pSpot = parkingSpotDao.find(parkingSpotId);
        Request request = requestDao.find(requestId);

        AssignSpotDTO assignSpotDTO = new AssignSpotDTO(requestId, parkingSpotId);

        if(pSpot != null && request != null)
        {
            if(!pSpot.isFree())
            {
                request.setStatus("Payment failed");
                request.setParkingSpot(0);
                pSpot.setFree(true);

                requestDao.update(request);
                parkingSpotDao.update(pSpot);
                assignSpotDTO.setMessage("Parking spot " + pSpot.getParkingNo() + " was retracted from request " + request.getId() + ". Client failed to pay.");
            }
            else
            {
                assignSpotDTO.setMessage("Parking spot " + pSpot.getParkingNo() + " couldn't be retracted from request " + request.getId() + ".");
            }
        }

        return assignSpotDTO;
    }
}
