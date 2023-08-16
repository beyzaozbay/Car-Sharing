package carsharing.controllers;

import carsharing.models.entities.Car;
import carsharing.models.entities.Company;
import carsharing.models.entities.Customer;
import carsharing.utility.Console;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerController extends Controlling {

    private static CustomerController instance;

    public static CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

    public Customer getCurrentCustomer() throws IOException {
        List<List<Object>> list = dbController.executeQuery("SELECT * FROM customer;");
        List<Customer> customers = convertObjectListTOCustomerList(list);
        int chosenCustomer = -1;
        if (!customers.isEmpty()) {
            Console.writeLine("\nChoose a customer:");
            chosenCustomer = view.showListToChoose(customers);
        } else {
            Console.writeLine("The customer list is empty!");
        }

        return chosenCustomer > -1 ? customers.get(chosenCustomer) : null;
    }

    public void createCustomer() throws IOException {
        String name = view.creationDialogue("customer");
        dbController.execute(String.format(
                "INSERT INTO customer(name, rented_car_id) VALUES ('%s', NULL);",
                name));
        Console.writeLine("The customer was created!\n");
    }

    public void returnCar(Customer customer) {
        dbController.execute("UPDATE customer " +
                "SET rented_car_id = NULL " +
                "WHERE id = " + customer.getId() + ";");
        dbController.execute("UPDATE car " +
                "SET rented = FALSE " +
                "WHERE id = " + customer.getRented_car_id() + ";");
        Console.writeLine("You've returned a rented car!");
    }

    public void pickCar(Customer customer, Car car) {
        dbController.execute("UPDATE customer " +
                "SET rented_car_id = " + car.getId() + " " +
                "WHERE id = " + customer.getId() + ";");
        dbController.execute("UPDATE car " +
                "SET rented = TRUE " +
                "WHERE id = " + car.getId() + ";");
        Console.writeLine("You rented '" + car + "'");
    }

    public Set<Integer> carsAvailable(Company company) {
        List<List<Object>> carList = dbController.executeQuery(
                "SELECT * FROM car WHERE company_id = " + company.getId() + ";");
        List<Car> cars = convertObjectListToCarList(carList);
        Set<Integer> carsIds = cars.stream().map(Car::getId).collect(Collectors.toSet());

        List<List<Object>> customerList = dbController.executeQuery(
                "SELECT * FROM customer;");
        List<Customer> customers = convertObjectListTOCustomerList(customerList);
        Set<Integer> k = customers.stream().map(Customer::getRented_car_id).collect(Collectors.toSet());
        carsIds.removeAll(k);
        return carsIds;
    }

    public Car getRentedCar(Customer customer) {
        List<List<Object>> carList = dbController.executeQuery(
                "SELECT * FROM car WHERE id = " + customer.getRented_car_id() + ";");
        List<Car> cars = convertObjectListToCarList(carList);

        return cars.isEmpty() ? null : cars.get(0);
    }

    public Company getCarCompany(Car car) {
        List<List<Object>> companyList = dbController.executeQuery(
                "SELECT * FROM company WHERE id = " + car.getCompany_id() + ";");
        List<Company> companies = convertObjectListToCompanyList(companyList);
        return companies.isEmpty() ? null : companies.get(0);
    }

    public void showRentedCar(Customer customer) {
        Car car = getRentedCar(customer);
        Company company = getCarCompany(car);
        view.showCarInfo(car, company);
    }
}