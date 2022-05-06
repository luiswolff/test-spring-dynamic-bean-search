package luiswolf.tests;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SpringDynamicBeanSearchSimulation extends Simulation {

    private static final int TEST_LOAD = Integer.getInteger("testLoad", 5);
    private static final int TEST_DURATION = Integer.getInteger("testDuration", 1);

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080");

    ScenarioBuilder scn = scenario("Dynamic Bean Search Scenario") // A scenario is a chain of requests and pauses
            .exec(http("default: without name").get("/greeting")) //
            .exec(http("default: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName())) //
            .exec(http("Euphoric: without name").get("/greeting").queryParam("greeter", "euphoricGreeter")) //
            .exec(http("Euphoric: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName()).queryParam("greeter", "euphoricGreeter")) //
            .exec(http("Slang: without name").get("/greeting").queryParam("greeter", "slangGreeter")) //
            .exec(http("Slang: with name").get("/greeting").queryParam("name", s -> NameGenerator.nextName()).queryParam("greeter", "slangGreeter"));

    {
        setUp(scn.injectOpen(constantUsersPerSec(TEST_LOAD).during(Duration.ofMinutes(TEST_DURATION)))).protocols(httpProtocol);
    }
}
