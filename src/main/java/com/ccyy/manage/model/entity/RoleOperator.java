package com.ccyy.manage.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色-操作关系表
 * </p>
 *
 * @author 是超超越越啊~
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RoleOperator对象", description="角色-操作关系表")
public class RoleOperator extends Model<RoleOperator> {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long operatorId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
