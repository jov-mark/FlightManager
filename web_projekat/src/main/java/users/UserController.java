package users;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {
    private static Gson gson = new Gson();

    public static Route getUsers = (Request req, Response res) ->{
        res.type("application/json");

        return gson.toJson(UserService.getUsers());
    };

    public static Route getReservations = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(UserService.getReservations("7"));
    };

    public static Route register = (Request req, Response res) ->{
        res.type("application/json");
        if(UserService.register(gson.fromJson(req.body(),User.class)))
            return "OK";
        return "User exists";
    };

    public static Route login = (Request req, Response res) ->{
        res.type("application/json");
        User user = UserService.login(req.queryParams("username"),req.queryParams("password"));
        if(user.getId()!=0)
            return gson.toJson(user);
        return "Input data is not valid!";
    };
}
