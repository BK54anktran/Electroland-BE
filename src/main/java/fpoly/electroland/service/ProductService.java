package fpoly.electroland.service;

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

    public List<Product> getProduct(Sort sort) {
        return productRepository.findAll(sort);
    }

    public List<Product> getProductByCategory(int idCategory) {
        return productRepository.findByCategoryId(idCategory);
    }

    public List<Product> getProductByCategory(int idCategory, Sort sort) {
        return productRepository.findByCategoryId(idCategory, sort);
    }

    public List<Product> getProductByPrice(int minPrice, int maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> getProductByPrice(int minPrice, int maxPrice, Sort sort) {
        return productRepository.findByPriceBetween(minPrice, maxPrice, sort);
    }

    public Object getProductByKey(String key) {
        List<Product> products = productRepository.findByNameContaining(key);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll();
    }

    public Object getProductByKey(String key, Sort sort) {
        List<Product> products = productRepository.findByNameContaining(key, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByKey(String key, int category, Sort sort) {
        List<Product> products = productRepository.findByNameContainingAndCategoryId(key, category, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(String key, int minPrice, int maxPrice, Sort sort) {
        List<Product> products = productRepository.findByNameContainingAndPriceBetween(key, minPrice, maxPrice, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(String key, int category, int minPrice, int maxPrice, Sort sort) {
        List<Product> products = productRepository.findByNameContainingAndCategoryIdAndPriceBetween(key, category,
                minPrice, maxPrice, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(int category, int minPrice, int maxPrice, Sort sort) {
        List<Product> products = productRepository.findByCategoryIdAndPriceBetween(category,
                minPrice, maxPrice, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(String key, int category, int minPrice, int maxPrice, List<Integer> supplier,
            Sort sort) {
        List<Product> products = productRepository.findByNameContainingAndCategoryIdAndPriceBetweenAndSupplierIdIn(key,
                category, minPrice, maxPrice, supplier, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(String key, int category, List<Integer> supplier, Sort sort) {
        List<Product> products = productRepository.findByNameContainingAndCategoryIdAndSupplierIdIn(key, category,
                supplier, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(String key, int minPrice, int maxPrice, List<Integer> supplier, Sort sort) {
        List<Product> products = productRepository.findByNameContainingAndPriceBetweenAndSupplierIdIn(key, minPrice,
                maxPrice, supplier, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(String key, List<Integer> supplier, Sort sort) {
        List<Product> products = productRepository.findByNameContainingAndSupplierIdIn(key, supplier, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(int category, int minPrice, int maxPrice, List<Integer> supplier, Sort sort) {
        List<Supplier> suppliers = supplierRepository.findAllById(supplier);
        List<Product> products = productRepository.findByCategoryIdAndPriceBetweenAndSupplierIn(category, minPrice,
                maxPrice, suppliers, sort);
        System.out.println(products.size());
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(int category, List<Integer> supplier, Sort sort) {
        List<Product> products = productRepository.findByCategoryIdAndSupplierIdIn(category, supplier, sort);
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(int minPrice, int maxPrice, List<Integer> supplier, Sort sort) {
        List<Supplier> suppliers = supplierRepository.findAllById(supplier);
        List<Product> products = productRepository.findByPriceBetweenAndSupplierIn(minPrice,
                maxPrice, suppliers, sort);
        System.out.println(products.size());
        if (products.size() > 0) {
            return products;
        }
        return productRepository.findAll(sort);
    }

    public Object getProductByFillter(List<Integer> supplier, Sort sort) {
        List<Product> products = productRepository.findBySupplierIdIn(supplier, sort);
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
