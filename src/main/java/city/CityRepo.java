package city;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CityRepo {

    private final static String URL = "jdbc:mysql://localhost:3306/web";
    private final static String USER = "root";
    private final static String PASSWORD = "anypassokha1001";

    public static List<City> getCities() {
        List<City> cityList = new ArrayList<>();
        String query = "select * from city";
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                City city = new City();
                city.setId(rs.getInt(1));
                city.setName(rs.getString(2));

                cityList.add(city);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return cityList;
    }
}
