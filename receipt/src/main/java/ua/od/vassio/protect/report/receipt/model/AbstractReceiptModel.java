package ua.od.vassio.protect.report.receipt.model;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 0:13
 */
public abstract class AbstractReceiptModel {
    private int docType;
    private String rcv_email;
    private int cncode;
    private int kvtNUM;
    private int stType;
    private int result;

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }

    public String getRcv_email() {
        return rcv_email;
    }

    public void setRcv_email(String rcv_email) {
        this.rcv_email = rcv_email;
    }

    public int getCncode() {
        return cncode;
    }

    public void setCncode(int cncode) {
        this.cncode = cncode;
    }

    public int getKvtNUM() {
        return kvtNUM;
    }

    public void setKvtNUM(int kvtNUM) {
        this.kvtNUM = kvtNUM;
    }

    public int getStType() {
        return stType;
    }

    public void setStType(int stType) {
        this.stType = stType;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
