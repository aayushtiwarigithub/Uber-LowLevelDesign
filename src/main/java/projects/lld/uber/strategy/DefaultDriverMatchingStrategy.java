package projects.lld.uber.strategy;

import projects.lld.uber.entity.Driver;
import projects.lld.uber.entity.Trip;

import java.util.List;
import java.util.Optional;

public class DefaultDriverMatchingStrategy implements IDriverMatchingStrategy {
    @Override
    public Optional<Driver> getDriver(Trip trip, List<Driver> nearbyDrivers) {
        // After getting all the drivers nearby the source location, notification is sent to all the drivers with trip metadata
        // and whichever driver accepts the request first gets the trip.
        return nearbyDrivers.stream().findAny();
    }
}
