package tickets;

import com.google.gson.Gson;
import reservations.ReservationController;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;

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
        List<Integer> tickets = TicketsService.getTicketsForCompany(id);
        for(int ticket: tickets){
            if(ReservationController.deleteForTicket(Integer.toString(ticket))
                    && TicketsService.deleteTicket(Integer.toString(ticket)))
                return true;
        }
        return false;
    }

    public static boolean updateCount(String id, boolean inc){
        return TicketsService.updateCount(id,inc);
    }
}
