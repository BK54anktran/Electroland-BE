package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ProductImgRepository;

@Service
public class ProductImgService {

    @Autowired
    ProductImgRepository productImgRepository;

}
