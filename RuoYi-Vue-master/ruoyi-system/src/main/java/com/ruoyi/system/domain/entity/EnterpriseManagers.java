package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("企业管理者")
@TableName("enterprise_managers")
public class EnterpriseManagers {

    @ApiModelProperty("管理者ID")
    @TableId(value = "manager_id", type = IdType.AUTO)  // 映射数据库字段并设置自增
    @Excel(name = "管理者ID", type = Excel.Type.EXPORT)
    private Integer managerId;

    @ApiModelProperty("姓名")
    @TableField("name")
    @Excel(name = "姓名", type = Excel.Type.EXPORT)
    private String name;

    @ApiModelProperty("性别")
    @TableField("gender")
    @Excel(name = "性别", type = Excel.Type.EXPORT,readConverterExp = "Male=男,Female=女")
    private String gender;

    @ApiModelProperty("身份证号")
    @TableField("id_card")
    @Excel(name = "身份证号", type = Excel.Type.EXPORT)
    private String idCard;

    @ApiModelProperty("生源地")
    @TableField("birthplace")
    @Excel(name = "生源地", type = Excel.Type.EXPORT)
    private String birthplace;

    @ApiModelProperty("政治面貌")
    @TableField("political_status")
    @Excel(name = "政治面貌", type = Excel.Type.EXPORT)
    private String politicalStatus;

    @ApiModelProperty("毕业时间")
    @TableField("graduation_date")
    @Excel(name = "毕业时间", type = Excel.Type.EXPORT)
    private Date graduationDate;

    @ApiModelProperty("毕业院校")
    @TableField("alma_mater")
    @Excel(name = "毕业院校", type = Excel.Type.EXPORT)
    private String almaMater;

    @ApiModelProperty("专业")
    @TableField("major")
    @Excel(name = "专业", type = Excel.Type.EXPORT)
    private String major;

    @ApiModelProperty("学历")
    @TableField("education_level")
    @Excel(name = "学历", type = Excel.Type.EXPORT, readConverterExp = "Undergraduate=本科,Master=硕士,Doctoral=博士,Associate=专科,Other=其他")
    private String educationLevel;


    @ApiModelProperty("职位")
    @TableField("position")
    @Excel(name = "职位", type = Excel.Type.EXPORT)
    private String position;

    @ApiModelProperty("公司ID")
    @TableField("company_id")
    @Excel(name = "公司ID", type = Excel.Type.EXPORT)
    private Integer companyId;

    @ApiModelProperty("联系方式")
    @TableField("contact_info")
    @Excel(name = "联系方式", type = Excel.Type.EXPORT)
    private String contactInfo;

    @ApiModelProperty("入职日期")
    @TableField("hire_date")
    @Excel(name = "入职日期", type = Excel.Type.EXPORT)
    private Date hireDate;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    @Excel(name = "创建时间", type = Excel.Type.EXPORT)
    private Date createdAt;

    @ApiModelProperty("更新时间")
    @TableField("updated_at")
    @Excel(name = "更新时间", type = Excel.Type.EXPORT)
    private Date updatedAt;

}
