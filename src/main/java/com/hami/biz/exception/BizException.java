package com.hami.biz.exception;

/**
 * <pre>
 * <li>Program Name : BizException
 * <li>Description  :
 * <li>History      : 2018. 2. 13.
 * </pre>
 *
 * @author HHG
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = -1L;

    public BizException(Throwable t) {
        super(t);
    }

    public BizException(String messageCode) {
        super(messageCode);
    }

    public BizException(String messageCode, Object messageParameters[]) {
        super(messageCode, messageParameters);
    }

    public String getMessage() {
        return super.getMessage();
    }

    public Throwable getWrappedException() {
        return super.getWrappedException();
    }

}
