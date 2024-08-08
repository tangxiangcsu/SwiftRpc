package com.swiftrpc.swift_common.service;

import com.swiftrpc.swift_common.model.AuthDto;

/**
 * @PACKAGE_NAME: com.huaweicloud.huaweicloud_rpc.model
 * @NAME: AuthRequestService
 * @USER: tangxiang
 * @DATE: 2024/8/4
 * @PROJECT_NAME: huaweicloud_rpc_project
 * @DESCRIPTION:
 **/
public interface AuthRequestService {
    int createAuthRequest(AuthDto dto);

    AuthDto getAuthRequestByAppId(String appId);
}
