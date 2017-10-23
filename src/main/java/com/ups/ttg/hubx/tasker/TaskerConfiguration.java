package com.ups.ttg.hubx.tasker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This is a POJO that maps to the application.yml configurations.
 * @author stran
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "configuration") 
public class TaskerConfiguration {

    private List<Data> data;

    public TaskerConfiguration() {
    	data = new ArrayList<>();
    }
    
	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Data d : data) {
			sb.append(d.toString());
		}
    	return sb.toString();
    }
    
	/**
	 * Inner class to hold nested configuration data.
	 * @author stran
	 */
	public static class Data {
		private String name;
		private String cron;
		private Map<String, String> endpoints;
		private Map<String, String> headers;

		public Data() {
			name = "";
			cron = "";
			endpoints = new HashMap<>();
			headers = new HashMap<>();
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCron() {
			return cron;
		}
		public void setCron(String cron) {
			this.cron = cron;
		}
		public Map<String, String> getEndpoints() {
			return endpoints;
		}
		public void setEndpoints(Map<String, String> endpoints) {
			this.endpoints = endpoints;
		}
		public Map<String, String> getHeaders() {
			return headers;
		}

		public void setHeaders(Map<String, String> headers) {
			this.headers = headers;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append(String.format("Name : %s\n", name));
			sb.append(String.format("Cron : %s\n", cron));
			sb.append(String.format("Endpoints : %s\n", endpoints.toString()));
			sb.append(String.format("Headers : %s\n", headers.toString()));

			return sb.toString();
		}
	}
}