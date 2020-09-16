package flights;

import city.City;
import tickets.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Flight {

    private int id;
    private String name;
    private City originCity;
    private List<Ticket> ticketList;
    private City destCity;
    private int version;

    public Flight() {
        this.id = 0;
        this.name = "";
        this.originCity = new City();
        this.ticketList = new ArrayList<>();
        this.destCity = new City();
        this.version = 0;
    }


    public Flight(int id, City originCity, City destCity, int version) {
        this.id = id;
        this.originCity = originCity;
        this.ticketList = new ArrayList<>();
        this.destCity = destCity;
        this.version = version;
    }


    public Flight(int id, City originCity, List<Ticket> ticketList, City destCity, int version) {
        this.id = id;
        this.originCity = originCity;
        this.ticketList = ticketList;
        this.destCity = destCity;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public City getOriginCity() {
        return originCity;
    }

    public void setOriginCity(City originCity) {
        this.originCity = originCity;
    }

    public City getDestCity() {
        return destCity;
    }

    public void setDestCity(City destCity) {
        this.destCity = destCity;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = originCity.getName()+"-"+destCity.getName();
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", originCity=" + originCity +
                ", destCity=" + destCity +
                ", version=" + version +
                '}';
    }
}
