package ff14repbouyomi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class BoyomiChanData implements Config {
	public Short command = 0x001;
	public Short speed = new Short("-1");
	public Short tone = new Short("-1");
	public Short volume = new Short("-1");
	public Short voice = new Short("0");
	public String charsetname = "UTF-8";

	public void post(String s, Network nc) throws IOException {
		byte[] b;
		b = s.getBytes(charsetname);

		Socket sock = new Socket();
		sock.connect(nc.soketAddress());
		DataOutputStream output = new DataOutputStream(sock.getOutputStream());
		output.writeShort(Short.reverseBytes(command));
		output.writeShort(Short.reverseBytes(speed));
		output.writeShort(Short.reverseBytes(tone));
		output.writeShort(Short.reverseBytes(volume));
		output.writeShort(Short.reverseBytes(voice));
		output.write(0);
		output.writeInt(Integer.reverseBytes(b.length));
		output.write(b, 0, b.length);
		output.flush();
		output.close();
		sock.close();

	}

	@Override
	public void loadProperties(Properties conf) {
		String s;
		s = conf.getProperty("speed", "-1");
		try {
			speed = new Short(s);
		} catch (NumberFormatException e) {
			speed = new Short("-1");
		}

		s = conf.getProperty("tone", "-1");
		try {
			tone = new Short(s);
		} catch (NumberFormatException e) {
			tone = new Short("-1");
		}

		s = conf.getProperty("volume", "-1");
		try {
			volume = new Short(s);
		} catch (NumberFormatException e) {
			volume = new Short("-1");
		}

		s = conf.getProperty("voice", "0");
		try {
			voice = new Short(s);
		} catch (NumberFormatException e) {
			voice = new Short("0");
		}

	}

	@Override
	public Properties saveProperties(Properties conf) {
		conf.setProperty("speed", speed.toString());
		conf.setProperty("tone", tone.toString());
		conf.setProperty("volume", volume.toString());
		conf.setProperty("voice", voice.toString());
		return conf;
	}

	@Override
	public Properties defaultProperties(Properties conf) {
		return saveProperties(conf);
	}
}