package com.example.webfluxcrud.annotated_controller.controller;

import com.example.webfluxcrud.annotated_controller.model.Employee;
import com.example.webfluxcrud.annotated_controller.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Employee>> getEmployee(@PathVariable String id) {
        return repository.findById(id)
                .map(employee -> ResponseEntity.ok(employee))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Employee> saveEmployee(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Employee>> updateEmployee(@PathVariable(value="id") String id,
                                                         @RequestBody Employee employee) {
        return repository.findById(id)
                .flatMap(existingEmployee -> {
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setLastName(employee.getLastName());
                    existingEmployee.setBonus(employee.getBonus());
                    return repository.save(existingEmployee);
                })
                .map(ResponseEntity::ok) // same as .map(employee -> ResponseEntity.ok(employee))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable(value="id") String id) {
        return repository.findById(id)
                .flatMap(existingEmployee -> repository.delete(existingEmployee)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build()));
    }

    @DeleteMapping
    public Mono<Void> deleteAllEmployees() {
        return repository.deleteAll();
    }

}
