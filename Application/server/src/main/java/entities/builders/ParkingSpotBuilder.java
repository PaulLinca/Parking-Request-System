package entities.builders;

import entities.ParkingSpot;

import java.util.Objects;

public class ParkingSpotBuilder
{
    private ParkingSpot underConstruction;

    public ParkingSpotBuilder()
    {
        underConstruction = new ParkingSpot();
    }

    public static ParkingSpotBuilder createParkingSpotBuilder()
    {
        return new ParkingSpotBuilder();
    }

    public ParkingSpotBuilder parkingNo(int parkingNo)
    {
        underConstruction.setParkingNo(parkingNo);
        return this;
    }

    public ParkingSpot build()
    {
        checkValid(underConstruction);
        return underConstruction;
    }

    private void checkValid(ParkingSpot underConstruction)
    {
        Objects.requireNonNull(underConstruction.getParkingNo());
    }
}