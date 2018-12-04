package com.hami.biz.common.model;

import lombok.Data;

/**
 * <pre>
 * <li>Program Name : ReqHeaderInfo
 * <li>Description  :
 * <li>History      : 2018. 1. 6.
 * </pre>
 *
 * @author HHG
 */
public @Data class ReqHeaderInfo {

    String clientIp;
    String refererUri;
    String requestUri;

    @Override
    public String toString() {
        return "ReqHeaderInfo [ clientIp=" + clientIp + ", refererUri=" + refererUri +", requestUri=" + requestUri + " ]";
    }
}
