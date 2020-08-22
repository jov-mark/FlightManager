package users;

import reservations.Reservation;

import java.util.List;

public class UserService {

    public static List<User> getUsers() {
        return UsersRepo.getUsers();
    }

    public static User login(String username, String password){
        return UsersRepo.login(username,password);
    }

    private static boolean checkUsername(String username){
        return UsersRepo.getUser(username);
    }

    public static boolean register(User user){
        if(!checkUsername(user.getUsername())) {
            UsersRepo.register(user);
            return true;
        }
        return false;
    }

    public static List<Reservation> getReservations(String userId){
        return UsersRepo.getReservations(userId);
    }

}
