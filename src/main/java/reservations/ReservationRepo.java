package reservations;

import city.City;
import company.Company;
import response.ServerResponse;
import tickets.TicketTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepo {

    private final static String URL = "jdbc:mysql://localhost:3306/web";
    private final static String USER = "root";
    private final static String PASSWORD = "anypassokha1001";

    public static List<ReservationTable> getReservationsTable(String id){
        List<ReservationTable> reservationTable = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getReservationTableQuery+id);
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt(10));
                company.setName(rs.getString(11));
                company.setVersion(rs.getInt(12));

                City origin = new City();
                origin.setId(rs.getInt(13));
                origin.setName(rs.getString(14));
                City dest = new City();
                dest.setId(rs.getInt(15));
                dest.setName(rs.getString(16));

                ReservationTable reservation = new ReservationTable();
                reservation.setReservationId(rs.getInt(1));
                reservation.setAvailable(rs.getBoolean(2));
                reservation.setTicketId(rs.getInt(4));
                reservation.setOneWay(rs.getBoolean(5));
                reservation.setDepartDate(rs.getDate(6));
                reservation.setReturnDate(rs.getDate(7));
                reservation.setCount(rs.getInt(8));
                reservation.setCompany(company);
                reservation.setOrigin(origin);
                reservation.setDestination(dest);
                reservation.setVersion(rs.getInt(17));

                reservationTable.add(reservation);
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return reservationTable;
    }

    public static ServerResponse createReservation(TicketTable ticket, String userId){
        String query = "\n" +
                "insert into reservation (is_available,flight_id,ticket_id,user_id,version)\n" +
                "values (true, "+ticket.getFlightId()+", "+ticket.getTicketId()+", "+userId+", 1)";
        int id = getLastId(query);
        query = "insert into user_reservations (user_id,reservation_id,version)\n" +
                "values ("+userId+","+id+",1)";
        ServerResponse response = new ServerResponse();
        if(preparedStatement(query)){
            response.setType("reservation");
            response.setMessage("OK-C");
            response.setStatus(201);
            response.setExecuted(true);
        }
        return response;
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

    public static boolean deleteForTicket(String ticketId){
        List<Integer> reservationList = new ArrayList<>();
        String query = "select id from reservation " +
                "where ticket_id="+ticketId;
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                reservationList.add(rs.getInt(1));
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        for(int res: reservationList){
            if(!deleteReservation(Integer.toString(res)).isExecuted())
                return false;
        }
        return true;
    }

    public static ServerResponse deleteReservation(String id){
        ServerResponse response = new ServerResponse();
        if(preparedStatement("delete from user_reservations where reservation_id="+id)
                && preparedStatement("delete from reservation where id="+id)){
            response.setType("reservation");
            response.setExecuted(true);
            response.setMessage("OK-D");
            response.setStatus(200);
        }
        return response;
    }

    public static String getTicketId(String id){
        String query = "select ticket_id from reservation where id="+id;
        String ticketId="";
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                ticketId = rs.getString(1);
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ticketId;
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

    private static String getReservationTableQuery="" +
            "select reservation.id, reservation.is_available, reservation.version, ticket.id, ticket.one_way, \n" +
            "ticket.departure, ticket.return_date, ticket.count, ticket.version, company.id, company.name, company.version, \n" +
            "c1.id, c1.name, c2.id, c2.name, ticket.version\n" +
            "from reservation\n" +
            "inner join ticket\n" +
            "on ticket.id=reservation.ticket_id\n" +
            "inner join company\n" +
            "on ticket.company_id=company.id\n" +
            "inner join flight\n" +
            "on reservation.flight_id=flight.id\n" +
            "inner join city c1\n" +
            "on flight.origin_city_id=c1.id\n" +
            "inner join city c2\n" +
            "on flight.dest_city_id=c2.id\n" +
            "where reservation.user_id=";
}
