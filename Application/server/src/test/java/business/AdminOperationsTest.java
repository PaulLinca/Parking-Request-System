package business;

import business.AdminOperations.AssignParkingSpot;
import business.AdminOperations.RetractParkingSpot;
import business.AdminOperations.ViewAllRequestsForLot;
import business.AdminOperations.ViewParkingLots;
import dao.*;
import entities.Car;
import entities.ParkingLot;
import entities.ParkingSpot;
import entities.Request;
import entities.builders.RequestBuilder;
import org.junit.Assert;
import org.junit.Test;
import output.RequestDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminOperationsTest
{
    private ViewAllRequestsForLot subjectViewRequests;
    private ViewParkingLots subjectViewLots;
    private AssignParkingSpot subjectAssignSpot;
    private RetractParkingSpot subjectRetractSpot;

    List<ParkingLot> parkingLots = new ArrayList<>();
    List<Request> requests = new ArrayList<>();
    List<ParkingSpot> parkingSpots= new ArrayList<>();

    @Test
    public void testViewAllParkingLots()
    {
        parkingLots.add(new ParkingLot());
        parkingLots.add(new ParkingLot());
        parkingLots.add(new ParkingLot());

        ParkingLotDao mockLotDao = mockParkingLotDao(parkingLots);
        DaoFactory mockDaoFactory = mockDaoFactory(null, mockLotDao, null);

        subjectViewLots = new ViewParkingLots(mockDaoFactory);

        Assert.assertEquals(3, subjectViewLots.execute().size());
    }

    @Test
    public void testViewAllRequestsForLot()
    {
        ParkingLot p1 = new ParkingLot();
        p1.setId(1);
        ParkingLot p2 = new ParkingLot();
        p2.setId(2);


        Request r1 = new Request();
        r1.getParkingLots().add(p1);
        Request r2 = new Request();
        r2.getParkingLots().add(p1);
        Request r3 = new Request();
        r3.getParkingLots().add(p2);

        p1.getRequests().add(r1);
        p1.getRequests().add(r2);
        p2.getRequests().add(r3);

        requests.add(r1);
        requests.add(r2);
        requests.add(r3);
        parkingLots.add(p1);
        parkingLots.add(p2);

        RequestDao mockRequestDao = mockRequestDao(requests);
        ParkingLotDao mockParkingLotDao = mockParkingLotDao(parkingLots);
        DaoFactory mockDaoFactory = mockDaoFactory(mockRequestDao, mockParkingLotDao, null);

        subjectViewRequests = new ViewAllRequestsForLot(mockDaoFactory, 1);

        Assert.assertEquals(2, subjectViewRequests.execute().size());
    }

    @Test
    public void testAssignParkingSpot()
    {
        Request request = RequestBuilder.createRequestBuilder().car(new Car()).status("Pending Approval").date(new Date()).build();
        request.setId(2);
        requests.add(request);
        ParkingSpot pSpot = new ParkingSpot();
        pSpot.setParkingNo(42);
        parkingSpots.add(pSpot);

        ParkingSpotDao parkingSpotDao = mockParkingSpotDao(parkingSpots);
        RequestDao requestDao = mockRequestDao(requests);
        DaoFactory mockDaoFactory = mockDaoFactory(requestDao, null, parkingSpotDao);

        subjectAssignSpot = new AssignParkingSpot(mockDaoFactory, pSpot.getId(), request.getId());

        Assert.assertEquals(subjectAssignSpot.execute().getMessage(), "Parking spot 42 was assigned for request 2.");
    }

    @Test
    public void testRetractParkingSpot()
    {
        Request request = RequestBuilder.createRequestBuilder().car(new Car()).status("Pending Approval").date(new Date()).build();
        request.setId(2);
        request.setParkingSpot(42);
        requests.add(request);
        ParkingSpot pSpot = new ParkingSpot();
        pSpot.setParkingNo(42);
        pSpot.setFree(false);
        parkingSpots.add(pSpot);

        ParkingSpotDao parkingSpotDao = mockParkingSpotDao(parkingSpots);
        RequestDao requestDao = mockRequestDao(requests);
        DaoFactory mockDaoFactory = mockDaoFactory(requestDao, null, parkingSpotDao);

        subjectRetractSpot = new RetractParkingSpot(mockDaoFactory, pSpot.getId(), request.getId());

        Assert.assertEquals(subjectRetractSpot.execute().getMessage(), "Parking spot 42 was retracted from request 2. Client failed to pay.");

    }

    private DaoFactory mockDaoFactory(RequestDao mockRequestDao, ParkingLotDao mockParkingLotDao, ParkingSpotDao mockParkingSpotDao)
    {
        return new DaoFactory()
        {
            @Override
            public CarDao getCarDao() {
                return null;
            }

            @Override
            public ParkingLotDao getParkingLotDao() {
                return mockParkingLotDao;
            }

            @Override
            public ParkingSpotDao getParkingSpotDao() {
                return mockParkingSpotDao;
            }

            @Override
            public RequestDao getRequestDao() {
                return mockRequestDao;
            }

            @Override
            public UserDao getUserDao() {
                return null;
            }

        };
    }

    private ParkingLotDao mockParkingLotDao(List<ParkingLot> parkingLots)
    {
        return new ParkingLotDao()
        {
            @Override
            public ParkingLot find(long id)
            {
                for(ParkingLot p: parkingLots)
                {
                    if(p.getId() == id)
                    {
                        return p;
                    }
                }
                return null;
            }

            @Override
            public void delete(ParkingLot objectToDelete) {

            }

            @Override
            public void update(ParkingLot objectToUpdate) {

            }

            @Override
            public void insert(ParkingLot objectToCreate) {

            }

            @Override
            public List<ParkingLot> selectAll()
            {
                return parkingLots;
            }
        };
    }

    private RequestDao mockRequestDao(List<Request> requests)
    {
        return new RequestDao() {
            @Override
            public Request find(long id) {
                return requests.stream().filter(it -> it.getId() == id).findAny().get();
            }

            @Override
            public void delete(Request objectToDelete) {
                requests.remove(objectToDelete);
            }

            @Override
            public void update(Request objectToUpdate) { }

            @Override
            public void insert(Request objectToInsert) {
                requests.add(objectToInsert);
            }

            @Override
            public List<Request> selectAllRequests(long id)
            {
                List<Request> requestList = new ArrayList<>();
                ParkingLotDao mockLotDao = mockParkingLotDao(parkingLots);
                for(Request r: mockLotDao.find(id).getRequests())
                {
                    requestList.add(r);
                }
                return requestList;
            }

            @Override
            public List<Request> selectAll() {
                return null;
            }

            @Override
            public Request selectByVin(String vin) {
                return null;
            }
        };
    }

    private ParkingSpotDao mockParkingSpotDao(List<ParkingSpot> parkingSpots)
    {
        return new ParkingSpotDao() {
            @Override
            public ParkingSpot find(long id){
                return parkingSpots.stream().filter(it -> it.getId() == id).findAny().get();
            }

            @Override
            public void delete(ParkingSpot objectToDelete) {

            }

            @Override
            public void update(ParkingSpot objectToUpdate) {

            }

            @Override
            public void insert(ParkingSpot objectToCreate) {

            }

            @Override
            public List<ParkingSpot> selectAll(long id) {
                return null;
            }
        };
    }
}
