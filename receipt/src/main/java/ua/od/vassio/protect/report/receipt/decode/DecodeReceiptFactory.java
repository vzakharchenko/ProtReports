package ua.od.vassio.protect.report.receipt.decode;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import ua.od.vassio.protect.report.core.exception.UnProtectIITException;
import ua.od.vassio.protect.report.core.helper.IITEncodeHelper;
import ua.od.vassio.protect.report.core.helper.KMPMatchHelper;
import ua.od.vassio.protect.report.core.key.Key;
import ua.od.vassio.protect.report.core.system.InternalTyped;
import ua.od.vassio.protect.report.receipt.HeaderValue;
import ua.od.vassio.protect.report.receipt.decode.part.RawDecodePart;
import ua.od.vassio.protect.report.receipt.decode.part.SignDecodePart;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceipt;
import ua.od.vassio.protect.report.receipt.exception.DecodeReadException;
import ua.od.vassio.protect.report.receipt.exception.ReceiptReadException;

import java.util.Arrays;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 20:33
 */
public class DecodeReceiptFactory {

    public static DecodeReceipt decodeReceipt(EncodeReceipt encodeReceipt, String charset, Key key) throws UnProtectIITException, ReceiptReadException {
        byte[] cryptData = encodeReceipt.getEncodeReceiptPart().getEncodeData();

        byte[] decryptData;
        if (isCrypted(encodeReceipt)) {
            decryptData = key.unprotect(cryptData);
            byte[] startData = Arrays.copyOf(decryptData, InternalTyped.UA1_SIGN.length);
            if (!ArrayUtils.isEquals(startData, InternalTyped.UA1_SIGN)) {
                throw new DecodeReadException("Read error");
            }

        } else {
            decryptData = cryptData;
        }


        SignDecodePart signDecodePart = getSignDecodePart(decryptData);
        RawDecodePart rawDecodePart = getXmlDecodePart(charset, signDecodePart, decryptData);
        return new DecodeReceipt(signDecodePart, rawDecodePart);
    }

    private static SignDecodePart getSignDecodePart(byte[] decryptData) {
        int startPos = InternalTyped.UA1_SIGN.length;
        int endPos = startPos + InternalTyped.SIGN_LENGTH;
        byte[] decodeBody = Arrays.copyOf(decryptData, InternalTyped.UA1_SIGN.length);
        return new SignDecodePart(startPos, InternalTyped.SIGN_LENGTH, endPos, decodeBody);
    }

    private static RawDecodePart getXmlDecodePart(String charset, SignDecodePart signDecodePart, byte[] decryptData) {
        int startPos = signDecodePart.end();
        int endPos = KMPMatchHelper.indexOf(decryptData, InternalTyped.END_MESSAGE);
        byte[] body = Arrays.copyOfRange(decryptData, startPos, endPos);
        String hex = IITEncodeHelper.getStringFromBytes(body, charset);
        return new RawDecodePart(startPos, endPos - startPos, endPos, hex);
    }

    private static Map<String, String> getEncodedValues(EncodeReceipt encodeReceipt) {
        return encodeReceipt.getInfoReceiptPart().getProperties(null);
    }

    private static boolean isCrypted(EncodeReceipt encodeReceipt) {
        return isCrypted(getEncodedValues(encodeReceipt));
    }

    private static boolean isCrypted(Map<String, String> map) {
        return (map.containsKey(HeaderValue.PRG_TYPE.name()) && StringUtils.equals(map.get(HeaderValue.PRG_TYPE.name()), "ZPGATE"));
    }
}
