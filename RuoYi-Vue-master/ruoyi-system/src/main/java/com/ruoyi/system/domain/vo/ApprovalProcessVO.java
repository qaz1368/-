package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "审批流程")
public class ApprovalProcessVO {

    @ApiModelProperty("流程ID，唯一标识")
    private Integer processId;  // 流程ID

    @ApiModelProperty("申请类型，关联application_type表")
    private String applicationType;  // 申请类型ID，关联application_type表

    @ApiModelProperty("流程步骤顺序，决定审批的顺序")
    private Integer stepOrder;  // 流程步骤顺序

    @ApiModelProperty("部门，关联部门表")
    private String department;  // 部门ID，关联部门表

    @ApiModelProperty("角色，关联角色表（可选）")
    private String role;  // 角色ID，关联角色表（可选）

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;  // 创建时间

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;  // 更新时间
}
