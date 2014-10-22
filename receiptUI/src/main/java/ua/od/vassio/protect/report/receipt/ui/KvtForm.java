package ua.od.vassio.protect.report.receipt.ui;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import ua.od.vassio.protect.report.core.exception.IITException;
import ua.od.vassio.protect.report.core.key.Key;
import ua.od.vassio.protect.report.core.key.KeyFactory;
import ua.od.vassio.protect.report.core.system.CertificateInfo;
import ua.od.vassio.protect.report.receipt.ReceiptReader;
import ua.od.vassio.protect.report.receipt.exception.ReceiptReadException;
import ua.od.vassio.protect.report.receipt.model.ReceiptModel;
import ua.od.vassio.protect.report.receipt.ui.config.Config;
import ua.od.vassio.protect.report.receipt.ui.config.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 19:09
 */
public class KvtForm extends Component {
    private static KvtForm kvtForm = new KvtForm();
    private static String defaultFileName;
    private JPanel kvtPanel;
    private JTextPane message;
    private JLabel rcv_email;
    private JLabel email;
    private JLabel kvtNUM;
    private JLabel result;
    private JLabel kvtNUMValue;
    private JLabel ResultValue;
    private JButton open;
    private JButton config;
    private JButton about;
    private JButton unprotect;
    private JLabel keyInfo;
    private File currentFile;
    private static JFrame frame = new JFrame("Квитанция");

    public static void main(String[] args) {
        if (args.length > 0) {
            if (new File(args[0]).exists()) {
                defaultFileName = new File(args[0]).getAbsolutePath();
            }
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        frame.setContentPane(kvtForm.kvtPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 400);
        kvtForm.init();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public boolean openFile(File file) {
        try {

            boolean useKey = false;

            String keyPath = Config.load(Configs.PRIVATEKEY_PATH);
            ReceiptModel receiptModel;
            if (StringUtils.isEmpty(keyPath) || !new File(keyPath).exists()) {
                receiptModel = ReceiptReader.readOnlyEncodePartFile(file);
            } else {
                String installPath = Config.load(Configs.INSTALL_PATH, EndUserResourceExtractor.GetInstallPath());
                String keyPassword = Config.load(Configs.PRIVATEKEY_PASSWORD);
                if (StringUtils.isEmpty(keyPassword)) {
                    keyPassword = DialogMessages.showPasswordPane("Пароль к ключу", "Введите пароль к ключу");
                }
                if (StringUtils.isEmpty(keyPassword)) {
                    receiptModel = ReceiptReader.readOnlyEncodePartFile(file);
                } else {
                    receiptModel = decryptFile(installPath, file, keyPath, keyPassword);
                    useKey = receiptModel != null;
                    if (!useKey) {
                        receiptModel = ReceiptReader.readOnlyEncodePartFile(file);
                    }

                }
            }
            showReceiptModel(receiptModel);
            currentFile = file;
            unprotect.setVisible(!useKey);
            return useKey;
        } catch (Exception ex) {
            DialogMessages.showErrorPane("Ошибка чтения файла", ex.getMessage());
            unprotect.setVisible(false);
            return false;
        }
    }


    protected void showReceiptModel(ReceiptModel receiptModel) {
        kvtForm.email.setText(receiptModel.getRcv_email());
        kvtForm.kvtNUMValue.setText(Objects.toString(receiptModel.getKvtNUM()));
        if (receiptModel.getResult() == 0) {
            kvtForm.ResultValue.setForeground(Color.GREEN);
            kvtForm.ResultValue.setText("Принят");
        } else {
            kvtForm.ResultValue.setForeground(Color.RED);
            kvtForm.ResultValue.setText("Не принят");
        }
        kvtForm.message.setText(receiptModel.getResponseAsString());

    }

    public ReceiptModel decryptFile(final String installPath, final File file, final String keyPath, final String keyPassword) {
        try {
            boolean nusekey = BooleanUtils.toBoolean(Config.load(Configs.NOT_USE_KEY));
            if (nusekey) {
                return null;
            }
            return DialogMessages.showProgressPane(frame, "Подождите файл открывается", "Подождите файл открывается...", 0, 6, new ProgressRunnable<ReceiptModel>() {
                @Override
                protected ReceiptModel executeLogic() {
                    try {
                        increment();
                        Key key = KeyFactory.openPrivateKey(new File(keyPath), keyPassword);
                        increment(3);
                        String codepage = Config.load(Configs.CODEPAGE, "windows-1251");
                        increment(1);
                        ReceiptModel receiptModel = ReceiptReader.readReceiptFile(codepage, key, file);
                        increment(2);
                        showKeyInfo(key.getUserCertificateInfo());
                        return receiptModel;
                    } catch (IITException e) {
                        e.printStackTrace();
                        DialogMessages.showErrorPane("Ошибка чтения файла", e.getMessage());
                        return null;
                    } catch (ReceiptReadException e) {
                        e.printStackTrace();
                        DialogMessages.showErrorPane("Ошибка расшифровки файла", e.getMessage());
                        return null;
                    }
                }
            });
        } catch (InterruptedException e) {
            DialogMessages.showErrorPane("Ошибка открытия файла", e.getMessage());
            return null;
        }

    }

    private boolean isInstalled() {
        keyInfo.setVisible(false);
        boolean nusekey = BooleanUtils.toBoolean(Config.load(Configs.NOT_USE_KEY));
        final String keyPath = Config.load(Configs.PRIVATEKEY_PATH);
        final String password = Config.load(Configs.PRIVATEKEY_PASSWORD);
        final String installPath = Config.load(Configs.INSTALL_PATH);
        if (nusekey) {
            return true;
        }
        if (StringUtils.isEmpty(keyPath) || StringUtils.isEmpty(installPath)) {
            return false;
        }
        if (StringUtils.isNotEmpty(password)) {
            try {
                CertificateInfo certificateInfo = DialogMessages.showProgressPane(frame, "Чтение ключа", "Чтение Ключа...", 0, 4, new ProgressRunnable<CertificateInfo>() {
                    @Override
                    protected CertificateInfo executeLogic() {
                        try {
                            increment();
                            Key key = KeyFactory.openPrivateKey(installPath, new File(keyPath), password);
                            increment(2);
                            CertificateInfo certificateInfo = key.getUserCertificateInfo();
                            increment();
                            return certificateInfo;
                        } catch (IITException e) {
                            DialogMessages.showErrorPane("Ошибка чтения ключа", "Ошибка чтения ключа: " + e.getMessage());
                            return null;
                        }
                    }
                });
                if (certificateInfo == null) {
                    return false;
                }
                showKeyInfo(certificateInfo);
            } catch (Exception e) {
                e.printStackTrace();
                DialogMessages.showErrorPane("Ошибка чтения ключа", "Ошибка чтения ключа: " + e.getMessage());
            }
        }
        return true;
    }


    private void showKeyInfo(CertificateInfo certificateInfo) {
        keyInfo.setVisible(true);
        keyInfo.setText("Действие Сертификата заканчивается через " + TimeUnit.DAYS.convert(certificateInfo.getCertEndTime().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS) + " дней");
    }

    protected void init() {
        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setEnabled(false);
                try {
                    ConfigForm.showGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    DialogMessages.showErrorPane("Ошибка открытия Параметров", ex.getMessage());
                } finally {
                    frame.setEnabled(true);
                }
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (currentFile != null) {
                    fc.setCurrentDirectory(currentFile);
                }
                int returnVal = fc.showOpenDialog(KvtForm.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.exists()) {
                        boolean useKey = openFile(file);
                        DialogMessages.showPane("Файл успешно прочитан", useKey ? "Файл успешно расшифрован" : "Файл не расшифрован", useKey ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        unprotect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyPath = Config.load(Configs.PRIVATEKEY_PATH);
                if (currentFile == null) {
                    DialogMessages.showInfoPane("Ошибка", "Откройте сначала файл ");
                    return;
                }
                if (StringUtils.isEmpty(keyPath) || !new File(keyPath).exists()) {
                    DialogMessages.showErrorPane("Ключ не найден", "Укажите путь к ключу и установите сертификат");
                    config.doClick();
                    return;
                }
                String installPath = Config.load(Configs.INSTALL_PATH, EndUserResourceExtractor.GetInstallPath());
                String keyPassword = Config.load(Configs.PRIVATEKEY_PASSWORD);
                if (StringUtils.isEmpty(keyPassword)) {
                    keyPassword = DialogMessages.showPasswordPane("Пароль к ключу", "Введите пароль к ключу");
                }
                if (StringUtils.isEmpty(keyPassword)) {
                    DialogMessages.showWarningPane("Действие отменено", "Действие отменено");
                } else {
                    try {
                        boolean nusekey = BooleanUtils.toBoolean(Config.load(Configs.NOT_USE_KEY));
                        if (nusekey) {
                            DialogMessages.showWarningPane("Внимание", "Установлен режим без использования приватного ключа!");
                            config.doClick();
                            return;
                        }

                        ReceiptModel receiptModel = decryptFile(installPath, currentFile, keyPath, keyPassword);
                        if (receiptModel != null) {
                            showReceiptModel(receiptModel);
                            unprotect.setVisible(false);
                        }

                    } catch (Exception ex) {
                        DialogMessages.showErrorPane("Ошибка", ex.getMessage());
                        return;
                    }
                }
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setEnabled(false);
                try {
                    boolean nusekey = BooleanUtils.toBoolean(Config.load(Configs.NOT_USE_KEY));
                    if (nusekey) {
                        DialogMessages.showWarningPane("Внимание", "Установлен режим без использования приватного ключа!");
                        config.doClick();
                        return;
                    }
                    AboutKey.showGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    DialogMessages.showErrorPane("Ошибка открытия О ключе", ex.getMessage());
                } finally {
                    frame.setEnabled(true);
                }
            }
        });
        message.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        boolean useKey = openFile(file);
                        DialogMessages.showPane("Файл успешно прочитан", useKey ? "Файл успешно расшифрован" : "Файл не расшифрован", useKey ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    DialogMessages.showErrorPane("Ошибка при перетаскивании", ex.getMessage());
                }
            }
        });

        if (!isInstalled()) {
            DialogMessages.showInfoPane("Приложение не установлено!", "Чтобы продолжить Настройте работу программы");
            config.doClick();
        }
        if (defaultFileName != null) {
            openFile(new File(defaultFileName));
        } else {
            kvtForm.email.setText("");
            kvtForm.kvtNUMValue.setText("");
            kvtForm.ResultValue.setText("");
        }

    }


}
