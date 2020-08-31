package tickets;

import java.util.List;

public class Pagination {

    List<TicketTable> table;
    int num;
    int pageInfo;

    public Pagination(List<TicketTable> table){
        this.table = table;
        pageInfo = 1;
        num = 5;
    }

    public List<TicketTable> getSubTable(int pageInfo){
        if(table.size()<=num)   return table;
        int pages = table.size()/num+1;
        if(table.size()%num==0)   pages--;
        int from = num*(pageInfo-1);
        int to = (pageInfo==pages)?table.size():pageInfo*num;

        return table.subList(from,to);
    }

}
