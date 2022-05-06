package luiswolf.tests.greeting.impl;

import org.springframework.stereotype.Component;

import luiswolf.tests.greeting.spi.Greeter;

@Component
public class EuphoricGreeter implements Greeter {
    @Override
    public String greet(String name) {
        return String.format("Hey %s, how are you", name);
    }
}
