package fpoly.electroland.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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

        product.getProductAttributes().forEach(pa -> pa.setProduct(product));
        Product savedProduct = productRepository.save(product);
        // int idProduct = savedProduct.getId();

        // Lấy danh sách ảnh cũ từ DB
        List<ProductImg> oldImages = productImgRepository.findByProduct(savedProduct);

        // Nếu có danh sách ảnh mới
        if (product.getProductImgs() != null) {
            List<ProductImg> newImages = product.getProductImgs();

            // Bước 1: Xóa các ảnh cũ không còn trong danh sách mới
            List<String> newImageLinks = newImages.stream()
                    .map(ProductImg::getLink)
                    .collect(Collectors.toList());

            oldImages.stream()
                    .filter(oldImg -> !newImageLinks.contains(oldImg.getLink()))
                    .forEach(productImgRepository::delete);

            // Bước 2 & 3: Cập nhật hoặc thêm mới ảnh
            for (ProductImg newImg : newImages) {
                if (newImg.getLink() != null) {
                    // Kiểm tra xem ảnh đã tồn tại hay chưa (dựa trên link)
                    Optional<ProductImg> existingImgOpt = oldImages.stream()
                            .filter(oldImg -> oldImg.getLink().equals(newImg.getLink()))
                            .findFirst();

                    if (existingImgOpt.isPresent()) {
                        // Ảnh đã tồn tại -> Cập nhật thông tin khác nếu cần
                        ProductImg existingImg = existingImgOpt.get();
                        existingImg.setProduct(savedProduct); // Đảm bảo ảnh vẫn thuộc sản phẩm
                        productImgRepository.save(existingImg);
                    } else {
                        // Ảnh chưa tồn tại -> Thêm mới
                        newImg.setProduct(savedProduct);
                        productImgRepository.save(newImg);
                    }
                }
            }
        }
        // // saveProductAttributes(product, savedProduct);
        List<ProductAttribute> listPA = product.getProductAttributes();

        // Lấy danh sách ProductAttribute hiện có trong database
        List<ProductAttribute> existingPAs = productAttributeRepository.findByProductId(savedProduct.getId());

        listPA.forEach(pa -> {
            Optional<ProductAttribute> pAttribute = productAttributeRepository.findByNameAndProductId(pa.getName(),
                    savedProduct.getId());

            ProductAttribute pat;
            if (!pAttribute.isPresent()) {
                ProductAttribute newPA = new ProductAttribute();
                newPA.setName(pa.getName());
                newPA.setProduct(savedProduct);
                pat = productAttributeRepository.save(newPA);
            } else {
                pat = pAttribute.get();
            }

            // Lấy danh sách Attribute hiện có trong database của ProductAttribute này
            List<Attribute> existingAttributes = attributeRepository.findByProductAttribute(pat);

            pa.getAttributes().forEach(att -> {
                Optional<Attribute> existingAttribute = attributeRepository
                        .findByNameAndProductAttributeId(att.getName(), pat.getId());
                if (!existingAttribute.isPresent()) {
                    Attribute newAttribute = new Attribute();
                    newAttribute.setName(att.getName());
                    newAttribute.setProductAttribute(pat);
                    newAttribute.setAttributePrice(att.getAttributePrice());
                    attributeRepository.save(newAttribute);
                } else {
                    existingAttributes.remove(existingAttribute.get());
                }
            });

            // Xóa các Attribute không có trong product.getProductAttributes()
            existingAttributes.forEach(attributeRepository::delete);

            existingPAs.remove(pat);
        });

        // Xóa các ProductAttribute không có trong product.getProductAttributes()
        existingPAs.forEach(pa -> {
            attributeRepository.deleteByProductAttributeId(pa.getId()); // Xóa tất cả Attribute liên quan
            productAttributeRepository.delete(pa);
        });
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
}
