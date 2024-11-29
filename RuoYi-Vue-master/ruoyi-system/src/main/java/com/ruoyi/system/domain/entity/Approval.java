package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("approval")  // 指定映射的数据库表
@ApiModel(description = "审批记录")
public class Approval {

    @TableId(value = "approval_id", type = IdType.AUTO)  // 自动增长的主键
    @ApiModelProperty("审批记录ID")
    @Excel(name = "审批记录ID", type = Excel.Type.EXPORT)
    private Integer approvalId;  // 审批记录ID

    @TableField("application_id")
    @ApiModelProperty("申请ID，关联 application 表")
    @Excel(name = "申请ID", type = Excel.Type.EXPORT)
    private Integer applicationId;  // 申请ID

    @TableField("process_id")
    @ApiModelProperty("流程ID，关联 approval_process 表")
    @Excel(name = "流程ID", type = Excel.Type.EXPORT)
    private Integer processId;  // 流程ID

    @TableField("sequence")
    @ApiModelProperty("流程顺序，关联 approval_process 中的顺序字段")
    @Excel(name = "流程顺序", type = Excel.Type.EXPORT)
    private Integer sequence;  // 流程顺序

    @TableField("approval_status")
    @ApiModelProperty("审批状态（0表示拒绝，1表示通过）")
    @Excel(name = "审批状态", type = Excel.Type.EXPORT, readConverterExp = "0=拒绝,1=通过")
    private String approvalStatus;  // 审批状态

    @TableField("department_id")
    @ApiModelProperty("审批部门ID，关联 department 表")
    @Excel(name = "审批部门ID", type = Excel.Type.EXPORT)
    private Integer departmentId;  // 审批部门ID

    @TableField("approval_date")
    @ApiModelProperty("审批日期")
    @Excel(name = "审批日期", type = Excel.Type.EXPORT)
    private LocalDateTime approvalDate;  // 审批日期

    @TableField("comments")
    @ApiModelProperty("审批备注")
    @Excel(name = "审批备注", type = Excel.Type.EXPORT)
    private String comments;  // 审批备注

    @TableField("created_at")
    @ApiModelProperty("记录创建时间")
    @Excel(name = "记录创建时间", type = Excel.Type.EXPORT)
    private LocalDateTime createdAt;  // 创建时间

    @TableField("updated_at")
    @ApiModelProperty("记录更新时间")
    @Excel(name = "记录更新时间", type = Excel.Type.EXPORT)
    private LocalDateTime updatedAt;  // 更新时间
}
