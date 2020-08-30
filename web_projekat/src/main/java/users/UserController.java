package users;

import com.google.gson.Gson;
import response.ServerResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {
    private static Gson gson = new Gson();

    public static Route register = (Request req, Response res) ->{
        res.type("application/json");
        User newUser = gson.fromJson(req.body(),User.class);
        ServerResponse response = UserService.register(newUser);
        return response.getMessage();
    };

    public static Route login = (Request req, Response res) ->{
        res.type("application/json");
        User user = UserService.login(req.queryParams("username"),req.queryParams("password"));
        if(user.getId()!=0)
            return gson.toJson(user);
        return new ServerResponse("user","ER-L",false);
    };
}
