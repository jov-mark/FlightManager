package company;

import com.google.gson.Gson;
import response.ServerResponse;
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
        Company company = CompanyService.getCompanyById(req.params("id"));
        ServerResponse response = new ServerResponse();
        if(company==null){
            res.status(500);
            return gson.toJson(response);
        }
        if(company.getId()==0){
            res.status(404);
            response.setStatus(404);
            response.setType("object");
            response.setMessage("ER-EX");
            return gson.toJson(response);
        }
        res.status(200);
        return gson.toJson(company);
    };

    public static Route createCompany = (Request req, Response res) ->{
        res.type("application/json");
        Company company = gson.fromJson(req.body(),Company.class);
        ServerResponse response = CompanyService.createCompany(company);
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route updateCompany = (Request req, Response res) ->{
        res.type("application/json");
        Company company = gson.fromJson(req.body(),Company.class);
        ServerResponse response = CompanyService.updateCompany(company);
        res.status(response.getStatus());
        return gson.toJson(response);
    };

    public static Route deleteCompany = (Request req, Response res) ->{
        res.type("application/json");
        String companyId = req.params("id");

        ServerResponse response = new ServerResponse();
        if(TicketsController.deleteForCompany(companyId))
            response = CompanyService.delete(companyId);
        res.status(response.getStatus());
        return gson.toJson(response);
    };
}
