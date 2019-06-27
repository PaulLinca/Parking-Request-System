package business.UserOperations;

import dao.CarDao;
import dao.DaoFactory;
import entities.Car;
import entities.User;
import output.CarDTO;

import java.util.Date;

public class RemoveCar
{
    private final DaoFactory daoFactory;
    private String vin;
    private Date pti;

    public RemoveCar(DaoFactory daoFactory, String vin)
    {
        this.daoFactory = daoFactory;
        this.vin = vin;
    }

    public CarDTO removeCar(User user)
    {
        //initialize DAO
        CarDao carDao = daoFactory.getCarDao();
        //search car in the DB through its vin
        Car car = carDao.findByVin(vin);

        CarDTO carDto = new CarDTO(vin);

        //check if car was found in the DB
        if(car != null)
        {
            //remove car from the user's car list
            user.getCars().remove((int) car.getId() - 1);

            //delete car from DB
            carDao.delete(car);
            carDto.setMessage("User " + user.getName() + " has successfully deleted car " + carDto.getVin() + ".");
        }
        else
        {
            carDto.setMessage("Car isn't registered to this user.");
        }

        return carDto;
    }
}
