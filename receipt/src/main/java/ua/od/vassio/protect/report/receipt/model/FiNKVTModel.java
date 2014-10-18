package ua.od.vassio.protect.report.receipt.model;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 0:07
 */
public class FiNKVTModel extends AbstractReceiptModel implements ReceiptModel<Object> {


    @Override
    public boolean isXml() {
        return true;
    }

    @Override
    public Object getResponse() {
        return null;//todo add model
    }
}
