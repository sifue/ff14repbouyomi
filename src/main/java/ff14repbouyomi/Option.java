package ff14repbouyomi;

import java.util.Properties;

public class Option implements Config {
	public volatile Boolean debug = false;
	private final String debagKey = "debug";
	public volatile Boolean speakPlayerName = true;
	private final String speakPlayerNameKey = "SpeakPlayerName";
	public volatile Integer reloadProperties = 1 * 1000;
	private final String reloadPropertiesKey = "ReloadProperties";
    public volatile String logFolderPath = "";
    private final String logFolderPathKey = "LogFolderPath";


	public void debugPrint(Object o) {
		if (debug) {
			System.out.println(o);
		}
	}

	@Override
	public void loadProperties(Properties conf) {
		String s;
		s = conf.getProperty(debagKey);
		try {
			debug = new Boolean(s);
		} catch (NumberFormatException e) {
			debug = false;
		}
		s = conf.getProperty(speakPlayerNameKey);
		try {
			speakPlayerName = new Boolean(s);
		} catch (NumberFormatException e) {
			speakPlayerName = true;
		}

		s = conf.getProperty(reloadPropertiesKey);
		try {
			reloadProperties = new Integer(s);
		} catch (NumberFormatException e) {
			reloadProperties = 1 * 1000;
		}

        s = conf.getProperty(logFolderPathKey);
        logFolderPath = s;
	}

	@Override
	public Properties saveProperties(Properties conf) {
		conf.setProperty(debagKey, debug.toString());
		conf.setProperty(speakPlayerNameKey, speakPlayerName.toString());
		conf.setProperty(reloadPropertiesKey, reloadProperties.toString());
        conf.setProperty(logFolderPathKey, logFolderPath);
		return conf;
	}

	@Override
	public Properties defaultProperties(Properties conf) {
		return saveProperties(conf);
	}
}
