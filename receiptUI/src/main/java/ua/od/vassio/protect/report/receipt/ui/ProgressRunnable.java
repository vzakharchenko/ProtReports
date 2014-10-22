package ua.od.vassio.protect.report.receipt.ui;

import javax.swing.*;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public abstract class ProgressRunnable<TYPE> implements Runnable {

    private JDialog dialog;

    private JProgressBar progressBar;

    private TYPE value = null;

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TYPE getValue() {
        return value;
    }

    protected void increment() {
        if (progressBar.getValue() < progressBar.getMaximum()) {
            progressBar.setValue(progressBar.getValue() + 1);
        }
    }

    protected void increment(int value) {
        if ((progressBar.getValue() + value) < progressBar.getMaximum()) {
            progressBar.setValue(progressBar.getValue() + 1);
        }
    }

    protected abstract TYPE executeLogic();

    @Override
    public void run() {
        try {
            progressBar.setValue(progressBar.getMinimum());

            value = executeLogic();
        } catch (Exception ex) {
            DialogMessages.showErrorPane("Ошибка", ex.getMessage());
        } finally {
            dialog.setVisible(false);
        }

    }
}
