package jodd.joy.server.tomcat;

import jodd.joy.server.Server;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class TomcatServer {

	private final Server server;

	public TomcatServer(final Server server) {
		this.server = server;
	}

	protected Tomcat tomcat;

	public void start() throws LifecycleException, ServletException {
		System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");

		tomcat = new Tomcat();

		tomcat.setPort(server.getPort());

		tomcat.setBaseDir(new File(server.getBaseDir()).getAbsolutePath());

		final String docBase = "src/main/webapp";

		Context context = tomcat.addWebapp(
			server.getContextPath(),
			new File(docBase).getAbsolutePath());

		context.addApplicationListener(server.getServletContextListener().getName());

		tomcat.start();

		tomcat.getServer().await();

		stop();
	}

	void stop() throws LifecycleException {
		tomcat.stop();
		tomcat.destroy();
	}
}
