package business.UserOperations;

import dao.DaoFactory;
import dao.RequestDao;
import entities.ParkingLot;
import entities.Request;
import output.DeleteRequestDTO;


public class DeleteRequest
{
    private final DaoFactory daoFactory;
    private long id;

    public DeleteRequest(DaoFactory daoFactory, long id)
    {
        this.daoFactory = daoFactory;
        this.id = id;
    }

    public DeleteRequestDTO deleteRequest()
    {
        //initialize dao
        RequestDao requestDao = daoFactory.getRequestDao();
        //search for the request in the DB
        Request request = requestDao.find(id);

        DeleteRequestDTO requestDto = new DeleteRequestDTO(id);

        //check if the request exists
        if(request != null)
        {
            for(ParkingLot p: request.getParkingLots())
            {
                p.getRequests().remove(request);
            }

            //remove request from DB
            requestDao.delete(request);

            requestDto.setMessage("Request " + id + " has been deleted successfully.");
            requestDto.setStatus("Deleted.");
        }
        else
        {
            requestDto.setMessage("Request doesn't exist.");
        }
        return requestDto;
    }
}
