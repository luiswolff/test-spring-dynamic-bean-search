package luiswolf.tests;

import io.gatling.javaapi.core.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

@SuppressWarnings("unused") // IntelliJ doesn't realize that this class is auto-detected by Gatling
public class SpringDynamicBeanSearchSimulation extends Simulation {

    private static final String[] ENDPOINTS = System.getProperty("testEndpoints", "http://localhost:8080").split(",");

    private static final int TEST_LOAD = Integer.getInteger("testLoad", 5);
    private static final int TEST_DURATION = Integer.getInteger("testDuration", 1);

    private ScenarioBuilder scn(String name) {

        return scenario(name) // A scenario is a chain of requests and pauses
                .exec(http(name + " - default: without name").get("/greeting")) //
                .exec(http(name + " - default: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName())) //
                .exec(http(name + " - Euphoric: without name").get("/greeting").queryParam("greeter", "euphoricGreeter")) //
                .exec(http(name + " - Euphoric: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName()).queryParam("greeter", "euphoricGreeter")) //
                .exec(http(name + " - Slang: without name").get("/greeting").queryParam("greeter", "slangGreeter")) //
                .exec(http(name + " - Slang: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName()).queryParam("greeter", "slangGreeter"));
    }
    {
        System.out.printf("Run simulation against %s with load of %d and duration %d", Arrays.toString(ENDPOINTS), TEST_LOAD, TEST_DURATION);
        System.out.println();
        setUp(Arrays.stream(ENDPOINTS).map(endpoint -> scn(endpoint).injectOpen(
                incrementUsersPerSec(TEST_LOAD / 5.0).times(4).eachLevelLasting(10).startingFrom(TEST_LOAD / 5.0),
                constantUsersPerSec(TEST_LOAD).during(Duration.ofMinutes(TEST_DURATION))
        ).protocols(http.baseUrl(endpoint).shareConnections())).collect(Collectors.toList()));
    }
}
