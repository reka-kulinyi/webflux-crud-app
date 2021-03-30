package com.example.webfluxcrud.annotated_controller;

import com.example.webfluxcrud.annotated_controller.controller.EmployeeController;
import com.example.webfluxcrud.annotated_controller.model.Employee;
import com.example.webfluxcrud.annotated_controller.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AnnotatedControllerTest {

    @Autowired
    private EmployeeRepository repository;

    private WebTestClient client;
    private List<Employee> expectedEmployeeList;

    @BeforeEach
    void setup() {
        this.client = WebTestClient.bindToController(new EmployeeController(repository))
                .configureClient()
                .baseUrl("/employees")
                .build();
        this.expectedEmployeeList = repository.findAll().collectList().block();
    }

    @Test
    void testGetAllEmployees() {
        client.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Employee.class)
                .isEqualTo(expectedEmployeeList);
    }

    @Test
    void testEmployeeWithValidIdNotFound() {
        Employee emp = expectedEmployeeList.get(0);
        client.get()
                .uri("/{id}", emp.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Employee.class).isEqualTo(emp);
    }

    @Test
    void testEmployeeWithInvalidIdNotFound() {
        client.get()
                .uri("/abcd")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
