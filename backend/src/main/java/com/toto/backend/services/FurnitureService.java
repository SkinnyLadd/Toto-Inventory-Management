package com.toto.backend.services;

import com.toto.backend.entities.Furniture;
import com.toto.backend.entities.enums.WoodType;
import com.toto.backend.repositories.FurnitureRepository;
import com.toto.backend.services.interfaces.IFurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing Furniture entities.
 * Provides business logic and encapsulates repository operations for furniture.
 */
@Service
@Transactional
public class FurnitureService implements IFurnitureService {

    private final FurnitureRepository furnitureRepository;

    @Autowired
    public FurnitureService(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    /**
     * Create a new furniture item.
     */
    public void createFurniture(Furniture furniture) {
        furnitureRepository.save(furniture);
    }

    /**
     * Find all furniture items.
     */
    public List<Furniture> findAll() {
        return furnitureRepository.findAll();
    }

    /**
     * Find furniture by ID.
     */
    public Optional<Furniture> findById(Long id) {
        return furnitureRepository.findById(id);
    }

    /**
     * Save a furniture item.
     */
    public Furniture save(Furniture furniture) {
        return furnitureRepository.save(furniture);
    }

    /**
     * Delete a furniture item by ID.
     */
    public void deleteById(Long id) {
        furnitureRepository.deleteById(id);
    }

    /**
     * Find furniture by name containing the given text (case-insensitive).
     */
    public List<Furniture> findByNameContaining(String name) {
        return furnitureRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Find furniture by price less than or equal to the given amount.
     */
    public List<Furniture> findByPriceLessThanEqual(double price) {
        return furnitureRepository.findByPriceLessThanEqual(price);
    }

    /**
     * Find furniture by manufacturer.
     */
    public List<Furniture> findByManufacturer(String manufacturer) {
        return furnitureRepository.findByManufacturerContainingIgnoreCase(manufacturer);
    }

    /**
     * Find furniture by wood type.
     */
    public List<Furniture> findByWoodType(WoodType woodType) {
        return furnitureRepository.findByWoodType(woodType);
    }

    /**
     * Find furniture by supplier ID.
     */
    public List<Furniture> findBySupplier(Long supplierId) {
        return furnitureRepository.findBySupplier_Id(supplierId);
    }

    /**
     * Find furniture by material.
     */
    public List<Furniture> findByMaterial(String material) {
        return furnitureRepository.findByMaterialContainingIgnoreCase(material);
    }

    /**
     * Find furniture by price range.
     */
    public List<Furniture> findByPriceRange(double minPrice, double maxPrice) {
        return furnitureRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Find top selling furniture items.
     */
    public List<Furniture> findTopSellingFurniture(int limit) {
        return furnitureRepository.findTopSellingFurniture(limit);
    }

    /**
     * Find furniture by supplier city.
     */
    public List<Furniture> findBySupplierCity(String city) {
        return furnitureRepository.findBySupplierCity(city);
    }

    /**
     * Find furniture by supplier type.
     */
    public List<Furniture> findBySupplierType(String supplierType) {
        return furnitureRepository.findBySupplierType(supplierType);
    }


}
