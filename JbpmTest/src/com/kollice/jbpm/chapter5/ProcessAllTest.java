package com.kollice.jbpm.chapter5;

import org.apache.avalon.framework.activity.Executable;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.jbpm.test.JbpmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcessAllTest extends JbpmTestCase {
	private String deploymentId;
	private static final String PATH = "com/kollice/jbpm/chapter5/kollice.jpdl.xml";

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
		ProcessInstance processInstance = executionService
				.startProcessInstanceByKey("kollice");
		String pid = processInstance.getId();
		Execution executionInState = processInstance.findActiveExecutionIn("state");
		assertNotNull(executionInState);
		executionService.signalExecutionById(executionInState.getId());
		processInstance = executionService.findProcessInstanceById(pid);
		Execution executionInTask = processInstance.findActiveExecutionIn("task");
		assertNotNull(executionInTask);
		Task task = taskService.findPersonalTasks("baijianye").get(0);
		taskService.completeTask(task.getId());
		HistoryTask historyTask = historyService.createHistoryTaskQuery().taskId(task.getId()).uniqueResult();
		assertNotNull(historyTask);
		assertProcessInstanceEnded(pid);
		HistoryProcessInstance historyProcessInstance = historyService.createHistoryProcessInstanceQuery().processInstanceId(pid).uniqueResult();
		assertNotNull(historyProcessInstance);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
