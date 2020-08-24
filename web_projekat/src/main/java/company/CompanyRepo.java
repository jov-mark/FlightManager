package company;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepo {

    private final static String URL = "jdbc:mysql://localhost:3306/web";
    private final static String USER = "root";
    private final static String PASSWORD = "anypassokha1001";

    public static List<Company> getCompanies() {
        List<Company> companyList = new ArrayList<>();
        String query = "select * from company";
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs =  st.executeQuery(query);
            while(rs.next()){
                Company company = new Company();
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
                company.setVersion(rs.getInt(3));

                companyList.add(company);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e){
            System.out.println(e);
        }

        return companyList;
    }

    public static Company getCompanyById(String id){
        Company company = new Company();
        String query = "select * from company where id="+id;
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
                company.setVersion(rs.getInt(3));
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }

        return company;
    }

    public static boolean createCompany(Company company){
        String query = "insert into company(name,version)\n" +
                       " values ('"+company.getName()+"',"+company.getVersion()+")";

        return checkName(company.getName()) && preparedStatement(query);
    }

    public static boolean updateCompany(Company company){
        String query = "update company \n" +
                "set name='"+company.getName()+"', version="+company.getVersion()+"\n" +
                "where id="+company.getId();
        return checkName(company.getName()) && preparedStatement(query);
    }

    private static boolean checkName(String name){
        String query = "select id from company where name='"+name+"'";
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next())
                return false;
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteCompany(String id){
        String query = "delete from company where id="+id;
        return preparedStatement(query);
    }


    private static boolean preparedStatement(String query){
        System.out.println(query);
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
