package dev.nbcsparta.assignment.commerce_backoffice.Product.Repository;

import dev.nbcsparta.assignment.commerce_backoffice.Product.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
