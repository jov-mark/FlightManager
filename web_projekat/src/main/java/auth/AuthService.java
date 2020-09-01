package auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import users.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static final String KEY = "secret";

    public static String generateToken(User user){

        String token = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512,KEY).compact();
        System.out.println("token: "+token);
        return "ok";
    }


    public static boolean isAuthorized(String token){
        if(!isEmpty(token) && token.contains("Bearer")){
            String jwt = token.substring(token.indexOf("Bearer")+7);
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt);
//            if(findUser)
            return true;
        }
        return false;
    }

    private static boolean isEmpty(String token){
        return token==null || token.isEmpty();
    }
}
