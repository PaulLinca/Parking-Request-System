import dao.DaoFactory;
import entities.*;
import entities.builders.*;
import server.Server;


import java.util.Calendar;
import java.util.Date;

public class ServerMain
{
    public static void main(String[] args)
    {
        DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);

        User user1 = UserBuilder.createUserBuilder().mail("linca").password("paul").admin(false).name("Linca Paul").build();
        User user2 = UserBuilder.createUserBuilder().mail("paul").password("linca").admin(true).name("Paul Linca").build();

        daoFactory.getUserDao().insert(user1);
        daoFactory.getUserDao().insert(user2);

        user1 = daoFactory.getUserDao().findByMail("linca");
        user2 = daoFactory.getUserDao().findByMail("paul");

        Date date = new Date(Calendar.getInstance().getTime().getTime() + 100000);
        Car car1 = CarBuilder.createCarBuilder().user(user1).vin("C1").pti(date).build();
        Car car2 = CarBuilder.createCarBuilder().user(user1).vin("C2").pti(date).build();

        user1.getCars().add(car1);
        user1.getCars().add(car2);

        daoFactory.getCarDao().insert(car1);
        daoFactory.getCarDao().insert(car2);

        ParkingLot p1 = ParkingLotBuilder.createParkingLotBuilder().address("Parking lot 1").build();
        ParkingLot p2 = ParkingLotBuilder.createParkingLotBuilder().address("Parking lot 2").build();

        daoFactory.getParkingLotDao().insert(p1);
        daoFactory.getParkingLotDao().insert(p2);

        car1 = daoFactory.getCarDao().findByVin("C1");

        Date currentTime = new Date(Calendar.getInstance().getTime().getTime());
        Request request1 = RequestBuilder.createRequestBuilder().car(car1).date(currentTime).status("Pending").build();
        p1 = daoFactory.getParkingLotDao().find(1);
        request1.getParkingLots().add(p1);

        daoFactory.getRequestDao().insert(request1);


        ParkingSpot spot1 = ParkingSpotBuilder.createParkingSpotBuilder().parkingNo(42).build();
        spot1.setFree(true);
        spot1.setParkingLot(p1);
        ParkingSpot spot2 = ParkingSpotBuilder.createParkingSpotBuilder().parkingNo(69).build();
        spot2.setFree(true);
        spot2.setParkingLot(p1);

        daoFactory.getParkingSpotDao().insert(spot1);
        daoFactory.getParkingSpotDao().insert(spot2);

        System.out.println(daoFactory.getRequestDao().selectByVin("C1"));

        Server.getInstance().start();
    }
}
