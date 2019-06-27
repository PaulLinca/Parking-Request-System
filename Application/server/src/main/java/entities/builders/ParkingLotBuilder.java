package entities.builders;

import entities.ParkingLot;

import java.util.Objects;

public class ParkingLotBuilder
{
    private ParkingLot underConstruction;

    public ParkingLotBuilder()
    {
        underConstruction = new ParkingLot();
    }

    public static ParkingLotBuilder createParkingLotBuilder()
    {
        return new ParkingLotBuilder();
    }

    public ParkingLotBuilder address(String address)
    {
        underConstruction.setAddress(address);
        return this;
    }

    public ParkingLot build()
    {
        checkValid(underConstruction);
        return underConstruction;
    }

    private void checkValid(ParkingLot underConstruction)
    {
        Objects.requireNonNull(underConstruction.getAddress());
    }
}