package business.TransactionScripts;

import dao.DaoFactory;
import entities.ParkingLot;
import output.*;

import java.util.ArrayList;
import java.util.List;

public class InitializeLotsTS implements TransactionScript
{
    private final DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);

    @Override
    public ParkingLotListDTO execute()
    {
        List<ParkingLotDTO> parkingLotDTOS = new ArrayList<>();
        List<ParkingLot>  parkingLots= daoFactory.getParkingLotDao().selectAll();
        for(ParkingLot p: parkingLots)
        {
            ParkingLotDTO parkingLotDTO = new ParkingLotDTO(p.getId());
            parkingLotDTO.setAddress(p.getAddress());
        }
        System.out.println(parkingLotDTOS);
        return new ParkingLotListDTO(parkingLotDTOS);
    }
}
