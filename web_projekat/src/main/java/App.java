
import city.CityController;
import company.CompanyController;
import flights.FlightsController;
import reservations.ReservationController;
import tickets.TicketsController;
import users.UserController;

import java.io.File;
import java.io.IOException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws IOException {
        port(8080);

        staticFiles.externalLocation(new File("./static").getCanonicalPath());

//      Tickets data:
        get("/rest/ticket/table/:page", TicketsController.getTicketsTable);
        post("/rest/ticket/filterTable", TicketsController.filterTable);
        get("/rest/ticket/getFilteredTable/:page", TicketsController.getFilteredTable);
        get("/rest/ticket/get/:id", TicketsController.getTicketById);
        get("/rest/ticket/company", TicketsController.getTicketsTableForCompany);
        post("/rest/ticket/create", TicketsController.createTicket);
        post("/rest/ticket/update", TicketsController.updateTicket);
        delete("/rest/ticket/delete/:id", TicketsController.deleteTicket);

//      Company data:
        get("/rest/company/getAll", CompanyController.getCompanies);
        get("/rest/company/get/:id", CompanyController.getCompanyById);
        post("/rest/company/create", CompanyController.createCompany);
        post("/rest/company/update", CompanyController.updateCompany);
        delete("/rest/company/delete/:id", CompanyController.deleteCompany);

//        Reservations data:
        get("/rest/reservation/table/:id", ReservationController.getReservationsTable);
        post("/rest/reservation/create/:id", ReservationController.createReservation);
        post("/rest/reservation/delete", ReservationController.deleteReservation);

//        Flight data:
        get("/rest/flight/getAll", FlightsController.getFlights);

//        City data:
        get("/rest/city/getAll", CityController.getCities);

//      User data:
        get("/rest/user/login", UserController.login);
        post("/rest/user/register",UserController.register);

    }
}
