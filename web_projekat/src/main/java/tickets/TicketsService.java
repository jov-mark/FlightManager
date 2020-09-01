package tickets;

import response.ServerResponse;

import java.util.List;

public class TicketsService {

    public static List<Ticket> getTickets(){
        return TicketsRepo.getTickets();
    }

    public static List<TicketTable> getFilteredTable(String page){
        return TicketsRepo.getFilteredTable(Integer.parseInt(page));
    }

    public static List<TicketTable> getTicketsTable(String page){
        return TicketsRepo.getTicketsTable(Integer.parseInt(page));
    }

    public static boolean setFilter(TableFilter filter){
        return TicketsRepo.setFilter(filter);
    }

    public static List<TicketTable> getTicketsTableForCompany(String id, int page){
        return TicketsRepo.getTicketsTableForCompany(id,page);
    }

    public static Ticket getTicketById(String id){
        return TicketsRepo.getTicketById(id);
    }

    public  static ServerResponse updateTicket(Ticket ticket){
        return TicketsRepo.updateTicket(ticket);
    }

    public static ServerResponse deleteTicket(String id){
        return TicketsRepo.deleteTicket(id);
    }

    public static ServerResponse createTicket(Ticket ticket){
        if(validateTicket(ticket)) {
            return TicketsRepo.createTicket(ticket);
        }
        ServerResponse response = new ServerResponse();
        response.setType("ticket");
        response.setMessage("ER-I");
        response.setStatus(400);
        return response;
    }

    public static List<Integer> getTicketsForCompany(String id){
        return TicketsRepo.getTicketsForCompany(id);
    }

    public static boolean checkVersion(String ticketId, int currVersion){
        return TicketsRepo.checkVersion(ticketId,currVersion);
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
