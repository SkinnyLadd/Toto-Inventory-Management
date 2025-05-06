package com.toto.backend.services;

import com.toto.backend.entities.Chair;
import com.toto.backend.repositories.ChairRepository;
import com.toto.backend.services.interfaces.IChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing Chair entities.
 * Provides business logic and encapsulates repository operations for chairs.
 */
@Service
@Transactional
public class ChairService implements IChairService {

    private final ChairRepository chairRepository;

    @Autowired
    public ChairService(ChairRepository chairRepository) {
        this.chairRepository = chairRepository;
    }

    /**
     * Find all chairs.
     */
    public List<Chair> findAll() {
        return chairRepository.findAll();
    }

    /**
     * Find chair by ID.
     */
    public Optional<Chair> findById(Long id) {
        return chairRepository.findById(id);
    }

    /**
     * Save a chair.
     */
    public Chair save(Chair chair) {
        return chairRepository.save(chair);
    }

    /**
     * Delete a chair by ID.
     */
    public void deleteById(Long id) {
        chairRepository.deleteById(id);
    }

    /**
     * Find chairs by seating capacity.
     */
    public List<Chair> findBySeatingCapacity(int seatingCapacity) {
        return chairRepository.findBySeatingCapacity(seatingCapacity);
    }

    /**
     * Find chairs by whether they have armrests.
     */
    public List<Chair> findByHasArmrests(boolean hasArmrests) {
        return chairRepository.findByHasArmrests(hasArmrests);
    }

    /**
     * Find chairs by chair style (dining, office, rocking, etc.).
     */
    public List<Chair> findByChairStyle(String chairStyle) {
        return chairRepository.findByChairStyleContainingIgnoreCase(chairStyle);
    }

    /**
     * Find chairs by whether they are adjustable.
     */
    public List<Chair> findByIsAdjustable(boolean isAdjustable) {
        return chairRepository.findByIsAdjustable(isAdjustable);
    }

    /**
     * Find chairs by whether they have wheels.
     */
    public List<Chair> findByHasWheels(boolean hasWheels) {
        return chairRepository.findByHasWheels(hasWheels);
    }

    /**
     * Find chairs by multiple criteria.
     */
    public List<Chair> findByStyleAndArmrestsAndAdjustable(
            String chairStyle, boolean hasArmrests, boolean isAdjustable) {
        return chairRepository.findByChairStyleAndHasArmrestsAndIsAdjustable(
                chairStyle, hasArmrests, isAdjustable);
    }

    /**
     * Find office chairs (chairs with wheels and adjustable height).
     */
    public List<Chair> findOfficeChairs() {
        return chairRepository.findByHasWheelsAndIsAdjustable(true, true);
    }

    /**
     * Find dining chairs (chairs without wheels and with a specific style).
     */
    public List<Chair> findDiningChairs() {
        return chairRepository.findDiningChairs("dining");
    }

    /**
     * Find chairs by price range and seating capacity.
     */
    public List<Chair> findByPriceRangeAndSeatingCapacity(
            double minPrice, double maxPrice, int seatingCapacity) {
        return chairRepository.findByPriceBetweenAndSeatingCapacity(
                minPrice, maxPrice, seatingCapacity);
    }

    /**
     * Find chairs by manufacturer and chair style.
     */
    public List<Chair> findByManufacturerAndStyle(String manufacturer, String chairStyle) {
        return chairRepository.findByManufacturerContainingIgnoreCaseAndChairStyleContainingIgnoreCase(
                manufacturer, chairStyle);
    }

    /**
     * Calculate discount for a chair based on its properties.
     * Business logic example: Office chairs get larger discounts, dining chairs get smaller discounts.
     */
    public double calculateDiscount(Chair chair) {
        double baseDiscount = 0.05; // 5% base discount

        // Office chairs (with wheels and adjustable) get larger discounts
        if (chair.isHasWheels() && chair.isAdjustable()) {
            baseDiscount += 0.03; // Additional 3% for office chairs
        }

        // Chairs with armrests get additional discount
        if (chair.isHasArmrests()) {
            baseDiscount += 0.01; // Additional 1% for chairs with armrests
        }

        // Dining chairs get smaller discounts
        if (chair.getChairStyle().toLowerCase().contains("dining")) {
            baseDiscount = Math.max(0.02, baseDiscount - 0.02); // Reduce by 2% but minimum 2%
        }

        return baseDiscount;
    }

    /**
     * Recommend chair maintenance schedule based on chair type.
     * Business logic example: Different chair types need different maintenance schedules.
     */
    public String recommendMaintenanceSchedule(Chair chair) {
        if (chair.isHasWheels() && chair.isAdjustable()) {
            return "Office chairs: Check wheels and adjustment mechanisms every 3 months.";
        } else if (chair.getChairStyle().toLowerCase().contains("rocking")) {
            return "Rocking chairs: Check rockers and joints every 6 months.";
        } else if (chair.getChairStyle().toLowerCase().contains("dining")) {
            return "Dining chairs: Check legs and joints annually.";
        } else {
            return "General chairs: Inspect annually for loose parts and wear.";
        }
    }
}
