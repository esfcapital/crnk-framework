package io.crnk.example.springboot.simple;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.crnk.client.CrnkClient;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepository;
import io.crnk.core.resource.list.ResourceList;
import io.crnk.example.springboot.microservice.MicroServiceApplication;
import io.crnk.example.springboot.microservice.task.ProjectProxy;
import io.crnk.example.springboot.microservice.task.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;


import java.io.Serializable;
import java.util.Arrays;

public class MicroServiceApplicationTest {

	private ConfigurableApplicationContext projectApp;

	private ConfigurableApplicationContext taskApp;

	private CrnkClient taskClient;



	@Before
	public void setup() {
		projectApp = MicroServiceApplication.startProjectApplication();
		taskApp = MicroServiceApplication.startTaskApplication();

		String url = "http://127.0.0.1:" + MicroServiceApplication.TASK_PORT;
		taskClient = new CrnkClient(url);
		RestAssured.baseURI = url;
	}

	@Test
	public void test() {
		checkRemoteProjectNotExposedInHome();
		checkRemoteProjectNotExposed();
	}

	@Test
	public void shouldIncludeRemoteResource() {
		QuerySpec querySpec = new QuerySpec(Task.class);
		querySpec.setLimit(10L);
		querySpec.includeRelation(Arrays.asList("project"));

		ResourceRepository<Task, Serializable> repository = taskClient.getRepositoryForType(Task.class);
		ResourceList<Task> tasks = repository.findAll(querySpec);
		Assert.assertNotEquals(0, tasks.size());

		for (Task task : tasks) {
			Assert.assertEquals("http://127.0.0.1:12001/task/" + task.getId(), task.getLinks().getSelf());
			ProjectProxy project = task.getProject();
			Assert.assertNotNull(project);
			shouldMapMatchingProperties(project);
			shouldStoreNoMatchingPropertiesInAttributes(project);

			Assert.assertEquals("http://127.0.0.1:12002/project/" + project.getId(), project.getLinks().getSelf());
		}
	}

	private void shouldStoreNoMatchingPropertiesInAttributes(ProjectProxy project) {
		Assert.assertEquals(project.getAttributes().size(), 2);
		Assert.assertTrue(project.getAttributes().containsKey("programme"));
		Assert.assertTrue(project.getAttributes().containsKey("businessSponsor"));
	}

	private void shouldMapMatchingProperties(ProjectProxy project) {
		Assert.assertEquals(project.getName(), "Great Project");
	}

	private void checkRemoteProjectNotExposedInHome() {
		Response response = RestAssured.given().when().get("/");
		response.then().assertThat().statusCode(200);
		String body = response.getBody().print();
		Assert.assertTrue(body, body.contains("/task"));
		Assert.assertTrue(body, !body.contains("/project"));

	}

	private void checkRemoteProjectNotExposed() {
		Response response = RestAssured.given().when().get("/project");
		response.then().assertThat().statusCode(404);
	}

	@After
	public void tearDown() {
		projectApp.close();
		taskApp.close();
	}
}
