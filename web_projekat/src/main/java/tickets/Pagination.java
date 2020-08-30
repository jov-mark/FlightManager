package tickets;

import java.util.List;

public class Pagination {

    List<TicketTable> ticketTable;
    int num;
    int pageInfo;

    public Pagination(List<TicketTable> ticketTable){
        this.ticketTable = ticketTable;
        pageInfo = 1;
        num = 5;
    }

    public List<TicketTable> getTicketTable(int pageInfo){
        if(ticketTable.size()==0)   return ticketTable;
        int pages = ticketTable.size()/num+1;
        if(ticketTable.size()%num==0)   pages--;
        int from = num*(pageInfo-1);
        int to = (pageInfo==pages)?ticketTable.size()-1:pageInfo*num;

        return ticketTable.subList(from,to);
    }

}
