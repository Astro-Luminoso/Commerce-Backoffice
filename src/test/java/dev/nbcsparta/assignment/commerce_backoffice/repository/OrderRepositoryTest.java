package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired private OrderRepository orderRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ManagerRepository managerRepository;

    @Test
    @DisplayName("Manager 없이(null) 일반 고객의 주문이 성공적으로 저장된다")
    void saveOrder_WithoutManager() {
        // [Given] 주문에 필요한 연관 엔티티들을 먼저 DB에 저장합니다.
        Customer customer = customerRepository.save(new Customer(
                "양성훈", "user@naver.com", "010-1111-1111", AccountStatus.ACTIVE
        ));

        // 상품을 만들려면 관리자가 필요하므로 임의의 관리자 생성 후 상품 저장
        Manager productManager = managerRepository.save(new Manager(
                "상품관리자", "admin@gmail.com", "password", "010-2222-2222", Role.Super_MANAGER, AccountStatus.ACTIVE
        ));
        Product product = productRepository.save(new Product(
                "폰", "전자제품", 5000L, 50, ProductStatus.SALE, productManager
        ));

        // 💡 핵심: Order 객체를 만들 때 Manager 자리에 'null'을 넣습니다!
        Order order = new Order(
                5,
                25000L,
                DeliveryStatus.PENDING,
                customer,
                null, // <--- 매니저가 없는 상태!
                product
        );

        // [When] 영속성 컨텍스트(DB)에 저장합니다.
        Order savedOrder = orderRepository.save(order);

        // [Then] 저장이 잘 되었는지 검증합니다.
        assertNotNull(savedOrder.getId(), "주문 ID가 발급되어야 합니다.");
        assertNull(savedOrder.getManager(), "주문의 매니저 필드는 null이어야 합니다.");
        assertEquals("양성훈", savedOrder.getCustomer().getName(), "고객 이름이 일치해야 합니다.");
    }
}