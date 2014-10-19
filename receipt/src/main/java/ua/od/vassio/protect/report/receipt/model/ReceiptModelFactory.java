package ua.od.vassio.protect.report.receipt.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import ua.od.vassio.protect.report.receipt.HeaderValue;
import ua.od.vassio.protect.report.receipt.decode.DecodeReceipt;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceipt;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 10:06
 */
public class ReceiptModelFactory {

    public static ReceiptModel buildFromEncodePart(EncodeReceipt encodeReceipt) {
        AbstractReceiptModel receiptModel;
        if (isXML(encodeReceipt)) {
            FiNKVTModel fiNKVTModel = new FiNKVTModel();
            receiptModel = fiNKVTModel;
        } else {
            MessageModel messageModel = new MessageModel();
            receiptModel = messageModel;
        }
        buildFromEncodePart(receiptModel, encodeReceipt);
        return receiptModel;
    }

    private static void buildFromEncodePart(AbstractReceiptModel receiptModel, EncodeReceipt encodeReceipt) {
        Map<String, String> map = getEncodedValues(encodeReceipt);
        if (map.containsKey(HeaderValue.CNCODE.name())) {
            receiptModel.setCncode(NumberUtils.createInteger(map.get(HeaderValue.CNCODE.name())));
        }

        if (map.containsKey(HeaderValue.DOC_TYPE.name())) {
            receiptModel.setDocType(NumberUtils.createInteger(map.get(HeaderValue.DOC_TYPE.name())));
        }

        if (map.containsKey(HeaderValue.KVTNUM.name())) {
            receiptModel.setKvtNUM(NumberUtils.createInteger(map.get(HeaderValue.KVTNUM.name())));
        }

        if (map.containsKey(HeaderValue.RCV_EMAIL.name())) {
            receiptModel.setRcv_email(map.get(HeaderValue.RCV_EMAIL.name()));
        }

        if (map.containsKey(HeaderValue.RESULT.name())) {
            receiptModel.setResult(NumberUtils.createInteger(map.get(HeaderValue.RESULT.name())));
        }

        if (map.containsKey(HeaderValue.STTYPE.name())) {
            receiptModel.setStType(NumberUtils.createInteger(map.get(HeaderValue.STTYPE.name())));
        }
    }

    private static Map<String, String> getEncodedValues(EncodeReceipt encodeReceipt) {
        return encodeReceipt.getInfoReceiptPart().getProperties(null);
    }

    public static ReceiptModel build(EncodeReceipt encodeReceipt, DecodeReceipt decodeReceipt) {

        AbstractReceiptModel receiptModel;
        if (decodeReceipt != null) {
            if (isXML(encodeReceipt)) {
                FiNKVTModel fiNKVTModel = new FiNKVTModel();
                fiNKVTModel.setMessage(decodeReceipt.getRawDecodePart().getMessage());
                receiptModel = fiNKVTModel;
            } else {
                MessageModel messageModel = new MessageModel();
                messageModel.setMessage(decodeReceipt.getRawDecodePart().getMessage());
                receiptModel = messageModel;
            }
        } else {
            receiptModel = new EncMessageModel();
        }

        buildFromEncodePart(receiptModel, encodeReceipt);
        return receiptModel;
    }

    private static boolean isXML(EncodeReceipt encodeReceipt) {
        return isXML(getEncodedValues(encodeReceipt));
    }

    private static boolean isXML(Map<String, String> map) {
        return (map.containsKey(HeaderValue.FINKVT.name()) && StringUtils.equals(map.get(HeaderValue.FINKVT.name()), "1"));
    }
}
