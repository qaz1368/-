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
@TableName("application")
@ApiModel(description = "申请实体类")
public class Application {

    @TableId(value = "application_id", type = IdType.AUTO)
    @ApiModelProperty("申请ID，唯一标识")
    private Integer applicationId;  // 申请ID

    @TableField("process_id")
    @ApiModelProperty("流程ID，关联approval_process表")
    private Integer processId;  // 流程ID，关联approval_process表

    @TableField("application_type_id")
    @ApiModelProperty("申请类型ID，关联application_type表")
    private Integer applicationTypeId;  // 申请类型ID，关联application_type表

    @TableField("applicant_name")
    @ApiModelProperty("申请人姓名")
    private String applicantName;  // 申请人姓名

    @TableField("applicant_phone")
    @ApiModelProperty("申请人电话号码")
    private String applicantPhone;  // 申请人电话号码

    @TableField("applicant_email")
    @ApiModelProperty("申请人邮箱地址")
    private String applicantEmail;  // 申请人邮箱地址

    @TableField("department_id")
    @ApiModelProperty("申请人部门ID")
    private Integer departmentId;  // 申请人部门ID

    @TableField("application_date")
    @ApiModelProperty("申请日期")
    private LocalDateTime applicationDate;  // 申请日期

    @TableField("status")
    @ApiModelProperty("申请状态：pending（待审批）、approved（已批准）、rejected（已拒绝）")
    private String status;  // 申请状态：pending, approved, rejected

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;  // 创建时间

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;  // 更新时间
}
