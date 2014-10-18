package ua.od.vassio.protect.report.receipt.model;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 23:43
 */
public class MessageModel extends AbstractReceiptModel implements ReceiptModel<String> {

    private String message;

    @Override
    public boolean isXml() {
        return false;
    }

    @Override
    public String getResponse() {
        return getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
