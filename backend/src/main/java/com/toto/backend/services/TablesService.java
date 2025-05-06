package com.toto.backend.services;

import com.toto.backend.entities.Tables;
import com.toto.backend.repositories.TablesRepository;
import com.toto.backend.services.interfaces.ITablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing Tables entities.
 * Provides business logic and encapsulates repository operations for tables.
 * This service replaces both TableService and DiningTableService.
 */
@Service
@Transactional
public class TablesService implements ITablesService {

    private final TablesRepository tablesRepository;

    @Autowired
    public TablesService(TablesRepository tablesRepository) {
        this.tablesRepository = tablesRepository;
    }

    /**
     * Find all tables.
     */
    public List<Tables> findAll() {
        return tablesRepository.findAll();
    }

    /**
     * Find table by ID.
     */
    public Optional<Tables> findById(Long id) {
        return tablesRepository.findById(id);
    }

    /**
     * Save a table.
     */
    public Tables save(Tables table) {
        return tablesRepository.save(table);
    }

    /**
     * Delete a table by ID.
     */
    public void deleteById(Long id) {
        tablesRepository.deleteById(id);
    }

    /**
     * Find tables by shape (round, rectangular, square).
     */
    public List<Tables> findByShape(String shape) {
        return tablesRepository.findByShapeContainingIgnoreCase(shape);
    }

    /**
     * Find tables by seating capacity.
     */
    public List<Tables> findBySeatingCapacity(int seatingCapacity) {
        return tablesRepository.findBySeatingCapacity(seatingCapacity);
    }

    /**
     * Find tables by whether they are extendable.
     */
    public List<Tables> findByIsExtendable(boolean isExtendable) {
        return tablesRepository.findByIsExtendable(isExtendable);
    }

    /**
     * Find tables by dimensions (length, width, height).
     */
    public List<Tables> findByDimensions(double length, double width, double height) {
        return tablesRepository.findByLengthAndWidthAndHeight(length, width, height);
    }

    /**
     * Find tables by whether they have a glass top.
     */
    public List<Tables> findByHasGlassTop(boolean hasGlassTop) {
        return tablesRepository.findByHasGlassTop(hasGlassTop);
    }

    /**
     * Find tables by seating capacity greater than or equal to the given value.
     */
    public List<Tables> findByMinSeatingCapacity(int minSeatingCapacity) {
        return tablesRepository.findBySeatingCapacityGreaterThanEqual(minSeatingCapacity);
    }

    /**
     * Find tables by shape and seating capacity.
     */
    public List<Tables> findByShapeAndSeatingCapacity(String shape, int seatingCapacity) {
        return tablesRepository.findByShapeAndSeatingCapacity(shape, seatingCapacity);
    }

    /**
     * Find dining tables (tables with seating capacity >= 4).
     */
    public List<Tables> findDiningTables() {
        return tablesRepository.findDiningTables(4);
    }

    /**
     * Find coffee tables (small tables with height less than 50 cm).
     */
    public List<Tables> findCoffeeTables() {
        return tablesRepository.findByHeightLessThan(50.0);
    }

    /**
     * Find tables by price range and shape.
     */
    public List<Tables> findByPriceRangeAndShape(double minPrice, double maxPrice, String shape) {
        return tablesRepository.findByPriceBetweenAndShapeContainingIgnoreCase(minPrice, maxPrice, shape);
    }

    /**
     * Find tables by manufacturer and whether they have a glass top.
     */
    public List<Tables> findByManufacturerAndHasGlassTop(String manufacturer, boolean hasGlassTop) {
        return tablesRepository.findByManufacturerContainingIgnoreCaseAndHasGlassTop(manufacturer, hasGlassTop);
    }

    /**
     * Calculate discount for a table based on its properties.
     * Business logic example: Glass top tables get smaller discounts, extendable tables get larger discounts.
     */
    public double calculateDiscount(Tables table) {
        double baseDiscount = 0.05; // 5% base discount

        // Glass top tables get smaller discounts due to higher demand
        if (table.isHasGlassTop()) {
            baseDiscount = 0.03; // 3% discount for glass top tables
        }

        // Extendable tables get additional discount
        if (table.isExtendable()) {
            baseDiscount += 0.02; // Additional 2% for extendable tables
        }

        // Large seating capacity tables get additional discount
        if (table.getSeatingCapacity() >= 6) {
            baseDiscount += 0.01; // Additional 1% for large tables
        }

        return baseDiscount;
    }

    /**
     * Recommend table accessories based on table properties.
     * Business logic example: Different table types need different accessories.
     */
    public String recommendAccessories(Tables table) {
        StringBuilder recommendations = new StringBuilder();

        // Glass top tables
        if (table.isHasGlassTop()) {
            recommendations.append("Glass cleaner, Table mats to prevent scratches, Coasters. ");
        }

        // Dining tables
        if (table.getSeatingCapacity() >= 4) {
            recommendations.append("Table cloth, Placemats, Napkin holders. ");
        }

        // Coffee tables
        if (table.getHeight() < 50.0) {
            recommendations.append("Coffee table books, Decorative trays, Coasters. ");
        }

        // Wooden tables
        if (!table.isHasGlassTop()) {
            recommendations.append("Wood polish, Furniture wax, Table runners. ");
        }

        return recommendations.toString().trim();
    }

    /**
     * Estimate space requirements for a table.
     * Business logic example: Calculate the total space needed including clearance around the table.
     */
    public double calculateSpaceRequirement(Tables table) {
        // Base dimensions
        double length = table.getLength();
        double width = table.getWidth();

        // Add clearance for chairs and movement (typically 60-75 cm per side)
        double clearance = 70.0; // 70 cm clearance on each side

        // Calculate total space required
        double totalLength = length + (2 * clearance);
        double totalWidth = width + (2 * clearance);

        // Return area in square meters
        return (totalLength * totalWidth) / 10000.0; // Convert from cm² to m²
    }
}
