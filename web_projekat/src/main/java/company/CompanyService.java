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
        return CompanyRepo.createCompany(company);
    }

    public static ServerResponse updateCompany(Company company){
        return CompanyRepo.updateCompany(company);
    }

    public static ServerResponse delete(String id){
        return CompanyRepo.deleteCompany(id);
    }
}
