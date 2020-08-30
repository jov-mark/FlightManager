package tickets;

import com.google.gson.Gson;
import reservations.ReservationController;
import response.ServerResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class TicketsController {

    private static Gson gson = new Gson();

    public static Route getTicketById = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTicketById(req.params("id")));
    };

    public static Route filterTable = (Request req, Response res) ->{
        res.type("application/json");
        TableFilter filter = gson.fromJson(req.body(),TableFilter.class);
        if(TicketsService.setFilter(filter)){
            return "OK";
        }
        return "ERR";
    };

    public static Route getFilteredTable = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getFilteredTable());
    };

    public static Route getTicketsTable = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTicketsTable(req.params("page")));
    };

    public static Route getTicketsTableForCompany = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTicketsTableForCompany(req.params("id")));
    };

    public static Route updateTicket = (Request req, Response res) ->{
        res.type("application/json");
        Ticket ticket = gson.fromJson(req.body(),Ticket.class);

        ServerResponse response = TicketsService.updateTicket(ticket);
        return response;
    };

    public static Route createTicket = (Request req, Response res) ->{
        res.type("application/json");
        Ticket ticket = gson.fromJson(req.body(), Ticket.class);

        ServerResponse response = TicketsService.createTicket(ticket);
        return response;
    };

    public static Route deleteTicket = (Request req, Response res) ->{
        res.type("application/json");
        String ticketId = req.params("id");

        ServerResponse response = new ServerResponse("ticket");
        if(ReservationController.deleteForTicket(ticketId)){
            response = TicketsService.deleteTicket(ticketId);
        }
        return response;
    };

    public static boolean deleteForCompany(String id){
        List<Integer> tickets = TicketsService.getTicketsForCompany(id);
        for(int ticket: tickets){
            if(ReservationController.deleteForTicket(Integer.toString(ticket))
                    && TicketsService.deleteTicket(Integer.toString(ticket)).isExecuted())
                return true;
        }
        return false;
    }

    public static boolean updateCount(String id, boolean inc){
        return TicketsService.updateCount(id,inc);
    }
}
