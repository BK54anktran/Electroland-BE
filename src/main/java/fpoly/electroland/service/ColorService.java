package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Color;
import fpoly.electroland.repository.ColorReponsitory;

@Service
public class ColorService {

    @Autowired
    ColorReponsitory colorReponsitory;

    public Optional<Color> getColorById(Integer id){
        return colorReponsitory.findById(id);
    }
}
