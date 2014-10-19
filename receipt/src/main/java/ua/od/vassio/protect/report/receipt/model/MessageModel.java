package ua.od.vassio.protect.report.receipt.model;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 23:43
 */
public class MessageModel extends AbstractReceiptModel<String> {

    private String message;

    @Override
    public String getResponseAsString() {
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
