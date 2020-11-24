package ml.lwei.mfile.exception;

/**
 * 无效的驱动器异常
 * @author lwei
 */
public class InvalidDriveException extends RuntimeException {

    public InvalidDriveException() {
    }

    public InvalidDriveException(String message) {
        super(message);
    }

    public InvalidDriveException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDriveException(Throwable cause) {
        super(cause);
    }

    public InvalidDriveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
