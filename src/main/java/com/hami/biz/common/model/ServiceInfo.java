package com.hami.biz.common.model;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : ServiceInfo
 * <li>Description  :
 * <li>History      : 2018. 1. 6.
 * </pre>
 *
 * @author HHG
 */
@Data 
public class ServiceInfo {

    String moduleId;
    String serviceId;
    String methodNm;
    String workNo;

    @Override
    public String toString() {
        return "ServiceInfo [ moduleId="+moduleId+", serviceId=" + serviceId + ", methodNm=" + methodNm + ", workNo="+workNo+" ]";
    }
}
