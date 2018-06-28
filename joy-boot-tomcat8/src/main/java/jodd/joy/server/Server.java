package jodd.joy.server;

import jodd.joy.server.tomcat.TomcatServer;

public class Server extends BaseServer<Server> {

	public static Server create() {
		return new Server();
	}

	TomcatServer tomcatServer;

	@Override
	public void start() {
		tomcatServer = new TomcatServer(this);
		try {
			tomcatServer.start();
		} catch (Exception ex) {
			throw new ServerException("Error starting Tomcat", ex);
		}
	}

}
