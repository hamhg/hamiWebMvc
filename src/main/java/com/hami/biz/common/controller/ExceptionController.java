package com.hami.biz.common.controller;

import com.hami.sys.common.handler.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

/**
 * <pre>
 * <li>Program Name : ExceptionController
 * <li>Description  :
 * <li>History      : 2017. 7. 30.
 * </pre>
 *
 * @author HHG
 */
@ControllerAdvice
public class ExceptionController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = NullPointerException.class)
    public String handleNullPointerException(Exception e) {

        log.debug("A null pointer exception ocurred " + e);

        return "nullpointerExceptionPage";
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public String handleAllException(Exception e) {

        log.debug("A unknow Exception Ocurred: " + e);

        return "unknowExceptionPage";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException() {

        return "accessDeniedPage";
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {

        return "resourceNotFound";
    }

    /**
     * Convert a predefined exception to an HTTP Status code
     */
    // 409
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
        log.error("Request raised a DataIntegrityViolationException");
        // Nothing to do
    }

    /**
     * Convert a predefined exception to an HTTP Status code and specify the
     * name of a specific view that will be used to display the error.
     *
     * @return Exception view.
     */
    @ExceptionHandler({ SQLException.class, DataAccessException.class })
    public String databaseError(Exception exception) {
        // Nothing to do. Return value 'databaseError' used as logical view name
        // of an error page, passed to view-resolver(s) in usual way.
        log.error("Request raised " + exception.getClass().getSimpleName());
        return "databaseError";
    }
}
