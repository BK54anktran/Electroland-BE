package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.repository.EmployeeReponsitory;

@Service
public class EmployeeService {

    @Autowired
    EmployeeReponsitory employeeReponsitory;

}
