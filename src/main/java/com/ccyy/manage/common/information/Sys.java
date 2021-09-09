package com.ccyy.manage.common.information;

import lombok.Data;

/**
 * @author lianghanmao
 * @date 2021年08月05日 下午2:17
 * 系统相关信息
 */
@Data
public class Sys {
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
