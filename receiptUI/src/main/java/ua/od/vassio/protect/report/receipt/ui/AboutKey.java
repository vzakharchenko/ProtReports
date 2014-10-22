package ua.od.vassio.protect.report.receipt.ui;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import ua.od.vassio.protect.report.core.exception.IITException;
import ua.od.vassio.protect.report.core.key.Key;
import ua.od.vassio.protect.report.core.key.KeyFactory;
import ua.od.vassio.protect.report.core.system.CertificateInfo;
import ua.od.vassio.protect.report.receipt.ui.config.Config;
import ua.od.vassio.protect.report.receipt.ui.config.Configs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyDescriptor;
import java.io.File;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class AboutKey extends Component implements ActionListener {
    private static AboutKey aboutKey = new AboutKey();
    private static JFrame frame = new JFrame("О ключе");
    private JTable KeyInfo;
    private JButton OK;
    private JPanel aboutPane;

    public AboutKey() {
        OK.addActionListener(this);
    }

    public static void showGUI() throws IITException {

        frame.setContentPane(aboutKey.aboutPane);
        init();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void init() throws IITException {

        String keyPassword = Config.load(Configs.PRIVATEKEY_PASSWORD);

        if (Config.load(Configs.PRIVATEKEY_PATH) == null) {
            throw new RuntimeException("Зайдите в настройки и укажите путь к ключу");
        }
        if (StringUtils.isEmpty(keyPassword)) {
            keyPassword = DialogMessages.showPasswordPane("Пароль к ключу", "Введите пароль к ключу");
        }
        final String finalKeyPassword = keyPassword;
        Key key = null;
        try {
            key = DialogMessages.showProgressPane(frame, "Подождите ключ открывается", "Подождите ключ открывается...", 0, 4, new ProgressRunnable<Key>() {
                @Override
                protected Key executeLogic() {
                    increment();
                    String installPath = Config.load(Configs.INSTALL_PATH, EndUserResourceExtractor.GetInstallPath());
                    increment();
                    try {
                        Key key = KeyFactory.openPrivateKey(new File(Config.load(Configs.PRIVATEKEY_PATH)), finalKeyPassword);
                        increment(2);
                        return key;
                    } catch (Exception ex) {
                        DialogMessages.showErrorPane("Ошибка при считывании ключа", ex.getMessage());
                        return null;
                    }

                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при считывании ключа" + e.getMessage(), e);
        }
        if (key == null) {
            throw new RuntimeException("Ошибка при считывании ключа");
        }


        CertificateInfo certificateInfo = key.getUserCertificateInfo();
        aboutKey.KeyInfo.setModel(getModelByUserInfo(certificateInfo));
        ((DefaultTableModel) aboutKey.KeyInfo.getModel()).fireTableDataChanged();

    }

    public static TableModel getModelByUserInfo(Object data) {
        try {
            DefaultTableModel defaultTableModel = new DefaultTableModel();
            defaultTableModel.addColumn("Ключ");
            defaultTableModel.addColumn("Значение");
            PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(data);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                defaultTableModel.addRow(new Object[]{propertyDescriptor.getName(), PropertyUtils.getProperty(data, propertyDescriptor.getName())});
            }

            return defaultTableModel;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
    }
}
