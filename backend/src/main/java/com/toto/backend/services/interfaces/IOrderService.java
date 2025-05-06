package com.toto.backend.services.interfaces;

import com.toto.backend.entities.Order;
import com.toto.backend.entities.enums.OrderStatus;
import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.entities.enums.PaymentPlan;
import com.toto.backend.entities.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface for Order service operations.
 * Defines business logic and repository operations for orders.
 */
public interface IOrderService {
    
    /**
     * Find all orders.
     */
    List<Order> findAll();
    
    /**
     * Find order by ID.
     */
    Optional<Order> findById(Long id);
    
    /**
     * Save an order.
     */
    Order save(Order order);
    
    /**
     * Delete an order by ID.
     */
    void deleteById(Long id);
    
    /**
     * Find orders by customer ID.
     */
    List<Order> findByCustomerId(Long customerId);
    
    /**
     * Find orders by order status.
     */
    List<Order> findByStatus(OrderStatus status);
    
    /**
     * Find orders by payment method.
     */
    List<Order> findByPaymentMethod(PaymentMethod paymentMethod);
    
    /**
     * Find orders by payment plan.
     */
    List<Order> findByPaymentPlan(PaymentPlan paymentPlan);
    
    /**
     * Find orders by payment status.
     */
    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);
    
    /**
     * Find orders by order date after a certain date.
     */
    List<Order> findByOrderDateAfter(LocalDateTime date);
    
    /**
     * Find orders by order date between two dates.
     */
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find orders by expected delivery date after a certain date.
     */
    List<Order> findByExpectedDeliveryDateAfter(LocalDateTime date);
    
    /**
     * Find orders by actual delivery date between two dates.
     */
    List<Order> findByActualDeliveryDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find orders by delivery city.
     */
    List<Order> findByDeliveryCity(String city);
    
    /**
     * Find orders by delivery area.
     */
    List<Order> findByDeliveryArea(String area);
    
    /**
     * Find orders by whether they require assembly.
     */
    List<Order> findByRequiresAssembly(boolean requiresAssembly);
    
    /**
     * Find orders by whether they require installation.
     */
    List<Order> findByRequiresInstallation(boolean requiresInstallation);
    
    /**
     * Find orders by sales person.
     */
    List<Order> findBySalesPerson(String salesPerson);
    
    /**
     * Find orders by total amount greater than a certain amount.
     */
    List<Order> findByTotalAmountGreaterThan(Double amount);
    
    /**
     * Find orders by total amount between two amounts.
     */
    List<Order> findByTotalAmountBetween(Double minAmount, Double maxAmount);
    
    /**
     * Find pending orders (orders with status PENDING).
     */
    List<Order> findPendingOrders();
    
    /**
     * Find orders with overdue delivery (expected delivery date before current date and status not DELIVERED).
     */
    List<Order> findOverdueDeliveries();
    
    /**
     * Find orders with pending payments (payment status PENDING or PARTIAL).
     */
    List<Order> findOrdersWithPendingPayments();
    
    /**
     * Find orders by customer and status.
     */
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);
    
    /**
     * Find orders containing a specific furniture item.
     */
    List<Order> findOrdersContainingFurniture(Long furnitureId);
    
    /**
     * Find orders by city and payment method.
     */
    List<Order> findByCityAndPaymentMethod(String city, PaymentMethod paymentMethod);
    
    /**
     * Update order status.
     * Business logic: Updates the order status and performs any necessary side effects.
     */
    Order updateOrderStatus(Long orderId, OrderStatus newStatus);
    
    /**
     * Update payment status.
     * Business logic: Updates the payment status and calculates remaining payment if needed.
     */
    Order updatePaymentStatus(Long orderId, PaymentStatus newStatus, Double paymentAmount);
    
    /**
     * Calculate installment plan.
     * Business logic: Calculates monthly installment amount based on total amount and installment months.
     */
    Order calculateInstallmentPlan(Long orderId, Integer installmentMonths);
}