package reservations;

import com.google.gson.Gson;
import response.ServerResponse;
import spark.Request;
import spark.Response;
import spark.Route;
import tickets.TicketTable;
import tickets.TicketsController;

import java.util.List;

public class ReservationController {
    private static Gson gson = new Gson();

    public static Route getReservationsTable = (Request req, Response res) ->{
        res.type("application/json");
        List<ReservationTable> reservationTable = ReservationService.getReservationsTable(req.params("id"));
//        TODO: ISPRAVI!!
        if(reservationTable!=null)
            return gson.toJson(reservationTable);
        return new ServerResponse("reservation");
    };

    public static Route createReservation = (Request req, Response res) ->{
        res.type("application/json");
        String userId= req.params("id");
        TicketTable ticket = gson.fromJson(req.body(),TicketTable.class);
        ServerResponse response = ReservationService.createReservation(ticket,userId);
        if(response.isExecuted() && !TicketsController.updateCount(Integer.toString(ticket.getTicketId()),false)){
            response.setMessage("ER");
            response.setStatus(500);
            response.setExecuted(false);
        }
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route deleteReservation = (Request req, Response res) ->{
        res.type("application/json");
        String reservationId = req.params("id");
        String ticketId = ReservationService.getTicketId(reservationId);

        ServerResponse response = ReservationService.deleteReservation(reservationId);
        if(response.isExecuted() && !TicketsController.updateCount(ticketId, true)) {
            response.setMessage("ER");
            response.setStatus(500);
            response.setExecuted(false);
        }
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static boolean deleteForTicket(String ticketId){
        return ReservationService.deleteForTicket(ticketId);
    }

}
