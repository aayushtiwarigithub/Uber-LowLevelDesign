package projects.lld.uber.manager;

import projects.lld.uber.entity.Driver;
import projects.lld.uber.exception.DriverAlreadyExistsException;
import projects.lld.uber.exception.DriverNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class DriverManager {

    private Map<String, Driver> drivers = new HashMap<>();
    private Lock lock = new ReentrantLock();

    public static DriverManager driverManager = null;

    private DriverManager() {

    }

    public DriverManager getDriverManager() {
        if(driverManager == null) {
            lock.lock();
            if( driverManager == null) {
                driverManager = new DriverManager();
            }
            lock.unlock();
        }
        return driverManager;
    }

    public void addDriver(Driver driver) {
        if(drivers.containsKey(driver)) {
            throw new DriverAlreadyExistsException("Driver already exists in the system.");
        }
        drivers.put(driver.getId(), driver);
    }

    public void removeDriver(Driver driver) {
        if(drivers.containsKey(driver)) {
            throw new DriverNotFoundException("Driver not found in the system.");
        }
        drivers.remove(driver.getId());
    }

    public List<Driver> findNearbyDrivers() {
        return drivers.values()
                .stream().filter(Driver::isAvalaible)
                .collect(Collectors.toList());
    }

}
