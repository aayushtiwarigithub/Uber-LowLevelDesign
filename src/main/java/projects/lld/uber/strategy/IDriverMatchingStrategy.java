package projects.lld.uber.strategy;

import projects.lld.uber.entity.Driver;
import projects.lld.uber.entity.Trip;

import java.util.List;
import java.util.Optional;

public interface IDriverMatchingStrategy {

    Optional<Driver> getDriver(Trip trip, List<Driver> nearbyDrivers);
}
