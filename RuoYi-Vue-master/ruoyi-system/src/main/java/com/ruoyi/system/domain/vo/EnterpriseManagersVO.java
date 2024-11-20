package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("企业管理者")
public class EnterpriseManagersVO {

    @ApiModelProperty("管理者ID")
    private Integer managerId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("生源地")
    private String birthplace;

    @ApiModelProperty("政治面貌")
    private String politicalStatus;

    @ApiModelProperty("毕业时间")
    private Date graduationDate;

    @ApiModelProperty("毕业院校")
    private String almaMater;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("学历")
    private String educationLevel;

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("公司名称")
    private String company;

    @ApiModelProperty("联系方式")
    private String contactInfo;

    @ApiModelProperty("入职日期")
    private Date hireDate;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("更新时间")
    private Date updatedAt;
}
