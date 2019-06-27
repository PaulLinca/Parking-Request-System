package entities.builders;

import entities.Car;
import entities.User;

import java.util.Date;
import java.util.Objects;

public class CarBuilder
{
    private Car underConstruction;

    public CarBuilder()
    {
        underConstruction = new Car();
    }

    public static CarBuilder createCarBuilder()
    {
        return new CarBuilder();
    }

    public CarBuilder vin(String vin)
    {
        underConstruction.setVin(vin);
        return this;
    }

    public CarBuilder pti(Date pti)
    {
        underConstruction.setPti(pti);
        return this;
    }

    public CarBuilder user(User user)
    {
        underConstruction.setUser(user);
        return this;
    }

    public Car build()
    {
        checkValid(underConstruction);
        return underConstruction;
    }

    private void checkValid(Car underConstruction)
    {
        Objects.requireNonNull(underConstruction.getVin());
    }
}
