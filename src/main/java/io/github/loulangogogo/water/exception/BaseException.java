package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 基础异常,里面的异常都必须继承这个异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class BaseException extends RuntimeException{

    /**
     * 构造一个无消息的基础异常
     *
     * @author :loulan
     */
    public BaseException() {
        super();
    }

    /**
     * 构造一个带消息的基础异常
     *
     * @param message 异常消息
     * @author :loulan
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * 构造一个带消息和原因的基础异常
     *
     * @param message 异常消息
     * @param cause   异常原因
     * @author :loulan
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个带原因的基础异常
     *
     * @param cause 异常原因
     * @author :loulan
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个带完整参数的基础异常
     *
     * @param message            异常消息
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     * @author :loulan
     */
    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
