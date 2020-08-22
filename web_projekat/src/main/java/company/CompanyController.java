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
        CompanyService.createCompany(gson.fromJson(req.body(), Company.class));
        return "OK";
    };

    public static Route updateCompany = (Request req, Response res) ->{
        res.type("application/json");
        CompanyService.updateCompany(gson.fromJson(req.body(), Company.class));
        return "OK";
    };

    public static Route deleteCompany = (Request req, Response res) ->{
        res.type("application/json");
        String companyId = req.params("id");
        if(TicketsController.deleteForCompany(companyId) && CompanyService.delete(companyId))
            return "OK";
        return "ERROR";
    };
}
