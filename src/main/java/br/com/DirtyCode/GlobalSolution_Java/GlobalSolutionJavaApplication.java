package br.com.DirtyCode.GlobalSolution_Java;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@OpenAPIDefinition(
		info = @Info(
				title = "DirtyCode",
				version = "1.0.0",
				description = "API REST",
				contact = @Contact(name = "DirtyCode", url = "https://www.google.com")
		)
)
public class GlobalSolutionJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalSolutionJavaApplication.class, args);
	}

}
