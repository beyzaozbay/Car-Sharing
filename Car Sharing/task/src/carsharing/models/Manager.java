package carsharing.models;

import carsharing.view.View;
import carsharing.controllers.ManagerController;
import carsharing.models.entities.Company;

import java.io.IOException;

public class Manager {
    private static Manager instance;
    private final ManagerController managerController = ManagerController.getInstance();
    private final View view = View.getInstance();

    private boolean inAction = false;
    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public void manage() throws IOException {
        int choice = view.showManagerMenu();
        switch (choice) {
            case 1 -> manageCompanies();
            case 2 -> createCompany();
            case 0 -> setInAction(false);
            default -> view.showWarnDialogue();
        }
    }

    private void manageCompanies() throws IOException {
        Company company = managerController.showCompanyList();
        if (company != null) {
            manageCompany(company);
        }
    }

    private void createCompany() throws IOException {
        managerController.createCompany();
    }

    private void manageCompany(Company company) throws IOException {
        int choice = view.showCompanyMenu(company);
        while (choice > 0) {
            switch (choice) {
                case 1 -> managerController.showCars(company);
                case 2 -> managerController.createCar(company);
                default -> view.showWarnDialogue();
            }
            choice = view.showCompanyMenu(company);
        }
    }


    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }
}
