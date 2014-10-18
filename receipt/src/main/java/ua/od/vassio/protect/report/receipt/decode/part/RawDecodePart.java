package ua.od.vassio.protect.report.receipt.decode.part;

import ua.od.vassio.protect.report.receipt.part.AbstractReceiptPart;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 21:03
 */
public class RawDecodePart extends AbstractReceiptPart {
    private String message;

    public RawDecodePart(int pos, int size, int end, String message) {
        super(pos, size, end);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
