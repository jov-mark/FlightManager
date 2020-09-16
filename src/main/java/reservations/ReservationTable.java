package reservations;

import city.City;
import company.Company;
import tickets.TicketTable;

import java.util.Date;

public class ReservationTable extends TicketTable {

    private int reservationId;
    private boolean isAvailable;

    public ReservationTable() {
        this.reservationId = 0;
        this.isAvailable = false;
    }

    public ReservationTable(int ticketId, boolean oneWay, int flightId, City origin, City destination, Date departDate, Date returnDate, Company company, int count, int version, int reservationId, boolean isAvailable) {
        super(ticketId, oneWay, flightId, origin, destination, departDate, returnDate, company, count, version);
        this.reservationId = reservationId;
        this.isAvailable = isAvailable;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getTicketId(){
        return super.getTicketId();
    }

    public int getTicketVersion(){
        return super.getVersion();
    }

    @Override
    public String toString() {
        return "ReservationTable{" +
                "reservationId=" + reservationId +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
