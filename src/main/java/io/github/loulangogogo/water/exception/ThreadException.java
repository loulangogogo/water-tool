package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 线程异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ThreadException extends BaseException {
    /**
     * 构造一个无消息的线程异常
     *
     * @author :loulan
     */
    public ThreadException() {
        super();
    }

    /**
     * 构造一个带消息的线程异常
     *
     * @param message 异常消息
     * @author :loulan
     */
    public ThreadException(String message) {
        super(message);
    }

    /**
     * 构造一个带消息和原因的线程异常
     *
     * @param message 异常消息
     * @param cause   异常原因
     * @author :loulan
     */
    public ThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个带原因的线程异常
     *
     * @param cause 异常原因
     * @author :loulan
     */
    public ThreadException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个带完整参数的线程异常
     *
     * @param message            异常消息
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     * @author :loulan
     */
    protected ThreadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
