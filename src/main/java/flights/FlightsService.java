package flights;

import java.util.List;

public class FlightsService {

    public static List<Flight> getFlights(){
        return FlightsRepo.getFlights();
    }
}
