package tickets;

import company.Company;
import flights.Flight;

import java.util.Date;

public class Ticket {

    private int id;
    private Company company;
    private boolean oneWay;
    private Date departureDate;
    private Date returnDate;
    private Flight flight;
    private long count;
    private int version;

    public Ticket() {
        this.id = 0;
        this.company = new Company();
        this.oneWay = false;
        this.departureDate = new Date();
        this.returnDate = new Date();
        this.flight = new Flight();
        this.count = 0;
        this.version = 1;
    }

    public Ticket(int id, Company company, boolean oneWay, Date departureDate, Flight flight, long count, int version) {
        this.id = id;
        this.company = company;
        this.oneWay = oneWay;
        this.departureDate = departureDate;
        this.returnDate = new Date();
        this.flight = flight;
        this.count = count;
        this.version = version;
    }

    public Ticket(int id, Company company, boolean oneWay, Date departureDate, Date returnDate, Flight flight, long count, int version) {
        this.id = id;
        this.company = company;
        this.oneWay = oneWay;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.flight = flight;
        this.count = count;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
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
        return "Ticket{" +
                "id=" + id +
                ", company=" + company +
                ", oneWay=" + oneWay +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", flight=" + flight +
                ", count=" + count +
                ", version=" + version +
                '}';
    }
}
