package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("企业管理者")
@TableName("enterprise_managers")
public class EnterpriseManagers {

    @ApiModelProperty("管理者ID")
    @TableId("manager_id")  // 映射数据库字段
    private Integer managerId;

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty("身份证号")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty("生源地")
    @TableField("birthplace")
    private String birthplace;

    @ApiModelProperty("政治面貌")
    @TableField("political_status")
    private String politicalStatus;

    @ApiModelProperty("毕业时间")
    @TableField("graduation_date")
    private Date graduationDate;

    @ApiModelProperty("毕业院校")
    @TableField("alma_mater")
    private String almaMater;

    @ApiModelProperty("专业")
    @TableField("major")
    private String major;

    @ApiModelProperty("学历")
    @TableField("education_level")
    private String educationLevel;

    @ApiModelProperty("职位")
    @TableField("position")
    private String position;

    @ApiModelProperty("公司ID")
    @TableField("company_id")
    private Integer companyId;

    @ApiModelProperty("联系方式")
    @TableField("contact_info")
    private String contactInfo;

    @ApiModelProperty("入职日期")
    @TableField("hire_date")
    private Date hireDate;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private Date createdAt;

    @ApiModelProperty("更新时间")
    @TableField("updated_at")
    private Date updatedAt;
}
