package ua.od.vassio.protect.report.receipt.model;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 22:05
 */
public class EncMessageModel extends AbstractReceiptModel<Object> {
    @Override
    public boolean isXml() {
        return false;
    }

    @Override
    public Object getResponse() {
        return null;
    }

    @Override
    public String getResponseAsString() {
        return "";
    }
}
