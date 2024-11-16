package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("approval_process")
public class ApprovalProcess {

    @TableId(value = "process_id", type = IdType.AUTO)
    private Integer processId;  // 流程ID

    @TableField("application_type_id")
    private Integer applicationTypeId;  // 申请类型ID，关联application_type表

    @TableField("step_order")
    private Integer stepOrder;  // 流程步骤顺序

    @TableField("department_id")
    private Integer departmentId;  // 部门ID，关联部门表

    @TableField("role_id")
    private Integer roleId;  // 角色ID，关联角色表（可选）

    @TableField("created_at")
    private LocalDateTime createdAt;  // 创建时间

    @TableField("updated_at")
    private LocalDateTime updatedAt;  // 更新时间
}
