package carsharing.view;

import carsharing.models.entities.Car;
import carsharing.models.entities.Company;
import carsharing.utility.Console;
import carsharing.utility.Constants;

import java.io.IOException;
import java.util.List;

public class View {
    private static View instance;

    public static View getInstance() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

    public int showMainMenu() throws IOException {
        Console.writeLine("1. Log in as a manager");
        Console.writeLine("2. Log in as a customer");
        Console.writeLine("3. Create a customer");
        Console.writeLine("0. Exit");
        return Console.readInt();
    }

    public int showManagerMenu() throws IOException {
        Console.writeLine("1. Company list");
        Console.writeLine("2. Create a company");
        Console.writeLine(Constants.BACK);
        return Console.readInt();
    }

    public int showCustomerMenu() throws IOException {
        Console.writeLine("1. Rent a car");
        Console.writeLine("2. Return a rented car");
        Console.writeLine("3. My rented car");
        Console.writeLine(Constants.BACK);
        return Console.readInt();
    }

    public void showList(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            Console.writeLine(i + 1 + ". " + list.get(i).toString());
        }
    }

    public int showListToChoose(List<?> list) throws IOException {
        showList(list);
        Console.writeLine(Constants.BACK);
        return Console.readInt() - 1;
    }

    public int showCompanyMenu(Company company) throws IOException {
        Console.writeLine(String.format("%n'%s' company", company.getName()));
        Console.writeLine("1. Car list");
        Console.writeLine("2. Create a car");
        Console.writeLine(Constants.BACK);
        return Console.readInt();
    }

    public void showCarInfo(Car car, Company company) {
        Console.writeLine("Your rented car:");
        Console.writeLine(car.toString());
        Console.writeLine("Company:");
        Console.writeLine(company.toString());
    }

    public void showWarnDialogue() {
        Console.writeLine("Invalid record. Please, check your input.\n");
    }


    public String creationDialogue(String o) throws IOException {
        Console.writeLine("Enter the " + o + " name:");
        return Console.readLine();
    }
}
