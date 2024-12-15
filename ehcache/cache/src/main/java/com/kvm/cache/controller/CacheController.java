package com.kvm.cache.controller;

import com.kvm.cache.entity.Employee;
import com.kvm.cache.service.CacheableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class CacheController {

    private final CacheableService cacheableService;

    public CacheController(CacheableService cacheableService) {
        this.cacheableService = cacheableService;
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") Integer key) {
        return cacheableService.getEmployeeById(key);
    }
    @GetMapping
    public List<Employee> getEmployees() {
        return cacheableService.getAllEmployees();
    }

    @PostMapping
    public void saveEmployee(@RequestBody Employee employee) {
         cacheableService.saveEmployee(employee);
    }

    @PutMapping
    public void updateEmployee(@RequestBody Employee employee) {
         cacheableService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        cacheableService.deleteEmployee(id);
    }

    @DeleteMapping("/invalidate")
    public void deleteEntries() {
        cacheableService.clearAllCache();
    }
}
