package com.swiftrpc.swift_common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@TableName("auth_requests")
public class AuthDto {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String appId;
    private String appName;
    private String status;
    private String ak;
    private String sk;
    private String requestedBy;
    private LocalDateTime requestedAt;
    private String rejectedReason;
    private String requestDescription;
}

