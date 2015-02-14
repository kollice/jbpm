package com.kollice.jbpm.chapter6;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.jbpm.test.JbpmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StateChoiceTest extends JbpmTestCase {
	private String deploymentId;
	private static final String PATH = "com/kollice/jbpm/chapter6/StateChoice.jpdl.xml";

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
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("StateChoice");
		Execution execution = processInstance.findActiveExecutionIn("wait for response");
		processInstance = executionService.signalExecutionById(execution.getId(),"accept");
		assertTrue(processInstance.isActive("submit"));
		
	}

}
