package tickets;

import city.City;
import com.google.gson.Gson;
import company.Company;
import flights.Flight;
import reservations.ReservationController;
import spark.Request;
import spark.Response;
import spark.Route;
import org.json.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketsController {

    private static Gson gson = new Gson();

    public static Route getTickets = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTickets());
    };

    public static Route getTicketById = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTicketById(req.params("id")));
    };

    public static Route getTicketsTable = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTicketsTable());
    };

    public static Route getTicketsTableForCompany = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTicketsTableForCompany(req.params("id")));
    };

    public static Route updateTicket = (Request req, Response res) ->{
        Ticket ticket = gson.fromJson(req.body(),Ticket.class);
        res.type("application/json");
        if(TicketsService.updateTicket(ticket))
            return "OK";
        return "ERROR";
    };

    public static Route createTicket = (Request req, Response res) ->{
        Ticket ticket = gson.fromJson(req.body(), Ticket.class);
        if(TicketsService.createTicket(ticket))
            return "OK";
        return "ERROR";
    };

    public static Route deleteTicket = (Request req, Response res) ->{
        res.type("application/json");
        String ticketId = req.params("id");
        if(ReservationController.deleteForTicket(ticketId) && TicketsService.deleteTicket(ticketId))
            return "OK";
        return "ERROR";
    };

    public static boolean deleteForCompany(String id){
        return TicketsService.deleteForCompany(id);
    }

    public static boolean updateCount(String id, boolean inc){
        return TicketsService.updateCount(id,inc);
    }

    private static Ticket parseJson(String json){
        JSONObject obj = new JSONObject(json);
        int id = obj.getInt("id");
        boolean oneWay = obj.getBoolean("oneWay");
        long count = obj.getLong("count");
        int version = obj.getInt("version");
        String depart = obj.getString("departureDate");
        String ret = obj.getString("returnDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date departure = null;
        Date returnDate = null;
//        try {
////            departure = sdf.parse(depart);
////            returnDate = sdf.parse(ret);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        JSONObject company = obj.getJSONObject("company");
        Company com = new Company(company.getInt("id"),company.getString("name"),company.getInt("version"));
        JSONObject origin = obj.getJSONObject("originCity");
        City oc = new City(origin.getInt("id"),origin.getString("name"));
        JSONObject dest = obj.getJSONObject("destCity");
        City de = new City(dest.getInt("id"),dest.getString("name"));
        JSONObject flight = obj.getJSONObject("flight");
        Flight fl = new Flight(flight.getInt("id"),oc,de,flight.getInt("version"));
        Ticket ticket = new Ticket(id, com,oneWay,new Date(),new Date(),fl,count,version);
        return ticket;
    }
}
