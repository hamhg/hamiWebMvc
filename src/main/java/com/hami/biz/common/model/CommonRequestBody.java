package com.hami.biz.common.model;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : CommonRequestBody
 * <li>Description  :
 * <li>History      : 2017. 7. 29.
 * </pre>
 *
 * @author HHG
 */
@Data 
public class CommonRequestBody {

    ServiceInfo serviceInfo;
    ReqHeaderInfo reqHeaderInfo; 
    
    @Override 
    public String toString() {
        return "CommonRequestBody { serviceInfo=" + serviceInfo.toString() + ", reqHeaderInfo=" + reqHeaderInfo.toString() + " }";
    }

}
