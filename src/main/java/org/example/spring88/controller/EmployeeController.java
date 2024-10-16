package org.example.spring88.controller;

import jakarta.validation.Valid;
import org.example.spring88.model.Employee;
import org.example.spring88.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/list")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employeeList";
    }

    @RequestMapping(value = "/{id}")
    public String getEmployeeById(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return "error";
        }
        model.addAttribute("employee", employee);
        return "employeeDetail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/employeeAdd", method = RequestMethod.GET)
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employeeAdd";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id, @Valid Employee employee, BindingResult result)
    {
        if (result.hasErrors()) {
            if (id == null) {
                return "employeeAdd";
            } else {
                return "employeeUpdate";
            }
        }
        employee.setId(id);
        Employee savedEmployee = employeeRepository.save(employee);
        return "redirect:/update/" + savedEmployee.getId();
    }
}