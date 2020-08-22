package users;

import reservations.Reservation;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean type;
    private List<Reservation> reservations;
    private int version;

    public User(){
        this.id=0;
        this.username="";
        this.password="";
        this.type=false;
        this.reservations = new ArrayList<>();
        this.version=0;

    }

    public User(int id, String username, String password, boolean type, List<Reservation> reservations, int version) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.reservations = reservations;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", reservations=" + reservations +
                ", version=" + version +
                '}';
    }
}
