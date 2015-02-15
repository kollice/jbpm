package com.kollice.jbpm.chapter6;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class MyDecisionHandler implements DecisionHandler {

	@Override
	public String decide(OpenExecution execution) {
		String contentString = execution.getVariable("content").toString();
		int content = Integer.parseInt(contentString);
		if (content == 1) {
			return "good";
		} else if (content == 2) {
			return "bad";
		} else {
			return "ugly";
		}
	}
}
