package reservations;

import response.ServerResponse;
import tickets.TicketTable;

import java.util.List;

public class ReservationService {

    public static List<ReservationTable> getReservationsTable(String id){
        return ReservationRepo.getReservationsTable(id);
    }

    public static ServerResponse createReservation(TicketTable ticket, String userId){
        return ReservationRepo.createReservation(ticket,userId);
    }

    public static boolean deleteForTicket(String ticketId){
        return ReservationRepo.deleteForTicket(ticketId);
    }

    public static ServerResponse deleteReservation(String id){
        return ReservationRepo.deleteReservation(id);
    }

    public static String getTicketId(String id){
        return ReservationRepo.getTicketId(id);
    }
}
