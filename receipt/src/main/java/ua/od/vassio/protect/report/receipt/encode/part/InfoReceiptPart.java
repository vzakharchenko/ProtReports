package ua.od.vassio.protect.report.receipt.encode.part;

import ua.od.vassio.protect.report.core.helper.IITEncodeHelper;
import ua.od.vassio.protect.report.receipt.exception.ReceiptReadException;
import ua.od.vassio.protect.report.receipt.part.AbstractReceiptPart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 19:06
 */
public class InfoReceiptPart extends AbstractReceiptPart {
    private Properties properties = new Properties();


    public InfoReceiptPart(int pos, int size, int end) throws ReceiptReadException {
        super(pos, size, end);
        if (size != end) {
            throw new ReceiptReadException("Read Error: " + size + "<>" + end);
        }
    }

    public void load(byte[] bytes) throws IOException {
        properties.load(new ByteArrayInputStream(bytes));
    }

    public Map<String, String> getProperties(String encode) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            result.put((String) entry.getKey(), IITEncodeHelper.getStringFromBytes(((String) entry.getValue()).getBytes(), encode));
        }
        return result;
    }


}
