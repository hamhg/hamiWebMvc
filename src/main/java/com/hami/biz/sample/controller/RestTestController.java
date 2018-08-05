package com.hami.biz.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : RestTestController
 * <li>Description  :
 * <li>History      : 2017. 7. 31.
 * </pre>
 *
 * @author HHG
 */
@RestController
public class RestTestController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/user/{svcId}")
    public HashMap<String, Object> testGet(@PathVariable("svcId") String svcId, @RequestParam Map<String, Object> paramMap) {
        printLog(svcId,paramMap);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Method", "Get");
        return map;
    }
    @PostMapping("/user/{svcId}")
    public HashMap<String, Object> testPost(@PathVariable("svcId") String svcId, @RequestBody Map<String, Object> paramMap) {
        printLog(svcId,paramMap);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Method", "Post");
        return map;
    }
    @PutMapping("/user/{svcId}")
    public HashMap<String, Object> testPut(@PathVariable("svcId") String svcId, @RequestBody Map<String, Object> paramMap) {
        printLog(svcId,paramMap);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Method", "Put");
        return map;
    }
    @DeleteMapping("/user/{svcId}")
    public HashMap<String, Object> testDelete(@PathVariable("svcId") String svcId, @RequestParam Map<String, Object> paramMap) {
        printLog(svcId,paramMap);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Method", "Delete");
        return map;
    }

    private void printLog(String svcId, Map prarmMap){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.debug("Request Method : {}, Request Service Id : {}, Parameters : {}", request.getMethod(), svcId, prarmMap.toString());
    }

}
