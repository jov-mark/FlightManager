package reservations;

import tickets.TicketTable;
import users.User;

import java.util.List;

public class ReservationService {

    public static List<ReservationTable> getReservationsTable(String id){
        return ReservationRepo.getReservationsTable(id);
    }

    public static void createReservation(TicketTable ticket, User user){
        ReservationRepo.createReservation(ticket,user);
    }

    public static boolean deleteForTicket(String ticketId){
        return ReservationRepo.deleteForTicket(ticketId);
    }

    public static boolean deleteReservation(String id){
        return ReservationRepo.deleteReservation(id);
    }

    public static String getTicketId(String id){
        return ReservationRepo.getTicketId(id);
    }
}
