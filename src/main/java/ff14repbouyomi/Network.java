package ff14repbouyomi;

import java.net.InetSocketAddress;
import java.util.Properties;

public class Network implements Config {
	private String host = "localhost";
	private Integer port = 50001;
	private String keyHost = "host";
	private String keyPort = "port";

	public InetSocketAddress soketAddress() {
		return new InetSocketAddress(host, port);
	}

	@Override
	public void loadProperties(Properties conf) {
		host = conf.getProperty(keyHost, "localhost");

		String s = conf.getProperty(keyPort);
		try {
			port = new Integer(s);
		} catch (NumberFormatException e) {
			port = 50001;
		}
	}

	@Override
	public Properties saveProperties(Properties conf) {
		conf.setProperty(keyHost, host);
		conf.setProperty(keyPort, port.toString());
		return conf;
	}

	@Override
	public Properties defaultProperties(Properties conf) {
		return saveProperties(conf);
	}
}