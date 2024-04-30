package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductImage;
import com.example.ecommerce.model.exception.ProductException;
import com.example.ecommerce.model.request.CreateProductRequest;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Transactional
    @Override
    public Product createProduct(
            CreateProductRequest productRequest,
            MultipartFile file1,
            MultipartFile file2,
            MultipartFile file3,
            MultipartFile file4,
            MultipartFile file5
    ) throws IOException {
        ProductImage image1;
        ProductImage image2;
        ProductImage image3;
        ProductImage image4;
        ProductImage image5;
        Category topLevel = categoryRepository.findByName(productRequest.getTopLevelCategory());

        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(productRequest.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(
                productRequest.getSecondLevelCategory(),
                topLevel.getName());

        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(productRequest.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }
        Category thirdLevel = categoryRepository
                .findByNameAndParent(productRequest.getThirdLevelCategory(), secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(productRequest.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setColor(productRequest.getColor());
        product.setDescription(productRequest.getDescription());
        product.setDiscountPrice(productRequest.getDiscountedPrice());
        product.setDiscountPercent(productRequest.getDiscountedPercent());
        product.setBrand(productRequest.getBrand());
        product.setHighlights(productRequest.getHighlights());
        product.setPrice(productRequest.getPrice());
        product.setDetails(productRequest.getDetails());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        if (file4.getSize() != 0) {
            image4 = toImageEntity(file4);
            product.addImageToProduct(image4);
        }
        if (file5.getSize() != 0) {
            image5 = toImageEntity(file5);
            product.addImageToProduct(image5);
        }

        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        Product savedProduct = productRepository.save(productFromDb);

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
    public List<Product> findProductByCategory(String categoryName) {
        return productRepository.findProductsByCategoryName(categoryName);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors,
                                       Integer minPrice,
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


    private ProductImage toImageEntity(MultipartFile file) throws IOException {
        ProductImage image = new ProductImage();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

}
