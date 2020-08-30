package auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import users.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static final String KEY = "secret";

    public static String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        claims.put("password",user.getPassword());
        claims.put("user",user);

        return Jwts.builder().setSubject(user.getUsername())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,KEY)
                .compact();
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
