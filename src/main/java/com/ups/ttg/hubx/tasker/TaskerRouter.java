package com.ups.ttg.hubx.tasker;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TaskerRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:hello?period={{timer.period}}")
                .to("direct:one");
//                .multicast().to("direct:one", "direct:two");

        from("direct:one")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("{{configuratorUrl}}")
                .to("stream:out");

        from("direct:two")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("http://www.yahoo.com");

    }

}
