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
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route login = (Request req, Response res) ->{
        res.type("application/json");
        User user = UserService.login(req.queryParams("username"),req.queryParams("password"));
        ServerResponse response = new ServerResponse();
        if(user == null){
            res.status(response.getStatus());
            return gson.toJson(response);
        }
        if(user.getId()!=0){
//            user.setToken(AuthService.generateToken(user));
            res.status(200);
            return gson.toJson(user);
        }
        response.setType("user");
        response.setMessage("ER-L");
        response.setStatus(400);
        res.status(400);
        return gson.toJson(response);
    };
}
