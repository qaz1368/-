package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("角色与部门关联实体类")
@Data
@TableName("role_dept")  // 映射数据库表 'role_dept'
public class RoleDept {

    @ApiModelProperty("角色ID")
    @TableField("role_id")  // 映射到 'role_id' 字段
    private Long roleId;

    @ApiModelProperty("部门ID")
    @TableField("department_id")  // 映射到 'department_id' 字段
    private Long departmentId;

}
