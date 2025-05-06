package com.toto.backend.services.interfaces;

import com.toto.backend.entities.MiscFurniture;

import java.util.List;
import java.util.Optional;

/**
 * Interface for MiscFurniture service operations.
 * Defines business logic and repository operations for miscellaneous furniture.
 */
public interface IMiscFurnitureService {
    
    /**
     * Find all miscellaneous furniture items.
     */
    List<MiscFurniture> findAll();
    
    /**
     * Find miscellaneous furniture by ID.
     */
    Optional<MiscFurniture> findById(Long id);
    
    /**
     * Save a miscellaneous furniture item.
     */
    MiscFurniture save(MiscFurniture miscFurniture);
    
    /**
     * Delete a miscellaneous furniture item by ID.
     */
    void deleteById(Long id);
    
    /**
     * Find miscellaneous furniture by category.
     */
    List<MiscFurniture> findByCategory(String category);
    
    /**
     * Find miscellaneous furniture by description containing the given text.
     */
    List<MiscFurniture> findByDescription(String description);
    
    /**
     * Find miscellaneous furniture by category and description.
     */
    List<MiscFurniture> findByCategoryAndDescription(String category, String description);
    
    /**
     * Find miscellaneous furniture by price less than or equal to the given amount.
     */
    List<MiscFurniture> findByMaxPrice(double price);
    
    /**
     * Find miscellaneous furniture by price range.
     */
    List<MiscFurniture> findByPriceRange(double minPrice, double maxPrice);
    
    /**
     * Find miscellaneous furniture by manufacturer.
     */
    List<MiscFurniture> findByManufacturer(String manufacturer);
    
    /**
     * Find miscellaneous furniture by custom attribute name.
     */
    List<MiscFurniture> findByCustomAttributeName(String attributeName);
    
    /**
     * Find miscellaneous furniture by custom attribute value.
     */
    List<MiscFurniture> findByCustomAttributeValue(String attributeName, String attributeValue);
    
    /**
     * Find miscellaneous furniture by price modifier name.
     */
    List<MiscFurniture> findByPriceModifierName(String modifierName);
    
    /**
     * Find miscellaneous furniture by price modifier value greater than a certain amount.
     */
    List<MiscFurniture> findByPriceModifierValueGreaterThan(String modifierName, double minValue);
    
    /**
     * Add a custom attribute to a miscellaneous furniture item.
     * Business logic: Adds or updates a custom attribute in the customAttributes map.
     */
    MiscFurniture addCustomAttribute(Long furnitureId, String attributeName, String attributeValue);
    
    /**
     * Remove a custom attribute from a miscellaneous furniture item.
     * Business logic: Removes a custom attribute from the customAttributes map.
     */
    MiscFurniture removeCustomAttribute(Long furnitureId, String attributeName);
    
    /**
     * Add a price modifier to a miscellaneous furniture item.
     * Business logic: Adds or updates a price modifier in the priceModifiers map.
     */
    MiscFurniture addPriceModifier(Long furnitureId, String modifierName, Double modifierValue);
    
    /**
     * Remove a price modifier from a miscellaneous furniture item.
     * Business logic: Removes a price modifier from the priceModifiers map.
     */
    MiscFurniture removePriceModifier(Long furnitureId, String modifierName);
    
    /**
     * Calculate final price with all modifiers applied.
     * Business logic: Applies all price modifiers to the base price.
     */
    double calculateFinalPrice(MiscFurniture furniture);
    
    /**
     * Categorize miscellaneous furniture based on its attributes.
     * Business logic: Suggests a category based on custom attributes and description.
     */
    String suggestCategory(MiscFurniture furniture);
}