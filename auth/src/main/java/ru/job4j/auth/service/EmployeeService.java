package ru.job4j.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private static final String API = "http://localhost:8080/person/listForEmployee/{id}";
    private final EmployeeRepository repository;
    @Autowired
    private RestTemplate rest;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() {
        List<Employee> employeeList = new ArrayList<>();
        Iterable<Employee> employees = this.repository.findAll();
        employees.forEach(e -> {
            employeeList.add(e);
            ResponseEntity<List<Person>> accounts = rest.exchange(
                    API,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    },
                    e.getId()
            );
            e.setAccounts(accounts.getBody());
        });
        return employeeList;
    }

    public Employee create(Employee employee) {
        return this.repository.save(employee);
    }

    public void update(Employee employee) {
        this.repository.save(employee);
    }

    public void delete(int id) {
        Employee employee = new Employee();
        employee.setId(id);
        this.repository.delete(employee);
    }
}
