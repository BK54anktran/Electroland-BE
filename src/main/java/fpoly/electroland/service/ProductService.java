package fpoly.electroland.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Category;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.CategoryRepository;
import fpoly.electroland.repository.ProductRepository;
import fpoly.electroland.repository.SupplierRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SupplierRepository supplierRepository;

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
                } else
                    products = productRepository.findByCategoryId(category);
            } else if (category != 0) {
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
        return productRepository.save(product);
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

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

}
