package com.toto.backend.services;

import com.toto.backend.entities.Sofa;
import com.toto.backend.repositories.SofaRepository;
import com.toto.backend.services.interfaces.ISofaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing Sofa entities.
 * Provides business logic and encapsulates repository operations for sofas.
 */
@Service
@Transactional
public class SofaService implements ISofaService {

    private final SofaRepository sofaRepository;

    @Autowired
    public SofaService(SofaRepository sofaRepository) {
        this.sofaRepository = sofaRepository;
    }

    /**
     * Find all sofas.
     */
    public List<Sofa> findAll() {
        return sofaRepository.findAll();
    }

    /**
     * Find sofa by ID.
     */
    public Optional<Sofa> findById(Long id) {
        return sofaRepository.findById(id);
    }

    /**
     * Save a sofa.
     */
    public Sofa save(Sofa sofa) {
        return sofaRepository.save(sofa);
    }

    /**
     * Delete a sofa by ID.
     */
    public void deleteById(Long id) {
        sofaRepository.deleteById(id);
    }

    /**
     * Find sofas by seating capacity.
     */
    public List<Sofa> findBySeatingCapacity(int seatingCapacity) {
        return sofaRepository.findBySeatingCapacity(seatingCapacity);
    }

    /**
     * Find sofas by whether they are convertible to a bed.
     */
    public List<Sofa> findByIsConvertible(boolean isConvertible) {
        return sofaRepository.findByIsConvertible(isConvertible);
    }

    /**
     * Find sofas by upholstery type (leather, fabric, etc.).
     */
    public List<Sofa> findByUpholsteryType(String upholsteryType) {
        return sofaRepository.findByUpholsteryTypeContainingIgnoreCase(upholsteryType);
    }

    /**
     * Find sofas by number of cushions.
     */
    public List<Sofa> findByNumberOfCushions(int numberOfCushions) {
        return sofaRepository.findByNumberOfCushions(numberOfCushions);
    }

    /**
     * Find sofas by whether they have recliners.
     */
    public List<Sofa> findByHasRecliners(boolean hasRecliners) {
        return sofaRepository.findByHasRecliners(hasRecliners);
    }

    /**
     * Find sofas by seating capacity greater than or equal to the given value.
     */
    public List<Sofa> findByMinSeatingCapacity(int minSeatingCapacity) {
        return sofaRepository.findBySeatingCapacityGreaterThanEqual(minSeatingCapacity);
    }

    /**
     * Find sofas by upholstery type and seating capacity.
     */
    public List<Sofa> findByUpholsteryTypeAndSeatingCapacity(String upholsteryType, int seatingCapacity) {
        return sofaRepository.findByUpholsteryTypeAndSeatingCapacity(upholsteryType, seatingCapacity);
    }

    /**
     * Find convertible sofas (sofas that can be converted to beds).
     */
    public List<Sofa> findConvertibleSofas() {
        return sofaRepository.findByIsConvertibleTrue();
    }

    /**
     * Find recliner sofas (sofas with recliners).
     */
    public List<Sofa> findReclinerSofas() {
        return sofaRepository.findByHasReclinersTrue();
    }

    /**
     * Find luxury sofas (leather sofas with recliners).
     */
    public List<Sofa> findLuxurySofas() {
        return sofaRepository.findLuxurySofas();
    }

    /**
     * Find sofas by price range and upholstery type.
     */
    public List<Sofa> findByPriceRangeAndUpholsteryType(
            double minPrice, double maxPrice, String upholsteryType) {
        return sofaRepository.findByPriceBetweenAndUpholsteryTypeContainingIgnoreCase(
                minPrice, maxPrice, upholsteryType);
    }

    /**
     * Find sofas by manufacturer and whether they have recliners.
     */
    public List<Sofa> findByManufacturerAndHasRecliners(String manufacturer, boolean hasRecliners) {
        return sofaRepository.findByManufacturerContainingIgnoreCaseAndHasRecliners(
                manufacturer, hasRecliners);
    }

    /**
     * Calculate discount for a sofa based on its properties.
     * Business logic example: Luxury sofas get smaller discounts, convertible sofas get larger discounts.
     */
    public double calculateDiscount(Sofa sofa) {
        double baseDiscount = 0.05; // 5% base discount

        // Luxury sofas (leather with recliners) get smaller discounts
        if (sofa.getUpholsteryType().toLowerCase().contains("leather") && sofa.isHasRecliners()) {
            baseDiscount = 0.03; // 3% discount for luxury sofas
        }

        // Convertible sofas get additional discount
        if (sofa.isConvertible()) {
            baseDiscount += 0.02; // Additional 2% for convertible sofas
        }

        // Large seating capacity sofas get additional discount
        if (sofa.getSeatingCapacity() >= 4) {
            baseDiscount += 0.01; // Additional 1% for large sofas
        }

        return baseDiscount;
    }

    /**
     * Recommend cleaning products based on upholstery type.
     * Business logic example: Different upholstery types need different cleaning products.
     */
    public String recommendCleaningProducts(Sofa sofa) {
        String upholsteryType = sofa.getUpholsteryType().toLowerCase();

        if (upholsteryType.contains("leather")) {
            return "Leather cleaner and conditioner. Avoid water-based cleaners.";
        } else if (upholsteryType.contains("fabric") || upholsteryType.contains("cotton")) {
            return "Fabric upholstery cleaner. Test on a hidden area first.";
        } else if (upholsteryType.contains("microfiber")) {
            return "Alcohol-based cleaner for microfiber. Avoid water.";
        } else if (upholsteryType.contains("velvet")) {
            return "Specialized velvet cleaner. Use a soft brush for dust.";
        } else {
            return "General upholstery cleaner. Check manufacturer's recommendations.";
        }
    }

    /**
     * Estimate delivery difficulty based on sofa properties.
     * Business logic example: Larger sofas with recliners are more difficult to deliver.
     */
    public String estimateDeliveryDifficulty(Sofa sofa) {
        int difficultyScore = 0;

        // Factors that increase delivery difficulty
        if (sofa.getSeatingCapacity() >= 4) {
            difficultyScore += 2; // Large sofas are harder to deliver
        }

        if (sofa.isHasRecliners()) {
            difficultyScore += 1; // Recliners add complexity
        }

        if (sofa.isConvertible()) {
            difficultyScore += 1; // Convertible mechanism adds weight and complexity
        }

        // Determine difficulty level
        if (difficultyScore >= 4) {
            return "High - Requires specialized delivery team and equipment";
        } else if (difficultyScore >= 2) {
            return "Medium - Requires at least 2-3 delivery personnel";
        } else {
            return "Low - Standard delivery procedures apply";
        }
    }
}
