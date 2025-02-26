package fpoly.electroland.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Category;
// import fpoly.electroland.model.Color;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductAttribute;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.AttributeRepository;
import fpoly.electroland.repository.CategoryRepository;
// import fpoly.electroland.repository.ColorRepository;
import fpoly.electroland.repository.ProductAttributeRepository;
// import fpoly.electroland.repository.ProductColorRepository;
import fpoly.electroland.repository.ProductImgRepository;
import fpoly.electroland.repository.ProductRepository;
import fpoly.electroland.repository.SupplierRepository;
import jakarta.persistence.criteria.JoinType;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SupplierRepository supplierRepository;

    // @Autowired
    // ColorRepository colorRepository;

    // @Autowired
    // ProductColorRepository productColorRepository;

    @Autowired
    ProductImgRepository productImgRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public Object getProductByFilter(String key, int category, int minPrice, int maxPrice,
            List<Integer> supplier,
            Sort sort) {
        List<Product> products = new ArrayList<>();
        if (key != null && key.length() > 0) {
            if (minPrice == 0 && maxPrice == 0) {
                if (category == 0) {
                    products = productRepository.findByNameContaining(key);
                }
                products = productRepository.findByCategoryId(category);
            }
            if (category != 0) {
                if (supplier != null && supplier.size() > 0) {
                    products = productRepository.findByNameContainingAndCategoryIdAndPriceBetweenAndSupplierIdIn(key,
                            category,
                            minPrice, maxPrice, supplier, sort);
                } else {
                    products = productRepository.findByNameContainingAndCategoryIdAndPriceBetween(key, category,
                            minPrice,
                            maxPrice, sort);
                }
            } else {
                if (supplier != null && supplier.size() > 0) {
                    products = productRepository.findByNameContainingAndPriceBetweenAndSupplierIdIn(key, minPrice,
                            maxPrice,
                            supplier, sort);
                } else {
                    products = productRepository.findByNameContainingAndPriceBetween(key, minPrice, maxPrice, sort);
                }
            }
        } else {
            if (minPrice == 0 && maxPrice == 0) {
                if (category == 0) {
                    products = productRepository.findAll(sort);
                } else
                    products = productRepository.findByCategoryId(category);
            } else if (category != 0) {
                if (supplier != null && supplier.size() > 0) {
                    products = productRepository.findByCategoryIdAndPriceBetweenAndSupplierIdIn(category, minPrice,
                            maxPrice,
                            supplier, sort);
                } else {
                    products = productRepository.findByCategoryIdAndPriceBetween(category, minPrice, maxPrice, sort);
                }
            } else {
                if (supplier != null && supplier.size() > 0) {
                    products = productRepository.findByPriceBetweenAndSupplierIdIn(minPrice, maxPrice, supplier, sort);
                } else {
                    products = productRepository.findByPriceBetween(minPrice, maxPrice, sort);
                }
            }
        }
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Product getProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    public List<Product> getProductSupplier(int id) {
        List<Product> products = productRepository.findBySupplier(supplierRepository.findById(id).get());
        if (products.size() > 0) {
            return products;
        }
        return null;
    }

    public Product saveProduct(Product product) {
        // Kiểm tra và gán Category nếu tồn tại
        assignCategory(product);
    
        // Kiểm tra và gán Supplier nếu tồn tại
        assignSupplier(product);
    
        // Lưu sản phẩm chính
        Product savedProduct = productRepository.save(product);
    
        // Lưu các ProductColor nếu tồn tại
        // saveProductColors(product, savedProduct);
    
        // Lưu các ProductImg nếu tồn tại
        saveProductImages(product, savedProduct);
    
        // Lưu các ProductAttribute và Attributes con nếu tồn tại
        saveProductAttributes(product, savedProduct);
    
        return savedProduct;
    }
    
    private void assignCategory(Product product) {
        if (product.getCategory() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + product.getCategory().getId()));
            product.setCategory(category);
        }
    }
    
    private void assignSupplier(Product product) {
        if (product.getSupplier() != null) {
            Supplier supplier = supplierRepository.findById(product.getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + product.getSupplier().getId()));
            product.setSupplier(supplier);
        }
    }
    
    // private void saveProductColors(Product product, Product savedProduct) {
    //     if (product.getProductColors() != null && !product.getProductColors().isEmpty()) {
    //         product.getProductColors().forEach(productColor -> {
    //             if (productColor.getColor() != null) {
    //                 Color color = colorRepository.findById(productColor.getColor().getId())
    //                         .orElseThrow(() -> new RuntimeException("Color not found with id: " + productColor.getColor().getId()));
    //                 productColor.setColor(color);
    //                 productColor.setProduct(savedProduct);
    //                 productColorRepository.save(productColor);
    //             }
    //         });
    //     }
    // }
    
    private void saveProductImages(Product product, Product savedProduct) {
        if (product.getProductImgs() != null && !product.getProductImgs().isEmpty()) {
            product.getProductImgs().forEach(productImg -> {
                if (productImg.getLink() != null) {
                    productImg.setProduct(savedProduct);
                    productImgRepository.save(productImg);
                }
            });
        }
    }
    
    private void saveProductAttributes(Product product, Product savedProduct) {
        if (product.getProductAttributes() != null && !product.getProductAttributes().isEmpty()) {
            product.getProductAttributes().forEach(productAttribute -> {
                if (productAttribute.getName() != null) {
                    // Kiểm tra xem ProductAttribute đã tồn tại chưa
                    Optional<ProductAttribute> existingAttribute = productAttributeRepository.findByNameAndProductId(productAttribute.getName(), savedProduct.getId());
    
                    ProductAttribute savedProductAttribute;
                    if (existingAttribute.isPresent()) {
                        // Nếu tồn tại, lấy ra đối tượng đã có
                        savedProductAttribute = existingAttribute.get();
                    } else {
                        // Nếu không tồn tại, tạo mới
                        productAttribute.setProduct(savedProduct);
                        savedProductAttribute = productAttributeRepository.save(productAttribute);
                    }
    
                    // Xử lý các attributes con
                    if (productAttribute.getAttributes() != null && !productAttribute.getAttributes().isEmpty()) {
                        productAttribute.getAttributes().forEach(attribute -> {
                            if (attribute.getName() != null) {
                                // Kiểm tra xem attribute con đã tồn tại trong savedProductAttribute chưa
                                boolean exists = savedProductAttribute.getAttributes().stream()
                                        .anyMatch(existingAttr -> existingAttr.getName().equals(attribute.getName()));
    
                                if (!exists) {
                                    // Nếu chưa tồn tại, thêm mới
                                    attribute.setProductAttribute(savedProductAttribute);
                                    attributeRepository.save(attribute);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    
    
    public Product updateProduct(Integer id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setAvatar(product.getAvatar());
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setPriceDiscount(product.getPriceDiscount());
            existingProduct.setStatus(product.getStatus());
            existingProduct.setDescription(product.getDescription());

            if (product.getCategory() != null) {
                Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
                existingProduct.setCategory(category);
            }

            if (product.getSupplier() != null) {
                Supplier supplier = supplierRepository.findById(product.getSupplier().getId()).orElse(null);
                existingProduct.setSupplier(supplier);
            }

            return productRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // public List<Product> searchProducts(String keyword) {
    //     if (keyword == null || keyword.isEmpty()) {
    //         return new ArrayList<>();
    //     }
    //     return productRepository.findProduct(keyword);
    // }

    // public List<Product> sortProducts(String criteria, String order) {
    //     if ("price".equalsIgnoreCase(criteria)) {
    //         if ("asc".equalsIgnoreCase(order)) {
    //             return productRepository.sortByPriceAsc();
    //         } else if ("desc".equalsIgnoreCase(order)) {
    //             return productRepository.sortByPriceDesc();
    //         } else {
    //             throw new IllegalArgumentException("Invalid sorting order: " + order);
    //         }
    //     } else if ("name".equalsIgnoreCase(criteria)) {
    //         if ("asc".equalsIgnoreCase(order)) {
    //             return productRepository.sortByNameAsc();
    //         } else if ("desc".equalsIgnoreCase(order)) {
    //             return productRepository.sortByNameDesc();
    //         } else {
    //             throw new IllegalArgumentException("Invalid sorting order: " + order);
    //         }
    //     } else {
    //         throw new IllegalArgumentException("Invalid sorting criteria: " + criteria);
    //     }
    // }    
}
