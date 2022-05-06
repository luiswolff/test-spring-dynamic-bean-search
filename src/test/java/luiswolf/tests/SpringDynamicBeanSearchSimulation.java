package luiswolf.tests;

import io.gatling.javaapi.core.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SpringDynamicBeanSearchSimulation extends Simulation {

    private static final String[] ENDPOINTS = System.getProperty("testEndpoints", "http://localhost:8080").split(",");

    private static final int TEST_LOAD = Integer.getInteger("testLoad", 5);
    private static final int TEST_DURATION = Integer.getInteger("testDuration", 1);

    private ScenarioBuilder scn(String name) {

        return scenario(name) // A scenario is a chain of requests and pauses
                .exec(http("default: without name").get("/greeting")) //
                .exec(http("default: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName())) //
                .exec(http("Euphoric: without name").get("/greeting").queryParam("greeter", "euphoricGreeter")) //
                .exec(http("Euphoric: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName()).queryParam("greeter", "euphoricGreeter")) //
                .exec(http("Slang: without name").get("/greeting").queryParam("greeter", "slangGreeter")) //
                .exec(http("Slang: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName()).queryParam("greeter", "slangGreeter"));
    }
    {
        System.out.printf("Run simulation against %s with load of %d and duration %d", Arrays.toString(ENDPOINTS), TEST_LOAD, TEST_DURATION);
        System.out.println();
        setUp(Arrays.stream(ENDPOINTS).map(endpoint -> scn(endpoint).injectOpen(constantUsersPerSec(TEST_LOAD).during(Duration.ofMinutes(TEST_DURATION))).protocols(http.baseUrl(endpoint))).collect(Collectors.toList()));
    }
}
