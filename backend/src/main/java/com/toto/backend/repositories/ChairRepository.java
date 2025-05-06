package com.toto.backend.repositories;

import com.toto.backend.entities.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Chair entities.
 * Provides methods for chair-specific operations in a Pakistani furniture store.
 */
@Repository
public interface ChairRepository extends JpaRepository<Chair, Long> {

    /**
     * Find chairs by seating capacity.
     */
    List<Chair> findBySeatingCapacity(int seatingCapacity);

    /**
     * Find chairs by whether they have armrests.
     */
    List<Chair> findByHasArmrests(boolean hasArmrests);

    /**
     * Find chairs by chair style (dining, office, rocking, etc.).
     */
    List<Chair> findByChairStyleContainingIgnoreCase(String chairStyle);

    /**
     * Find chairs by whether they are adjustable.
     */
    List<Chair> findByIsAdjustable(boolean isAdjustable);

    /**
     * Find chairs by whether they have wheels.
     */
    List<Chair> findByHasWheels(boolean hasWheels);

    /**
     * Find chairs by multiple criteria.
     */
    List<Chair> findByChairStyleAndHasArmrestsAndIsAdjustable(
            String chairStyle, boolean hasArmrests, boolean isAdjustable);

    /**
     * Find office chairs (chairs with wheels and adjustable height).
     */
    List<Chair> findByHasWheelsAndIsAdjustable(boolean hasWheels, boolean isAdjustable);

    /**
     * Find dining chairs (chairs without wheels and with a specific style).
     */
    @Query("SELECT c FROM Chair c WHERE c.hasWheels = false AND c.chairStyle LIKE %:style%")
    List<Chair> findDiningChairs(@Param("style") String style);

    /**
     * Find chairs by price range and seating capacity.
     */
    List<Chair> findByPriceBetweenAndSeatingCapacity(
            double minPrice, double maxPrice, int seatingCapacity);

    /**
     * Find chairs by manufacturer and chair style.
     */
    List<Chair> findByManufacturerContainingIgnoreCaseAndChairStyleContainingIgnoreCase(
            String manufacturer, String chairStyle);
}
