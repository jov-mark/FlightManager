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
        ServerResponse response = new ServerResponse();
        if(reservationTable == null){
            res.status(response.getStatus());
            return gson.toJson(response);
        }

        return gson.toJson(reservationTable);
    };

    public static Route createReservation = (Request req, Response res) ->{
        res.type("application/json");
        String userId= req.params("id");
        TicketTable ticket = gson.fromJson(req.body(),TicketTable.class);
        ServerResponse response = new ServerResponse();
        if(TicketsController.checkVersion(Integer.toString(ticket.getTicketId()),ticket.getVersion())) {
            response = ReservationService.createReservation(ticket, userId);
            if (response.isExecuted() && !TicketsController.updateCount(Integer.toString(ticket.getTicketId()), false,ticket.getVersion())) {
                response.setType("object");
                response.setMessage("ER");
                response.setStatus(500);
                response.setExecuted(false);
            }
        }
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route deleteReservation = (Request req, Response res) ->{
        res.type("application/json");
        ReservationTable reservation = gson.fromJson(req.body(),ReservationTable.class);
        String ticketId = Integer.toString(reservation.getTicketId());
        int ticketVersion = reservation.getVersion();

        ServerResponse response = new ServerResponse();
        if(TicketsController.checkVersion(ticketId,ticketVersion)){
            response = ReservationService.deleteReservation(Integer.toString(reservation.getReservationId()));
            if(response.isExecuted() && !TicketsController.updateCount(ticketId, true,ticketVersion)) {
                response.setType("object");
                response.setMessage("ER");
                response.setStatus(500);
                response.setExecuted(false);
            }
        }
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static boolean deleteForTicket(String ticketId){
        return ReservationService.deleteForTicket(ticketId);
    }

}
