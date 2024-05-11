package projects.lld.uber.entity;

public class Trip {

    private Rider rider;
    private Driver driver;
    private Location sourceLocation;
    private Location destLocation;
    private int price;
    private TripStatus tripStatus;

    public Trip(Builder builder) {
        this.rider = builder.rider;
        this.sourceLocation = builder.sourceLocation;
        this.destLocation = builder.destLocation;
    }
    
    public static class Builder {
        private Rider rider;
        private Location sourceLocation;
        private Location destLocation;

        public Builder rider(Rider rider) {
            this.rider = rider;
            return this;
        }

        public Builder sourceLocation(Location sourceLocation) {
            this.sourceLocation = sourceLocation;
            return this;
        }

        public Builder destLocation(Location destLocation) {
            this.destLocation = destLocation;
            return this;
        }

        public Trip build() {
            return new Trip(this);
        }
    }


    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public Location getDestLocation() {
        return destLocation;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }
    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }
}
