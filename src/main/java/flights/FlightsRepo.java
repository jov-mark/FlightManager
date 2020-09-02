package flights;

import city.City;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FlightsRepo {

    private final static String URL = "jdbc:mysql://localhost:3306/web";
    private final static String USER = "root";
    private final static String PASSWORD = "anypassokha1001";

    public static List<Flight> getFlights(){
        List<Flight> flights = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getAllQuery);
            while(rs.next()){
                City origin = new City();
                origin.setId(rs.getInt(3));
                origin.setName(rs.getString(4));
                City dest = new City();
                dest.setId(rs.getInt(5));
                dest.setName(rs.getString(6));

                Flight flight = new Flight();
                flight.setId(rs.getInt(1));
                flight.setOriginCity(origin);
                flight.setDestCity(dest);
                flight.setName();
                flight.setVersion(rs.getInt(2));

                flights.add(flight);
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return flights;
    }

    private static String getAllQuery="" +
            "select flight.id, flight.version, c1.id, c1.name, c2.id, c2.name\n" +
            "from flight\n" +
            "inner join city c1\n" +
            "on flight.origin_city_id=c1.id\n" +
            "inner join city c2\n" +
            "on flight.dest_city_id=c2.id";
}
