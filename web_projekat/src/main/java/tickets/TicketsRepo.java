package tickets;

import city.City;
import company.Company;
import db.DBConnection;
import flights.Flight;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TicketsRepo {

    private final static String URL = "jdbc:mysql://localhost:3306/web";
    private final static String USER = "root";
    private final static String PASSWORD = "anypassokha1001";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Ticket> getTickets(){
        List<Ticket> tickets = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryAllTickets);

            while(rs.next()) {
                Company com = new Company();
                com.setId(rs.getInt(2));
                com.setName(rs.getString(3));
                com.setVersion(rs.getInt(4));

                City origin = new City();
                origin.setId(rs.getInt(11));
                origin.setName(rs.getString(12));
                City dest = new City();
                dest.setId(rs.getInt(13));
                dest.setName(rs.getString(14));

                Flight flight = new Flight();
                flight.setId(rs.getInt(9));
                flight.setOriginCity(origin);
                flight.setDestCity(dest);
                flight.setName();
                flight.setVersion(rs.getInt(10));

                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setOneWay(rs.getBoolean(5));
                ticket.setDepartureDate(rs.getDate(6));
                ticket.setReturnDate(rs.getDate(7));
                ticket.setCount(rs.getInt(8));
                ticket.setVersion(rs.getInt(15));
                ticket.setCompany(com);
                ticket.setFlight(flight);

                tickets.add(ticket);
            }
            rs.close();
            st.close();
            con.close();

        }catch (Exception e){
            System.out.println(e);
        }

        return tickets;
    }

    public static List<TicketTable> getTicketsTable(){
        List<TicketTable> table = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryTicketTable);

            while(rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt(2));
                company.setName(rs.getString(3));
                company.setVersion(rs.getInt(4));

                City origin = new City();
                origin.setId(rs.getInt(9));
                origin.setName(rs.getString(10));

                City destination = new City();
                destination.setId(rs.getInt(11));
                destination.setName(rs.getString(12));

                TicketTable tickets = new TicketTable();
                tickets.setTicketId(rs.getInt(1));
                tickets.setCompany(company);
                tickets.setOneWay(rs.getBoolean(5));
                tickets.setDepartDate(rs.getDate(6));
                tickets.setReturnDate(rs.getDate(7));
                tickets.setCount(rs.getInt(8));
                tickets.setFlightId(rs.getInt(13));
                tickets.setOrigin(origin);
                tickets.setDestination(destination);

                table.add(tickets);
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return table;
    }

    public static List<TicketTable> getTicketsTableForCompany(String id){
        List<TicketTable> table = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryTicketTableCompany+id);

            while(rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt(2));
                company.setName(rs.getString(3));
                company.setVersion(rs.getInt(4));

                City origin = new City();
                origin.setId(rs.getInt(9));
                origin.setName(rs.getString(10));

                City destination = new City();
                destination.setId(rs.getInt(11));
                destination.setName(rs.getString(12));

                TicketTable tickets = new TicketTable();
                tickets.setTicketId(rs.getInt(1));
                tickets.setCompany(company);
                tickets.setOneWay(rs.getBoolean(5));
                tickets.setDepartDate(rs.getDate(6));
                tickets.setReturnDate(rs.getDate(7));
                tickets.setCount(rs.getInt(8));
                tickets.setFlightId(rs.getInt(13));
                tickets.setOrigin(origin);
                tickets.setDestination(destination);

                table.add(tickets);
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return table;
    }

    public static boolean updateCount(String id, boolean inc){
        String query = "update ticket set count=count"+(inc?"+1":"-1")+" where id="+id;
        return preparedStatement(query);
    }

    public static boolean deleteForCompany(String id){
        List<Integer> tickets = new ArrayList<>();
        String query = "select id from ticket where company_id="+id;
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                tickets.add(rs.getInt(1));
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        for(int ticket: tickets){
            if(!deleteTicket(Integer.toString(ticket)))
                return false;
        }
        return true;
    }

    public static Ticket getTicketById(String id){
        Ticket ticket = new Ticket();
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryTicketById+id);

            while (rs.next()){
                Company com = new Company();
                com.setId(rs.getInt(2));
                com.setName(rs.getString(3));
                com.setVersion(rs.getInt(4));

                City origin = new City();
                origin.setId(rs.getInt(11));
                origin.setName(rs.getString(12));
                City dest = new City();
                dest.setId(rs.getInt(13));
                dest.setName(rs.getString(14));

                Flight flight = new Flight();
                flight.setId(rs.getInt(9));
                flight.setOriginCity(origin);
                flight.setDestCity(dest);
                flight.setName();
                flight.setVersion(rs.getInt(10));

                ticket.setId(rs.getInt(1));
                ticket.setOneWay(rs.getBoolean(5));
                ticket.setDepartureDate(rs.getDate(6));
                ticket.setReturnDate(rs.getDate(7));
                ticket.setCount(rs.getInt(8));
                ticket.setVersion(rs.getInt(15));
                ticket.setCompany(com);
                ticket.setFlight(flight);
            }
            rs.close();
            st.close();
            con.close();

        }catch (Exception e){
            System.out.println(e);
        }

        return ticket;
    }

    public static boolean updateTicket(Ticket ticket){
        if(!updateFlights(getTicketById(Integer.toString(ticket.getId())),ticket))
            return false;
        String query = "" +
                "update ticket\n" +
                "set company_id="+ticket.getCompany().getId()+", " +
                "one_way="+ticket.isOneWay()+", " +
                "departure='"+dateFormat.format(ticket.getDepartureDate())+"', " +
                "return_date="+(ticket.isOneWay()?null:"'"+(dateFormat.format(ticket.getReturnDate()))+"'")+", "+
                "flight_id="+ticket.getFlight().getId()+", " +
                "count="+ticket.getCount()+" \n" +
                "where id="+ticket.getId();
        return preparedStatement(query);
    }

    private static boolean updateFlights(Ticket oldVersion, Ticket newTicket){
        if(oldVersion.getFlight().getId()!=newTicket.getFlight().getId()) {
            String query = "update flight_tickets\n" +
                    "set flight_id="+newTicket.getFlight().getId()+"\n" +
                    "where ticket_id="+newTicket.getId();
            return preparedStatement(query);
        }
        return true;
    }

    public static boolean createTicket(Ticket ticket){
        String query="insert into ticket (company_id,one_way,departure,"+((!ticket.isOneWay())?"return_date,":"")+"flight_id,count,version)\n" +
                "values ("+ticket.getCompany().getId()+"," +
                ""+ticket.isOneWay()+",'"+dateFormat.format(ticket.getDepartureDate())+"',"+((ticket.isOneWay())?"":"'"+dateFormat.format(ticket.getReturnDate())+"',")+
                ""+ticket.getFlight().getId()+","+ticket.getCount()+",1)";
        int ticketId = getLastId(query);
        query="insert into flight_tickets (flight_id,ticket_id,version)\n" +
                "values("+ticket.getFlight().getId()+","+ticketId+",1)";
        return preparedStatement(query);
    }

    public static boolean deleteTicket(String id){
        return preparedStatement("delete from flight_tickets where ticket_id="+id)
                && preparedStatement("delete from ticket where id="+id);
    }

    private static int getLastId(String query){
        int id=0;
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    private static boolean preparedStatement(String query){
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement pst = con.prepareStatement(query);
            pst.execute();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String queryCreateTicket="INSERT INTO `ticket` " +
            "(`id`, `company_id`, `one_way`, `departure`, `flight_id`, `count`, `version`)" +
            " VALUES ('6', '2', '1', '2020-08-29', '5', '80', '1');\n";

    private static String queryTicketTableCompany="" +
            "select ticket.id,com.id, com.name,com.version, ticket.one_way, ticket.departure, ticket.return_date," +
            " ticket.count,c1.id, c1.name,c2.id, c2.name, ticket.flight_id\n" +
            "from ticket\n" +
            "inner join flight_tickets\n" +
            "on ticket.id=flight_tickets.ticket_id\n" +
            "inner join flight\n" +
            "on flight_tickets.flight_id=flight.id\n" +
            "inner join city c1\n" +
            "on flight.origin_city_id = c1.id\n" +
            "inner join city c2\n" +
            "on flight.dest_city_id = c2.id\n" +
            "inner join company com\n" +
            "on ticket.company_id=com.id\n" +
            "where com.id=";

    private static String queryTicketById = "" +
            "select ticket.id,com.id, com.name,com.version, ticket.one_way, ticket.departure, ticket.return_date," +
            "ticket.count, flight.id, flight.version, c1.id, c1.name,c2.id, c2.name, ticket.version\n" +
            "from ticket\n" +
            "inner join flight_tickets\n" +
            "on ticket.id=flight_tickets.ticket_id\n" +
            "inner join flight\n" +
            "on flight_tickets.flight_id=flight.id\n" +
            "inner join city c1\n" +
            "on flight.origin_city_id = c1.id\n" +
            "inner join city c2\n" +
            "on flight.dest_city_id = c2.id\n" +
            "inner join company com\n" +
            "on ticket.company_id=com.id\n" +
            "where ticket.id=";

    private static String queryAllTickets = "" +
            "select ticket.id,com.id, com.name,com.version, ticket.one_way, ticket.departure, ticket.return_date," +
            "ticket.count, flight.id, flight.version, c1.id, c1.name,c2.id, c2.name, ticket.version\n" +
            "from ticket\n" +
            "inner join flight_tickets\n" +
            "on ticket.id=flight_tickets.ticket_id\n" +
            "inner join flight\n" +
            "on flight_tickets.flight_id=flight.id\n" +
            "inner join city c1\n" +
            "on flight.origin_city_id = c1.id\n" +
            "inner join city c2\n" +
            "on flight.dest_city_id = c2.id\n" +
            "inner join company com\n" +
            "on ticket.company_id=com.id";

    private static String queryTicketTable = "" +
            "select ticket.id,com.id, com.name,com.version, ticket.one_way, ticket.departure, ticket.return_date," +
            " ticket.count,c1.id, c1.name,c2.id, c2.name, ticket.flight_id\n" +
            "from ticket\n" +
            "inner join flight_tickets\n" +
            "on ticket.id=flight_tickets.ticket_id\n" +
            "inner join flight\n" +
            "on flight_tickets.flight_id=flight.id\n" +
            "inner join city c1\n" +
            "on flight.origin_city_id = c1.id\n" +
            "inner join city c2\n" +
            "on flight.dest_city_id = c2.id\n" +
            "inner join company com\n" +
            "on ticket.company_id=com.id\n";

    private static String queryDelete(String id){
        return "" +
                "delete from `flight_tickets` where `ticket_id`="+id+";\n" +
                "delete from `ticket` where `id`="+id+"\n";
    }
}
