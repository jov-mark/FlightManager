package flights;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class FlightsController {

    private static Gson gson = new Gson();

    public static Route getFlights = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(FlightsService.getFlights());
    };
}
