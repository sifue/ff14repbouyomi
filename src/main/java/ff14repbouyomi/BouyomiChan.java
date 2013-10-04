package ff14repbouyomi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BouyomiChan extends Thread {
	public static BouyomiChan get() {
		return BouyomiChan.instance;
	}

	public static void post(String s) {
		if (Thread.State.NEW == instance.getState()) {
			instance.setDaemon(true);
			instance.start();
		}
		queue.offer(s);
	}

	private final File propertiesFile = new File("ff14repbouyomi.properties");
	private final Network network = new Network();
	private final BoyomiChanData boyomichanData = new BoyomiChanData();
	private final Option option = new Option();
	private final ArrayList<Config> configs;

	private static Queue<String> queue = new ConcurrentLinkedQueue<String>();

	private static final BouyomiChan instance = new BouyomiChan();

	private BouyomiChan() {
		configs = new ArrayList<Config>();
		configs.add(network);
		configs.add(boyomichanData);
		configs.add(option);
		this.readAndSaveConfig();
	}

	public void readAndSaveConfig() {
        Properties conf = readProperties();
        saveToFile(conf);
    }

    public Properties readProperties() {
        Properties def_conf = new Properties();
        for (Iterator<Config> i = configs.iterator(); i.hasNext();) {
            i.next().defaultProperties(def_conf);
        }
        Properties conf = new Properties(def_conf);
        try {
            // option.debugPrint(file.getAbsolutePath() + " Reading...");

            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(propertiesFile));
            conf.load(bis);
            bis.close();

            for (Iterator<Config> i = configs.iterator(); i.hasNext();) {
                i.next().loadProperties(conf);
            }

            for (Iterator<Config> i = configs.iterator(); i.hasNext();) {
                i.next().saveProperties(conf);
            }

            // option.debugPrint(file.getAbsolutePath() + " Reading success.");
        } catch (IOException e) {
            // option.debugPrint(file.getAbsolutePath() + " Reading failure.");
        }
        return conf;
    }

    public void saveToFile(Properties conf) {
        try {
            option.debugPrint(propertiesFile.getAbsolutePath() + " Writing...");

            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(propertiesFile));
            conf.store(bos, "");
            bos.flush();
            bos.close();

            option.debugPrint(propertiesFile.getAbsolutePath()
                    + " Writing success.");
        } catch (IOException e) {
            option.debugPrint(propertiesFile.getAbsolutePath()
                    + " Writing failure.");
        }
    }

    @Override
	public void run() {
		Date date = new Date();
		while (true) {
			try {
				while (queue.isEmpty())
					Thread.sleep(100);

				if (option.reloadProperties > 0) {
					long n = new Date().getTime() - date.getTime();
					if (n > option.reloadProperties) {
						readAndSaveConfig();
						date = new Date();
					}
				}

				String json = queue.poll();
				if (json.charAt(0) != '{')
					continue;
				option.debugPrint(json);

				JsonChat jc = JsonChat.build(json);

				if (jc.translate != null) {
					StringBuffer sb = new StringBuffer();
					if (jc.translate.startsWith("chat")) {

						if (option.speakPlayerName) {
							sb.append(jc.using.get(0));
							sb.append("  ");
						}
						sb.append(jc.using.get(1));
						boyomichanData.post(sb.toString(), network);
					}
				} else if (jc.text != null) {
					String str = jc.text.replaceAll("\\u00a7.", "");
					boyomichanData.post(str, network);
				}

			} catch (IOException e) {
				System.out.println("Can not connect to BouyomiChan.");
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

    public Option getOption() {
        return option;
    }
}
