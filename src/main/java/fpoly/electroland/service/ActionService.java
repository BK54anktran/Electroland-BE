package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ActionReponsitory;

@Service
public class ActionService {

    @Autowired
    ActionReponsitory actionReponsitory;

}
