package projects.lld.uber.strategy;

import projects.lld.uber.entity.Trip;

public interface IPricingStrategy {
    int getPrice(Trip trip);
}
