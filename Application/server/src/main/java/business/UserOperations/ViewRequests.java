package business.UserOperations;

import dao.CarDao;
import dao.DaoFactory;
import dao.RequestDao;
import dao.UserDao;
import entities.Car;
import entities.Request;
import entities.User;
import output.ViewRequestsDTO;

import java.util.ArrayList;
import java.util.List;

public class ViewRequests
{
    private final DaoFactory daoFactory;
    private long id;

    public ViewRequests(DaoFactory daoFactory, long id)
    {
        this.daoFactory = daoFactory;
        this.id = id;
    }

    public ViewRequestsDTO execute()
    {
//        UserDao userDao = daoFactory.getUserDao();
//        CarDao carDao = daoFactory.getCarDao();
//        RequestDao requestDao = daoFactory.getRequestDao();
//
//        //search for the user
//        User user = userDao.find(id);
//
//        ViewRequestsDTO requestsDTO = new ViewRequestsDTO(user);
//
//
//        List<Request> allRequests = new ArrayList<>();
//        //iterate through the user's car and collect all the requests they have
//        for(Car c: user.getCars())
//        {
//            allRequests.addAll(c.getRequestList());
//        }
//
//        //update the dto with the requests
//        requestsDTO.setRequests(allRequests);
//
//        requestsDTO.setMessage("Requests:\n");
//        for(Request r: allRequests)
//        {
//            requestsDTO.setMessage(requestsDTO.getMessage() + r.getDate() + " " + r.getCar() + " " + r.getStatus() + "\n");
//        }
//
//        return requestsDTO;
        return null;
    }
}
