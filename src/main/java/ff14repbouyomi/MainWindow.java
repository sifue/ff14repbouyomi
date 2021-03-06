package ff14repbouyomi;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Properties;

/**
 * メインのJFrameを起動するUIクラス
 */
public class MainWindow extends JFrame{

    private JTextField textFieldLogFolder = new JTextField();

    private JCheckBox checkBoxSpeakPlayerName = new JCheckBox("プレイヤー名を読み上げる");

    private JCheckBox checkBoxSpeakSayLogType = new JCheckBox("sayを読み上げる");
    private JCheckBox checkBoxSpeakEmoteLogType = new JCheckBox("emoteを読み上げる");
    private JCheckBox checkBoxSpeakYellLogType = new JCheckBox("yellを読み上げる");
    private JCheckBox checkBoxSpeakShoutLogType = new JCheckBox("shoutを読み上げる");
    private JCheckBox checkBoxSpeakLinkShellLogType = new JCheckBox("linkshellを読み上げる");
    private JCheckBox checkBoxSpeakFreeCompanyLogType = new JCheckBox("freecompanyを読み上げる");
    private JCheckBox checkBoxSpeakPartyLogType = new JCheckBox("partyを読み上げる");
    private JCheckBox checkBoxSpeakTellLogType = new JCheckBox("tellを読み上げる");
    private JCheckBox checkBoxSpeakOtherLogType = new JCheckBox("その他のログを読み上げる");

    private JTextField textFieldMatchText = new JTextField();

    private final BouyomiChan bouyomiChan;

    private volatile LogFileMonitor logFileMonitor;

    public MainWindow() {
        super();
        this.bouyomiChan = BouyomiChan.get();

        textFieldLogFolder.setText(bouyomiChan.getOption().logFolderPath);

        checkBoxSpeakPlayerName.setSelected(bouyomiChan.getOption().speakPlayerName);
        checkBoxSpeakSayLogType.setSelected(bouyomiChan.getOption().speakSayLogType);
        checkBoxSpeakEmoteLogType.setSelected(bouyomiChan.getOption().speakEmoteLogType);
        checkBoxSpeakYellLogType.setSelected(bouyomiChan.getOption().speakYellLogType);
        checkBoxSpeakShoutLogType.setSelected(bouyomiChan.getOption().speakShoutLogType);
        checkBoxSpeakLinkShellLogType.setSelected(bouyomiChan.getOption().speakLinkShellLogType);
        checkBoxSpeakFreeCompanyLogType.setSelected(bouyomiChan.getOption().speakFreeCompanyLogType);
        checkBoxSpeakPartyLogType.setSelected(bouyomiChan.getOption().speakPartyLogType);
        checkBoxSpeakTellLogType.setSelected(bouyomiChan.getOption().speakTellLogType);
        checkBoxSpeakOtherLogType.setSelected(bouyomiChan.getOption().speakOtherLogType);
        textFieldMatchText.setText(bouyomiChan.getOption().matchText);
    }

    public void showMainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 450, 400);
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
                logFileMonitor = new LogFileMonitor(new File(loadFolderPath));
                boolean successStarted = logFileMonitor.start();
                if(successStarted) {
                    textFieldLogFolder.setEditable(false);
                    buttonFolderSelect.setEnabled(false);
                    setTitle("FF14Rep棒読みちゃん:実行中...");
                    Option option = bouyomiChan.getOption();
                    Properties conf = bouyomiChan.readProperties();
                    option.logFolderPath = loadFolderPath;
                    option.speakPlayerName = checkBoxSpeakPlayerName.isSelected();
                    option.saveProperties(conf);
                    bouyomiChan.saveToFile(conf);
                }
                buttonStart.setEnabled(!successStarted);
            }
        });

        JPanel panelCheckBoxes = new JPanel();
        add(panelCheckBoxes, BorderLayout.PAGE_END);
        panelCheckBoxes.setLayout(new BoxLayout(panelCheckBoxes, BoxLayout.Y_AXIS));

        class SaveStateChangeListener implements ChangeListener {
            @Override
            public void stateChanged(ChangeEvent e) {
                saveState();
            }
        }
        SaveStateChangeListener saveStateChangeListener = new SaveStateChangeListener();
        panelCheckBoxes.add(checkBoxSpeakPlayerName);
        checkBoxSpeakPlayerName.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakSayLogType);
        checkBoxSpeakSayLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakEmoteLogType);
        checkBoxSpeakEmoteLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakYellLogType);
        checkBoxSpeakYellLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakShoutLogType);
        checkBoxSpeakShoutLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakLinkShellLogType);
        checkBoxSpeakLinkShellLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakFreeCompanyLogType);
        checkBoxSpeakFreeCompanyLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakPartyLogType);
        checkBoxSpeakPartyLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakTellLogType);
        checkBoxSpeakTellLogType.addChangeListener(saveStateChangeListener);
        panelCheckBoxes.add(checkBoxSpeakOtherLogType);
        checkBoxSpeakOtherLogType.addChangeListener(saveStateChangeListener);

        JPanel panelMatchText = new JPanel();
        panelMatchText.setLayout(new BorderLayout());

        JLabel labelMatchText = new JLabel("含むテキスト: ");
        panelMatchText.add(labelMatchText, BorderLayout.WEST);

        panelMatchText.add(textFieldMatchText, BorderLayout.CENTER);
        textFieldMatchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                saveState();
            }
        });

        panelCheckBoxes.add(panelMatchText);


        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                logFileMonitor.shutdown();
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private void saveState() {
        Option option = bouyomiChan.getOption();
        Properties conf = bouyomiChan.readProperties();
        option.speakPlayerName = checkBoxSpeakPlayerName.isSelected();
        option.speakSayLogType = checkBoxSpeakSayLogType.isSelected();
        option.speakEmoteLogType = checkBoxSpeakEmoteLogType.isSelected();
        option.speakYellLogType = checkBoxSpeakYellLogType.isSelected();
        option.speakShoutLogType = checkBoxSpeakShoutLogType.isSelected();
        option.speakLinkShellLogType = checkBoxSpeakLinkShellLogType.isSelected();
        option.speakFreeCompanyLogType = checkBoxSpeakFreeCompanyLogType.isSelected();
        option.speakPartyLogType = checkBoxSpeakPartyLogType.isSelected();
        option.speakTellLogType = checkBoxSpeakTellLogType.isSelected();
        option.speakOtherLogType = checkBoxSpeakOtherLogType.isSelected();
        option.matchText = textFieldMatchText.getText();
        option.saveProperties(conf);
        bouyomiChan.saveToFile(conf);
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
