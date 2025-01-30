package com.luv2code.cruddemo.rest;

import com.luv2code.cruddemo.entity.Employee;
import com.luv2code.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // expose "/employees" and return a list of employees
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // add mapping foe GET /employees/{employeeID}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee == null) {
            throw new RuntimeException("Employee not found: " + employeeId);

        }
        return theEmployee;
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee theEmployee) {
        // also just in case they pass an in JSON ... set id to 0
        // this is to force a save of new item instead of update
        theEmployee.setId(0);
        Employee newEmployee = employeeService.save(theEmployee);
        return newEmployee;
    }

    // add mapping for PUT /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {
        Employee updateEmployee = employeeService.save(theEmployee);
        return updateEmployee;
    }

    // add mapping for DELETE /employees/{employeeId} - deletes an existing employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);

        // throw exception if null
        if (theEmployee == null) {
            throw new RuntimeException("Employee not found: " + employeeId);
        }
        employeeService.deleteById(employeeId);
        return "Employee deleted: " + employeeId;
    }


}
