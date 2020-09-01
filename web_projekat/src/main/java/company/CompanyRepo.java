package company;


import response.ServerResponse;

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
            return null;
        }

        return company;
    }

    public static ServerResponse createCompany(Company company){
        String query = "insert into company(name,version)\n" +
                       " values ('"+company.getName()+"',"+company.getVersion()+")";
        ServerResponse response = new ServerResponse();
        if(!checkName(company.getName())){
            response.setType("company");
            response.setMessage("EX");
            response.setStatus(409);
        }   else if(preparedStatement(query)){
            response.setType("company");
            response.setMessage("OK-C");
            response.setStatus(201);
            response.setExecuted(true);
        }
        return response;
    }

    public static ServerResponse updateCompany(Company company){
        ServerResponse response = new ServerResponse();
        if(!checkVersion(company.getId(),company.getVersion())){
            response.setStatus(409);
            return response;
        }
        String query = "update company \n" +
                "set "+
                " name='"+company.getName()+"', version="+(company.getVersion()+1)+"\n" +
                "where id="+company.getId();
        if(!checkName(company.getName())){
            response.setType("company");
            response.setMessage("EX");
            response.setStatus(409);
        }else if(preparedStatement(query)){
            response.setType("company");
            response.setMessage("OK-U");
            response.setStatus(200);
            response.setExecuted(true);
        }
        return response;
    }

    public static ServerResponse deleteCompany(String id){
        String query = "delete from company where id="+id;
        ServerResponse response = new ServerResponse();
        if(preparedStatement(query)){
            response.setType("company");
            response.setMessage("OK-D");
            response.setStatus(200);
            response.setExecuted(true);
        }
        return response;
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

    private static boolean checkVersion(int companyId, int currVersion){
        int newestVersion = 0;
        String query = "select version from company where id="+companyId;
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                newestVersion = rs.getInt(1);
            }
            rs.close();
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        if(newestVersion!=0)
            return newestVersion==currVersion;
        return false;
    }

    private static boolean preparedStatement(String query){
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
