package com.example.webfluxcrud.annotated_controller;

import com.example.webfluxcrud.annotated_controller.controller.EmployeeController;
import com.example.webfluxcrud.annotated_controller.model.Employee;
import com.example.webfluxcrud.annotated_controller.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AnnotatedControllerMockTest {

        private WebTestClient client;
        private List<Employee> expectedEmployeeList;

        @MockBean
        private EmployeeRepository repository;

        @BeforeEach
        void setup() {
            this.client = WebTestClient.bindToController(new EmployeeController(repository))
                            .configureClient()
                            .baseUrl("/employees")
                            .build();

            this.expectedEmployeeList = Arrays.asList(
                    new Employee("1", "Jane", "Taylor", 15.55));
        }

        @Test
        void testGetAllEmployees() {
            when(repository.findAll()).thenReturn(Flux.fromIterable(this.expectedEmployeeList));

            client.get()
                    .uri("/")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBodyList(Employee.class)
                    .isEqualTo(expectedEmployeeList);
        }

        @Test
        void testEmployeeWithInvalidIdNotFound() {
            String invalidId = "abc";
            when(repository.findById(invalidId)).thenReturn(Mono.empty());

            client.get()
                    .uri("/{id}", invalidId)
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }

        @Test
        void testEmployeeWithValidIdFound() {
            Employee expectedEmployee = this.expectedEmployeeList.get(0);
            when(repository.findById(expectedEmployee.getId())).thenReturn(Mono.just(expectedEmployee));

            client.get()
                    .uri("/{id}", expectedEmployee.getId())
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(Employee.class)
                    .isEqualTo(expectedEmployee);
        }
}
