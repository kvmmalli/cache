package com.kvm.cache.service;

import com.kvm.cache.dao.EmployeeDao;
import com.kvm.cache.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheableService {

    private static final String CACHE_NAME = "a1cache";
    @Autowired
    private EmployeeDao employeeDao;
    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public Employee getEmployeeById(Integer id) {
        // Simulate a time-consuming operation
        System.out.println("getEmployeeById called for key = " + id);
            return employeeDao.findById(id).get();
    }

    @Cacheable(cacheNames = CACHE_NAME )//, condition = "#result != null and #result.size() > 0")
    public List<Employee> getAllEmployees() {
        // Simulate a time-consuming operation
        System.out.println("get All Employees");
        return employeeDao.findAll();
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#employee.id")
    public Employee saveEmployee(Employee employee) {
        System.out.println("Persisting Employee for key = " + employee.getId());
        return employeeDao.save(employee);
    }

    @CachePut(cacheNames = CACHE_NAME,  key = "#employee.id")
    public Employee updateEmployee(Employee employee) {
        System.out.println("Update Employee for key = " + employee.getId());
        Employee persistedEmployee = employeeDao.findById(employee.getId()).get();
        persistedEmployee.setDepartment(employee.getDepartment());
        persistedEmployee.setName(employee.getName());
        persistedEmployee.setSalary(employee.getSalary());
        return employeeDao.save(persistedEmployee);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void deleteEmployee(Integer id) {
        System.out.println("Delete Employee for key = " + id);
        Employee persistedEmployee = employeeDao.findById(id).get();
        employeeDao.deleteById(persistedEmployee.getId());
    }

    @CacheEvict(cacheNames = CACHE_NAME, allEntries = true)
    public void clearAllCache() {
        System.out.println("Clearing all cache entries");
    }
}
