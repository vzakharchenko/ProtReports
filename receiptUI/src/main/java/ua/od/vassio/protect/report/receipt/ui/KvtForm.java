package ua.od.vassio.protect.report.receipt.ui;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;
import org.apache.commons.lang3.StringUtils;
import ua.od.vassio.protect.report.core.key.Key;
import ua.od.vassio.protect.report.core.key.KeyFactory;
import ua.od.vassio.protect.report.receipt.ReceiptReader;
import ua.od.vassio.protect.report.receipt.model.ReceiptModel;
import ua.od.vassio.protect.report.receipt.ui.config.Config;
import ua.od.vassio.protect.report.receipt.ui.config.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

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
        kvtForm.init();
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
                    keyPassword = showPasswordPane("Пароль к ключу", "Введите пароль к ключу");
                }
                if (StringUtils.isEmpty(keyPassword)) {
                    receiptModel = ReceiptReader.readOnlyEncodePartFile(file);
                } else {
                    Key key = KeyFactory.openPrivateKey(installPath, new File(keyPath), keyPassword);
                    String codepage = Config.load(Configs.CODEPAGE, "windows-1251");
                    receiptModel = ReceiptReader.readReceiptFile(codepage, key, file);
                    useKey = true;
                }
            }
            showReceiptModel(receiptModel);
            return useKey;
        } catch (Exception ex) {
            showErrorPane("error", ex.getMessage());
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

    protected void init() {
        if (defaultFileName != null) {
            openFile(new File(defaultFileName));
        } else {
            kvtForm.email.setText("");
            kvtForm.kvtNUMValue.setText("");
            kvtForm.ResultValue.setText("");
        }

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(KvtForm.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.exists()) {
                        boolean useKey = openFile(file);
                        showPane("Файл успешно прочитан", useKey ? "Файл успешно расшифрован" : "Файл не Распаковывался", useKey ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigForm.showGUI();
            }
        });
    }

    protected void showErrorPane(String title, String msg) {
        showPane(title, msg, JOptionPane.ERROR_MESSAGE);
    }

    protected void showPane(String title, String msg, int type) {
        JOptionPane pane = new JOptionPane(msg, type);
        JDialog dialog = pane.createDialog(title);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    protected String showPasswordPane(String title, String msg) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "The title",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if (option == JOptionPane.OK_OPTION) // pressing OK button
        {
            return new String(pass.getPassword());
        }
        return null;
    }


}
