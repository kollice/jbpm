package com.kollice.jbpm.chapter5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryProcessInstanceQuery;
import org.jbpm.test.JbpmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcessEngineTest extends JbpmTestCase {
	private String deploymentId;
	private static final String PATH = "com/kollice/jbpm/chapter5/MyProcess.jpdl.xml";

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

		Map parMap = new HashMap();
		parMap.put("key1", "value1");
		parMap.put("key2", "value2");
		ProcessInstance processInstance = executionService
				.startProcessInstanceByKey("kollice", parMap, "baijy0001");
		String pidString = processInstance.getId();

		Execution execution = processInstance.findActiveExecutionIn("state1");
		executionService.signalExecutionById(execution.getId());

		List<HistoryProcessInstance> historyProcessInstances = historyService
				.createHistoryProcessInstanceQuery()
				.processDefinitionId("kollice-1")
				.orderDesc(HistoryProcessInstanceQuery.PROPERTY_STARTTIME)
				.list();
		List<HistoryActivityInstance> historyActivityInstances = historyService
				.createHistoryActivityInstanceQuery()
				.processDefinitionId("kollice-1").activityName("state1").list();

		assertNotNull(repositoryService);
	}
}
