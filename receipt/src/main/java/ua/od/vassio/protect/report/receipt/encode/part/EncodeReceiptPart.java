package ua.od.vassio.protect.report.receipt.encode.part;

import ua.od.vassio.protect.report.receipt.part.AbstractReceiptPart;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 20:20
 */
public class EncodeReceiptPart extends AbstractReceiptPart {

    private byte[] encodeData;

    public EncodeReceiptPart(int pos, int size, int end, byte[] encodeData) {
        super(pos, size, end);
        this.encodeData = encodeData;
    }

    public byte[] getEncodeData() {
        ByteBuffer wrapped = ByteBuffer.wrap(encodeData);
        Integer size = Short.reverseBytes(wrapped.getShort()) & 0xffff;
        return Arrays.copyOfRange(encodeData, 4, size + 4);// skip 4 bytes (length)
    }
}
