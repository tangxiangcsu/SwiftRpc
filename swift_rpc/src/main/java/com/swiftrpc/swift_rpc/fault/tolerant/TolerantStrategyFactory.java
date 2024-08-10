package com.swiftrpc.swift_rpc.fault.tolerant;

import com.swiftrpc.swift_rpc.spi.SpiLoader;

/**
 * @PACKAGE_NAME: com.swiftrpc.huaweicloud_rpc.fault.tolerant
 * @NAME: TolerantStrategyFactory
 * @USER: tangxiang
 * @DATE: 2024/7/20
 * @DESCRIPTION:
 **/
public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
