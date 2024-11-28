package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "审批记录")
public class ApprovalVO {

    @ApiModelProperty("审批记录ID")
    private Integer approvalId;  // 审批记录ID

    @ApiModelProperty("申请ID，关联 application 表")
    private Integer applicationId;  // 申请ID

    @ApiModelProperty("申请人姓名")
    private String applicantName;

    @ApiModelProperty("申请类型")
    private String applicationType;

    @ApiModelProperty("流程ID，关联 approval_process 表")
    private Integer processId;  // 流程ID

    @ApiModelProperty("流程顺序，关联 approval_process 中的顺序字段")
    private Integer sequence;  // 流程顺序

    @ApiModelProperty("审批状态（0表示拒绝，1表示通过）")
    private String approvalStatus;  // 审批状态

    @ApiModelProperty("审批部门，关联 department 表")
    private String department;  // 审批部门ID

    @ApiModelProperty("审批日期")
    private LocalDateTime approvalDate;  // 审批日期

    @ApiModelProperty("审批备注")
    private String comments;  // 审批备注

    @ApiModelProperty("记录创建时间")
    private LocalDateTime createdAt;  // 创建时间

    @ApiModelProperty("记录更新时间")
    private LocalDateTime updatedAt;  // 更新时间
}
