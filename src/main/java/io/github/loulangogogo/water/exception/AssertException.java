package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 断言异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class AssertException extends BaseException {
    /**
     * 构造一个无消息的断言异常
     *
     * @author :loulan
     */
    public AssertException() {
        super();
    }

    /**
     * 构造一个带消息的断言异常
     *
     * @param message 异常消息
     * @author :loulan
     */
    public AssertException(String message) {
        super(message);
    }

    /**
     * 构造一个带消息和原因的断言异常
     *
     * @param message 异常消息
     * @param cause   异常原因
     * @author :loulan
     */
    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个带原因的断言异常
     *
     * @param cause 异常原因
     * @author :loulan
     */
    public AssertException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个带完整参数的断言异常
     *
     * @param message            异常消息
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     * @author :loulan
     */
    protected AssertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
