package com.ups.ttg.hubx.tasker;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskerRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("quartz://crdTimer?cron={{crddata.period}}")
                .to("direct:setHeaders")
                .multicast().to("direct:CRDData.EARMO", "direct:CRDData.LCHKY");

        from("quartz://tdsTimer?cron={{tds.period}}")
                .to("direct:setHeaders")
                .multicast().to("direct:TDS.EARMO", "direct:TDS.LCHKY");

        from("quartz://seasTimer?cron={{seas.period}}")
                .to("direct:setHeaders")
                .multicast().to("direct:SEAS.EARMO", "direct:SEAS.LCHKY");

        from("direct:CRDData.EARMO")
                .to("{{crddata.earmo}}")
                .log(LoggingLevel.INFO,"Response from CRDData.EARMO: ${body}");

        from("direct:CRDData.LCHKY")
                .to("{{crddata.lchky}}")
                .log(LoggingLevel.INFO,"Response from CRDData.LCHKY: ${body}");

        from("direct:TDS.EARMO")
                .to("{{tds.earmo}}")
                .log(LoggingLevel.INFO,"Response from TDS.EARMO: ${body}");

        from("direct:TDS.LCHKY")
                .to("{{tds.lchky}}")
                .log(LoggingLevel.INFO,"Response from TDS.LCHKY: ${body}");

        from("direct:SEAS.EARMO")
                .to("{{seas.earmo}}")
                .log(LoggingLevel.INFO,"Response from SEAS.EARMO: ${body}");

        from("direct:SEAS.LCHKY")
                .to("{{seas.lchky}}")
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
