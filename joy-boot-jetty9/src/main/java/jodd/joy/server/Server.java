package jodd.joy.server;

import jodd.joy.server.jetty.JettyServer;

public class Server extends BaseServer<Server> {

	public static Server create() {
		return new Server();
	}

	JettyServer jettyServer;

	@Override
	public void start() {
		jettyServer = new JettyServer(this);
		try {
			jettyServer.start();
		} catch (Exception ex) {
			throw new ServerException("Error starting Jetty", ex);
		}
	}

}
