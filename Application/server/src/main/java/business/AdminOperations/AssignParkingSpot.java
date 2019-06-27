package business.AdminOperations;

import dao.DaoFactory;
import dao.ParkingSpotDao;
import dao.RequestDao;
import entities.ParkingSpot;
import entities.Request;
import output.AssignSpotDTO;

public class AssignParkingSpot
{
    private final DaoFactory daoFactory;
    private long parkingSpotId;
    private long requestId;

    public AssignParkingSpot(DaoFactory daoFactory, long parkingSpotId, long requestId)
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
            if(pSpot.isFree())
            {
                request.setStatus("Parking spot assigned");
                request.setParkingSpot(pSpot.getParkingNo());
                pSpot.setFree(false);

                requestDao.update(request);
                parkingSpotDao.update(pSpot);
                assignSpotDTO.setMessage("Parking spot " + pSpot.getParkingNo() + " was assigned for request " + request.getId() + ".");
            }
            else
            {
                assignSpotDTO.setMessage("Parking spot " + pSpot.getParkingNo() + " couldn't be assigned for request " + request.getId() + ".");
            }
        }

        return assignSpotDTO;
    }
}
