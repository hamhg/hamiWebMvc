package com.hami.biz.system.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.hami.biz.system.exception.handler.ResourceNotFoundException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

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

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllException(Exception e) {

        log.debug("A unknow Exception Ocurred: " + e);
        return "unknowExceptionPage";
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }    
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(Exception e) {

    	log.debug("AccessDenied" + e);
        return "accessDeniedPage";
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
