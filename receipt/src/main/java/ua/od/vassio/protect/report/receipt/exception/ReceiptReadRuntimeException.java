package ua.od.vassio.protect.report.receipt.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 9:31
 */
public class ReceiptReadRuntimeException extends RuntimeException {
    public ReceiptReadRuntimeException() {
    }

    public ReceiptReadRuntimeException(String message) {
        super(message);
    }

    public ReceiptReadRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiptReadRuntimeException(Throwable cause) {
        super(cause);
    }
}
