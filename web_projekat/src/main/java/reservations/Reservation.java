package reservations;

import flights.Flight;
import tickets.Ticket;
import users.User;

public class Reservation {
    private int id;
    private boolean available;
    private Flight flight;
    private Ticket ticket;
    private User user;
    private int version;

    public Reservation() {
        this.id = 0;
        this.available = false;
        this.flight = new Flight();
        this.ticket = new Ticket();
        this.user = new User();
        this.version = 0;
    }

    public Reservation(int id, boolean available, Flight flight, Ticket ticket, User user, int version) {
        this.id = id;
        this.available = available;
        this.flight = flight;
        this.ticket = ticket;
        this.user = user;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", available=" + available +
                ", flight=" + flight +
                ", ticket=" + ticket +
                ", user=" + user +
                ", version=" + version +
                '}';
    }
}
