package com.toto.backend.repositories;

import com.toto.backend.entities.Order;
import com.toto.backend.entities.enums.OrderStatus;
import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.entities.enums.PaymentPlan;
import com.toto.backend.entities.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing Order entities.
 * Provides methods for order-specific operations in a Pakistani furniture store.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Find orders by customer ID.
     */
    List<Order> findByCustomer_Id(Long customerId);
    
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
    List<Order> findByDeliveryCityIgnoreCase(String city);
    
    /**
     * Find orders by delivery area.
     */
    List<Order> findByDeliveryAreaContainingIgnoreCase(String area);
    
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
    List<Order> findBySalesPersonContainingIgnoreCase(String salesPerson);
    
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
    @Query("SELECT o FROM Order o WHERE o.status = com.toto.backend.entities.enums.OrderStatus.PENDING")
    List<Order> findPendingOrders();
    
    /**
     * Find orders with overdue delivery (expected delivery date before current date and status not DELIVERED).
     */
    @Query("SELECT o FROM Order o WHERE o.expectedDeliveryDate < CURRENT_DATE AND o.status <> com.toto.backend.entities.enums.OrderStatus.DELIVERED")
    List<Order> findOverdueDeliveries();
    
    /**
     * Find orders with pending payments (payment status PENDING or PARTIAL).
     */
    @Query("SELECT o FROM Order o WHERE o.paymentStatus = com.toto.backend.entities.enums.PaymentStatus.PENDING OR o.paymentStatus = com.toto.backend.entities.enums.PaymentStatus.PARTIAL")
    List<Order> findOrdersWithPendingPayments();
    
    /**
     * Find orders by customer and status.
     */
    List<Order> findByCustomer_IdAndStatus(Long customerId, OrderStatus status);
    
    /**
     * Find orders containing a specific furniture item.
     */
    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.id = :furnitureId")
    List<Order> findOrdersContainingFurniture(@Param("furnitureId") Long furnitureId);
    
    /**
     * Find orders by city and payment method.
     */
    List<Order> findByDeliveryCityIgnoreCaseAndPaymentMethod(String city, PaymentMethod paymentMethod);
}