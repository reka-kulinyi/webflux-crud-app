package com.example.webfluxcrud;

import com.example.webfluxcrud.annotated_controller.model.Employee;
import com.example.webfluxcrud.annotated_controller.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class WebfluxCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxCrudApplication.class, args);
	}

	// - - - Creating Employees - - -
	@Bean
	CommandLineRunner init(ReactiveMongoOperations operations, EmployeeRepository repository) {
		return args -> {
			Flux<Employee> employeeFlux = Flux.just(
				new Employee(null, "Joe", "Smith", 25.85),
					new Employee(null, "Gary", "Wright", 12.56),
					new Employee(null, "Mary", "Taylor", 23.14)
			).flatMap(repository::save);

			employeeFlux.thenMany(repository.findAll()).subscribe(System.out::println); // check
		};
	}

}
