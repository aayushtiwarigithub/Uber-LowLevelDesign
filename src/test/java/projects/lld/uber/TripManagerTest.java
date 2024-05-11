package projects.lld.uber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projects.lld.uber.entity.*;
import projects.lld.uber.exception.DriverNotFoundException;
import projects.lld.uber.manager.DriverManager;
import projects.lld.uber.manager.RiderManager;
import projects.lld.uber.manager.TripManager;
import projects.lld.uber.strategy.IDriverMatchingStrategy;
import projects.lld.uber.strategy.IPricingStrategy;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TripManagerTest {

    private RiderManager riderManager;
    private DriverManager driverManager;
    private IDriverMatchingStrategy driverMatchingStrategy;
    private IPricingStrategy pricingStrategy;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        Field instanceField = TripManager.class.getDeclaredField("tripManager");
        instanceField.setAccessible(true);
        instanceField.set(null, null);

        riderManager = mock(RiderManager.class);
        driverManager = mock(DriverManager.class);
        pricingStrategy = mock(IPricingStrategy.class);
        driverMatchingStrategy = mock(IDriverMatchingStrategy.class);

    }

    @Test
    void testSingletonGetTripmManager() {

        TripManager firstTripManager = TripManager.getTripManager(riderManager, driverManager, pricingStrategy, driverMatchingStrategy);
        TripManager secondTripManager = TripManager.getTripManager(riderManager, driverManager, pricingStrategy, driverMatchingStrategy);

        assertNotNull(firstTripManager, "tripManager instance should not be null.");
        assertSame(firstTripManager, secondTripManager, "Both instances should be the same.");
    }

    @Test
    void testCreateRideSuccess() {
        Rider rider = new Rider("1", "John", Rating.STAR_4);
        Location sourceLocation = new Location(12.9716, 77.5946);
        Location destLocation = new Location(12.2958, 76.6394);
        Driver driver = new Driver("ABC123", "Doe", "Maruti Suzuki", new Location(12.9716, 77.5946), true);


        when(pricingStrategy.getPrice(any(Trip.class))).thenReturn(200);
        when(driverManager.findNearbyDrivers()).thenReturn(List.of(driver));
        when(driverMatchingStrategy.getDriver(any(Trip.class), any(List.class))).thenReturn(Optional.of(driver));

        TripManager.getTripManager(riderManager, driverManager, pricingStrategy, driverMatchingStrategy)
                .createRide(sourceLocation, destLocation, rider);

        verify(driverManager).findNearbyDrivers();
        verify(pricingStrategy).getPrice(any(Trip.class));
        verify(driverMatchingStrategy).getDriver(any(Trip.class), any(List.class));
    }

    @Test
    void testCreateRide_NoDriverAvailable() {
        Rider rider = new Rider("1", "John", Rating.STAR_4);
        Location sourceLocation = new Location(12.9716, 77.5946);
        Location destLocation = new Location(12.2958, 76.6394);
        Driver driver = new Driver("ABC123", "Doe", "Maruti Suzuki", new Location(12.9716, 77.5946), false);


        when(pricingStrategy.getPrice(any(Trip.class))).thenReturn(200);
        when(driverManager.findNearbyDrivers()).thenReturn(Collections.EMPTY_LIST);
        when(driverMatchingStrategy.getDriver(any(Trip.class), any(List.class))).thenReturn(Optional.empty());

        assertThrows(DriverNotFoundException.class,
                () -> TripManager.getTripManager(riderManager, driverManager, pricingStrategy, driverMatchingStrategy)
                        .createRide(sourceLocation, destLocation, rider));

        verify(driverManager).findNearbyDrivers();
        verify(pricingStrategy).getPrice(any(Trip.class));
        verify(driverMatchingStrategy).getDriver(any(Trip.class), any(List.class));
    }
}
