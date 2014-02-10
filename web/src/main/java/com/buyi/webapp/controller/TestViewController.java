package com.buyi.webapp.controller;

import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.springframework.stereotype.Controller;

@Controller("testController")
public class TestViewController implements ViewController {

	@Override
	public String getTemplate() {
		return "/controller/hello.vm";
	}

	@Override
	public Context fillData(Map<String, Object> params) {
		Context context = new VelocityContext();
		for (Map.Entry<String, Object> ent : params.entrySet()) {
			context.put(ent.getKey(), ent.getValue());
		}
		return context;
	}

}
