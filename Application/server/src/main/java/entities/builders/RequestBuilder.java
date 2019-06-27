package entities.builders;

import entities.Car;
import entities.Request;

import java.util.Date;

public class RequestBuilder
{
    private Request underConstruction;

    public RequestBuilder()
    {
        underConstruction = new Request();
    }

    public static RequestBuilder createRequestBuilder()
    {
        return new RequestBuilder();
    }

    public RequestBuilder date(Date date)
    {
        underConstruction.setDate(date);
        return this;
    }

    public RequestBuilder status(String status)
    {
        underConstruction.setStatus(status);
        return this;
    }

    public RequestBuilder car(Car car)
    {
        underConstruction.setCar(car);
        return this;
    }

    public Request build()
    {
        return underConstruction;
    }
}
