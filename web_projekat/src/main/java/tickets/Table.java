package tickets;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<TicketTable> tickets;
    private TableFilter filter;

    public Table() {
        this.tickets = new ArrayList<>();
        this.filter = new TableFilter();
    }

    public List<TicketTable> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketTable> tickets) {
        this.tickets = tickets;
    }

    public TableFilter getFilter() {
        return filter;
    }

    public void setFilter(TableFilter filter) {
        this.filter = filter;
    }
}
