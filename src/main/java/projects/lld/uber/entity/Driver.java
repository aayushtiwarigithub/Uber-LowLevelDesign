package projects.lld.uber.entity;


public class Driver {

    private String id;

    private String name;

    private String vehicle;

    private Location location;

    private boolean isAvalaible;

    public Driver(String id, String name, String vehicle, Location location, boolean isAvalaible) {
        this.id = id;
        this.name = name;
        this.vehicle = vehicle;
        this.location = location;
        this.isAvalaible = isAvalaible;
    }

    public Driver() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAvalaible() {
        return isAvalaible;
    }

    public void setAvalaible(boolean avalaible) {
        isAvalaible = avalaible;
    }
}
