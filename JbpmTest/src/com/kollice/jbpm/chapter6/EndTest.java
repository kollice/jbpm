package com.kollice.jbpm.chapter6;

import org.jbpm.api.ProcessInstance;
import org.jbpm.test.JbpmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EndTest extends JbpmTestCase {
	private String deploymentId = "";
	private static final String PATH = "com/kollice/jbpm/chapter6/EndTest.jpdl.xml";

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		deploymentId = repositoryService.createDeployment().addResourceFromClasspath(PATH).deploy();
	}

	@After
	protected void tearDown() throws Exception {
		repositoryService.deleteDeploymentCascade(deploymentId);
		super.tearDown();
	}

	@Test
	public void test() {
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("EndTest");
//		String state = "200";
//		String state = "500";
		String state = "400";
		processInstance = executionService.signalExecutionById(processInstance.findActiveExecutionIn("get return code").getId(),state);
		assertEquals(true,processInstance.isEnded());
//		assertEquals("ended",processInstance.getState());
//		assertEquals("error",processInstance.getState());
		assertEquals("cancel",processInstance.getState());
	}

}
