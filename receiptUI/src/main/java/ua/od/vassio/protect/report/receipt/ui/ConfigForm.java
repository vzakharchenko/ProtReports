package ua.od.vassio.protect.report.receipt.ui;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;
import ua.od.vassio.protect.report.receipt.ui.config.Config;
import ua.od.vassio.protect.report.receipt.ui.config.Configs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 23:09
 */
public class ConfigForm implements ActionListener {
    private static ConfigForm configForm = new ConfigForm();
    private static JFrame frame = new JFrame("ConfigForm");
    private JLabel installPath;
    private JTextField installIITPath;
    private JTextField certPath;
    private JTextField codePage;
    private JTextField pkPath;
    private JPasswordField password;
    private JButton OK;
    private JButton cancelButton;
    private JPanel config;

    public ConfigForm() {
        OK.addActionListener(this);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
    }

    public static void showGUI() {
        frame.setContentPane(configForm.config);
        init();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void init() {
        configForm.installIITPath.setText(Config.load(Configs.INSTALL_PATH, EndUserResourceExtractor.GetInstallPath()));
        configForm.certPath.setText(Config.load(Configs.CERT_PATH, "/data/certificates"));
        configForm.codePage.setText(Config.load(Configs.CODEPAGE, "windows-1251"));
        configForm.pkPath.setText(Config.load(Configs.PRIVATEKEY_PATH));
        configForm.password.setText(Config.load(Configs.PRIVATEKEY_PASSWORD));

        //  Config.load(Configs.INSTALL_PATH,System.getProperty("java.io.tmpdir"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Config.save(Configs.INSTALL_PATH, configForm.installIITPath.getText());
        Config.save(Configs.CERT_PATH, configForm.certPath.getText());
        Config.save(Configs.CODEPAGE, configForm.codePage.getText());
        Config.save(Configs.PRIVATEKEY_PATH, configForm.pkPath.getText());
        Config.save(Configs.PRIVATEKEY_PASSWORD, new String(configForm.password.getPassword()));
        frame.setVisible(false);
    }
}
