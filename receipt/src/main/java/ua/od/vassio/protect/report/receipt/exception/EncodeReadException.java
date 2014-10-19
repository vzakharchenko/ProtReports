package ua.od.vassio.protect.report.receipt.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 9:27
 */
public class EncodeReadException extends ReceiptReadException {
    public EncodeReadException() {
    }

    public EncodeReadException(String message) {
        super(message);
    }

    public EncodeReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncodeReadException(Throwable cause) {
        super(cause);
    }
}
