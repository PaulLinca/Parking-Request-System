package business.TransactionScripts;


import dao.DaoFactory;
import entities.ParkingLot;
import entities.Request;
import output.RequestDTO;
import output.RequestDTOBuilder;
import output.RequestListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InitializeRequestsTS implements TransactionScript
{
    private final DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);

    @Override
    public RequestListDTO execute()
    {
        List<RequestDTO> requestDTOS = new ArrayList<>();
        List<Request> requests = daoFactory.getRequestDao().selectAll();
        for(Request r: requests)
        {
            Set<ParkingLot> s = r.getParkingLots();
            ParkingLot[] p = new ParkingLot[10];
            s.toArray(p);
            RequestDTO d = RequestDTOBuilder.createRequestDTO().date(r.getDate()).status(r.getStatus()).vin(r.getCar().getVin()).pLot((int)p[0].getId()).build();
            requestDTOS.add(d);
        }
        System.out.println(requestDTOS);
        return new RequestListDTO(requestDTOS);
    }
}
