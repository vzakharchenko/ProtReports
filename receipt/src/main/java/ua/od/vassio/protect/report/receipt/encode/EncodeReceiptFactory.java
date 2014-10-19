package ua.od.vassio.protect.report.receipt.encode;

import org.apache.commons.io.FileUtils;
import ua.od.vassio.protect.report.core.helper.KMPMatchHelper;
import ua.od.vassio.protect.report.core.system.InternalTyped;
import ua.od.vassio.protect.report.receipt.encode.part.EncodeReceiptPart;
import ua.od.vassio.protect.report.receipt.encode.part.InfoReceiptPart;
import ua.od.vassio.protect.report.receipt.encode.part.SignReceiptPart;
import ua.od.vassio.protect.report.receipt.exception.EncodeReadException;
import ua.od.vassio.protect.report.receipt.exception.ReceiptReadException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 18:54
 */
public class EncodeReceiptFactory {
    public static EncodeReceipt build(File receiptFile) throws ReceiptReadException {
        try{
            byte[] receiptBytes = FileUtils.readFileToByteArray(receiptFile);
            InfoReceiptPart infoReceiptPart = getInfoReceiptPart(receiptBytes);
            SignReceiptPart signReceiptPart = getSignReceiptPart(infoReceiptPart, receiptBytes);
            EncodeReceiptPart encodeReceiptPart = getEncodeReceiptPart(signReceiptPart, receiptBytes);
            return new EncodeReceipt(infoReceiptPart, signReceiptPart, encodeReceiptPart);
        } catch (IOException e){
            throw new EncodeReadException(e);
        }


    }

    private static InfoReceiptPart getInfoReceiptPart(byte[] receiptBytes) throws IOException, ReceiptReadException {
        int infoEnd = KMPMatchHelper.indexOf(receiptBytes, InternalTyped.UA1_SIGN);
        if (infoEnd < 1) {
            throw new EncodeReadException("Incorrect FileName");
        }
        byte[] infoReceiptBytes = Arrays.copyOf(receiptBytes, infoEnd);
        InfoReceiptPart infoReceiptPart = new InfoReceiptPart(0, infoReceiptBytes.length, infoEnd);
        infoReceiptPart.load(infoReceiptBytes);
        return infoReceiptPart;
    }

    private static SignReceiptPart getSignReceiptPart(InfoReceiptPart infoReceiptPart, byte[] receiptBytes) {
        int startPos = infoReceiptPart.end() + InternalTyped.UA1_SIGN.length;
        int endPos = startPos + InternalTyped.SIGN_LENGTH;
        byte[] signReceiptBytes = Arrays.copyOfRange(receiptBytes, startPos, endPos);
        SignReceiptPart signReceiptPart = new SignReceiptPart(startPos, InternalTyped.SIGN_LENGTH, endPos, signReceiptBytes);
        return signReceiptPart;
    }

    private static EncodeReceiptPart getEncodeReceiptPart(SignReceiptPart signReceiptPart, byte[] receiptBytes) {
        int startPos = signReceiptPart.end() + InternalTyped.UA1_CRYPT.length;
        int endPos = receiptBytes.length;
        byte[] cryptReceiptBytes = Arrays.copyOfRange(receiptBytes, startPos, endPos);
        EncodeReceiptPart encodeReceiptPart = new EncodeReceiptPart(startPos, endPos - startPos, endPos, cryptReceiptBytes);
        return encodeReceiptPart;
    }

}
