package ua.od.vassio.protect.report.receipt.model;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 0:08
 */
public interface ReceiptModel<TYPE> {
    public boolean isXml();

    public TYPE getResponse();

    public String getResponseAsString();

    public int getDocType();

    public String getRcv_email();

    public int getCncode();

    public int getKvtNUM();

    public int getStType();

    public int getResult();
}
