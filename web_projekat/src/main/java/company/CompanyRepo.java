package company;

import db.DBConnection;

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

    public static void createUpdateCompany(Company company,boolean create){
        String query = (create) ? "insert into company(name,version)" +
                                    " values (\""+company.getName()+"\",1)"
                                : "update company \n" +
                                    "set name=\""+company.getName()+"\", version="+(company.getVersion()+1)+"\n" +
                                    "where id="+company.getId();
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement pst = con.prepareStatement(query);
            pst.execute();
            pst.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static boolean deleteCompany(String id){
        String query = "delete from company where id="+id;
        try{
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement pst = con.prepareStatement(query);
            pst.execute();
            pst.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

}
