package carsharing.controllers;

import carsharing.view.View;
import carsharing.models.Manager;
import carsharing.models.entities.Customer;
import carsharing.utility.Console;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {
    private final View view = View.getInstance();
    private DBController dbController;
    private boolean isFinished;

    public Controller(String[] args) {
        try {
            dbController = DBController.getInstance(args);
            isFinished = false;
        } catch (SQLException e) {
            Console.writeLine("Controller() SQLException caught");
            Console.writeLine(e.getMessage());
        }
    }

    public void makeAction() {
        try {
            int choice = mainMenu();
            switch (choice) {
                case 1 -> managerAction();
                case 2 -> customerAction();
                case 3 -> createCustomer();
                case 0 -> {
                    setFinished(true);
                    dbController.terminateConnection();
                }
                default -> throw new IOException();
            }
        } catch (IOException e) {
            view.showWarnDialogue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int mainMenu() throws IOException {
        return view.showMainMenu();
    }

    private void managerAction() throws IOException, SQLException {
        Manager manager = Manager.getInstance();
        manager.setInAction(true);
        while (manager.isInAction()) {
            manager.manage();
        }
    }

    private void customerAction() throws IOException {
        Customer customer = CustomerController.getInstance().getCurrentCustomer();
        if (customer == null) {
            return;
        }

        customer.setInAction(true);
        while (customer.isInAction()) {
            customer.act();
        }
    }

    private void createCustomer() throws IOException {
        CustomerController.getInstance().createCustomer();
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
