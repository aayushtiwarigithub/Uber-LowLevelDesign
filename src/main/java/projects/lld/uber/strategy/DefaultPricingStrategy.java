package projects.lld.uber.strategy;

import projects.lld.uber.entity.Location;
import projects.lld.uber.entity.Trip;

public class DefaultPricingStrategy implements IPricingStrategy {
    @Override
    public int getPrice(Trip trip) {
        int price = 35; // default charges
        double distance = calculateDistance(trip.getSourceLocation(), trip.getDestLocation());
        price += distance*8;

        return price;
    }

    private static double calculateDistance(Location source, Location dest) {
        // The radius of the Earth in kilometers.
        final double R = 6371;

        // Convert the latitudes and longitudes to radians.
        double lat1 = Math.toRadians(source.getLattitude());
        double lon1 = Math.toRadians(source.getLongitude());
        double lat2 = Math.toRadians(dest.getLattitude());
        double lon2 = Math.toRadians(dest.getLongitude());

        // Calculate the difference in latitude and longitude.
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        // Calculate the distance using the Haversine formula.
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
