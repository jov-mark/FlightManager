package company;

import java.util.List;

public class CompanyService {

    public static List<Company> getCompanies(){
        return CompanyRepo.getCompanies();
    }

    public static Company getCompanyById(String id){
        return CompanyRepo.getCompanyById(id);
    }

    public static boolean createCompany(Company company){
        return CompanyRepo.createCompany(company);
    }

    public static boolean updateCompany(Company company){
        return CompanyRepo.updateCompany(company);
    }

    public static boolean delete(String id){
        return CompanyRepo.deleteCompany(id);
    }
}
