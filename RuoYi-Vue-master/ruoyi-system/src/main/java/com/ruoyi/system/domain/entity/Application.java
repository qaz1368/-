package com.ruoyi.system.domain.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@TableName("application")
@ApiModel(description = "申请表实体类")
public class Application {

    @TableId
    @ApiModelProperty("申请ID")
    private Integer applicationId;

    @TableField("process_id")
    @ApiModelProperty("流程ID，关联approval_process表")
    private Integer processId;

    @TableField("application_type_id")
    @ApiModelProperty("申请类型ID，关联application_type表")
    private Integer applicationTypeId;

    @TableField("applicant_name")
    @ApiModelProperty("申请人姓名")
    private String applicantName;

    @TableField("applicant_phone")
    @ApiModelProperty("申请人电话号码")
    private String applicantPhone;

    @TableField("applicant_email")
    @ApiModelProperty("申请人邮箱地址")
    private String applicantEmail;

    @TableField("industry_id")
    @ApiModelProperty("行业ID，关联industries表")
    private Integer industryId;

    @TableField("application_date")
    @ApiModelProperty("申请日期")
    private Date applicationDate;

    @TableField("status")
    @ApiModelProperty("申请状态")
    private String status;

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    private Date createdAt;

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    private Date updatedAt;

    @TableField("enterprise_description")
    @ApiModelProperty("企业介绍")
    private String enterpriseDescription;
}
