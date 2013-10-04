package ff14repbouyomi;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogFileMonitor {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final File logFolder;

    private final BouyomiChan bouyomiChan;

    public LogFileMonitor(File logFolder) {
        this.logFolder = logFolder;
        this.bouyomiChan = BouyomiChan.get();
    }

    /**
     * ログファイル監視を開始することができればtrue、そうでなければfalseを返す
     * @return
     */
    public boolean start(){
        if(!logFolder.isDirectory()) {
            JOptionPane.showMessageDialog(null, "フォルダを選択してください", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    monitor();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }, 250, 250, TimeUnit.MILLISECONDS);
        return true;
    }

    private final Map<File, Long> fileSizeMap = new ConcurrentHashMap<File, Long>();

    private synchronized void monitor(){
        File[] files = logFolder.listFiles();
        for (File file : files) {
            if(file.isDirectory()) continue;
            if(fileSizeMap.containsKey(file)){
                if(isFileChanged(file)) {
                    // 最後のサイズをとりおいて、先に更新して、今のところまで読む
                    long lastPosition = fileSizeMap.get(file).longValue();
                    long nextPosition = file.length();
                    fileSizeMap.put(file, Long.valueOf(nextPosition)); // 先に更新
                    sendToBouyomi(file, lastPosition, nextPosition);
                }
            } else {
                fileSizeMap.put(file, Long.valueOf(file.length()));
            }
        }
    }

    private void sendToBouyomi(File file, long lastPosition, long nextPosition) {
        if(lastPosition >= nextPosition) return; // もしファイルサイズが減ってたらなにもしない
        if(!file.getAbsolutePath().endsWith(".csv")) return; //csv以外だったら何もしない

        try {
            FileInputStream fis = new FileInputStream(file);
            fis.skip(lastPosition);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "Shift_JIS"));
            String line;
            while ((line = br.readLine()) != null) {
                sendOneLineToBouyomi(line);
            }
            br.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void sendOneLineToBouyomi(String line) {
        String[] splitted = line.split(",");
        for (int i = 0; i < splitted.length ; i++) {
            splitted[i] = splitted[i].replaceAll("\"", "");

        }
        String type = splitted[0];
        String user = splitted[1];
        String content = splitted[2];

        JsonChat chat = new JsonChat();
        chat.text = bouyomiChan.getOption().speakPlayerName ? content + "＠" + user : content;
        bouyomiChan.post(JsonChat.toJson(chat));
        System.out.println(chat.text);
    }

    private boolean isFileChanged(File file) {
        return fileSizeMap.get(file).longValue() != file.length();
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

}
