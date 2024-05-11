package projects.lld.uber.manager;

import projects.lld.uber.entity.*;
import projects.lld.uber.exception.DriverNotFoundException;
import projects.lld.uber.strategy.IDriverMatchingStrategy;
import projects.lld.uber.strategy.IPricingStrategy;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TripManager {

    private static TripManager tripManager = null;

    private RiderManager riderManager;
    private DriverManager driverManager;
    private IPricingStrategy pricingStrategy;
    private IDriverMatchingStrategy driverMatchingStrategy;
    

    private final static Lock lock = new ReentrantLock();

    private TripManager(RiderManager riderManager, DriverManager driverManager, IPricingStrategy pricingStrategy, IDriverMatchingStrategy driverMatchingStrategy) {
        this.riderManager = riderManager;
        this.driverManager = driverManager;
        this.pricingStrategy = pricingStrategy;
        this.driverMatchingStrategy = driverMatchingStrategy;
    }

    public static TripManager getTripManager(RiderManager riderManager, DriverManager driverManager, IPricingStrategy pricingStrategy, IDriverMatchingStrategy driverMatchingStrategy) {
        if(tripManager == null) {
            lock.lock();
            try {
                if(tripManager == null) {
                    tripManager = new TripManager(riderManager, driverManager, pricingStrategy, driverMatchingStrategy);
                }
            }
            finally {
                lock.unlock();
            }
        }
        return tripManager;
    }

    public void createRide(Location sourceLocation, Location destLocation, Rider rider) {
        Trip trip = new Trip.Builder()
                .rider(rider)
                .sourceLocation(sourceLocation)
                .destLocation(destLocation)
                .build();

        int price = pricingStrategy.getPrice(trip);
        trip.setPrice(price);

        List<Driver> nearbyDrivers = driverManager.findNearbyDrivers();
        Optional<Driver> driver = driverMatchingStrategy.getDriver(trip, nearbyDrivers);

        if(!driver.isPresent()) throw new DriverNotFoundException("No driver is available right now in your area.");
        trip.setDriver(driver.get());

        trip.setTripStatus(TripStatus.DRIVER_ON_THE_WAY);
    }
}
