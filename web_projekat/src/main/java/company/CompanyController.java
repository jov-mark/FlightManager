package company;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import tickets.TicketsController;

public class CompanyController {
    private static Gson gson = new Gson();

    public static Route getCompanies = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(CompanyService.getCompanies());
    };

    public static Route getCompanyById = (Request req, Response res) ->{
        res.type("application/json");
        return gson.toJson(CompanyService.getCompanyById(req.params("id")));
    };

    public static Route createCompany = (Request req, Response res) ->{
        res.type("application/json");
        Company company = gson.fromJson(req.body(),Company.class);
        if(CompanyService.createCompany(company))
            return "OK";
        return "ERROR";
    };

    public static Route updateCompany = (Request req, Response res) ->{
        res.type("application/json");
        Company company = gson.fromJson(req.body(),Company.class);
        if(CompanyService.updateCompany(company))
            return "OK";
        return "ERROR";
    };

    public static Route deleteCompany = (Request req, Response res) ->{
        res.type("application/json");
        String companyId = req.params("id");
        if(TicketsController.deleteForCompany(companyId) && CompanyService.delete(companyId))
            return "OK";
        return "ERROR";
    };
}
