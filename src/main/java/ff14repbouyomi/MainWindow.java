package ff14repbouyomi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

/**
 * メインのJFrameを起動するUIクラス
 */
public class MainWindow extends JFrame{

    private JTextField textFieldLogFolder = new JTextField();

    private final BouyomiChan bouyomiChan;

    public MainWindow() {
        super();
        this.bouyomiChan = BouyomiChan.get();
        textFieldLogFolder.setText(bouyomiChan.getOption().logFolderPath);
    }

    public void showMainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 400, 50);
        setTitle("FF14Rep棒読みちゃん");
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        add(textFieldLogFolder, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        add(panelButtons, BorderLayout.LINE_END);
        panelButtons.setLayout(new BorderLayout());

        final JButton buttonFolderSelect = new JButton("ログフォルダ選択");
        panelButtons.add(buttonFolderSelect, BorderLayout.CENTER);
        buttonFolderSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonFolderSelect.setEnabled(false);
                selectFolder();
                buttonFolderSelect.setEnabled(true);
            }
        });

        final JButton buttonStart = new JButton("開始");
        panelButtons.add(buttonStart, BorderLayout.LINE_END);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStart.setEnabled(false);
                String loadFolderPath = textFieldLogFolder.getText();
                LogFileMonitor logFileMonitor = new LogFileMonitor(new File(loadFolderPath));
                boolean successStarted = logFileMonitor.start();
                if(successStarted) {
                    setTitle("FF14Rep棒読みちゃん:実行中...");
                    Option option = bouyomiChan.getOption();
                    Properties conf = bouyomiChan.readProperties();
                    option.logFolderPath = loadFolderPath;
                    option.saveProperties(conf);
                    bouyomiChan.saveToFile(conf);
                }
                buttonStart.setEnabled(!successStarted);
            }
        });

        setVisible(true);
    }

    private void selectFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int selected = fileChooser.showOpenDialog(this);
        if (selected == JFileChooser.APPROVE_OPTION){
            File folder = fileChooser.getSelectedFile();
            saveLogFolderPath(folder);
        }
    }

    private void saveLogFolderPath(File folder){
        textFieldLogFolder.setText(folder.getAbsolutePath());
    }
}
