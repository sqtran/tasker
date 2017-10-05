package com.ups.ttg.hubx.tasker;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
public class TaskerRouter extends RouteBuilder {

    @Autowired
    private SegmentListConfig segmentListConfig;

    @Override
    public void configure() throws Exception {

        //String EXCEPTION_CAUGHT = "CamelExceptionCaught";
        onException(Exception.class).handled(true)
                .log(LoggingLevel.ERROR,"${exception.message}");

        //TODO see what we can do with .toD and looping thru list
        //https://camel.apache.org/message-endpoint.html#MessageEndpoint-DynamicTo

        from("quartz://crdDataTimer?cron={{crddata.period}}").routeId("crdDataTimer")
//                .process(new Processor() {
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//                        System.out.println("list " + segmentListConfig.getList());
//                    }
//                })
                .to("direct:setHeaders")
                .multicast().to("direct:CRDData.EARMO", "direct:CRDData.LCHKY");

        from("quartz://tdsTimer?cron={{tds.period}}").routeId("tdsTimer")
                .to("direct:setHeaders")
                .multicast().to("direct:TDS.EARMO", "direct:TDS.LCHKY");

        from("quartz://seasTimer?cron={{seas.period}}").routeId("seasTimer")
                .to("direct:setHeaders")
                .multicast().to("direct:SEAS.EARMO", "direct:SEAS.LCHKY");

        from("direct:CRDData.EARMO")
                .toD("{{crddata.earmo}}")
                .log(LoggingLevel.INFO,"Response from CRDData.EARMO: ${body}");

        from("direct:CRDData.LCHKY")
                .toD("{{crddata.lchky}}")
                .log(LoggingLevel.INFO,"Response from CRDData.LCHKY: ${body}");

        from("direct:TDS.EARMO")
                .toD("{{tds.earmo}}}")
                .log(LoggingLevel.INFO,"Response from TDS.EARMO: ${body}");

        from("direct:TDS.LCHKY")
                .toD("{{tds.lchky}}")
                .log(LoggingLevel.INFO,"Response from TDS.LCHKY: ${body}");

        from("direct:SEAS.EARMO")
                .toD("{{seas.earmo}}")
                .log(LoggingLevel.INFO,"Response from SEAS.EARMO: ${body}");

        from("direct:SEAS.LCHKY")
                .toD("{{seas.lchky}}")
                .log(LoggingLevel.INFO,"Response from SEAS.LCHKY: ${body}");

        from("direct:setHeaders").routeId("setHeaders")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Authorization", constant("Basic UPSDummyUser:UPSPassword"))
                .setHeader("UserID", constant("Openshift"))
                .setHeader("MobileID", constant("Scheduler"))
                .setHeader("Role", constant("admin"));

    }
}
