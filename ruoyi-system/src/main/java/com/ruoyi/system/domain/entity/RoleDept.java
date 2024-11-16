package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 角色和部门关联表（role_dept）对应的实体类
 * 记录角色和部门之间的关联信息。
 */
@Data
@TableName("role_dept")  // 映射到数据库中的"role_dept"表
public class RoleDept {

    @TableId(value = "role_id", type = IdType.INPUT)  // role_id作为主键的一部分
    private Long roleId;  // 角色ID

    @TableField("department_id")
    private Long departmentId;  // 部门ID

}
