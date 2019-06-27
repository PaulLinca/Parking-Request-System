package business.AdminOperations;

import dao.DaoFactory;
import dao.ParkingLotDao;
import entities.ParkingLot;
import output.ParkingLotDTO;

import java.util.ArrayList;
import java.util.List;

public class ViewParkingLots
{
    private final DaoFactory daoFactory;

    public ViewParkingLots(DaoFactory daoFactory)
    {
        this.daoFactory = daoFactory;
    }

    public List<ParkingLotDTO> execute()
    {
        ParkingLotDao parkingLotDao = daoFactory.getParkingLotDao();
        List<ParkingLotDTO> parkingLotDTOs = new ArrayList<>();
        List<ParkingLot> allParkingLots = parkingLotDao.selectAll();

        for(ParkingLot lot: allParkingLots)
        {
            ParkingLotDTO dto = new ParkingLotDTO(lot.getId());
            dto.setAddress(lot.getAddress());
            parkingLotDTOs.add(dto);
        }

        return parkingLotDTOs;
    }
}
