package jodd.joy.server;

import jodd.joy.JoyContextListener;

public abstract class BaseServer<S> {

	protected int port = 8080;
	protected String contextPath = "/";
	protected String baseDir = ".";
	protected String webRoot = "src/main/webapp";
	protected Class<? extends JoyContextListener> servletContextListener = JoyContextListener.class;

	/**
	 * Sets a server port. Default value is 8080.
	 */
	public S setPort(int port) {
		this.port = port;
		return _this();
	}

	public int getPort() {
		return port;
	}

	public String getContextPath() {
		return contextPath;
	}

	/**
	 * Sets context path. Default value is "/".
	 */
	public S setContextPath(String contextPath) {
		this.contextPath = contextPath;
		return _this();
	}

	public String getBaseDir() {
		return baseDir;
	}

	public S setBaseDir(String baseDir) {
		this.baseDir = baseDir;
		return _this();
	}

	public Class<? extends JoyContextListener> getServletContextListener() {
		return servletContextListener;
	}

	/**
	 * Defines servlet context implementation. By default it's JoyContextListener.
	 */
	public S setServletContextListener(Class<? extends JoyContextListener> servletContextListener) {
		this.servletContextListener = servletContextListener;
		return _this();
	}

	public String getWebRoot() {
		return webRoot;
	}

	/**
	 * Defines a web root.
	 */
	public S setWebRoot(String webRoot) {
		this.webRoot = webRoot;
		return _this();
	}

	/**
	 * Starts a server.
	 */
	public abstract void start();

	/**
	 * Utility method to avoid cast in fluent methods.
	 */
	protected S _this() {
		return (S) this;
	}


}
