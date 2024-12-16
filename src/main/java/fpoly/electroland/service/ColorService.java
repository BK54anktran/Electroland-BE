package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ColorReponsitory;

@Service
public class ColorService {

    @Autowired
    ColorReponsitory colorReponsitory;

}
