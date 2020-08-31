package tickets;

import city.City;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TableFilter {

    private int originCity;
    private int destCity;
    private Date departDate;
    private Date returnDate;
    private String way;
    private boolean active;

    public TableFilter() {
        this.originCity = 0;
        this.destCity = 0;
        this.departDate = new Date();
        this.returnDate = new Date();
        this.way = "all";
        this.active = false;
    }

    public int getOriginCity() {
        return originCity;
    }

    public void setOriginCity(int originCity) {
        this.originCity = originCity;
    }

    public int getDestCity() {
        return destCity;
    }

    public void setDestCity(int destCity) {
        this.destCity = destCity;
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

    public boolean getReturn(SimpleDateFormat format){
        return format.format(departDate).compareTo(format.format(returnDate))<0;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TableFilter{" +
                "originCity=" + originCity +
                ", destCity=" + destCity +
                ", departDate=" + departDate +
                ", returnDate=" + returnDate +
                ", way='" + way + '\'' +
                ", active=" + active +
                '}';
    }
}
