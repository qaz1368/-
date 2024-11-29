package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@TableName("application")
@ApiModel(description = "申请表实体类")
public class Application {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("申请ID")
    @Excel(name = "申请ID", type = Excel.Type.EXPORT)
    private Integer applicationId;

    @TableField("process_id")
    @ApiModelProperty("流程ID，关联approval_process表")
    @Excel(name = "流程ID", type = Excel.Type.EXPORT)
    private Integer processId;

    @TableField("application_type_id")
    @ApiModelProperty("申请类型ID，关联application_type表")
    @Excel(name = "申请类型ID", type = Excel.Type.EXPORT)
    private Integer applicationTypeId;

    @TableField("applicant_name")
    @ApiModelProperty("申请人姓名")
    @Excel(name = "申请人姓名", type = Excel.Type.EXPORT)
    private String applicantName;

    @TableField("applicant_phone")
    @ApiModelProperty("申请人电话号码")
    @Excel(name = "申请人电话号码", type = Excel.Type.EXPORT)
    private String applicantPhone;

    @TableField("applicant_email")
    @ApiModelProperty("申请人邮箱地址")
    @Excel(name = "申请人邮箱地址", type = Excel.Type.EXPORT)
    private String applicantEmail;

    @TableField("industry_id")
    @ApiModelProperty("行业ID，关联industries表")
    @Excel(name = "行业ID", type = Excel.Type.EXPORT)
    private Integer industryId;

    @TableField("application_date")
    @ApiModelProperty("申请日期")
    @Excel(name = "申请日期", type = Excel.Type.EXPORT)
    private Date applicationDate;

    @TableField("status")
    @ApiModelProperty("申请状态")
    @Excel(name = "申请状态", type = Excel.Type.EXPORT,readConverterExp = "pending=待审核,rejected=已批准,approved=已拒绝")
    private String status;

    @TableField("created_at")
    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间", type = Excel.Type.EXPORT)
    private Date createdAt;

    @TableField("updated_at")
    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间", type = Excel.Type.EXPORT)
    private Date updatedAt;

    @TableField("enterprise_description")
    @ApiModelProperty("企业介绍")
    @Excel(name = "企业介绍", type = Excel.Type.EXPORT)
    private String enterpriseDescription;

    // Getter方法：用于转换status字段
    public String getStatus() {
        if ("pending".equals(this.status)) {
            return "待审核";  // 当status为pending时，返回中文"待审核"
        }
        return this.status;  // 其他状态值返回原值
    }
}
