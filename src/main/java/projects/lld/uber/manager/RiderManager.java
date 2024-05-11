package projects.lld.uber.manager;

import projects.lld.uber.entity.Rider;
import projects.lld.uber.exception.RiderAlreadyExistsException;
import projects.lld.uber.exception.RiderNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RiderManager {
    private Map<String, Rider> riders = new HashMap<>();
    private Lock lock = new ReentrantLock();

    public static RiderManager riderManager = null;

    private RiderManager() {

    }

    public RiderManager getDriverManager() {
        if(riderManager == null) {
            lock.lock();
            if( riderManager == null) {
                riderManager = new RiderManager();
            }
            lock.unlock();
        }
        return riderManager;
    }

    public void addRider(Rider rider) {
        if(riders.containsKey(rider)) {
            throw new RiderAlreadyExistsException("Rider already exists in the system.");
        }
        riders.put(rider.getId(), rider);
    }

    public void removeRider(Rider rider) {
        if(riders.containsKey(rider)) {
            throw new RiderNotFoundException("Rider not found in the system.");
        }
        riders.remove(rider.getId());
    }
}
