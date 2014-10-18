package ua.od.vassio.protect.report.receipt.encode;

import ua.od.vassio.protect.report.receipt.encode.part.EncodeReceiptPart;
import ua.od.vassio.protect.report.receipt.encode.part.InfoReceiptPart;
import ua.od.vassio.protect.report.receipt.encode.part.SignReceiptPart;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 18:54
 */
public class EncodeReceipt {
    private InfoReceiptPart infoReceiptPart;
    private SignReceiptPart signReceiptPart;
    private EncodeReceiptPart encodeReceiptPart;

    protected EncodeReceipt(InfoReceiptPart infoReceiptPart, SignReceiptPart signReceiptPart, EncodeReceiptPart encodeReceiptPart) {
        this.infoReceiptPart = infoReceiptPart;
        this.signReceiptPart = signReceiptPart;
        this.encodeReceiptPart = encodeReceiptPart;
    }

    public InfoReceiptPart getInfoReceiptPart() {
        return infoReceiptPart;
    }

    public SignReceiptPart getSignReceiptPart() {
        return signReceiptPart;
    }

    public EncodeReceiptPart getEncodeReceiptPart() {
        return encodeReceiptPart;
    }
}
