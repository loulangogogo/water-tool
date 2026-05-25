package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 加密异常
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class EncryptException extends BaseException {
    /**
     * 构造一个无消息的加密异常
     *
     * @author :loulan
     */
    public EncryptException() {
        super();
    }

    /**
     * 构造一个带消息的加密异常
     *
     * @param message 异常消息
     * @author :loulan
     */
    public EncryptException(String message) {
        super(message);
    }

    /**
     * 构造一个带消息和原因的加密异常
     *
     * @param message 异常消息
     * @param cause   异常原因
     * @author :loulan
     */
    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个带原因的加密异常
     *
     * @param cause 异常原因
     * @author :loulan
     */
    public EncryptException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个带完整参数的加密异常
     *
     * @param message            异常消息
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     * @author :loulan
     */
    protected EncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
