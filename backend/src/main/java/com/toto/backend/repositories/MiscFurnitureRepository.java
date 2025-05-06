package com.toto.backend.repositories;

import com.toto.backend.entities.MiscFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing MiscFurniture entities.
 * Provides methods for miscellaneous furniture operations in a Pakistani furniture store.
 */
@Repository
public interface MiscFurnitureRepository extends JpaRepository<MiscFurniture, Long> {
    
    /**
     * Find miscellaneous furniture by category.
     */
    List<MiscFurniture> findByCategoryContainingIgnoreCase(String category);
    
    /**
     * Find miscellaneous furniture by description containing the given text.
     */
    List<MiscFurniture> findByDescriptionContainingIgnoreCase(String description);
    
    /**
     * Find miscellaneous furniture by category and description.
     */
    List<MiscFurniture> findByCategoryAndDescriptionContainingIgnoreCase(
            String category, String description);
    
    /**
     * Find miscellaneous furniture by price less than or equal to the given amount.
     */
    List<MiscFurniture> findByPriceLessThanEqual(double price);
    
    /**
     * Find miscellaneous furniture by price range.
     */
    List<MiscFurniture> findByPriceBetween(double minPrice, double maxPrice);
    
    /**
     * Find miscellaneous furniture by manufacturer.
     */
    List<MiscFurniture> findByManufacturerContainingIgnoreCase(String manufacturer);
    
    /**
     * Find miscellaneous furniture by custom attribute.
     * This is a custom query that checks if a specific attribute exists in the customAttributes map.
     */
    @Query("SELECT m FROM MiscFurniture m JOIN m.customAttributes attrs WHERE KEY(attrs) = :attributeName")
    List<MiscFurniture> findByCustomAttributeName(@Param("attributeName") String attributeName);
    
    /**
     * Find miscellaneous furniture by custom attribute value.
     * This is a custom query that checks if a specific attribute has a specific value in the customAttributes map.
     */
    @Query("SELECT m FROM MiscFurniture m JOIN m.customAttributes attrs WHERE KEY(attrs) = :attributeName AND VALUE(attrs) = :attributeValue")
    List<MiscFurniture> findByCustomAttributeValue(
            @Param("attributeName") String attributeName, 
            @Param("attributeValue") String attributeValue);
    
    /**
     * Find miscellaneous furniture by price modifier.
     * This is a custom query that checks if a specific price modifier exists in the priceModifiers map.
     */
    @Query("SELECT m FROM MiscFurniture m JOIN m.priceModifiers mods WHERE KEY(mods) = :modifierName")
    List<MiscFurniture> findByPriceModifierName(@Param("modifierName") String modifierName);
    
    /**
     * Find miscellaneous furniture by price modifier value greater than a certain amount.
     * This is a custom query that checks if a specific price modifier has a value greater than a certain amount.
     */
    @Query("SELECT m FROM MiscFurniture m JOIN m.priceModifiers mods WHERE KEY(mods) = :modifierName AND VALUE(mods) > :minValue")
    List<MiscFurniture> findByPriceModifierValueGreaterThan(
            @Param("modifierName") String modifierName, 
            @Param("minValue") double minValue);
}