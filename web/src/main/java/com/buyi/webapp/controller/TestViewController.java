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

	// public static void main(String[] args) {
	// Reader input = new StringReader("呢绒沙发布艺上传商城");
	// // 智能分词关闭（对分词的精度影响很大）
	// IKSegmentation iks = new IKSegmentation(input, false);
	// Lexeme lexeme = null;
	// StringBuilder sb = new StringBuilder();
	//
	// try {
	// while ((lexeme = iks.next()) != null) {
	// sb.append(lexeme.getLexemeText()).append("|");
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// System.out.println(sb.toString());
	//
	// }

}
