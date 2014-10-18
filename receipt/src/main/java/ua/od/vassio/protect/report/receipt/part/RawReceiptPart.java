package ua.od.vassio.protect.report.receipt.part;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 18:49
 */
public interface RawReceiptPart {
    public int positionInFile();

    public int size();

    public int end();
}
