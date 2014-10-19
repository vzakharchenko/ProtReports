package ua.od.vassio.protect.report.receipt.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 9:28
 */
public class DecodeReadException extends ReceiptReadException {
    public DecodeReadException() {
    }

    public DecodeReadException(String message) {
        super(message);
    }

    public DecodeReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecodeReadException(Throwable cause) {
        super(cause);
    }
}
