package business.AdminOperations;

import dao.DaoFactory;
import dao.RequestDao;
import entities.Request;
import output.RequestDTO;

import java.util.ArrayList;
import java.util.List;

public class ViewAllRequestsForLot
{
    private final DaoFactory daoFactory;
    private long id;

    public ViewAllRequestsForLot(DaoFactory daoFactory, long id)
    {
        this.daoFactory = daoFactory;
        this.id = id;
    }

    public List<RequestDTO> execute()
    {
        RequestDao requestDao = daoFactory.getRequestDao();
        List<RequestDTO> requestDTOS = new ArrayList<>();
        List<Request> allLotRequest = requestDao.selectAllRequests(id);

        for(Request request: allLotRequest)
        {
            RequestDTO dto = new RequestDTO(request.getDate());
            dto.setStatus(request.getStatus());
            requestDTOS.add(dto);
        }

        return requestDTOS;
    }

}
