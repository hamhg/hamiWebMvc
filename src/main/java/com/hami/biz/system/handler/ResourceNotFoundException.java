package com.hami.biz.system.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * <li>Program Name : ResourceNotFoundException
 * <li>Description  :
 * <li>History      : 2017. 7. 30.
 * </pre>
 *
 * @author HHG
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
}
