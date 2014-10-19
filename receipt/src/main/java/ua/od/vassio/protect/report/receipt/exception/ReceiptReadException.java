package ua.od.vassio.protect.report.receipt.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 9:24
 */
public class ReceiptReadException extends Exception {
    public ReceiptReadException() {
    }

    public ReceiptReadException(String message) {
        super(message);
    }

    public ReceiptReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiptReadException(Throwable cause) {
        super(cause);
    }
}
