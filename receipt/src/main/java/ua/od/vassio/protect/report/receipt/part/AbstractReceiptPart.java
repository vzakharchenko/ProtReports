package ua.od.vassio.protect.report.receipt.part;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 20:04
 */
public abstract class AbstractReceiptPart implements RawReceiptPart {
    protected int pos;
    protected int size;
    protected int end;

    protected AbstractReceiptPart(int pos, int size, int end) {
        this.pos = pos;
        this.size = size;
        this.end = end;
    }

    @Override
    public int positionInFile() {
        return pos;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int end() {
        return end;
    }
}
