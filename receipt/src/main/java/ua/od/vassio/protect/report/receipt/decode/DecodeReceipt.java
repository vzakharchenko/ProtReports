package ua.od.vassio.protect.report.receipt.decode;

import ua.od.vassio.protect.report.receipt.decode.part.RawDecodePart;
import ua.od.vassio.protect.report.receipt.decode.part.SignDecodePart;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 20:32
 */
public class DecodeReceipt {
    private SignDecodePart signDecodePart;
    private RawDecodePart rawDecodePart;

    protected DecodeReceipt(SignDecodePart signDecodePart, RawDecodePart rawDecodePart) {
        this.signDecodePart = signDecodePart;
        this.rawDecodePart = rawDecodePart;
    }

    public SignDecodePart getSignDecodePart() {
        return signDecodePart;
    }

    public RawDecodePart getRawDecodePart() {
        return rawDecodePart;
    }
}
