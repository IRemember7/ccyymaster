package com.ccyy.manage.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@ApiModel(value="RoleMenu对象", description="")
public class RoleMenu extends Model<RoleMenu> {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
