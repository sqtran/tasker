package com.ups.ttg.hubx.tasker;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TaskerRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:hello?period={{timer.period}}")
                .transform(method("myBean", "saySomething"))
                .to("stream:out");

    }

}
