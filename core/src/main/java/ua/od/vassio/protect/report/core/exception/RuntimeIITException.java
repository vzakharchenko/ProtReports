package ua.od.vassio.protect.report.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 16:23
 */
public abstract class RuntimeIITException extends RuntimeException {
    private int code;
    private String message;

    public RuntimeIITException(int code, String message) {
        super("Code: " + code + ";Message:" + message);
        this.code = code;
        this.message = message;
    }

    public RuntimeIITException(int code, String message, Throwable cause) {
        super("Code: " + code + ";Message:" + message, cause);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
