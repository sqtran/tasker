package com.ups.ttg.hubx.tasker;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ups.ttg.hubx.tasker.TaskerConfiguration.Data;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskerApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void toStringWorks() {
		Assert.assertNotNull(getTester().toString());
		System.out.println(getTester().toString());
	}
	
	@Test
	public void testTaskerConfigurationStable() {
		Assert.assertNotNull(getTester());
		Assert.assertNotNull(getTester().getData());
		Assert.assertNotNull(getTester().getData().get(0));
		Assert.assertNotNull(getTester().getData().get(0).getCron());
		Assert.assertNotNull(getTester().getData().get(0).getName());
		Assert.assertNotNull(getTester().getData().get(0).getEndpoints());
		Assert.assertNotNull(getTester().getData().get(0).getHeaders());
	}
	
	private TaskerConfiguration getTester() {
		TaskerConfiguration tc = new TaskerConfiguration();
		Data d = new Data();
		tc.getData().add(d);

		d.setName("Test Name");
		d.setCron("some random non-cron value");

		Map<String, String> endpoints = new HashMap<>();
		endpoints.put("test1", "value1");
		endpoints.put("test2", "value2");
		d.setEndpoints(endpoints);
		
		return tc;
	}
}