package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("approval_process")
@ApiModel(description = "审批流程实体类")
public class ApprovalProcess {

    @TableId(value = "process_id", type = IdType.AUTO)
    @ApiModelProperty("流程ID，唯一标识")
    private Integer processId;  // 流程ID

    @TableField("application_type_id")
    @ApiModelProperty("申请类型ID，关联application_type表")
    private Integer applicationTypeId;  // 申请类型ID，关联application_type表

    @TableField("step_order")
    @ApiModelProperty("流程步骤顺序，决定审批的顺序")
    private Integer stepOrder;  // 流程步骤顺序

    @TableField("department_id")
    @ApiModelProperty("部门ID，关联部门表")
    private Integer departmentId;  // 部门ID，关联部门表

    @TableField("role_id")
    @ApiModelProperty("角色ID，关联角色表（可选）")
    private Integer roleId;  // 角色ID，关联角色表（可选）

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;  // 创建时间

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;  // 更新时间
}
