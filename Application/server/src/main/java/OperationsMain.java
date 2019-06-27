import business.AdminOperations.AssignParkingSpot;
import business.AdminOperations.RetractParkingSpot;
import business.AdminOperations.ViewAllRequestsForLot;
import business.AdminOperations.ViewParkingLots;
import business.UserOperations.*;
import dao.DaoFactory;
import dao.impl.hibernate.util.HibernateUtil;
import entities.Car;
import entities.ParkingLot;
import entities.ParkingSpot;
import entities.User;
import entities.builders.ParkingLotBuilder;
import output.*;

import java.io.IOException;
import java.util.*;

public class OperationsMain
{
    public static void main(String args[]) throws IOException
    {
        //instantiate dao factory
        DaoFactory daoFactory = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE);

        //add user
        RegisterUser registerUser =  new RegisterUser(daoFactory, "linca_paul@yahoo.com", "password", "Paul Linca", "Acasa");
        UserDTO executeRegistration;
        executeRegistration = registerUser.execute();
        if(executeRegistration != null)
        {
            System.out.println(executeRegistration.getMessage());
        }

        //add user
        registerUser = new RegisterUser(daoFactory, "email_address@mail.com", "pass", "Name Surname", "Address");
        executeRegistration = registerUser.execute();
        if(executeRegistration != null)
        {
            System.out.println(executeRegistration.getMessage());
        }

        //wait for user input to proceed
        System.out.println("Proceed to adding a car to a user?");
        System.in.read();

        //find user by mail address
        User user = daoFactory.getUserDao().findByMail("linca_paul@yahoo.com");
        System.out.println(user.getId());
        //add car to user
        Date date = new Date(Calendar.getInstance().getTime().getTime() + 100000);
        AddCar addCar = new AddCar(daoFactory, "CJ13KFJ", date);
        CarDTO executeAddition = addCar.execute(user);
        if(executeAddition != null)
        {
            System.out.println(executeAddition.getMessage());
        }

        //wait for user input to proceed
        System.out.println("Proceed to adding another car to a user?");
        System.in.read();

        //add another car
        addCar = new AddCar(daoFactory, "CJ17EAL", date);
        executeAddition = addCar.execute(user);
        if(executeAddition != null)
        {
            System.out.println(executeAddition.getMessage());
        }

        //wait for user input to proceed
        System.out.println("Proceed to adding a parking request for the first car?");
        System.in.read();

        //create parking lots
        ParkingLot p1 = ParkingLotBuilder.createParkingLotBuilder().address("P1Address").build();
        ParkingLot p2 = ParkingLotBuilder.createParkingLotBuilder().address("P2Address").build();

        //add parking lots to DB
        daoFactory.getParkingLotDao().insert(p1);
        daoFactory.getParkingLotDao().insert(p2);

        //create set of parking lots
        Set<ParkingLot> pSet = new HashSet<>();
        pSet.add(daoFactory.getParkingLotDao().find(1)); //add first parking lot to the request

        //find car by vin
        Car car = daoFactory.getCarDao().findByVin("CJ13KFJ");

        //create a parking request for the car and
        MakeRequest makeRequest = new MakeRequest(daoFactory);
        RequestDTO executeRequest = makeRequest.registerRequest(car, pSet);
        if(executeRequest != null)
        {
            System.out.println(executeRequest.getMessage());
        }

        //wait for user input to proceed
        System.out.println("Proceed to updating the request to have another parking lot? (a request for the second car will be added as well)");
        System.in.read();

        //add second parking lot to the set
        pSet.add(daoFactory.getParkingLotDao().find(2));
        //update the request with the new parking lot set
        UpdateRequest updateRequest = new UpdateRequest(daoFactory, 1);
        RequestDTO executeUpdate = updateRequest.updateRequest(pSet);
        if(executeUpdate != null)
        {
            System.out.println(executeUpdate.getMessage());
        }

        System.out.println("Proceed to adding a parking request for the first car?");
        System.in.read();

        //remove a parking lot from set and create a request for the other car
        pSet.clear();
        pSet.add(daoFactory.getParkingLotDao().find(2));

        car = daoFactory.getCarDao().findByVin("CJ17EAL");

        makeRequest = new MakeRequest(daoFactory);
        executeRequest = makeRequest.registerRequest(car, pSet);
        if(executeRequest != null)
        {
            System.out.println(executeRequest.getMessage());
        }

//        //wait for user input to proceed
//        System.out.println("Proceed viewing all requests of the user?");
//        System.in.read();
//
//        //view all requests of a user
//        ViewRequests viewRequests = new ViewRequests(daoFactory, 1);
//        ViewRequestsDTO viewRequestsDTO = viewRequests.execute();
//        if(viewRequests != null)
//        {
//            System.out.println(viewRequestsDTO.getMessage());
//        }

//        //wait for user input to proceed
//        System.out.println("Proceed to deleting the first request?");
//        System.in.read();
//
//        //delete a request
//        DeleteRequest deleteRequest = new DeleteRequest(daoFactory, 1);
//        DeleteRequestDTO executeDeleteRequest = deleteRequest.deleteRequest();
//        if(executeDeleteRequest != null)
//        {
//            System.out.println(executeDeleteRequest.getMessage());
//        }
//
//        //wait for user input to proceed
//        System.out.println("Proceed to removing a car from the user?");
//        System.in.read();
//
//        //remove a user's car
//        RemoveCar removeCar = new RemoveCar(daoFactory, "CJ13KFJ");
//        CarDTO executeRemoval = removeCar.removeCar(user);
//        if(executeRemoval != null)
//        {
//            System.out.println(executeRemoval.getMessage());
//        }

        //wait for user input to proceed
        System.out.println("Proceed to viewing all parking lots?");
        System.in.read();

        ViewParkingLots viewParkingLots = new ViewParkingLots(daoFactory);
        List<ParkingLotDTO> parkingLotDTOs = viewParkingLots.execute();
        if(parkingLotDTOs != null)
        {
            for(ParkingLotDTO p: parkingLotDTOs)
            {
                System.out.println("Parking lot " + p.getId() + " - Address: " + p.getAddress() + ".");
            }
        }

        //wait for user input to proceed
        System.out.println("Proceed to viewing all requests for parking lot 2?");
        System.in.read();

        ViewAllRequestsForLot viewAllRequestsForLot = new ViewAllRequestsForLot(daoFactory, 2);
        List<RequestDTO> requestDTOs = viewAllRequestsForLot.execute();
        if(requestDTOs != null)
        {
            for(RequestDTO r: requestDTOs)
            {
                System.out.println("Request from " + r.getDate() + " - Status: " + r.getStatus() + ".");
            }
        }

        //wait for user input to proceed
        System.out.println("Proceed to assigning parking spot to a request?");
        System.in.read();

        ParkingSpot pSpot = new ParkingSpot();
        pSpot.setParkingNo(42);
        daoFactory.getParkingSpotDao().insert(pSpot);

        AssignParkingSpot assignParkingSpot = new AssignParkingSpot(daoFactory, 1, 1);
        AssignSpotDTO assignSpotDTO = assignParkingSpot.execute();
        if(assignSpotDTO != null)
        {
            System.out.println(assignSpotDTO.getMessage());
        }

        //wait for user input to proceed
        System.out.println("Proceed to retracting parking spot from the request?");
        System.in.read();


        RetractParkingSpot retractParkingSpot = new RetractParkingSpot(daoFactory, 1, 1);
        AssignSpotDTO retractSpotDTO = retractParkingSpot.execute();
        if(retractSpotDTO != null)
        {
            System.out.println(retractSpotDTO.getMessage());
        }

        //wait for user input to proceed
        System.out.println("Finish?");
        System.in.read();

        HibernateUtil.getSessionFactory().close();

    }
}
