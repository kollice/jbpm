package com.kollice.jbpm.chapter6;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.ProcessInstance;
import org.jbpm.test.JbpmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConditionDecisionTest extends JbpmTestCase {
	private String deploymentId = null;
	private static final String PATH = "com/kollice/jbpm/chapter6/ConditionDicision.jpdl.xml";

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
		Map param = new HashMap();
		param.put("content", "good");
		ProcessInstance processInstance = executionService
				.startProcessInstanceByKey("ConditionDicision", param);
		assertTrue(processInstance.isActive("submit"));
	}
}
