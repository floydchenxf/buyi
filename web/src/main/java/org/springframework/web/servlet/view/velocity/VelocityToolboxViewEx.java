package org.springframework.web.servlet.view.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.Toolbox;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.config.FactoryConfiguration;
import org.apache.velocity.tools.view.ServletUtils;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class VelocityToolboxViewEx extends VelocityToolboxView implements BeanFactoryAware {
	public static final String VELOCITY_TOOL_KEY = "^VELOCITY_TOOL_KEY$";

	private volatile ToolboxFactory toolboxFactory;
	private Toolbox appToolbox;

	private BeanFactory beanFactory;

	@Override
	@SuppressWarnings("rawtypes")
	protected Context createVelocityContext(Map model, HttpServletRequest request, HttpServletResponse response) {

		// Create a ChainedContext instance.
		ViewToolContext velocityContext = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());

		velocityContext.putAll(model);
		velocityContext.putToolProperty("beanFactory", beanFactory);
		velocityContext.put("velocityContext", velocityContext);

		// Load a Velocity Tools toolbox, if necessary.
		if (getToolboxConfigLocation() != null) {
			Toolbox box;
			if ((box = getToolbox(request, Scope.REQUEST)) != null)
				velocityContext.addToolbox(box);
			if ((box = getToolbox(request, Scope.SESSION)) != null)
				velocityContext.addToolbox(box);
			if ((box = getToolbox(request, Scope.APPLICATION)) != null)
				velocityContext.addToolbox(box);
		}

		return velocityContext;
	}

	private Toolbox getToolbox(HttpServletRequest request, String scope) {
		// singleton
		ToolboxFactory factory = toolboxFactory;
		if (factory == null) {
			synchronized (this) {
				if (toolboxFactory == null) {
					FactoryConfiguration configuration = ServletUtils.getConfiguration(getToolboxConfigLocation(), getServletContext());
					factory = toolboxFactory = configuration.createFactory();
					appToolbox = factory.createToolbox(Scope.APPLICATION);
				} else
					factory = toolboxFactory;
			}
		}

		if (!factory.hasTools(scope))
			return null;

		if (Scope.REQUEST.equals(scope)) {
			Toolbox box = (Toolbox) request.getAttribute(VELOCITY_TOOL_KEY);
			if (box == null) {
				box = factory.createToolbox(scope);
				request.setAttribute(VELOCITY_TOOL_KEY, box);
			}
			return box;
		}
		if (Scope.SESSION.equals(scope)) {
			// session may be created here
			HttpSession session = request.getSession();
			Toolbox box = (Toolbox) session.getAttribute(VELOCITY_TOOL_KEY);
			if (box == null) {
				box = factory.createToolbox(scope);
				session.setAttribute(VELOCITY_TOOL_KEY, box);
				return box;
			}
		}
		if (Scope.APPLICATION.equals(scope)) {
			return appToolbox;
		}

		return null;
	}

	/**
	 * velocity tool 2.x depracted init(Object) method
	 */
	@Override
	protected void initTool(Object tool, Context velocityContext) throws Exception {
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
