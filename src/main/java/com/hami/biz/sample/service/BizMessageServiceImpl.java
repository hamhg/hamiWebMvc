package com.hami.biz.sample.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <pre>
 * <li>Program Name : BizMessageServiceImpl
 * <li>Description  :
 * <li>History      : 2018. 2. 13.
 * </pre>
 *
 * @author HHG
 */
@Service("BizMessageService")
public class BizMessageServiceImpl implements BizMessageService {
    //@Autowired
    //private HabisMessageDao habisMessageDao;

    //@Autowired
    //private MsgMgmtDao msgMgmtDao;

    /**
     * 시스템에서 사용하는 메시지 조회
     * (개발자 사용 금지!!)
     */
    public Map selectMessages(Map<String, Object> data) throws Exception{
        //return habisMessageDao.selectMessages(data);
        return null;
    }

    /**
     * 업무에서 사용하는 메시지 조회
     */
    public Map selectMsg(Map<String, Object> data) throws Exception{
        //return msgMgmtDao.selectMsg(data);
        return null;
    }
}
