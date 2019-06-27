package business.TransactionScripts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DaoFactory;

import entities.ParkingLot;
import entities.ParkingSpot;
import entities.Request;
import output.NotificationDTO;
import output.RequestDTO;
import output.RequestDTOBuilder;
import server.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class AssignSpotTS implements TransactionScript
{
    private final DaoFactory daoFactory=DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);
    private String vin;

    public AssignSpotTS(String vin)
    {
        this.vin = vin;
    }

    @Override
    public RequestDTO execute()
    {
        Request request = daoFactory.getRequestDao().selectByVin(vin);
        Set<ParkingLot> s = request.getParkingLots();
        ParkingLot[] p = new ParkingLot[10];
        s.toArray(p);
        ParkingLot parkingLot = p[0];

        List<ParkingSpot> parkingSpots = daoFactory.getParkingSpotDao().selectAll(parkingLot.getId());

        RequestDTO d = RequestDTOBuilder.createRequestDTO().date(request.getDate()).vin(vin).pLot((int) parkingLot.getId()).build();


        for(ParkingSpot spot: parkingSpots)
        {
            if(spot.isFree())
            {
                d.setPSpot(spot.getParkingNo());
                d.setStatus("Assigned");

                request.setParkingSpot(spot.getParkingNo());
                request.setStatus("Assigned");

                daoFactory.getRequestDao().update(request);

                ParkingSpot temp = daoFactory.getParkingSpotDao().find(spot.getId());
                temp.setFree(false);
                daoFactory.getParkingSpotDao().update(temp);

                Server.getInstance().getObservers().forEach(this::sendNotification);

                return d;
            }
        }

        return null;
    }

    private void sendNotification(Socket socket)
    {
        if (socket.isClosed())
        {
            return;
        }
        try
        {
            Gson gson = new GsonBuilder().create();;
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            NotificationDTO notification = new NotificationDTO("Assigned spot");
            String json = gson.toJson(notification);
            out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}