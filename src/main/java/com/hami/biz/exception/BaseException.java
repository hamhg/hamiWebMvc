package com.hami.biz.exception;

/**
 * <pre>
 * <li>Program Name : BaseException
 * <li>Description  :
 * <li>History      : 2018. 2. 13.
 * </pre>
 *
 * @author HHG
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;
    protected String message;
    protected String messageKey;
    protected Object messageParameters[];
    protected Exception wrappedException;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public Object[] getMessageParameters() {
        return messageParameters;
    }

    public void setMessageParameters(Object messageParameters[]) {
        this.messageParameters = messageParameters;
    }

    public Throwable getWrappedException() {
        return wrappedException;
    }

    public void setWrappedException(Exception wrappedException) {
        this.wrappedException = wrappedException;
    }

    public BaseException() {
        this("BaseException without message", null, null);
    }

    public BaseException(String messageCode) {
        this(messageCode, null, null);
    }

    public BaseException(String messageCode, Object messageParameters[]) {
        this(messageCode, messageParameters, null);
    }

    public BaseException(Throwable wrappedException) {
        this("BaseException without message", null, wrappedException);
    }

    public BaseException(String defaultMessage, Throwable wrappedException) {
        this(defaultMessage, null, wrappedException);
    }

    public BaseException(String messageCode, Object messageParameters[], Throwable wrappedException) {
        super(wrappedException);
        message = null;
        messageKey = messageCode;
        message = messageCode;
        this.messageParameters = messageParameters;
        this.wrappedException = null;
    }

}
