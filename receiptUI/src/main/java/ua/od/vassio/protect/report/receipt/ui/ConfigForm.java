package ua.od.vassio.protect.report.receipt.ui;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;
import org.apache.commons.lang3.StringUtils;
import ua.od.vassio.protect.report.cert.acsk.ACSKDownloaderHelper;
import ua.od.vassio.protect.report.cert.own.AcskiddOwnCertFactory;
import ua.od.vassio.protect.report.core.system.IITHelper;
import ua.od.vassio.protect.report.core.system.storage.Storage;
import ua.od.vassio.protect.report.core.system.storage.StorageFactory;
import ua.od.vassio.protect.report.receipt.ui.config.Config;
import ua.od.vassio.protect.report.receipt.ui.config.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 23:09
 */
public class ConfigForm extends Component implements ActionListener {
    private static ConfigForm configForm = new ConfigForm();
    private static JFrame frame = new JFrame("Настройки");
    private JLabel installPath;
    private JTextField installIITPath;
    private JTextField certPath;
    private JTextField codePage;
    private JTextField pkPath;
    private JPasswordField password;
    private JButton OK;
    private JButton cancelButton;
    private JPanel config;
    private JButton setupCert;
    private JButton selectDirProtect;
    private JLabel edrpo;
    private JTextField inn;
    private JButton owncert;
    private JButton btnkeypath;
    private JButton btnSelectCertPath;

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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void init() {
        configForm.installIITPath.setText(Config.load(Configs.INSTALL_PATH, EndUserResourceExtractor.GetInstallPath()));
        try {
            Storage storage = DialogMessages.showProgressPane(frame, "Загружаем информациию о Хранилище", "Загружаем информациию о Хранилище...", 0, 5, new ProgressRunnable<Storage>() {
                @Override
                protected Storage executeLogic() throws Exception {
                    increment();
                    IITHelper.getInstance().reInstall(configForm.installIITPath.getText());
                    increment(2);
                    Storage storage = StorageFactory.loadStorage();
                    increment(2);
                    return storage;
                }
            });

            configForm.certPath.setText(Config.load(Configs.CERT_PATH, storage.getFileStorage().getPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        configForm.codePage.setText(Config.load(Configs.CODEPAGE, "windows-1251"));
        configForm.pkPath.setText(Config.load(Configs.PRIVATEKEY_PATH));
        configForm.password.setText(Config.load(Configs.PRIVATEKEY_PASSWORD));
        configForm.inn.setText(Config.load(Configs.INN));

        configForm.selectDirProtect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);
                File curdir = new File(configForm.installIITPath.getText());
                if (curdir.exists()) {
                    fc.setCurrentDirectory(curdir);
                }

                int returnVal = fc.showOpenDialog(configForm);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.exists() && file.isDirectory()) {
                        configForm.installIITPath.setText(file.getAbsolutePath());
                    }
                }
            }
        });

        configForm.btnSelectCertPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);
                File curdir = new File(configForm.installIITPath.getText());
                if (curdir.exists()) {
                    fc.setCurrentDirectory(curdir);
                }
                int returnVal = fc.showOpenDialog(configForm);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.exists() && file.isDirectory()) {
                        configForm.certPath.setText(file.getAbsolutePath());
                    }
                }
            }
        });

        configForm.setupCert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File directory = new File(configForm.certPath.getText());
                    if ((!directory.exists()) || (!directory.isDirectory())) {
                        DialogMessages.showErrorPane("Ошибка при установке сертификатов", "введен пустой или неверный путь");
                        return;
                    }
                    ACSKDownloaderHelper.downloadFromAcskidd(new File(configForm.certPath.getText()));
                    DialogMessages.showInfoPane("Установка сертификатов", "Установка сертификатов АЦСК успешно выполнена");
                } catch (Exception ex) {
                    DialogMessages.showErrorPane("Ошибка при установке сертификатов", ex.getMessage());
                }
            }
        });

        configForm.owncert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtils.isEmpty(configForm.inn.getText())) {
                    DialogMessages.showInfoPane("Ошибка при установке личного сертификата", "введен пустой ИНН");
                    return;
                }
                File directory = new File(configForm.certPath.getText());
                if ((!directory.exists()) || (!directory.isDirectory())) {
                    DialogMessages.showErrorPane("Ошибка при установке личного сертификата", "введен пустой или неверный путь к ключу");
                    return;
                }
                try {
                    AcskiddOwnCertFactory.downloadByEDRPO(configForm.inn.getText(), directory);
                    DialogMessages.showInfoPane("Установка личного сертификата", " Личный сертификат найден и успешно установлен");
                } catch (Exception ex) {
                    DialogMessages.showErrorPane("Ошибка при установке сертификатов", ex.getMessage());
                }

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Config.save(Configs.INSTALL_PATH, configForm.installIITPath.getText());
        Config.save(Configs.CERT_PATH, configForm.certPath.getText());
        Config.save(Configs.CODEPAGE, configForm.codePage.getText());
        Config.save(Configs.PRIVATEKEY_PATH, configForm.pkPath.getText());
        Config.save(Configs.PRIVATEKEY_PASSWORD, new String(configForm.password.getPassword()));
        Config.save(Configs.INN, configForm.inn.getText());
        try {
            DialogMessages.showProgressPane(frame, "Сохраняем Хранилище", "Сохраняем информациию о Хранилище...", 0, 5, new ProgressRunnable<Object>() {

                @Override
                protected Object executeLogic() throws Exception {
                    StorageFactory.saveStorage(StorageFactory.buildDefaultStorage(configForm.certPath.getText()));
                    return null;
                }
            });
        } catch (Exception e1) {
            DialogMessages.showErrorPane("Сохранение параметров не получилось", e1.getMessage());
            return;
        }
        frame.setVisible(false);
    }
}
