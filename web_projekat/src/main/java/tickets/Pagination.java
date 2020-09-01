package tickets;

import java.util.List;

public class Pagination {

    protected List<TicketTable> table;
    protected int num;
    protected int pageInfo;

    protected Pagination(List<TicketTable> table){
        this.table = table;
        pageInfo = 1;
        num = 5;
    }

    protected List<TicketTable> getSubTable(int pageInfo){
        if(table.size()<num)   return table;
        int pages = table.size()/num+1;
        if(table.size()%num==0)   pages--;
        int from = num*(pageInfo-1);
        int to = (pageInfo==pages)?table.size():pageInfo*num;
        if(table.size()==from)  return table.subList(0,0);
        return table.subList(from,to);
    }
}
