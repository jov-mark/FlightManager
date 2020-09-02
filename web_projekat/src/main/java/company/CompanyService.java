package company;

import response.ServerResponse;

import java.util.List;

public class CompanyService {

    public static List<Company> getCompanies(){
        return CompanyRepo.getCompanies();
    }

    public static Company getCompanyById(String id){
        return CompanyRepo.getCompanyById(id);
    }

    public static ServerResponse createCompany(Company company){
        if(validateInput(company))
            return CompanyRepo.createCompany(company);
        return new ServerResponse("company","ER-I", 400,false);
    }

    public static ServerResponse updateCompany(Company company){
        if(validateInput(company))
            return CompanyRepo.updateCompany(company);
        return new ServerResponse("company","ER-I", 400,false);
    }

    public static ServerResponse delete(String id){
        return CompanyRepo.deleteCompany(id);
    }

    private static boolean validateInput(Company company){
        return company.getName().equals("");
    }
}
