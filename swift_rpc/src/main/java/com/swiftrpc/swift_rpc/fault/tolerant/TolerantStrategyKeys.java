package com.swiftrpc.swift_rpc.fault.tolerant;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.fault.tolerant
 * @NAME: TolerantStrategyKeys
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION:
 **/
public interface TolerantStrategyKeys {
    /**
     * 故障恢复
     */
    String FAIL_BACK = "failBack";

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 故障转移
     */
    String FAIL_OVER = "failOver";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";
}
