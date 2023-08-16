package carsharing;

import carsharing.controllers.Controller;

import java.io.IOException;
import java.sql.SQLException;

public class CarSharing {
    final Controller controller;

    public CarSharing(String[] args) throws IOException, SQLException {
        this.controller = new Controller(args);
    }

    public void run() throws IOException, SQLException {
        while (!controller.isFinished()) {
            controller.makeAction();
        }
    }
}