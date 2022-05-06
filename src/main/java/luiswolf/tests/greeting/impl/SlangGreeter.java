package luiswolf.tests.greeting.impl;

import luiswolf.tests.greeting.spi.Greeter;
import org.springframework.stereotype.Component;

@Component
public class SlangGreeter implements Greeter {
    @Override
    public String greet(String name) {
        return String.format("Hey bro %s, whats happening?", name);
    }
}
