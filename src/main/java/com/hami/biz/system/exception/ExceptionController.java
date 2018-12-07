package com.hami.biz.system.exception;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
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
    public ModelAndView databaseError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        // Nothing to do. Return value 'databaseError' used as logical view name
        // of an error page, passed to view-resolver(s) in usual way.
        log.error("Request raised " + exception.getClass().getSimpleName());
        log.error(exception.getMessage());
        //return "databaseError";

        return makeError(request, response, exception);
    }
    
    public ModelAndView makeError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String contentType = request.getHeader("Content-Type");
        ModelAndView model=null;
        String reason = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();;
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
 
        // Content-Type 확인, json 만 View를 따로 처리함.
        if(contentType != null && MediaType.APPLICATION_JSON_VALUE.equals(contentType)){
            model = new ModelAndView("jsonView");
            ResponseStatus annotation = exception.getClass().getAnnotation(ResponseStatus.class);
 
            if(annotation!=null){
                reason = annotation.reason();
                statusCode = annotation.value().value();
            }
        } else {
            reason = "Database Error";
            model = new ModelAndView("/com/exception/500.html");
        }
 
        model.addObject("reason",reason);
        model.addObject("statusCode",statusCode);
        response.setStatus(statusCode);
        return model;
    }
}
