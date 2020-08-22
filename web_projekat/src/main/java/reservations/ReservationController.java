package reservations;

import com.google.gson.Gson;
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
//        TODO: izvuci user
        res.type("application/json");
        ReservationService.createReservation(gson.fromJson(req.body(), TicketTable.class),null);
        return "OK";
    };

    public static Route deleteReservation = (Request req, Response res) ->{
        res.type("application/json");
        String reservationId = req.params("id");
        String ticketId = ReservationService.getTicketId(reservationId);
        if(ReservationService.deleteReservation(reservationId)
                && TicketsController.updateCount(ticketId,true))
            return "OK";
        return "ERROR";
    };

    public static boolean deleteForTicket(String ticketId){
        return ReservationService.deleteForTicket(ticketId);
    }

}
