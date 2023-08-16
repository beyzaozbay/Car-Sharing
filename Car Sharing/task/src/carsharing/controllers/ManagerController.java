package carsharing.controllers;

import carsharing.models.entities.Company;
import carsharing.utility.Console;

import java.io.IOException;

public class ManagerController extends Controlling {
    private static ManagerController instance;

    public static ManagerController getInstance() {
        if (instance == null) {
            instance = new ManagerController();
        }
        return instance;
    }

    public void createCompany() throws IOException {
        String name = view.creationDialogue("company");
        dbController.execute(String.format("INSERT INTO company(name) VALUES ('%s');", name));
        Console.writeLine("The company was created!\n");
    }

    public void createCar(Company company) throws IOException {
        String name = view.creationDialogue("car");
        dbController.execute(String.format("INSERT INTO car(name, company_id, rented) VALUES ('%s', %d, %b);",
                name, company.getId(), false));
        Console.writeLine("The car was added!\n");
    }
}
