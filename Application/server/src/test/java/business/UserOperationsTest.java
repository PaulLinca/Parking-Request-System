package business;

import business.UserOperations.*;
import dao.*;
import entities.Car;
import entities.ParkingLot;
import entities.Request;
import entities.User;
import entities.builders.CarBuilder;
import entities.builders.RequestBuilder;
import entities.builders.UserBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class UserOperationsTest
{
    private RegisterUser subjectUser;
    private AddCar subjectAddCar;
    private RemoveCar subjectRemoveCar;
    private MakeRequest subjectMakeRequest;
    private UpdateRequest subjectUpdateRequest;
    private DeleteRequest subjectDeleteRequest;
    private ViewRequests subjectViewRequests;

    List<User> users = new ArrayList<>();
    List<Car> cars = new ArrayList<>();
    Set<ParkingLot> plots = new HashSet<>();
    List<Request> requests = new ArrayList<>();

    @Test
    public void testRegisterUser()
    {
        UserDao mockUserDao = mockUserDao(users);
        DaoFactory mockDaoFactory = mockDaoFactory(mockUserDao, null, null);
        subjectUser = new RegisterUser(mockDaoFactory, "m1", "p1", "n1", "a1");
        subjectUser.execute();
        subjectUser = new RegisterUser(mockDaoFactory, "m2", "p2", "n2", "a2");
        subjectUser.execute();

        Assert.assertEquals(2, users.size());
    }

    @Test
    public void testAddCar()
    {
        Date date = new Date(Calendar.getInstance().getTime().getTime() + 100000);
        CarDao mockCarDao = mockCarDao(cars);
        DaoFactory mockDaoFactory = mockDaoFactory(null, mockCarDao, null);
        subjectAddCar = new AddCar(mockDaoFactory, "v1", date);
        subjectAddCar.execute(new User());
        subjectAddCar = new AddCar(mockDaoFactory, "v2", date);
        subjectAddCar.execute(new User());

        Assert.assertEquals(2, cars.size());
    }

    @Test
    public void testRemoveCar()
    {
        CarDao mockCarDao = mockCarDao(cars);
        DaoFactory mockDaoFactory = mockDaoFactory(null, mockCarDao, null);

        Car c1 = new Car();
        c1.setVin("v1");
        c1.setId(1);
        Car c2 = new Car();
        c1.setVin("v2");
        c2.setId(2);

        User u1 = new User();
        u1.getCars().add(c1);
        u1.getCars().add(c2);

        cars.clear();
        cars.add(c1);
        cars.add(c2);

        subjectRemoveCar = new RemoveCar(mockDaoFactory, "v2");
        subjectRemoveCar.removeCar(u1);

        Assert.assertEquals(1, cars.size());
    }

    @Test
    public void testMakeRequest()
    {
        RequestDao requestDao = mockRequestDao(requests);
        DaoFactory mockDaoFactory = mockDaoFactory(null, null, requestDao);

        Car c1 = new Car();
        c1.setVin("v1");
        c1.setId(1);

        cars.clear();
        cars.add(c1);

        ParkingLot p1 = new ParkingLot();
        p1.setId(1);
        plots.add(p1);

        subjectMakeRequest = new MakeRequest(mockDaoFactory);
        subjectMakeRequest.registerRequest(c1, plots);

        Assert.assertEquals(1, requests.size());
    }

    @Test
    public void testUpdateRequest()
    {
        RequestDao requestDao = mockRequestDao(requests);
        DaoFactory mockDaoFactory = mockDaoFactory(null, null, requestDao);

        Car c1 = new Car();
        c1.setVin("v1");
        c1.setId(1);

        cars.clear();
        cars.add(c1);

        ParkingLot p1 = new ParkingLot();
        p1.setId(1);
        plots.add(p1);

        subjectMakeRequest = new MakeRequest(mockDaoFactory);
        subjectMakeRequest.registerRequest(c1, plots);

        ParkingLot p2 = new ParkingLot();
        p2.setId(2);
        plots.add(p2);

        subjectUpdateRequest = new UpdateRequest(mockDaoFactory, 0);
        subjectUpdateRequest.updateRequest(plots);

        Assert.assertEquals(1, requests.size());
    }

    @Test
    public void testDeleteRequest()
    {
        RequestDao requestDao = mockRequestDao(requests);
        DaoFactory mockDaoFactory = mockDaoFactory(null, null, requestDao);

        Car c1 = new Car();
        c1.setVin("v1");
        c1.setId(1);

        cars.clear();
        cars.add(c1);

        ParkingLot p1 = new ParkingLot();
        p1.setId(1);
        plots.add(p1);

        subjectMakeRequest = new MakeRequest(mockDaoFactory);
        subjectMakeRequest.registerRequest(c1, plots);

        subjectDeleteRequest = new DeleteRequest(mockDaoFactory, 0);
        subjectDeleteRequest.deleteRequest();

        Assert.assertEquals(0, requests.size());
    }

    @Test
    public void testViewRequests()
    {
        User user = UserBuilder.createUserBuilder().mail("m").address("a").admin(false).password("p").name("n").build();
        user.setId(1);

        Car car = CarBuilder.createCarBuilder().user(user).vin("v1").build();
        car.setId(1);
        user.getCars().add(car);

        Request request = RequestBuilder.createRequestBuilder().car(car).build();
        request.setCar(car);
        request.setId(1);
        car.getRequestList().add(request);

        users.add(user);
        cars.add(car);
        requests.add(request);

        RequestDao requestDao = mockRequestDao(requests);
        UserDao mockUserDao = mockUserDao(users);
        CarDao mockCarDao = mockCarDao(cars);

        DaoFactory mockDaoFactory = mockDaoFactory(mockUserDao, mockCarDao, requestDao);

        subjectViewRequests = new ViewRequests(mockDaoFactory, 1);

        //Assert.assertEquals(1, subjectViewRequests.execute().getRequests().size());
    }

    private DaoFactory mockDaoFactory(UserDao mockUserDao, CarDao mockCarDao, RequestDao mockRequestDao) {
        return new DaoFactory() {
            @Override
            public CarDao getCarDao() {
                return mockCarDao;
            }

            @Override
            public ParkingLotDao getParkingLotDao() {
                return null;
            }

            @Override
            public ParkingSpotDao getParkingSpotDao() {
                return null;
            }

            @Override
            public RequestDao getRequestDao() {
                return mockRequestDao;
            }

            @Override
            public UserDao getUserDao() {
                return mockUserDao;
            }

        };
    }

    private UserDao mockUserDao(List<User> users) {
        return new UserDao() {
            @Override
            public User find(long id) {
                return users.stream().filter(it -> it.getId() == id).findAny().get();
            }

            @Override
            public void delete(User objectToDelete) {
                users.remove(objectToDelete);
            }

            @Override
            public void update(User objectToUpdate) {
            }

            @Override
            public void insert(User objectToInsert) {
                users.add(objectToInsert);
            }

            @Override
            public User findByMail(String mail) {
                return null;
            }
        };
    }

    private CarDao mockCarDao(List<Car> cars) {
        return new CarDao() {
            @Override
            public Car find(long id) {
                return cars.stream().filter(it -> it.getId() == id).findAny().get();
            }

            @Override
            public void delete(Car objectToDelete) {
                cars.remove(objectToDelete);
            }

            @Override
            public void update(Car objectToUpdate) {

            }

            @Override
            public void insert(Car objectToInsert) {
                cars.add(objectToInsert);
            }

            @Override
            public Car findByVin(String vin) {
                for(Car c: cars)
                {
                    if(c.getVin() == vin)
                    {
                        return c;
                    }
                }
                return null;
            }
        };
    }

    private RequestDao mockRequestDao(List<Request> requests) {
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
            public void update(Request objectToUpdate)
            {
            }

            @Override
            public void insert(Request objectToInsert) {
                requests.add(objectToInsert);
            }

            @Override
            public List<Request> selectAllRequests(long id) {
                return null;
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
}
