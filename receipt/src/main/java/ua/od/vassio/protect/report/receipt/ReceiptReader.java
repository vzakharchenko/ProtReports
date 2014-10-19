package ua.od.vassio.protect.report.receipt;

import ua.od.vassio.protect.report.core.exception.UnProtectIITException;
import ua.od.vassio.protect.report.core.key.Key;
import ua.od.vassio.protect.report.receipt.decode.DecodeReceipt;
import ua.od.vassio.protect.report.receipt.decode.DecodeReceiptFactory;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceipt;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceiptFactory;
import ua.od.vassio.protect.report.receipt.exception.ReceiptReadException;
import ua.od.vassio.protect.report.receipt.model.ReceiptModel;
import ua.od.vassio.protect.report.receipt.model.ReceiptModelFactory;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 18:48
 */
public class ReceiptReader {

    public static ReceiptModel readOnlyEncodePartFile(File kvit) throws UnProtectIITException, ReceiptReadException {
        EncodeReceipt encodeReceipt = EncodeReceiptFactory.build(kvit);
        return populate(encodeReceipt, null);
    }

    public static ReceiptModel readReceiptFile(String charset, Key key, File kvit) throws UnProtectIITException, ReceiptReadException {
        EncodeReceipt encodeReceipt = EncodeReceiptFactory.build(kvit);
        DecodeReceipt decodeReceipt = DecodeReceiptFactory.decodeReceipt(encodeReceipt.getEncodeReceiptPart(), charset, key);
        return populate(encodeReceipt, decodeReceipt);
    }

    private static ReceiptModel populate(EncodeReceipt encodeReceipt, DecodeReceipt decodeReceipt) {
        return ReceiptModelFactory.build(encodeReceipt, decodeReceipt);
    }
}
