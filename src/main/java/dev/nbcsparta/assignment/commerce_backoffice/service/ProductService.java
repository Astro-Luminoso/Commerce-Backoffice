package dev.nbcsparta.assignment.commerce_backoffice.service;

import dev.nbcsparta.assignment.commerce_backoffice.dto.*;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ProductCategoryCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.ReviewRatingCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ReviewStatistics;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.ProductStatistics;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Product;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Review;
import dev.nbcsparta.assignment.commerce_backoffice.exception.ProductNotFoundException;
import dev.nbcsparta.assignment.commerce_backoffice.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ManagerService managerService;
    private final ReviewService reviewService;

    public ProductService(ProductRepository productRepository, ManagerService managerService, ReviewService reviewService) {
        this.productRepository = productRepository;
        this.managerService = managerService;
        this.reviewService = reviewService;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public void addQuantity(Long productId, Integer quantity) {
        Product product = getProductById(productId);

        product.addQuantity(quantity);
    }

    @Transactional
    public CreateProductResponse create(CreateProductRequest request, Long managerId) {
        Manager manager = managerService.getManagerById(managerId);

        Product product = productRepository.save(
                new Product(
                        request.name(),
                        request.category(),
                        request.price(),
                        request.quantity(),
                        request.status(),
                        manager
                )
        );

        return CreateProductResponse.from(product);
    }



    @Transactional(readOnly = true)
    public GetListProductResponse getAllProduct(Pageable pageable, ProductFilter productFilter) {
        Page<Product> productPage = productRepository.findAll(productFilter, pageable);

        return GetListProductResponse.from(productPage);
    }

    /**
     * 상품 상세 조회 시 상품 정보와 리뷰 관련 데이터를 함께 반환한다.
     *
     * @param id            상품 아이디
     *
     * ReviewStatistics:     상품 평점 평균 및 리뷰 총 개수
     * ReviewRatingCount:   상품의 평점별 리뷰 개수 리스트
     * recentReviews:       최신 리뷰 3개 리스트
     *
     * @return 상품 정보, 리뷰 통계, 평점 분포, 최신 리뷰 3개를 포함한 응답
     */
    @Transactional(readOnly = true)
    public GetProductReviewResponse getOne(Long id) {
        Product product = getProductById(id);
        ReviewStatistics reviewStatistics = reviewService.getProductStatistics(id);
        List<ReviewRatingCount> ratingCounts = reviewService.getRatingCountByProductId(id);
        List<Review> recentReviews = reviewService.getRecent3Review(id);

        return GetProductReviewResponse.from(
                product,
                reviewStatistics,
                ratingCounts,
                recentReviews
        );
    }

    /**
     *
     * @param id        상품 아이디
     * @param request   name 상품이름,
     *                  Category 상품 카테고리,
     *                  Price 가격
     *
     * @return UpdateProductResponse
     */
    @Transactional
    public UpdateProductResponse update(Long id, UpdateProductRequest request) {
        Product product = getProductById(id);
        product.update(request.name(), request.category(), request.price());

        return UpdateProductResponse.from(product);
    }

    /**
     *
     * @param id        상품 아이디
     * @param request   ProductStatus 상품 상태
     */
    @Transactional
    public void updateStatus(Long id, UpdateProductStatusRequest request) {
        Product product = getProductById(id);

        product.setStatus(request.status());
    }


    @Transactional
    public void delete(Long id) {
        boolean existence = productRepository.existsById(id);
        if(!existence) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    public ProductStatistics getStatistics() {
        return productRepository.getStatistics();
    }

    public List<ProductCategoryCount> getCategoryCount() {
        return productRepository.getCategoryCount();
    }
}
