package ua.od.vassio.protect.report.core.helper;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 19:38
 */
public class IITEncodeHelper {
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String encodeWindows(String input){
        try {
            return new String(input.getBytes(),"windows-1251");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getStringFromBytes(byte[] paramArrayOfByte,String charset) {
        try {
            if (charset != null) {
                return new String(paramArrayOfByte, charset);
            }
            return new String(paramArrayOfByte);
        } catch (Exception ex){
            throw new IllegalArgumentException(ex);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
