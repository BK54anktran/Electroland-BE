package fpoly.electroland.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Attribute;
import fpoly.electroland.model.CartProductAttribute;
import fpoly.electroland.model.Category;
import fpoly.electroland.model.Employee;
import fpoly.electroland.model.Product;
import fpoly.electroland.model.ProductAttribute;
import fpoly.electroland.model.ProductImg;
import fpoly.electroland.model.Supplier;
import fpoly.electroland.repository.AttributeRepository;
import fpoly.electroland.repository.CartProductAttributeRepository;
import fpoly.electroland.repository.CategoryRepository;
import fpoly.electroland.repository.ProductAttributeRepository;
import fpoly.electroland.repository.ProductImgRepository;
import fpoly.electroland.repository.ProductRepository;
import fpoly.electroland.repository.SupplierRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProductImgRepository productImgRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    @Autowired
    CartProductAttributeRepository cartProductAttributeRepository;

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    @Transactional
    public void editProduct(Product product) {
        if (product.getId() == 0) {
            Product savedProduct = productRepository.save(product);
            // Trường hợp thêm mới Product
            if (product.getProductImgs() != null && !product.getProductImgs().isEmpty()) {
                for (ProductImg img : product.getProductImgs()) {
                    img.setProduct(savedProduct); // Đảm bảo tham chiếu tới savedProduct
                    productImgRepository.save(img);
                }
            }

            if (product.getProductAttributes() != null) {
                for (ProductAttribute attr : product.getProductAttributes()) {
                    attr.setProduct(product);
                    if (attr.getAttributes() != null) {
                        for (Attribute attribute : attr.getAttributes()) {
                            attribute.setProductAttribute(attr);
                            attributeRepository.save(attribute);
                        }
                    }
                }
            }

        } else {
            // Trường hợp chỉnh sửa Product đã tồn tại
            Product existingProduct = productRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Cập nhật thông tin cơ bản của Product
            existingProduct.setName(product.getName());
            existingProduct.setAvatar(product.getAvatar());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setPriceDiscount(product.getPriceDiscount());
            existingProduct.setStatus(product.getStatus());
            existingProduct.setWeight(product.getWeight());
            existingProduct.setLength(product.getLength());
            existingProduct.setWidth(product.getWidth());
            existingProduct.setHeight(product.getHeight());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setSupplier(product.getSupplier());

            // 1. Xử lý ProductImg
            List<ProductImg> existingImgs = productImgRepository.findByProductId(existingProduct.getId());
            List<ProductImg> newImgs = product.getProductImgs() != null ? product.getProductImgs() : new ArrayList<>();

            // Xóa ProductImg trong DB nếu không có trong dữ liệu mới
            for (ProductImg existingImg : existingImgs) {
                if (!newImgs.stream().anyMatch(newImg -> newImg.getId() == existingImg.getId())) {
                    productImgRepository.delete(existingImg);
                }
            }

            // Thêm ProductImg mới vào DB
            for (ProductImg newImg : newImgs) {
                if (newImg.getId() == 0) {
                    newImg.setProduct(existingProduct);
                    productImgRepository.save(newImg);
                }
            }

            // 2. Xử lý ProductAttribute
            List<ProductAttribute> existingAttrs = productAttributeRepository.findByProductId(existingProduct.getId());
            List<ProductAttribute> newAttrs = product.getProductAttributes() != null ? product.getProductAttributes()
                    : new ArrayList<>();

            // Xóa ProductAttribute trong DB nếu không có trong dữ liệu mới
            for (ProductAttribute existingAttr : existingAttrs) {
                if (!newAttrs.stream().anyMatch(newAttr -> newAttr.getId() == existingAttr.getId())) {
                    // Xóa Attribute bên trong trước
                    List<Attribute> attrsToRemove = attributeRepository.findByProductAttributeId(existingAttr.getId());
                    for (Attribute attr : attrsToRemove) {
                        cartProductAttributeRepository.deleteByAttributeId(attr.getId());
                        attributeRepository.delete(attr);
                    }
                    // Xóa ProductAttribute
                    productAttributeRepository.delete(existingAttr);
                }
            }

            // Thêm hoặc cập nhật ProductAttribute
            for (ProductAttribute newAttr : newAttrs) {
                if (newAttr.getId() == 0) {
                    // Thêm ProductAttribute mới
                    newAttr.setProduct(existingProduct);
                    ProductAttribute savedAttr = productAttributeRepository.save(newAttr);

                    // Thêm Attribute mới bên trong
                    if (newAttr.getAttributes() != null) {
                        for (Attribute attribute : newAttr.getAttributes()) {
                            attribute.setProductAttribute(savedAttr);
                            attributeRepository.save(attribute);
                        }
                    }
                } else {
                    // Cập nhật ProductAttribute hiện có
                    ProductAttribute existingAttr = productAttributeRepository.findById(newAttr.getId())
                            .orElseThrow(() -> new RuntimeException("ProductAttribute not found"));
                    existingAttr.setName(newAttr.getName()); // Cập nhật tên

                    // 3. Xử lý Attribute trong ProductAttribute
                    List<Attribute> existingAttributes = attributeRepository
                            .findByProductAttributeId(existingAttr.getId());
                    List<Attribute> newAttributes = newAttr.getAttributes() != null ? newAttr.getAttributes()
                            : new ArrayList<>();

                    // Xóa Attribute trong DB nếu không có trong dữ liệu mới
                    for (Attribute existingAttribute : existingAttributes) {
                        if (!newAttributes.stream().anyMatch(newA -> newA.getId() == existingAttribute.getId())) {
                            cartProductAttributeRepository.deleteByAttributeId(existingAttribute.getId());
                            attributeRepository.delete(existingAttribute);
                        }
                    }

                    // Thêm hoặc cập nhật Attribute
                    for (Attribute newAttribute : newAttributes) {
                        if (newAttribute.getId() == 0) {
                            // Thêm Attribute mới
                            newAttribute.setProductAttribute(existingAttr);
                            attributeRepository.save(newAttribute);
                        } else {
                            // Cập nhật Attribute hiện có
                            Attribute existingA = attributeRepository.findById(newAttribute.getId())
                                    .orElseThrow(() -> new RuntimeException("Attribute not found"));
                            existingA.setName(newAttribute.getName());
                            existingA.setAttributePrice(newAttribute.getAttributePrice());
                            attributeRepository.save(existingA);
                        }
                    }
                }
            }

            // Lưu lại Product đã chỉnh sửa
            productRepository.save(existingProduct);
        }
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
        if (product.isPresent() && product.get() != null) {
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
        // Lưu các ProductImg nếu tồn tại
        saveProductImages(product, savedProduct);

        // Lưu các ProductAttribute và Attributes con nếu tồn tại

        return savedProduct;
    }

    private void assignCategory(Product product) {
        if (product.getCategory() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(
                            () -> new RuntimeException("Category not found with id: " + product.getCategory().getId()));
            product.setCategory(category);
        }
    }

    private void assignSupplier(Product product) {
        if (product.getSupplier() != null) {
            Supplier supplier = supplierRepository.findById(product.getSupplier().getId())
                    .orElseThrow(
                            () -> new RuntimeException("Supplier not found with id: " + product.getSupplier().getId()));
            product.setSupplier(supplier);
        }
    }

    private void saveProductImages(Product product, Product savedProduct) {
        productImgRepository.deleteByProduct(savedProduct);
        if (product.getProductImgs() != null && !product.getProductImgs().isEmpty()) {
            product.getProductImgs().forEach(productImg -> {
                if (productImg.getLink() != null) {

                    productImg.setProduct(savedProduct);

                    productImgRepository.save(productImg);
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

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    // Thêm phương thức thống kê sản phẩm
    public List<Object[]> getProductStatistics(Sort sort) {
        return productRepository.getProductStatistics(sort);
    }

    // Thống kê Top 10 sản phẩm có doanh thu cao nhất
    public List<Map<String, Object>> getTop10ProductRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = productRepository.getTop10ProductRevenue(startDate, endDate);
        List<Map<String, Object>> productRevenue = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("productName", row[0]);
            data.put("revenue", row[1]);
            data.put("totalQuantity", row[2]);
            productRevenue.add(data);
        }
        return productRevenue;
    }

}
