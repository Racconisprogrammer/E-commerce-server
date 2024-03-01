package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.CreateProductRequest;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;


    @Override
    public Product createProduct(CreateProductRequest productRequest) {
        Category topLevel = categoryRepository.findByName(productRequest.getTopLevelCategory());

        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(productRequest.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(
                productRequest.getSecondLevelCategory(),
                topLevel.getName()
        );
        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(productRequest.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }
        Category thirdLevel = categoryRepository
                .findByNameAndParent(productRequest.getSecondLevelCategory(), secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(productRequest.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            secondLevel = categoryRepository.save(thirdLevelCategory);
        }
        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setColor(productRequest.getColor());
        product.setDescription(productRequest.getDescription());
        product.setDiscountPrice(productRequest.getDiscountedPrice());
        product.setDiscountPercent(productRequest.getDiscountedPercent());
        product.setImageUrl(productRequest.getImageUrl());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        product.setSizes(productRequest.getSize());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;

    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        productRepository.deleteById(productId);
        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);

        if (product.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new UsernameNotFoundException("Not found product by id " + productId)
        );

        return product;

    }

    @Override
    public List<Product> findProductByCategory(String category) throws ProductException {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors,
                                       List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount,
                                       String sort, String stock,
                                       Integer pageNumber, Integer pageSize
    ) throws ProductException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice,
                maxPrice, minDiscount, sort
        );
        if (!colors.isEmpty()) {
            products = products.stream()
                    .filter(p ->
                            colors.stream()
                                    .anyMatch(
                                            c -> c.equalsIgnoreCase(
                                                    p.getColor())))
                    .collect(Collectors.toList());
        }

        if (stock!=null) {
            if(stock.equals("in_stock")){
                products = products.stream()
                        .filter(p -> p.getQuantity() > 0)
                        .collect(Collectors.toList());
            }
            else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p ->
                        p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProduct = new PageImpl<>(pageContent, pageable, products.size());

        return filteredProduct;
    }
}
