package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.EmployeeRepository;
import fpoly.electroland.util.CreateAction;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeTest {
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    CreateAction createAction;

    @InjectMocks
    EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private Employee creatorEmployee;
    private Employee updateData;

    @BeforeEach
    void setUp() {
        employee1 = new Employee();
        employee1.setId(1);
        employee1.setEmail("daotankiet@gmail.com");
        employee1.setFullName("Đào Tấn Kiệt");
        employee1.setPassword("123");
        employee1.setPhoneNumber("0773699825");
        employee1.setRole("admin");
        employee1.setStatus(true);

        employee2 = new Employee();
        employee2.setId(2);
        employee2.setEmail("tani@gmail.com");
        employee2.setFullName("OHTANI");
        employee2.setPassword("456");
        employee2.setPhoneNumber("0123456789");
        employee2.setRole("user");
        employee2.setStatus(true);

        creatorEmployee = new Employee();
        creatorEmployee.setId(100);
        creatorEmployee.setEmail("admin@gmail.com");
        creatorEmployee.setFullName("Admin");
        creatorEmployee.setPassword("123");
        creatorEmployee.setPhoneNumber("0123456789");
        creatorEmployee.setRole("admin");
        creatorEmployee.setStatus(true);

        updateData = new Employee();
        updateData.setId(1);
        updateData.setEmail("daotankiet123@gmail.com");
        updateData.setFullName("Đào Tấn Kiệt");
        updateData.setPassword("123");
        updateData.setPhoneNumber("0773699825");
        updateData.setRole("admin");
        updateData.setStatus(true);

        doNothing().when(createAction).createAction(anyString(), anyString(), anyInt(), any(), anyString(), any());
        lenient().when(employeeRepository.findById(100)).thenReturn(Optional.of(creatorEmployee));
        lenient().when(employeeRepository.save(employee1)).thenReturn(employee1);
    }

    @Test
    void getAll() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.getAll();

        System.out.println(employees);
        assertNotNull(employees, "List employee should not be null");
        assertEquals("Đào Tấn Kiệt", employees.get(0).getFullName(), "First Employee should be Đào Tấn Kiệt");
        assertEquals("OHTANI", employees.get(1).getFullName(), "Second Employee should be OHTANI");
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployee() {
        when(employeeRepository.findByEmail("tani@gmail.com")).thenReturn(Optional.of(employee2));
        Optional<Employee> result = employeeService.getEmployee("tani@gmail.com");
        System.out.println(result);
        assertEquals("tani@gmail.com", result.get().getEmail(), "Email should be tani@gmail.com");
        assertNotNull(result, "Email should not be null");
        verify(employeeRepository, times(1)).findByEmail("tani@gmail.com");
    }

    @Test
    void createEmployee() throws Exception {
        when(employeeRepository.save(employee1)).thenReturn(employee1);
        when(employeeRepository.findById(100)).thenReturn(Optional.of(creatorEmployee));

        Employee savedEmployee = employeeService.createEmployee(employee1, 100);
        assertNotNull(savedEmployee, "Employee should not be null");
        assertEquals("Đào Tấn Kiệt", savedEmployee.getFullName(), "Employee saved should be Đào Tấn Kiệt");
        assertEquals("daotankiet@gmail.com", savedEmployee.getEmail(),
                "Employee email saved should be daotankiet@gmail.com");
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void updatedEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        when(employeeRepository.findById(100)).thenReturn(Optional.of(creatorEmployee));

        Employee updatedEmployee = employeeService.updateEmployee(1L, updateData, creatorEmployee.getId());

        assertNotNull(updatedEmployee, "Update employee should not be null");
        assertEquals("daotankiet123@gmail.com", updatedEmployee.getEmail(),
                "Updated email should be daotankiet123@gmail.com");
        assertEquals("Đào Tấn Kiệt", updatedEmployee.getFullName(), "Updated full name should be Đào Tấn Kiệt");
        assertEquals("0773699825", updatedEmployee.getPhoneNumber(), "Updated phone number should be 0773699825");

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void searchEmployeesByName() {
        when(employeeRepository.findByFullNameContainingOrEmailContainingOrPhoneNumberContaining("Kiệt", "Kiệt",
                "Kiệt"))
                .thenReturn(List.of(employee1));

        List<Employee> result = employeeService.searchEmployees("Kiệt");
        System.out.println(result);
        assertEquals(1, result.size(), "Result size should be 1");
        assertEquals("Đào Tấn Kiệt", result.get(0).getFullName(), "Employee full name should be Đào Tấn Kiệt");
        assertNotNull(result, "Result should not be null");
        verify(employeeRepository, times(1)).findByFullNameContainingOrEmailContainingOrPhoneNumberContaining("Kiệt",
                "Kiệt", "Kiệt");
    }

    @Test
    void searchEmployeesByEmail() {
        when(employeeRepository.findByFullNameContainingOrEmailContainingOrPhoneNumberContaining("daotankiet@gmail.com",
                "daotankiet@gmail.com", "daotankiet@gmail.com"))
                .thenReturn(List.of(employee1));

        List<Employee> result = employeeService.searchEmployees("daotankiet@gmail.com");
        System.out.println(result);
        assertEquals(1, result.size(), "Result size should be 1");
        assertEquals("daotankiet@gmail.com", result.get(0).getEmail(), "Employee email should be daotankiet@gmail.com");
        assertNotNull(result, "Result should not be null");
        verify(employeeRepository, times(1)).findByFullNameContainingOrEmailContainingOrPhoneNumberContaining(
                "daotankiet@gmail.com", "daotankiet@gmail.com", "daotankiet@gmail.com");
    }

    @Test
    void searchEmployeesByPhoneNumber() {
        when(employeeRepository.findByFullNameContainingOrEmailContainingOrPhoneNumberContaining("0773699825",
                "0773699825", "0773699825"))
                .thenReturn(List.of(employee1));

        List<Employee> result = employeeService.searchEmployees("0773699825");
        System.out.println(result);
        assertEquals(1, result.size(), "Result size should be 1");
        assertEquals("0773699825", result.get(0).getPhoneNumber(), "Employee phone number should be 0773699825");
        assertNotNull(result, "Result should not be null");
        verify(employeeRepository, times(1)).findByFullNameContainingOrEmailContainingOrPhoneNumberContaining(
                "0773699825", "0773699825", "0773699825");
    }

    @Test
    void save() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        Employee result = employeeService.save(employee1);
        assertEquals(employee1, result, "Result should be employee1");
        assertNotNull(result, "Employee saved should not be null");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
}
