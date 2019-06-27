package business.UserOperations;

import dao.CarDao;
import dao.DaoFactory;
import entities.Car;
import entities.User;
import entities.builders.CarBuilder;
import output.CarDTO;

import java.util.Calendar;
import java.util.Date;

public class AddCar
{
    private final DaoFactory daoFactory;
    private String vin;
    private Date pti;

    public AddCar(DaoFactory daoFactory, String vin, Date pti)
    {
        this.daoFactory = daoFactory;
        this.vin = vin;
        this.pti = pti;
    }

//    public AddCar(DaoFactory daoFactory, String vin)
//    {
//        this.daoFactory = daoFactory;
//        this.vin = vin;
//        this.pti = null;
//    }

    public CarDTO execute(User user)
    {
        //initialize DAO
        CarDao carDao = daoFactory.getCarDao();

        //search for the car in the DB
        Car car = carDao.findByVin(vin);

        CarDTO carDto = new CarDTO(vin);

        //check if the car was found in the DB -> car already registered
        if(car == null)
        {
            //check that car's pti is valid
            if(pti.after(Calendar.getInstance().getTime()))
            {
                //build car
                car = CarBuilder.createCarBuilder()
                        .vin(vin)
                        .pti(pti)
                        .user(user)
                        .build();

                //add car to user's car list
                user.getCars().add(car);

                //insert car in DB
                carDao.insert(car);
                //update car DTO with the needed data
                carDto.setPti(pti);
                carDto.setMessage("User " + user.getName() + " has successfully registered car " + carDto.getVin() + ".");
            }
            else
            {
                carDto.setMessage("The car's Periodical Technical Inspection is invalid.\nCar not added.");
            }
        }
        else
        {
            carDto.setMessage("Car already registered.");
        }

        return carDto;
    }
}
