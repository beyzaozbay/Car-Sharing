package carsharing.models.entities;

import carsharing.view.View;
import carsharing.controllers.CustomerController;
import carsharing.utility.Console;
import carsharing.utility.Constants;

import java.io.IOException;
import java.util.Set;

public class Customer extends Entity {

    private final CustomerController customerController = CustomerController.getInstance();

    private final View view = View.getInstance();
    private Integer rented_car_id;
    private boolean inAction = false;

    public Customer(int id, String name, int rented_car_id) {
        super(id, name);
        this.rented_car_id = rented_car_id;
    }

    public void act() throws IOException {
        int choice = view.showCustomerMenu();
        switch (choice) {
            case 1 -> rentCar();
            case 2 -> returnRentedCar();
            case 3 -> getRentedCar();
            case 0 -> setInAction(false);
            default -> view.showWarnDialogue();
        }
    }

    private void rentCar() throws IOException {
        if (getRented_car_id() < 1) {
            Company company = customerController.showCompanyList();
            if (company != null) {
                Set<Integer> availableIds = customerController.carsAvailable(company);
                if (!availableIds.isEmpty()) {
                    Car car = chooseCar(company);
                    if (car != null) {
                        setRented_car_id(car.getId());
                    }
                } else {
                    Console.writeLine("No available cars in the '" + company + "' company");
                }
            } else {
                Console.writeLine("The company list is empty!");
            }
        } else {
            Console.writeLine("You've already rented a car!");
        }
    }

    private Car chooseCar(Company company) throws IOException {
        Car car = customerController.showCarList(company, true);
        if (car != null) {
            customerController.pickCar(this, car);
        }

        return car;
    }

    private void returnRentedCar() {
        Car car = customerController.getRentedCar(this);

        if (car != null) {
            customerController.returnCar(this);
        }
        else {
            Console.writeLine(Constants.DID_NOT_RENT);
        }
    }

    private void getRentedCar() {
        if (getRented_car_id() != null && getRented_car_id() > 0) {
            customerController.showRentedCar(this);
        }
        else {
            Console.writeLine(Constants.DID_NOT_RENT);
        }
    }

    public Integer getRented_car_id() {
        return rented_car_id;
    }

    public void setRented_car_id(Integer rented_car_id) {
        this.rented_car_id = rented_car_id;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }


}