package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
