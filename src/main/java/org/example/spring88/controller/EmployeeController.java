package org.example.spring88.controller;

import jakarta.validation.Valid;
import org.example.spring88.model.Company;
import org.example.spring88.model.Employee;
import org.example.spring88.repository.CompanyRepository;
import org.example.spring88.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping(value = "/list")
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
        return "employDetail";
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employee/list";
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/employeeAdd", method = RequestMethod.GET)
    public String showAddEmployeeForm(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        model.addAttribute("employee", new Employee());
        return "employeeAdd";
    }

    @PostMapping("/save")
    public String saveUpdate(@Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Company> companies = companyRepository.findAll();
            model.addAttribute("companies", companies);
            return employee.getId() == null ? "employeeAdd" : "employeeUpdate";
        }
        employeeRepository.save(employee);
        return "redirect:/employee/list";
    }

   @RequestMapping("/search")
   public String searchEmployee(@RequestParam(value = "name") String name, Model model) {
       List<Employee> employees = employeeRepository.findByNameContaining(name);
       model.addAttribute("employees", employees);
       return "employeeList";
   }

    @GetMapping("/sort/asc")
    public String sortEmployeeAsc(Model model) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("employees", employees);
        return "employeeList";
    }

    @GetMapping("/sort/desc")
    public String sortEmployeeDesc(Model model) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("employees", employees);
        return "employeeList";
    }


}