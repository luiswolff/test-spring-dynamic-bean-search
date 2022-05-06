package luiswolf.tests;

import luiswolf.tests.greeting.spi.Greeter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController()
public class TestSpringDynamicBeanSearchApplication {

	private final ApplicationContext context;

	public TestSpringDynamicBeanSearchApplication(ApplicationContext context) {
		this.context = context;
	}

	@GetMapping(path = "greeting")
	public String getGreeting(@RequestParam(defaultValue = "world") String name, @RequestParam(defaultValue = "normalGreeter") String greeter) {
		Greeter greeterImpl = context.getBean(greeter, Greeter.class);
		return greeterImpl.greet(name);
	}

	public static void main(String[] args) {
		SpringApplication.run(TestSpringDynamicBeanSearchApplication.class, args);
	}

}
