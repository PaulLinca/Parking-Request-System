package business.TransactionScripts;

import dao.DaoFactory;
import entities.ParkingLot;
import entities.Request;
import output.DTO;
import output.RequestDTO;
import output.RequestDTOBuilder;
import output.RequestListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ViewRequestsTS implements TransactionScript
{
    private final DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);
    private long lotId;

    public ViewRequestsTS(long lotId)
    {
        this.lotId = lotId;
    }

    @Override
    public RequestListDTO execute()
    {
        List<RequestDTO> requestDTOS = new ArrayList<>();
        List<Request> requests = daoFactory.getRequestDao().selectAllRequests(lotId);
        for(Request r: requests)
        {
            Set<ParkingLot> s = r.getParkingLots();
            ParkingLot[] p = new ParkingLot[10];
            s.toArray(p);
            RequestDTO d = RequestDTOBuilder.createRequestDTO().date(r.getDate()).status(r.getStatus()).vin(r.getCar().getVin()).pLot((int)p[0].getId()).build();
            requestDTOS.add(d);
        }
        System.out.println(requests);
        return new RequestListDTO(requestDTOS);
    }
}
