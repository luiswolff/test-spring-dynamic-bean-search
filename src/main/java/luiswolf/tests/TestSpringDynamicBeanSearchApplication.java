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

	/*
	 * In order to have a healthy application you can comment out everything related to ApplicationContext
	 * and comment in the following lines.
	 */
//	private final java.util.Map<String, Greeter> greeterIndex;
//
//	public TestSpringDynamicBeanSearchApplication(java.util.Map<String, Greeter> greeterIndex) {
//		this.greeterIndex = greeterIndex;
//	}

	@GetMapping(path = "greeting")
	public String getGreeting(@RequestParam(defaultValue = "world") String name, @RequestParam(defaultValue = "normalGreeter") String greeter) {
		Greeter greeterImpl = context.getBean(greeter, Greeter.class);
		//Greeter greeterImpl = greeterIndex.get(greeter);
		return greeterImpl.greet(name);
	}

	public static void main(String[] args) {
		SpringApplication.run(TestSpringDynamicBeanSearchApplication.class, args);
	}

}
