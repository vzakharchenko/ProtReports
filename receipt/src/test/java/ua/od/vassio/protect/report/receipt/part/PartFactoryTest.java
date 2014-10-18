package ua.od.vassio.protect.report.receipt.part;

import org.testng.annotations.Test;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceipt;
import ua.od.vassio.protect.report.receipt.encode.EncodeReceiptFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 19:29
 */
public class PartFactoryTest {

    @Test
    public void testParseReceipt() throws IOException {
        File testFile=new File("./src/test/resources/15532081900842F1391101100000000451220141553.RPL");
        EncodeReceipt receipt= EncodeReceiptFactory.build(testFile);
    }


}
