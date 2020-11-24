package ml.lwei.mfile.exception;

/**
 * 刷新缓存时出现的异常信息
 *
 * @author lwei
 */
public class RefreshCacheException extends RuntimeException {

    public RefreshCacheException() {
    }

    public RefreshCacheException(String message) {
        super(message);
    }

    public RefreshCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshCacheException(Throwable cause) {
        super(cause);
    }

    public RefreshCacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
