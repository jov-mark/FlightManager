package company;

import java.util.List;

public class CompanyService {

    public static List<Company> getCompanies(){
        return CompanyRepo.getCompanies();
    }

    public static Company getCompanyById(String id){
        return CompanyRepo.getCompanyById(id);
    }

    public static void createCompany(Company company){
        CompanyRepo.createUpdateCompany(company, true);
    }

    public static void updateCompany(Company company){
        CompanyRepo.createUpdateCompany(company, false);
    }

    public static boolean delete(String id){
        return CompanyRepo.deleteCompany(id);
    }
}
