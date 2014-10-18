package ua.od.vassio.protect.report.receipt.encode.part;

import ua.od.vassio.protect.report.receipt.part.AbstractReceiptPart;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 20:03
 */
public class SignReceiptPart extends AbstractReceiptPart {

    private byte[] body;

    public SignReceiptPart(int pos, int size, int end, byte[] body) {
        super(pos, size, end);
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
