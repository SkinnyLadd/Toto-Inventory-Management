package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Customer;
import com.toto.backend.entities.enums.CustomerStatus;
import com.toto.backend.entities.enums.CustomerType;
import com.toto.backend.entities.enums.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface for Customer service operations.
 * Defines business logic and repository operations for customers.
 */
public interface ICustomerService {
    
    /**
     * Find all customers.
     */
    List<Customer> findAll();
    
    /**
     * Find customer by ID.
     */
    Optional<Customer> findById(Long id);
    
    /**
     * Save a customer.
     */
    Customer save(Customer customer);
    
    /**
     * Delete a customer by ID.
     */
    void deleteById(Long id);
    
    /**
     * Find customers by first name.
     */
    List<Customer> findByFirstName(String firstName);
    
    /**
     * Find customers by last name.
     */
    List<Customer> findByLastName(String lastName);
    
    /**
     * Find customers by full name.
     */
    List<Customer> findByFullName(String name);
    
    /**
     * Find customer by primary phone number.
     */
    Customer findByPrimaryPhone(String primaryPhone);
    
    /**
     * Find customer by CNIC (Pakistani National ID).
     */
    Customer findByCnic(String cnic);
    
    /**
     * Find customers by city.
     */
    List<Customer> findByCity(String city);
    
    /**
     * Find customers by area.
     */
    List<Customer> findByArea(String area);
    
    /**
     * Find customers by status.
     */
    List<Customer> findByStatus(CustomerStatus status);
    
    /**
     * Find customers by customer type.
     */
    List<Customer> findByCustomerType(CustomerType customerType);
    
    /**
     * Find VIP customers.
     */
    List<Customer> findVipCustomers();
    
    /**
     * Find customers by preferred payment method.
     */
    List<Customer> findByPreferredPaymentMethod(PaymentMethod preferredPaymentMethod);
    
    /**
     * Find customers by marketing consent.
     */
    List<Customer> findByMarketingConsent(boolean marketingConsent);
    
    /**
     * Find customers by referral source.
     */
    List<Customer> findByReferralSource(String referralSource);
    
    /**
     * Find customers registered after a certain date.
     */
    List<Customer> findByRegistrationDateAfter(LocalDateTime date);
    
    /**
     * Find customers registered between two dates.
     */
    List<Customer> findByRegistrationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find customers with no orders.
     */
    List<Customer> findCustomersWithNoOrders();
    
    /**
     * Find customers with at least a certain number of orders.
     */
    List<Customer> findCustomersWithMinimumOrders(int minOrders);
    
    /**
     * Find customers by city and customer type.
     */
    List<Customer> findByCityAndCustomerType(String city, CustomerType customerType);
    
    /**
     * Upgrade customer to VIP status based on order history.
     * Business logic: Customers with 5 or more orders are eligible for VIP status.
     */
    boolean upgradeToVipIfEligible(Long customerId);
    
    /**
     * Calculate customer loyalty score based on order history and registration duration.
     * Business logic: Score is based on number of orders and years as a customer.
     */
    int calculateLoyaltyScore(Customer customer);
    
    /**
     * Mark inactive customers based on last order date.
     * Business logic: Customers with no orders in the last year are marked as inactive.
     */
    int markInactiveCustomers();
}