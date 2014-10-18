package ua.od.vassio.protect.report.receipt.decode.part;

import ua.od.vassio.protect.report.receipt.part.AbstractReceiptPart;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 20:51
 */
public class SignDecodePart extends AbstractReceiptPart {
    private byte[] body;

    public SignDecodePart(int pos, int size, int end, byte[] body) {
        super(pos, size, end);
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
