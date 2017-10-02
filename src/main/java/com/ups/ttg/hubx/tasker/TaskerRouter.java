package com.ups.ttg.hubx.tasker;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
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
                .log(LoggingLevel.INFO, "Response from {{configuratorUrl}}: ${body}");
              //.to("log:com.ups.ttg.hubx.tasker?level=INFO&message=Response from {{configuratorUrl}}: ${body}");

//            .log(LoggingLevel.DEBUG, "Processing file ${file:name}").to("bean:foo");


        // from("direct:two")
        //         .setHeader(Exchange.HTTP_METHOD, constant("GET"))
        //         .to("http://www.yahoo.com");

    }

}
