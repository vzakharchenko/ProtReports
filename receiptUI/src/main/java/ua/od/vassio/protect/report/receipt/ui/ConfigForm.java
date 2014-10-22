package ua.od.vassio.protect.report.receipt.ui;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserError;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import ua.od.vassio.protect.report.cert.acsk.ACSKDownloaderHelper;
import ua.od.vassio.protect.report.cert.own.AcskiddOwnCertFactory;
import ua.od.vassio.protect.report.core.exception.IITException;
import ua.od.vassio.protect.report.core.exception.KeyReadExceptionIITException;
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
    private JPanel config;
    private JButton setupCert;
    private JButton selectDirProtect;
    private JLabel edrpo;
    private JTextField inn;
    private JButton owncert;
    private JButton btnkeypath;
    private JButton btnSelectCertPath;
    private JCheckBox notUseKey;

    public ConfigForm() {
        OK.addActionListener(this);
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
            configForm.notUseKey.setSelected(BooleanUtils.toBoolean(Config.load(Configs.NOT_USE_KEY, Boolean.FALSE.toString())));
            Storage storage = DialogMessages.showProgressPane(frame, "Загружаем информациию о Хранилище", "Загружаем информациию о Хранилище...", 0, 4, new ProgressRunnable<Storage>() {
                @Override
                protected Storage executeLogic() {
                    increment(2);
                    Storage storage = null;
                    try {
                        storage = StorageFactory.loadStorage();
                    } catch (IITException e) {
                        e.printStackTrace();
                        DialogMessages.showErrorPane("Ошибка При Загрузке информациии из Хранилища", e.getMessage());
                    }
                    increment(2);
                    return storage;
                }
            });
            if (storage == null) {
                throw new RuntimeException("Ошибка При Загрузке информациии из Хранилища");
            }
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
        configForm.btnkeypath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                File curdir = new File(configForm.pkPath.getText());
                if (curdir.exists()) {
                    fc.setCurrentDirectory(curdir);
                }
                int returnVal = fc.showOpenDialog(configForm);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.exists() && file.isFile()) {
                        configForm.pkPath.setText(file.getAbsolutePath());
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

    private boolean validateDirectory(String dir) {
        return StringUtils.isNotEmpty(dir) && new File(dir).exists() && new File(dir).isDirectory();
    }

    private boolean validateIsFile(String file) {
        return StringUtils.isNotEmpty(file) && new File(file).exists() && new File(file).isFile();
    }

    private boolean validateDirectoryIsNotEmpty(String dir) {
        return CollectionUtils.isNotEmpty(FileUtils.listFiles(new File(dir), FileFileFilter.FILE, DirectoryFileFilter.INSTANCE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean nUseKey = notUseKey.isSelected();
        Config.save(Configs.NOT_USE_KEY, BooleanUtils.toString(nUseKey, Boolean.TRUE.toString(), Boolean.FALSE.toString()));
        if (StringUtils.isEmpty(configForm.inn.getText()) || configForm.inn.getText().length() < 7) {
            DialogMessages.showErrorPane("ЕДРПО", "Ошибка проверки ЕДРПО(ИНН) поле не может быть пустым или меньше 6 символов");
            configForm.inn.setFocusable(true);
            return;
        }
        try {
            String password = new String(configForm.password.getPassword());
            if (!nUseKey) {
                if (!validateDirectory(configForm.installIITPath.getText())) {
                    IITHelper.getInstance().reInstall(configForm.installIITPath.getText());
                    if (!validateDirectory(configForm.installIITPath.getText())) {
                        DialogMessages.showErrorPane("Шифратор", "Путь к шифратору указан неверно");
                        return;
                    }
                }
                if (!validateDirectory(configForm.certPath.getText())) {
                    DialogMessages.showErrorPane("АЦСК Сертификаты", "Путь к АЦСК Сертификатам неверный");
                    return;
                }
                if (!validateDirectoryIsNotEmpty(configForm.certPath.getText())) {
                    DialogMessages.showErrorPane("АЦСК Сертификаты", "Нажмите \"" + setupCert.getText() + "\" чтобы скачать АЦСК сертификаты");
                    return;
                }
                if (!validateIsFile(configForm.pkPath.getText())) {
                    DialogMessages.showErrorPane("Приватный ключ", "Приватный Ключ не найден");
                    return;
                }
                if (StringUtils.isNotEmpty(password)) {
                    try {
                        IITHelper.getInstance().readPrivateKey(configForm.pkPath.getText(), password);
                    } catch (KeyReadExceptionIITException e1) {
                        if (e1.getCode() == EndUserError.ERROR_CERT_NOT_FOUND) {
                            DialogMessages.showErrorPane("Приватный ключ", "Сертификат Приватного Ключа не найден! Нажмите \"" + owncert.getText() + "\" чтобы найти Сертификат и установить его в хранилище");
                        } else {
                            DialogMessages.showErrorPane("Приватный ключ", "Ошибка Приватного Ключа! " + e1.getMessage());
                        }
                        return;
                    }
                }
            }

        Config.save(Configs.INSTALL_PATH, configForm.installIITPath.getText());
        Config.save(Configs.CERT_PATH, configForm.certPath.getText());
        Config.save(Configs.CODEPAGE, configForm.codePage.getText());
        Config.save(Configs.PRIVATEKEY_PATH, configForm.pkPath.getText());
        Config.save(Configs.PRIVATEKEY_PASSWORD, new String(configForm.password.getPassword()));
        Config.save(Configs.INN, configForm.inn.getText());

            boolean isSaved = DialogMessages.showProgressPane(frame, "Сохраняем Хранилище", "Сохраняем информациию о Хранилище...", 0, 5, new ProgressRunnable<Boolean>() {

                @Override
                protected Boolean executeLogic() {
                    try {
                        IITHelper.getInstance().reInstall(configForm.installIITPath.getText());
                        StorageFactory.saveStorage(StorageFactory.buildDefaultStorage(configForm.certPath.getText()));
                    } catch (Exception ex) {
                        DialogMessages.showErrorPane("Сохранение параметров не получилось", ex.getMessage());
                        return false;
                    }

                    return true;
                }
            });
            if (!isSaved) {
                return;
            }
        } catch (Exception e1) {
            DialogMessages.showErrorPane("Сохранение параметров не получилось", e1.getMessage());
            return;
        }
        frame.setVisible(false);
    }
}
