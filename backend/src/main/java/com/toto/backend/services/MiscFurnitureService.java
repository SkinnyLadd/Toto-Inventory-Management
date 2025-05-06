package com.toto.backend.services;

import com.toto.backend.entities.MiscFurniture;
import com.toto.backend.repositories.MiscFurnitureRepository;
import com.toto.backend.services.interfaces.IMiscFurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing MiscFurniture entities.
 * Provides business logic and encapsulates repository operations for miscellaneous furniture.
 */
@Service
@Transactional
public class MiscFurnitureService implements IMiscFurnitureService {

    private final MiscFurnitureRepository miscFurnitureRepository;

    @Autowired
    public MiscFurnitureService(MiscFurnitureRepository miscFurnitureRepository) {
        this.miscFurnitureRepository = miscFurnitureRepository;
    }

    /**
     * Find all miscellaneous furniture items.
     */
    public List<MiscFurniture> findAll() {
        return miscFurnitureRepository.findAll();
    }

    /**
     * Find miscellaneous furniture by ID.
     */
    public Optional<MiscFurniture> findById(Long id) {
        return miscFurnitureRepository.findById(id);
    }

    /**
     * Save a miscellaneous furniture item.
     */
    public MiscFurniture save(MiscFurniture miscFurniture) {
        return miscFurnitureRepository.save(miscFurniture);
    }

    /**
     * Delete a miscellaneous furniture item by ID.
     */
    public void deleteById(Long id) {
        miscFurnitureRepository.deleteById(id);
    }

    /**
     * Find miscellaneous furniture by category.
     */
    public List<MiscFurniture> findByCategory(String category) {
        return miscFurnitureRepository.findByCategoryContainingIgnoreCase(category);
    }

    /**
     * Find miscellaneous furniture by description containing the given text.
     */
    public List<MiscFurniture> findByDescription(String description) {
        return miscFurnitureRepository.findByDescriptionContainingIgnoreCase(description);
    }

    /**
     * Find miscellaneous furniture by category and description.
     */
    public List<MiscFurniture> findByCategoryAndDescription(String category, String description) {
        return miscFurnitureRepository.findByCategoryAndDescriptionContainingIgnoreCase(category, description);
    }

    /**
     * Find miscellaneous furniture by price less than or equal to the given amount.
     */
    public List<MiscFurniture> findByMaxPrice(double price) {
        return miscFurnitureRepository.findByPriceLessThanEqual(price);
    }

    /**
     * Find miscellaneous furniture by price range.
     */
    public List<MiscFurniture> findByPriceRange(double minPrice, double maxPrice) {
        return miscFurnitureRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Find miscellaneous furniture by manufacturer.
     */
    public List<MiscFurniture> findByManufacturer(String manufacturer) {
        return miscFurnitureRepository.findByManufacturerContainingIgnoreCase(manufacturer);
    }

    /**
     * Find miscellaneous furniture by custom attribute name.
     */
    public List<MiscFurniture> findByCustomAttributeName(String attributeName) {
        return miscFurnitureRepository.findByCustomAttributeName(attributeName);
    }

    /**
     * Find miscellaneous furniture by custom attribute value.
     */
    public List<MiscFurniture> findByCustomAttributeValue(String attributeName, String attributeValue) {
        return miscFurnitureRepository.findByCustomAttributeValue(attributeName, attributeValue);
    }

    /**
     * Find miscellaneous furniture by price modifier name.
     */
    public List<MiscFurniture> findByPriceModifierName(String modifierName) {
        return miscFurnitureRepository.findByPriceModifierName(modifierName);
    }

    /**
     * Find miscellaneous furniture by price modifier value greater than a certain amount.
     */
    public List<MiscFurniture> findByPriceModifierValueGreaterThan(String modifierName, double minValue) {
        return miscFurnitureRepository.findByPriceModifierValueGreaterThan(modifierName, minValue);
    }

    /**
     * Add a custom attribute to a miscellaneous furniture item.
     * Business logic: Adds or updates a custom attribute in the customAttributes map.
     */
    public MiscFurniture addCustomAttribute(Long furnitureId, String attributeName, String attributeValue) {
        Optional<MiscFurniture> furnitureOpt = miscFurnitureRepository.findById(furnitureId);
        if (furnitureOpt.isPresent()) {
            MiscFurniture furniture = furnitureOpt.get();
            Map<String, String> attributes = furniture.getCustomAttributes();
            attributes.put(attributeName, attributeValue);
            return miscFurnitureRepository.save(furniture);
        }
        return null;
    }

    /**
     * Remove a custom attribute from a miscellaneous furniture item.
     * Business logic: Removes a custom attribute from the customAttributes map.
     */
    public MiscFurniture removeCustomAttribute(Long furnitureId, String attributeName) {
        Optional<MiscFurniture> furnitureOpt = miscFurnitureRepository.findById(furnitureId);
        if (furnitureOpt.isPresent()) {
            MiscFurniture furniture = furnitureOpt.get();
            Map<String, String> attributes = furniture.getCustomAttributes();
            attributes.remove(attributeName);
            return miscFurnitureRepository.save(furniture);
        }
        return null;
    }

    /**
     * Add a price modifier to a miscellaneous furniture item.
     * Business logic: Adds or updates a price modifier in the priceModifiers map.
     */
    public MiscFurniture addPriceModifier(Long furnitureId, String modifierName, Double modifierValue) {
        Optional<MiscFurniture> furnitureOpt = miscFurnitureRepository.findById(furnitureId);
        if (furnitureOpt.isPresent()) {
            MiscFurniture furniture = furnitureOpt.get();
            Map<String, Double> modifiers = furniture.getPriceModifiers();
            modifiers.put(modifierName, modifierValue);
            return miscFurnitureRepository.save(furniture);
        }
        return null;
    }

    /**
     * Remove a price modifier from a miscellaneous furniture item.
     * Business logic: Removes a price modifier from the priceModifiers map.
     */
    public MiscFurniture removePriceModifier(Long furnitureId, String modifierName) {
        Optional<MiscFurniture> furnitureOpt = miscFurnitureRepository.findById(furnitureId);
        if (furnitureOpt.isPresent()) {
            MiscFurniture furniture = furnitureOpt.get();
            Map<String, Double> modifiers = furniture.getPriceModifiers();
            modifiers.remove(modifierName);
            return miscFurnitureRepository.save(furniture);
        }
        return null;
    }

    /**
     * Calculate final price with all modifiers applied.
     * Business logic: Applies all price modifiers to the base price.
     */
    public double calculateFinalPrice(MiscFurniture furniture) {
        double basePrice = furniture.getPrice();
        double finalPrice = basePrice;

        // Apply all price modifiers
        Map<String, Double> modifiers = furniture.getPriceModifiers();
        for (Double modifier : modifiers.values()) {
            finalPrice += modifier;
        }

        // Ensure price doesn't go below a minimum threshold (e.g., 10% of base price)
        double minimumPrice = basePrice * 0.1;
        return Math.max(finalPrice, minimumPrice);
    }

    /**
     * Categorize miscellaneous furniture based on its attributes.
     * Business logic: Suggests a category based on custom attributes and description.
     */
    public String suggestCategory(MiscFurniture furniture) {
        String description = furniture.getDescription().toLowerCase();
        Map<String, String> attributes = furniture.getCustomAttributes();

        // Check for storage-related keywords
        if (description.contains("shelf") || description.contains("cabinet") || 
                description.contains("storage") || description.contains("drawer")) {
            return "Storage";
        }

        // Check for decor-related keywords
        if (description.contains("decor") || description.contains("decorative") || 
                description.contains("ornament") || description.contains("vase")) {
            return "Decor";
        }

        // Check for lighting-related keywords
        if (description.contains("lamp") || description.contains("light") || 
                description.contains("chandelier") || description.contains("sconce")) {
            return "Lighting";
        }

        // Check custom attributes for material
        if (attributes.containsKey("material")) {
            String material = attributes.get("material").toLowerCase();
            if (material.contains("glass") || material.contains("crystal")) {
                return "Glass Items";
            }
            if (material.contains("metal") || material.contains("iron") || material.contains("steel")) {
                return "Metal Furniture";
            }
        }

        // Default category
        return "Miscellaneous";
    }
}
