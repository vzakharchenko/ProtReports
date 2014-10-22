package ua.od.vassio.protect.report.receipt.ui;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class DialogMessages {
    public static void showErrorPane(String title, String msg) {
        showPane(title, msg, JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoPane(String title, String msg) {
        showPane(title, msg, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showPane(String title, String msg, int type) {

        JOptionPane pane = new JOptionPane(msg, type);
        JDialog dialog = pane.createDialog(title);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public static <TYPE> TYPE showProgressPane(Frame parentFrame, String title, String msg, int minValue, int maxValue, ProgressRunnable<TYPE> progressRunnable, Class<TYPE> type) throws InterruptedException {
        final JDialog dlg = new JDialog(parentFrame, title, true);
        JProgressBar dpb = new JProgressBar(minValue, maxValue);
        dlg.add(BorderLayout.CENTER, dpb);
        dlg.add(BorderLayout.NORTH, new JLabel(msg));
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlg.setSize(300, 75);
        dlg.setLocationRelativeTo(parentFrame);
        progressRunnable.setDialog(dlg);
        progressRunnable.setProgressBar(dpb);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(progressRunnable);
        dlg.setVisible(true);
        executorService.shutdown();
        executorService.awaitTermination(15, TimeUnit.SECONDS);
        dlg.setVisible(false);
        return progressRunnable.getValue();
    }


    public static String showPasswordPane(String title, String msg) {
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
