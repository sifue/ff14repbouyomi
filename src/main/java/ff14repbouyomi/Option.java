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

    public volatile Boolean speakSayLogType = true;
    private final String speakSayLogTypeKey = "SpeakSayLogType";
    public volatile Boolean speakEmoteLogType = true;
    private final String speakEmoteLogTypeKey = "SpeakEmoteLogType";
    public volatile Boolean speakYellLogType = true;
    private final String speakYellLogTypeKey = "SpeakYellLogType";
    public volatile Boolean speakShoutLogType = true;
    private final String speakShoutLogTypeKey = "SpeakShoutLogType";
    public volatile Boolean speakLinkShellLogType = true;
    private final String speakLinkShellLogTypeKey = "SpeakLinkShellLogType";
    public volatile Boolean speakFreeCompanyLogType = true;
    private final String speakFreeCompanyLogTypeKey = "SpeakFreeCompanyLogType";
    public volatile Boolean speakPartyLogType = true;
    private final String speakPartyLogTypeKey = "SpeakPartyLogType";
    public volatile Boolean speakTellLogType = true;
    private final String speakTellLogTypeKey = "SpeakTellLogType";
    public volatile Boolean speakOtherLogType = true;
    private final String speakOtherLogTypeKey = "SpeakOtherLogType";


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


        s = conf.getProperty(speakSayLogTypeKey);
        try {
            speakSayLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakSayLogType = true;
        }

        s = conf.getProperty(speakEmoteLogTypeKey);
        try {
            speakEmoteLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakEmoteLogType = true;
        }

        s = conf.getProperty(speakYellLogTypeKey);
        try {
            speakYellLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakYellLogType = true;
        }

        s = conf.getProperty(speakShoutLogTypeKey);
        try {
            speakShoutLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakShoutLogType = true;
        }

        s = conf.getProperty(speakLinkShellLogTypeKey);
        try {
            speakLinkShellLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakLinkShellLogType = true;
        }

        s = conf.getProperty(speakFreeCompanyLogTypeKey);
        try {
            speakFreeCompanyLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakFreeCompanyLogType = true;
        }

        s = conf.getProperty(speakPartyLogTypeKey);
        try {
            speakPartyLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakPartyLogType = true;
        }

        s = conf.getProperty(speakTellLogTypeKey);
        try {
            speakTellLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakTellLogType = true;
        }

        s = conf.getProperty(speakOtherLogTypeKey);
        try {
            speakOtherLogType = new Boolean(s);
        } catch (NumberFormatException e) {
            speakOtherLogType = true;
        }
    }

	@Override
	public Properties saveProperties(Properties conf) {
		conf.setProperty(debagKey, debug.toString());
		conf.setProperty(speakPlayerNameKey, speakPlayerName.toString());
		conf.setProperty(reloadPropertiesKey, reloadProperties.toString());
        conf.setProperty(logFolderPathKey, logFolderPath);

        conf.setProperty(speakSayLogTypeKey, speakSayLogType.toString());
        conf.setProperty(speakEmoteLogTypeKey, speakEmoteLogType.toString());
        conf.setProperty(speakYellLogTypeKey, speakYellLogType.toString());
        conf.setProperty(speakShoutLogTypeKey, speakShoutLogType.toString());
        conf.setProperty(speakLinkShellLogTypeKey, speakLinkShellLogType.toString());
        conf.setProperty(speakFreeCompanyLogTypeKey, speakFreeCompanyLogType.toString());
        conf.setProperty(speakPartyLogTypeKey, speakPartyLogType.toString());
        conf.setProperty(speakTellLogTypeKey, speakTellLogType.toString());
        conf.setProperty(speakOtherLogTypeKey, speakOtherLogType.toString());

		return conf;
	}

	@Override
	public Properties defaultProperties(Properties conf) {
		return saveProperties(conf);
	}
}
