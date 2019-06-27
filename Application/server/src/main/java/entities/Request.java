package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "REQUEST")
public class Request
{
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",  strategy = "native")

    //attributes
    @Column(name = "request_id")
    private long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    @Column(name ="parking_spot")
    private int parkingSpot = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
            @JoinTable(
                    name = "parking_lot_request",
                    joinColumns = {@JoinColumn (name = "request_id")},
                    inverseJoinColumns = {@JoinColumn(name = "paking_lot_id")})
    Set<ParkingLot> parkingLots = new HashSet<>();

    //operations
    public boolean addParkingLot(ParkingLot parkingLot)
    {
        parkingLot.getRequests().add(this);
        return parkingLots.add(parkingLot);
    }

    public boolean removeParkingLot(ParkingLot parkingLot)
    {
        parkingLot.getRequests().remove(this);
        return parkingLots.remove(parkingLot);
    }

    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Set<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(Set<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public int getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(int parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
}
