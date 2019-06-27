package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PARKING_LOT")
public class ParkingLot
{
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",  strategy = "native")

    @Column(name = "parking_lot_id")
    private long id;

    @Column(name = "address")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingLot")
    private List<ParkingSpot> parkingSpots = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "parkingLots")
    private Set<Request> requests = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequest(Set<Request> requests) {
        this.requests = requests;
    }
}
