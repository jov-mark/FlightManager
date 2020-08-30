package users;

import response.ServerResponse;

import java.sql.*;

public class UsersRepo {

    private final static String URL = "jdbc:mysql://localhost:3306/web";
    private final static String USER = "root";
    private final static String PASSWORD = "anypassokha1001";


    public static User login(String username, String password){
        User user = new User();
        String query = "select * from user\n" +
                        "where user.username='"+username+"' and user.password='"+password+"'";
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                user.setId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setType(rs.getBoolean(4));
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return user;
    }

    public static ServerResponse register(User user){
        String query = "insert into user(user.username,user.password,user.type)" +
                "values ('"+user.getUsername()+"','"+user.getPassword()+"',"+user.isType()+")";
        ServerResponse response = new ServerResponse("user");
        if(preparedStatement(query)){
            response.setMessage("OK-C");
            response.setExecuted(true);
        }
        return response;
    }

    public static boolean getUser(String username){
        boolean exists = false;
        String query = "select * from user\n" +
                "where user.username='"+username+"'";
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next())
                exists = true;
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return exists;
    }


    private static boolean preparedStatement(String query){
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement pst = con.prepareStatement(query);
            pst.execute();
            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
