package com.ccyy.manage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lianghanmao
 * @date 2021年07月22日 上午11:30
 */
@Data
public class UserProfile implements Serializable {
    private Long id;
    private String username;
    private String nickName;
    private String email;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Integer deptId;
    private String avatar;

}
