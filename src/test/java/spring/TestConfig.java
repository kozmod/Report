package spring;

import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("report.spring.spring.components")
public class TestConfig {

    @Test
    public void prototypeTest() {

    }
}
