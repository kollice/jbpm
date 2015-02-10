package com.kollice.jbpm.chapter5;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.test.JbpmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcessEngineTest extends JbpmTestCase {
	private String deploymentId;
	private static final String PATH = "";

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		deploymentId = repositoryService.createDeployment()
				.addResourceFromClasspath(PATH).deploy();
	}

	@After
	protected void tearDown() throws Exception {
		repositoryService.deleteDeploymentCascade(deploymentId);
		super.tearDown();
	}

	@Test
	public void test() {
		RepositoryService repositoryService = processEngine
				.getRepositoryService();
		ExecutionService executionService = processEngine.getExecutionService();
		TaskService taskService = processEngine.getTaskService();
		HistoryService historyService = processEngine.getHistoryService();
		ManagementService managementService = processEngine
				.getManagementService();

		assertNotNull(repositoryService);
		assertNotNull(executionService);
		assertNotNull(taskService);
		assertNotNull(historyService);
		assertNotNull(managementService);

		String deploymentId = repositoryService
				.createDeployment()
				.addResourceFromClasspath(
						"com/kollice/jbpm/chapter5/MyProcess.jpdl.xml")
				.deploy();
		
		

		repositoryService.deleteDeployment(deploymentId);

	}

}
