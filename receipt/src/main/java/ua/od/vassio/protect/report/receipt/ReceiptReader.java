package ua.od.vassio.protect.report.receipt;

import ua.od.vassio.protect.report.core.exception.UnProtectIITException;
import ua.od.vassio.protect.report.core.key.Key;
import ua.od.vassio.protect.report.receipt.decode.DecodeReceipt;
import ua.od.vassio.protect.report.receipt.decode.DecodeReceiptFactory;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceipt;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceiptFactory;
import ua.od.vassio.protect.report.receipt.model.MessageModel;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 18:48
 */
public class ReceiptReader {

    public static MessageModel readReceiptFile(String charset,Key key,File kvit) throws IOException, UnProtectIITException {
          EncodeReceipt encodeReceipt= EncodeReceiptFactory.build(kvit);
          DecodeReceipt decodeReceipt= DecodeReceiptFactory.decodeReceipt(encodeReceipt.getEncodeReceiptPart(),charset,key);
          return populate(encodeReceipt,decodeReceipt);
    }

    private static MessageModel populate(EncodeReceipt encodeReceipt,DecodeReceipt decodeReceipt){
        MessageModel receiptModel=new MessageModel();
         return  receiptModel;
    }
}
