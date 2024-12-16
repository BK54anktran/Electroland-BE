package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.AttributeReponsitory;

@Service
public class AttributeService {

    @Autowired
    AttributeReponsitory attributeReponsitory;

}
