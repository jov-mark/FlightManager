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
        Ticket ticket = TicketsService.getTicketById(req.params("id"));
        ServerResponse response = new ServerResponse("ticket");
        if(ticket == null) {
            res.status(response.getStatus());
            return gson.toJson(response);
        }else if(ticket.getId()==0){
            response.setStatus(404);
            response.setType("object");
            response.setMessage("ER-EX");
            res.status(response.getStatus());
            return gson.toJson(response);
        }
        return gson.toJson(ticket);
    };

    public static Route filterTable = (Request req, Response res) ->{
        res.type("application/json");
        TableFilter filter = gson.fromJson(req.body(),TableFilter.class);
        ServerResponse response = new ServerResponse("filter");
        if(TicketsService.setFilter(filter)){
            response.setMessage("OK-F");
            response.setStatus(200);
            response.setExecuted(true);
        }
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route getFilteredTable = (Request req, Response res) ->{
        res.type("application/json");
        String page = req.params("page");
        return gson.toJson(TicketsService.getFilteredTable(page));
    };

    public static Route getTicketsTable = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(TicketsService.getTicketsTable(req.params("page")));
    };

    public static Route getTicketsTableForCompany = (Request req, Response res) ->{
        res.type("application/json");
        String companyId = req.queryParams("companyId");
        int page = Integer.parseInt(req.queryParams("page"));
        return gson.toJson(TicketsService.getTicketsTableForCompany(companyId,page));
    };

    public static Route updateTicket = (Request req, Response res) ->{
        res.type("application/json");
        Ticket ticket = gson.fromJson(req.body(),Ticket.class);

        ServerResponse response = TicketsService.updateTicket(ticket);
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route createTicket = (Request req, Response res) ->{
        res.type("application/json");
        Ticket ticket = gson.fromJson(req.body(), Ticket.class);

        ServerResponse response = TicketsService.createTicket(ticket);
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route deleteTicket = (Request req, Response res) ->{
        res.type("application/json");
        String ticketId = req.params("id");

        ServerResponse response = new ServerResponse("ticket");
        if(ReservationController.deleteForTicket(ticketId))
            response = TicketsService.deleteTicket(ticketId);
        res.status(response.getStatus());
        return gson.toJson(response);
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
