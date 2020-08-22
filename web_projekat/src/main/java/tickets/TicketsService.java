package tickets;

import java.util.List;

public class TicketsService {

    public static List<Ticket> getTickets(){
        return TicketsRepo.getTickets();
    }

    public static List<TicketTable> getTicketsTable(){
        return TicketsRepo.getTicketsTable();
    }

    public static List<TicketTable> getTicketsTableForCompany(String id){
        return TicketsRepo.getTicketsTableForCompany(id);
    }

    public static Ticket getTicketById(String id){
        return TicketsRepo.getTicketById(id);
    }

    public  static boolean updateTicket(Ticket ticket){
        return TicketsRepo.updateTicket(ticket);
    }

    public static boolean deleteTicket(String id){
        return TicketsRepo.deleteTicket(id);
    }

    public static boolean createTicket(Ticket ticket){
        if(validateTicket(ticket)) {
            return TicketsRepo.createTicket(ticket);
        }
        return false;
    }

    public static boolean deleteForCompany(String id){
        return TicketsRepo.deleteForCompany(id);
    }

    public static boolean updateCount(String id, boolean inc){
        return TicketsRepo.updateCount(id,inc);
    }

    private static boolean validateTicket(Ticket ticket){
        boolean dates = (ticket.isOneWay() && ticket.getDepartureDate().compareTo(ticket.getReturnDate())==0) ||
                ticket.getDepartureDate().compareTo(ticket.getReturnDate())<=0;
        boolean count = ticket.getCount()>0;
        return dates && count;
    }
}
