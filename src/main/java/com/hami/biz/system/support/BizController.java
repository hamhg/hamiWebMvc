package com.hami.biz.system.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hami.biz.common.model.CommonResponseBody;
import com.hami.biz.sample.service.BizMessageService;
import com.hami.sys.annotation.BizAnnotationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : BizController
 * <li>Description  :
 * <li>History      : 2018. 1. 20.
 * </pre>
 *
 * @author HHG
 */
public abstract class BizController {

    @Autowired
    public BizAnnotationHandler bizAnnotationHandler;

    @Autowired
    protected BizMessageService bizMessageService;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected CommonResponseBody result = new CommonResponseBody();
    protected ObjectMapper mapper = new ObjectMapper();

    protected Map<String, Object> systemHeader 		= new HashMap<String, Object>();
    protected Map<String, Object> transactionHeader	= new HashMap<String, Object>();
    protected Map<String, Object> messageHeader 	= new HashMap<String, Object>();
    public Map<String, Object> reqData 				= new HashMap<String, Object>();
    public Map<String, Object> resData		        = new HashMap<String, Object>();

    public String pcsResult = "F";		//처리결과 : S(성공), F(실패, default)
    public String msgCode = "";	        //메시지코드
    public String msgValue = "";		//메시지값

}
