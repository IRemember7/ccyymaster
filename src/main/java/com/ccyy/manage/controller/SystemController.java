package com.ccyy.manage.controller;

import com.ccyy.manage.common.information.Server;
import com.ccyy.manage.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lianghanmao
 * @date 2021年08月05日 下午2:27
 */
@Api("系统模块")
@RestController
public class SystemController {

    @ApiOperation(value = "系统信息接口")
    @GetMapping("/system/index")
    public BaseResponse systemDetail() throws Exception {
        Server server = new Server();
        server.copyTo();
        return BaseResponse.success(server);
    }
}
