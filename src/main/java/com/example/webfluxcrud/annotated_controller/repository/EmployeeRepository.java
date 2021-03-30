package com.example.webfluxcrud.annotated_controller.repository;

import com.example.webfluxcrud.annotated_controller.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
