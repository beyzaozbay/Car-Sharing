package carsharing.models.entities;

public class Car extends Entity {

    private final int company_id;

    public Car(int id, String name, int company_id) {
        super(id, name);
        this.company_id = company_id;
    }

    public int getCompany_id() {
        return company_id;
    }
}
