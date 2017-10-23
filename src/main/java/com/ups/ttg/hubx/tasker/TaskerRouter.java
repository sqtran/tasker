package com.ups.ttg.hubx.tasker;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Main route builder for Tasker.  This dynamically builds a camel route per 
 *  configuration via the application.yml file.  Unless new functionality is 
 *  needed, this class should not be modified.  All configurations are done
 *  in the application.yml file.
 * 
 * @author stran
 */
@Component
@EnableConfigurationProperties
public class TaskerRouter extends RouteBuilder {

    @Autowired
    private TaskerConfiguration conf;
    
    private static final String FROM_CONFIG = "quartz://%s?cron=%s";
    private static final String MESSAGE =  "Response from %s (%s): ${body}";

    @Override
    public void configure() throws Exception {

        //String EXCEPTION_CAUGHT = "CamelExceptionCaught";
        onException(Exception.class).handled(true)
        	.log(LoggingLevel.ERROR,"${exception.message}");
       
        System.out.println("Building routes: ");
        
		String routeURL;
        String routeName;
        String routeCron;
        
        for(TaskerConfiguration.Data data : conf.getData()) {
        	System.out.println("Processing " + (routeName = data.getName()));
        	System.out.println("Cron = " + (routeCron = data.getCron()));
        	
        	Map<String, String> endpoints = data.getEndpoints();

        	for(String key : endpoints.keySet()) {
        		System.out.println("route = " + (routeURL = endpoints.get(key)));
        		
        		// routeName + route's key makes a unique identifier
        		String routeKey = routeName + "_" + key;
        		
            	from(String.format(FROM_CONFIG, routeKey, routeCron))
            		.routeId(routeKey)
            		.process(new Processor() {
            			// Process the default headers as well as anything overridden
						@Override
						public void process(Exchange exchange) throws Exception {
 							exchange.getIn().setHeader(Exchange.HTTP_METHOD, "GET");
 							exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
 							exchange.getIn().setHeader("Authorization", "Basic UPSDummyUser:UPSPassword");
 							exchange.getIn().setHeader("UserID", "Openshift");
 							exchange.getIn().setHeader("MobileID", "Scheduler");
 							exchange.getIn().setHeader("Role", "admin");
 				            
 				            Map<String, String> headers = data.getHeaders();
 				            for(String key : headers.keySet()) { 
 				            	exchange.getIn().setHeader(key, headers.get(key));
 				            }
						}
            		})
            		.to(routeURL)
            		.log(LoggingLevel.INFO, String.format(MESSAGE, routeKey, routeURL));
        	}
        }
    }    
}