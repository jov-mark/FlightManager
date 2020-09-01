package tickets;

import city.City;
import company.Company;

import java.util.Date;

public class TicketTable {

    private int ticketId;
    private boolean oneWay;
    private int flightId;
    private City origin;
    private City destination;
    private Date departDate;
    private Date returnDate;
    private Company company;
    private int count;
    private int version;

    public TicketTable() {
        this.ticketId = 0;
        this.oneWay = false;
        this.flightId = 0;
        this.origin = new City();
        this.destination = new City();
        this.departDate = new Date();
        this.returnDate = new Date();
        this.company = new Company();
        this.count = 0;
        this.version = 0;
    }

    public TicketTable(int ticketId, boolean oneWay, int flightId, City origin, City destination,
                       Date departDate, Date returnDate, Company company, int count, int version) {
        this.ticketId = ticketId;
        this.oneWay = oneWay;
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.company = company;
        this.count = count;
        this.version = version;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TicketTable{" +
                "oneWay=" + oneWay +
                ", origin=" + origin +
                ", destination=" + destination +
                ", departDate=" + departDate +
                ", returnDate=" + returnDate +
                ", company=" + company +
                ", count=" + count +
                '}';
    }
}
