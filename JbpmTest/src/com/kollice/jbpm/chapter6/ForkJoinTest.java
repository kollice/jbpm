package com.kollice.jbpm.chapter6;

import java.util.HashSet;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.jbpm.test.JbpmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ForkJoinTest extends JbpmTestCase {
	private String deploymentId;
	private static final String PATH = "com/kollice/jbpm/chapter6/ForkJoin.jpdl.xml";

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
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("ForkJoin");
		HashSet activties = new HashSet<>();
		activties.add("send invoice");
		activties.add("load trunk");
		activties.add("print doc");
		assertEquals(activties, processInstance.findActiveActivityNames());
		
		Execution execution =  processInstance.findActiveExecutionIn("send invoice");
		processInstance = executionService.signalExecutionById(execution.getId());
		activties.remove("send invoice");
		assertNotNull(processInstance.findActiveExecutionIn("load trunk"));
		assertNotNull(processInstance.findActiveExecutionIn("print doc"));
		
		Execution execution2 =  processInstance.findActiveExecutionIn("load trunk");
		processInstance = executionService.signalExecutionById(execution2.getId());
		activties.remove("load trunk");
		Execution execution3 =  processInstance.findActiveExecutionIn("print doc");
		processInstance = executionService.signalExecutionById(execution3.getId());
		activties.remove("print doc");
		
		activties.add("drive truck to destination");
		assertEquals(activties, processInstance.findActiveActivityNames());
		assertNotNull(processInstance.findActiveExecutionIn("drive truck to destination"));
		
		String pidString = processInstance.findActiveExecutionIn("drive truck to destination").getId();
		processInstance = executionService.signalExecutionById(processInstance.findActiveExecutionIn("drive truck to destination").getId());
		assertNull(executionService.findExecutionById(pidString));
	}

}
