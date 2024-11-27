package com.ruoyi.system.domain.DTO;

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
public class ApprovalProcessDTO {

    @ApiModelProperty("流程ID，唯一标识")
    private Integer processId;  // 流程ID

    @ApiModelProperty("申请类型，关联application_type表")
    private String applicationType;

    @ApiModelProperty("流程步骤顺序")
    private Integer stepOrder;  // 流程步骤顺序

    @ApiModelProperty("部门")
    private String department;  // 部门ID，关联部门表

    @ApiModelProperty("角色")
    private String role;  // 角色

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;  // 创建时间

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;  // 更新时间
}
