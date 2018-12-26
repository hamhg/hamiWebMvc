package com.hami.biz.common.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hami.biz.common.model.CommonResponseBody;
import com.hami.biz.system.support.BizController;
import com.hami.biz.system.utils.SecurityUtils;
import com.hami.sys.annotation.ServiceMethod;
import com.hami.sys.exception.BizException;
import com.hami.sys.util.ContextUtil;
import com.hami.sys.util.StringUtils;

/**
 * <pre>
 * <li>Program Name : CommonController
 * <li>Description  : 공통 Controller
 * <li>History      : 2017. 12. 26.
 * </pre>
 *
 * @author HHG
 */
@SuppressWarnings({"unchecked"})
@RestController
public class CommonController extends BizController{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");

    @JsonView(JsonView.class)
    @PostMapping("/doExcute")
    public ResponseEntity<CommonResponseBody> doExcute(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws JsonProcessingException {

        if (isValidParam(paramMap)) {
            try {
                if (paramMap.containsKey("SystemHeader")) {
                    systemHeader 		= (Map<String, Object>)paramMap.get("SystemHeader");
                    transactionHeader 	= (Map<String, Object>)paramMap.get("TransactionHeader");
                    messageHeader 		= (Map<String, Object>)paramMap.get("MessageHeader");
                    reqData 			= (Map<String, Object>)paramMap.get("ReqData");

                    systemHeader.put("TMSG_RQST_DTM", sdf.format(new Date()));	//요청전문 발생 시각

                    log.debug("\n[[[[[ INPUT ]]]]]\n"
                            +mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paramMap));
                } else {
                    Map<String, Object> inMap = makeInData(paramMap);
                    systemHeader 		= (Map<String, Object>)inMap.get("SystemHeader");
                    transactionHeader 	= (Map<String, Object>)inMap.get("TransactionHeader");
                    messageHeader 		= (Map<String, Object>)inMap.get("MessageHeader");
                    reqData 			= (Map<String, Object>)inMap.get("ReqData");

                    systemHeader.put("TMSG_RQST_DTM", sdf.format(new Date()));	//요청전문 발생 시각

                    log.debug("\n[[[[[ INPUT ]]]]]\n"
                            +mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inMap));
                }
                
                if(!((Map<String, Object>) reqData.get("ds_search")).containsKey("C_CD")){
                    ((Map<String, Object>) reqData.get("ds_search")).put("C_CD", SecurityUtils.getUser().getCcd());
                }

                //set Headers
                ContextUtil.setSystemHeader(systemHeader);
                ContextUtil.setTransactionHeader(transactionHeader);
                ContextUtil.setMessageHeader(messageHeader);

                if (paramMap.containsKey("etcMsg")) {
                    Map<String, Object> etcMsg = (Map<String, Object>)paramMap.get("etcMsg");
                    if(etcMsg.containsKey("MSG_CD")){
                        if(etcMsg.containsKey("MSG_ARGS")) {
                            List<?> mArg = (List<?>) etcMsg.get("MSG_ARGS");
                            String[] msgArgs = (String[]) mArg.toArray(new String[0]);
                            ContextUtil.addReturnMessage((String) etcMsg.get("MSG_CD"), msgArgs);
                        } else {
                            ContextUtil.addReturnMessage((String) etcMsg.get("MSG_CD"));
                        }
                    }
                }

                //전처리
                //PreExecute();

                String svcId = StringUtils.nvl((String)systemHeader.get("RECV_SVC_CD"));  //서비스코드
                log.debug(" * Request Service ID : '{}'" , svcId);

                Map<?, ?> serviceMethodMap = bizAnnotationHandler.getServiceMethodMap();
                Object refectionService = serviceMethodMap.get(svcId);

                //서비스ID 가 있을 경우
                if (refectionService != null) {
                    ServiceMethod sm = (ServiceMethod)refectionService;

                    //Service call
                    Object invokeResult = ReflectionUtils.invokeMethod(sm.getMethod(), sm.getBeanObject(), reqData);
                    resData = (Map<String, Object>)invokeResult;

                    //후처리
                    //PostExecute();

                    pcsResult = "S";							//처리결과 : S(성공), F(실패, default)
                    Map<String, Object> addMsg = ContextUtil.getReturnMessageCode();	//등록한 메시지

                    //등록한 메시지가 없을 경우
                    if (addMsg.isEmpty()) {
                        msgCode = "MSGS000001";					//메시지코드 : 정상적으로 처리되었습니다.
                    } else {
                        msgCode = (String)addMsg.get("MSG_CD");
                    }
                    msgValue = searchMessageByCode(msgCode, (String[])addMsg.get("MSG_ARGS"));

                    //서비스 ID가 없을 경우
                } else {
                    pcsResult = "F";							//처리결과 : S(성공), F(실패, default)
                    msgCode = "MSGE000001";						//메시지코드 : 서비스 아이디를 찾을 수가 없습니다.
                    msgValue = searchMessageByCode(msgCode, null);
                }

            } catch (Exception be) {
                log.error("[ERROR]", be);

                Throwable t = be;
                while (t.getCause() != null) {
                    t = t.getCause();
                }

                if (t instanceof BizException) {
                    pcsResult = "F";								//처리결과 : S(성공), F(실패, default)
                    msgCode = t.getMessage();						//메시지코드 : Biz업무 메시지코드
                    msgValue = searchMessageByCode(msgCode, (String[])((BizException)t).getMessageParameters());

                } else {
                    //메시지 없을 경우 전체 에러를 리턴
                    if ("".equals(msgValue) || msgValue == null) {
                        log.error("[Not Find Message!]");
                        msgCode = "Unknown Message Code";
                        StackTraceElement[] ste = be.getStackTrace();
                        msgValue = msgValue + be.fillInStackTrace() + System.lineSeparator();
                        for (StackTraceElement el : ste) {
                            msgValue += (el + System.lineSeparator());
                        }
                    }
                }

            } finally {
                //set Reponse
                makeSystemHeader();
                makeMessageHeader(pcsResult, msgCode, msgValue);		//처리결과코드(S 성공, F 실패) , 메시지코드, 메시지값

                systemHeader = (Map<String, Object>) ContextUtil.getSystemHeader();
                transactionHeader = (Map<String, Object>) ContextUtil.getTransactionHeader();
                messageHeader = (Map<String, Object>) ContextUtil.getMessageHeader();

                if(resData == null){
                    result.setCode(HttpStatus.NO_CONTENT);
                    result.setMsg("No Data Found!");
                }else{
                    result.setCode(HttpStatus.OK);
                    result.setMsg("");
                    result.setSystemHeader(systemHeader);
                    result.setTransactionHeader(transactionHeader);
                    result.setMessageHeader(messageHeader);
                    result.setResultData(resData);
                }
            }

        } else {
            result.setCode(HttpStatus.BAD_REQUEST);
            result.setMsg("Search criteria is empty!");
        }

        log.debug("\n[[[[[ OUTPUT ]]]]]\n"
                   +mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));

        //Principal principal = request.getUserPrincipal();
        //log.debug("\n[[[[[ principal ]]]]]\n"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getUserPrincipal()));

        return new ResponseEntity<CommonResponseBody>(result, result.getCode());
    }

    private boolean isValidParam(Map<String, Object> paramMap) {

        boolean valid = true;

        if (paramMap == null) {
            valid = false;
        }

        return valid;
    }

    private Map<String, Object> makeInData(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> inMap = new HashMap<String, Object>();

        Map<String, Object> systemHeader 		= new HashMap<String, Object>();
        Map<String, Object> transactionHeader 	= new HashMap<String, Object>();
        Map<String, Object> messageHeader 		= new HashMap<String, Object>();
        Map<String, Object> serviceInfo         = (Map<String, Object>)paramMap.get("serviceInfo");

        String dtm = sdf.format(new Date());

        systemHeader.put("LANG_CD", 			serviceInfo.get("LANG_CD"));
        systemHeader.put("TMSG_WRTG_DT", 		dtm.substring(0, 8));
        systemHeader.put("TMSG_CRE_SYS_NM", 	serviceInfo.get("TMSG_CRE_SYS_NM"));
        systemHeader.put("STN_TMSG_IP", 		serviceInfo.get("STN_TMSG_IP"));
        systemHeader.put("FRS_RQST_SYS_CD", 	serviceInfo.get("FRS_RQST_SYS_CD"));
        systemHeader.put("RECV_SVC_CD", 		serviceInfo.get("RECV_SVC_CD"));
        systemHeader.put("TRMS_SYS_CD", 		"");
        systemHeader.put("TMSG_RES_DTM", 		"");
        systemHeader.put("PRCS_RSLT_CD", 		"");
        systemHeader.put("STN_TMSG_ERR_CD", 	"");
        systemHeader.put("TMSG_RES_TM", 	    "");

        transactionHeader.put("SYSTEM_TYPE", 		serviceInfo.get("SYSTEM_TYPE"));
        transactionHeader.put("SCREEN_ID", 			"");
        transactionHeader.put("CORP_CD", 			serviceInfo.get("CORP_CD"));
        transactionHeader.put("EMP_NO", 			serviceInfo.get("EMP_NO"));
        transactionHeader.put("OSDE_TR_CD", 		"");
        transactionHeader.put("OSDE_TR_MSG_CD", 	"");
        transactionHeader.put("OSDE_TR_JOB_CD", 	"");
        transactionHeader.put("OSDE_TR_RUTN_ID", 	"");
        transactionHeader.put("OSDE_TR_PRG_NO", 	"");

        //paramMap.remove("serviceInfo");
        inMap.put("ReqData", paramMap);
        inMap.put("MessageHeader", messageHeader);
        inMap.put("TransactionHeader", transactionHeader);
        inMap.put("SystemHeader", systemHeader);

        return inMap;
    }

    /**
     * 출력메시지 조회
     * @param msgCode 메시지코드
     * @param args 메시지 변수
     * @return
     */
    private String searchMessageByCode(String msgCode, String[] args) {

        //TODO: 운영단계 에러메시지 숨기고 Normal 메시지 리턴으로 변경
//		String msgValue = "시스템 에러가 발생하였습니다.";
        String msgValue = "";

        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> resultList = new HashMap<String, Object>();

        param.put("MSG_CD", msgCode);
        param.put("LANG_CD", (String)ContextUtil.getSystemHeaderValue("LANG_CD"));

        log.debug(" * SearchMessage >> MSG_CD: '{}', LANG_CD: '{}'", msgCode, ContextUtil.getSystemHeaderValue("LANG_CD"));

        try {
            resultList = bizMessageService.selectMessages(param);

        } catch (Exception e) {
            log.error("[Message Search Error]", e);
            return "처리결과메시지 조회 중 에러가 발생하였습니다.";
        }

        if ( resultList!=null && !resultList.isEmpty() ){
            if( args == null ||args.length == 0){
                msgValue = (String)(resultList.get("MSG_CD_NM"));
            }else{
                msgValue = parseMessage((String)(resultList.get("MSG_CD_NM")), args);
            }
        }

        return msgValue;
    }

    /**
     * 메시지에 변수처리 함수
     * @param message
     * @param args
     * @return
     */
    private String parseMessage(String message, String[] args) {
        if (message == null || message.trim().length() <= 0)
            return message;

        if (args == null || args.length <= 0) return message;

        String[] splitMsgs = message.split("%");
        if (splitMsgs == null || splitMsgs.length <= 1)
            return message;

        for (String arg : args) {
            String replaceChar = "%" + arg;
            message = message.replaceFirst(replaceChar, arg);
        }
        return message;
    }

    /**
     * SystemHeader 설정
     */
    private void makeSystemHeader(){
        Map<String, Object> systemHeaderMap = (HashMap<String, Object>)ContextUtil.getSystemHeader();
//		systemHeaderMap.put("STD_TMSG_PRGR_NO", "");					//표준전문진행번호 : 문자12
        systemHeaderMap.put("TRMS_SYS_CD", "HPS");						//송신시스템코드 : 문자3
//		systemHeaderMap.put("TRMS_ND_NO", "");							//송신노드번호 : 숫자8
//		systemHeaderMap.put("TMSG_RQST_DTM", "");						//표준전문송신일시 : 문자17
//		systemHeaderMap.put("PRCS_RSLT_CD", "");						//처리결과구분코드 : 문자1
//		systemHeaderMap.put("ERR_OCC_SYS_CD", "");						//오류발생시스템코드 : 문자3
//		systemHeaderMap.put("STN_TMSG_ERR_CD", "");				    	//표준전문오류코드 : 오류유형코드(1)+업무구분(3)+Serial(5)

        //Normal & No SVCID & Exception
        systemHeaderMap.put("TMSG_RES_DTM", sdf.format(new Date()));	//응답전문 발생 시각 :  YYYYMMDDHHMMSSTTT (년-월-일-시-분-초-1/1000초)
        ContextUtil.setSystemHeader(systemHeaderMap);
    }

    /**
     * MessageHeader 생성
     * @param pcsResult
     * @param msgCode
     * @param msgValue
     */
    private void makeMessageHeader(String pcsResult, String msgCode, String msgValue){

        Map<String, Object> messageHeaderMap = (HashMap<String, Object>)ContextUtil.getMessageHeader();

        if ((Integer)messageHeaderMap.get("MSG_DATA_SUB_RPTT_CNT") == null || (Integer)messageHeaderMap.get("MSG_DATA_SUB_RPTT_CNT") == 0) {

            List<Map<String, String>> subMsg = new ArrayList<Map<String, String>>();
            Map<String, String> msg = new HashMap<String, String>();
            msg.put("MSG_CD", msgCode);
            msg.put("MSG_TXT", msgValue);

            if ("S".equals(pcsResult)) {
                messageHeaderMap.put("MSG_PRCS_RSLT_CD", "0");			//메세지정상처리여부 : 0(정상) , -1 이하(에러)
                msg.put("MSG_INDC_CD", "1");							//메시지표시구분코드 : 0(일반메시지창),1(상태바),g(출력안함)

            } else if ("F".equals(pcsResult)) {
                messageHeaderMap.put("MSG_PRCS_RSLT_CD", "-1");			//메세지정상처리여부 : 0(정상) , -1 이하(에러)
                msg.put("MSG_INDC_CD", "0");							//메시지표시구분코드 : 0(일반메시지창),1(상태바),g(출력안함)
            }
            messageHeaderMap.put("MSG_DATA_SUB_RPTT_CNT", 1);
            subMsg.add(msg);
            messageHeaderMap.put("MSG_DATA_SUB", subMsg);
        } else {
            ((Map<String, Object>)((List<?>)messageHeaderMap.get("MSG_DATA_SUB")).get(0)).put("MSG_CD", msgCode);
            ((Map<String, Object>)((List<?>)messageHeaderMap.get("MSG_DATA_SUB")).get(0)).put("MSG_TXT", msgValue);
        }

        ContextUtil.setMessageHeader(messageHeaderMap);
    }

    /**
     * 전처리용 함수
     * @throws Exception
     */
    private void PreExecute() throws Exception {
        log.debug("[PreExecute Start]");
        //TODO: 필요시 로그인등 인증 처리
        log.debug("[PreExecute End]");
    }

    /**
     * 후처리용 함수 (정상일 경우 호출)
     * @throws Exception
     */
    private void PostExecute() throws Exception {
        log.debug("[PostExecute Start] ");

        log.debug("[PostExecute End] ");
    }
}