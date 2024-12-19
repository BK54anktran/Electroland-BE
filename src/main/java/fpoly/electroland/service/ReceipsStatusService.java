package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.ReceipsStatusReponsitory;

@Service
public class ReceipsStatusService {

    @Autowired
    ReceipsStatusReponsitory receipsStatusReponsitory;

}
