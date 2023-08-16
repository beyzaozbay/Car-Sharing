package carsharing.utility;

public class Constants {
    public static final String PATH = "./src/carsharing/db/";
    public static final String DB_URL = "jdbc:h2:file:" + PATH;
    public static final String DRIVER = "org.h2.Driver";
    public static final String CREATE_COMPANY_TABLE =
            "CREATE TABLE IF NOT EXISTS company(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(75) UNIQUE NOT NULL);";

    public static final String CREATE_CAR_TABLE =
            "CREATE TABLE IF NOT EXISTS car(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(75) UNIQUE NOT NULL," +
                    "company_id INT NOT NULL," +
                    "rented BOOLEAN," +
                    "CONSTRAINT fk_company FOREIGN KEY (company_id)" +
                    "REFERENCES company(id));";

    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE IF NOT EXISTS customer(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(75) UNIQUE NOT NULL," +
                    "rented_car_id INT," +
                    "CONSTRAINT fk_car FOREIGN KEY (rented_car_id)" +
                    "REFERENCES car(id));";

    public static final String BACK = "0. Back";

    public static final String DID_NOT_RENT = "You didn't rent a car!\n";
}