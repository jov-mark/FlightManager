package city;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class CityController {
    private static Gson gson = new Gson();

    public static Route getCities = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(CityService.getCities());
    };
}
