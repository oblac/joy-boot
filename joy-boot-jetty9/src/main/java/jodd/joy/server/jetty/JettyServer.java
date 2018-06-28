package jodd.joy.server.jetty;

import jodd.joy.JoyContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

public class JettyServer {

	private final jodd.joy.server.Server server;

	public JettyServer(final jodd.joy.server.Server server) {
		this.server = server;
	}

	protected Server jetty;

	public void start() throws Exception {
		// base dir
		System.setProperty("jetty.base", new File(server.getBaseDir()).getAbsolutePath());

		// server
		jetty = new Server();

		// connector and port
		ServerConnector connector = new ServerConnector(jetty);
		connector.setPort(server.getPort());
		jetty.addConnector(connector);

		// annotation scanning
		Configuration.ClassList classlist = Configuration.ClassList.setServerDefault(jetty);
		classlist.addBefore(
			"org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
			"org.eclipse.jetty.annotations.AnnotationConfiguration");


		// context
		final WebAppContext context = new WebAppContext();

		// paths
		context.setContextPath(server.getContextPath());
		final String resourceBase = server.getWebRoot();
		context.setResourceBase(resourceBase);
		context.setDescriptor(null);

		// jar pattern
		context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
			".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$" );

		// classloader
		context.setClassLoader(
			Thread.currentThread().getContextClassLoader()
		);

		// servlets & JSP
		// Default Servlet (always last, always named "default")
		ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
		holderDefault.setInitParameter("resourceBase", resourceBase);
		holderDefault.setInitParameter("dirAllowed", "false");
		context.addServlet(holderDefault, "/");

		// add listener
		context.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
			@Override
			public void lifeCycleStarting(LifeCycle event) {

				ContextHandler.Context ctx = context.getServletContext();
				ctx.setExtendedListenerTypes(true);
				JoyContextListener.registerInServletContext(context.getServletContext(), server.getServletContextListener());
			}
		});


		// bind context to server
		jetty.setHandler(context);

		// start and wait

		jetty.start();
		jetty.join();

		stop();
	}

	void stop() throws Exception {
		jetty.stop();
		jetty.destroy();
	}
}
