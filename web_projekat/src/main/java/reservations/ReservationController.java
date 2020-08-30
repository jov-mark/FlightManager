package reservations;

import com.google.gson.Gson;
import response.ServerResponse;
import spark.Request;
import spark.Response;
import spark.Route;
import tickets.TicketTable;
import tickets.TicketsController;

public class ReservationController {
    private static Gson gson = new Gson();

    public static Route getReservationsTable = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(ReservationService.getReservationsTable(req.params("id")));
    };

    public static Route createReservation = (Request req, Response res) ->{
        res.type("application/json");
        String userId= req.params("id");
        TicketTable ticket = gson.fromJson(req.body(),TicketTable.class);

        ServerResponse response = ReservationService.createReservation(ticket,userId);
        if(response.isExecuted() && !TicketsController.updateCount(Integer.toString(ticket.getTicketId()),false)){
            response.setMessage("ER");
            response.setExecuted(false);
        }
        return response;
    };

    public static Route deleteReservation = (Request req, Response res) ->{
        res.type("application/json");
        String reservationId = req.params("id");
        String ticketId = ReservationService.getTicketId(reservationId);

        ServerResponse response = ReservationService.deleteReservation(reservationId);
        if(response.isExecuted() && !TicketsController.updateCount(ticketId, true)) {
            response.setMessage("ER");
            response.setExecuted(false);
        }
        return response;
    };

    public static boolean deleteForTicket(String ticketId){
        return ReservationService.deleteForTicket(ticketId);
    }

}
