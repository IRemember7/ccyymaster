package com.ccyy.manage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Operator对象", description="")
public class Operator extends Model<Operator> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单 ID")
    @TableId(value = "operator_id", type = IdType.ASSIGN_UUID)
    private Long operatorId;

    @ApiModelProperty(value = "所属菜单 ID")
    private Long menuId;

    @ApiModelProperty(value = "资源名称")
    private String operatorName;

    @ApiModelProperty(value = "资源 URL")
    private String url;

    @ApiModelProperty(value = "权限标识符")
    private String perms;

    @ApiModelProperty(value = "资源需要的 HTTP 请求方法")
    private String httpMethod;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;


    @Override
    protected Serializable pkVal() {
        return this.operatorId;
    }

}
