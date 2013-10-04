package ff14repbouyomi;

import java.util.Properties;

public interface Config {
	public void loadProperties(Properties conf);

	public Properties saveProperties(Properties conf);

	public Properties defaultProperties(Properties conf);
}
