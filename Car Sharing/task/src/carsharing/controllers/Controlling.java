package carsharing.controllers;

import carsharing.view.View;
import carsharing.models.entities.Car;
import carsharing.models.entities.Company;
import carsharing.models.entities.Customer;
import carsharing.utility.Console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Controlling {

    protected final View view = View.getInstance();
    protected final DBController dbController = DBController.getInstance();

    public Company showCompanyList() throws IOException {
        List<List<Object>> list = dbController.executeQuery("SELECT * FROM company;");
        List<Company> companies = convertObjectListToCompanyList(list);
        int chosenCompany = -1;
        if (!companies.isEmpty()) {
            Console.writeLine("\nChoose the company:");
            chosenCompany = view.showListToChoose(companies);
        } else {
            Console.writeLine("The company list is empty!");
        }

        return chosenCompany > -1 ? companies.get(chosenCompany) : null;
    }

    public void showCars(Company company) {
        List<List<Object>> list = dbController.executeQuery(
                "SELECT * FROM car WHERE company_id = " + company.getId() + ";");
        List<Car> cars = convertObjectListToCarList(list);
        if (!cars.isEmpty()) {
            Console.writeLine("\nCar list:");
            view.showList(cars);
        } else {
            Console.writeLine("The car list is empty!");
        }
    }

    public Car showCarList(Company company, boolean restriction) throws IOException {
        String query = "SELECT * FROM car WHERE company_id = " + company.getId();
        if (restriction) {
            query = query + " AND rented = FALSE";
        }
        List<List<Object>> list = dbController.executeQuery(query + ";");
        List<Car> cars = convertObjectListToCarList(list);
        int chosenCar = -1;
        if (!cars.isEmpty()) {
            Console.writeLine("\nCar list:");
            chosenCar = view.showListToChoose(cars);
        } else {
            Console.writeLine("The car list is empty!");
        }

        return chosenCar > -1 ? cars.get(chosenCar) : null;
    }

    protected List<Car> convertObjectListToCarList(List<List<Object>> list) {
        List<Car> cars = new ArrayList<>();
        for (List<Object> each : list) {
            cars.add(new Car((int) each.get(0), (String) each.get(1), (int) each.get(2)));
        }
        return cars;
    }

    protected List<Company> convertObjectListToCompanyList(List<List<Object>> list) {
        List<Company> companies = new ArrayList<>();
        for (List<Object> each : list) {
            companies.add(new Company((int) each.get(0), (String) each.get(1)));
        }
        return companies;
    }

    protected List<Customer> convertObjectListTOCustomerList(List<List<Object>> list) {
        List<Customer> customers = new ArrayList<>();
        for (List<Object> each : list) {
            customers.add(new Customer(
                    (int) each.get(0),
                    (String) each.get(1),
                    (int) Optional.ofNullable(each.get(2)).orElse(0)));
        }
        return customers;
    }
}
