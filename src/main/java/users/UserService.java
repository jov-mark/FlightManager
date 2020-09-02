package users;

import response.ServerResponse;

public class UserService {

    public static User login(String username, String password){
        return UsersRepo.login(username,password);
    }

    public static ServerResponse register(User user){
        ServerResponse response = new ServerResponse();
        if(!UsersRepo.getUser(user.getUsername())) {
            response = UsersRepo.register(user);
        }else{
            response.setType("user");
            response.setStatus(409);
            response.setMessage("EX");
        }
        return response;
    }

}
