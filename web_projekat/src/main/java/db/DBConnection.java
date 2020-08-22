package db;

import java.sql.*;

public class DBConnection {

    private final static String URL = "jdbc:mysql://localhost:3306/web";
    private final static String USER = "root";
    private final static String PASSWORD = "anypassokha1001";

    private static Connection con;

    public static void preparedStatement(String query){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query){
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            st.close();
            return rs;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static void establishConnection(){
        try{
            con = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void closeConnection(){
        try {
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
