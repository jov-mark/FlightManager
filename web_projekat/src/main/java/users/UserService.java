package users;

import response.ServerResponse;

public class UserService {

    public static User login(String username, String password){
        return UsersRepo.login(username,password);
    }

    private static boolean checkUsername(String username){
        return UsersRepo.getUser(username);
    }

    public static ServerResponse register(User user){
        ServerResponse response = new ServerResponse("user");
        if(!checkUsername(user.getUsername())) {
            response = UsersRepo.register(user);
        }else{
            response.setStatus(409);
            response.setMessage("EX");
        }
        return response;
    }

}
