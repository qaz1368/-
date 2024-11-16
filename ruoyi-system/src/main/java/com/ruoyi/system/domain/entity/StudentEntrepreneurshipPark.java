package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("创业园信息")
@TableName("student_entrepreneurship_park") // 映射数据库表
public class StudentEntrepreneurshipPark {

    @ApiModelProperty("创业园ID")
    @TableId("park_id") // 映射字段名
    private Integer parkId;

    @ApiModelProperty("创业园名称")
    @TableField("park_name")
    private String parkName;

    @ApiModelProperty("所属行业")
    @TableField("industry")
    private String industry;

    @ApiModelProperty("入驻地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("团队成员数量")
    @TableField("company_members")
    private Integer companyMembers;

    @ApiModelProperty("入驻日期")
    @TableField("entry_date")
    private Date entryDate;

    @ApiModelProperty("资金支持金额")
    @TableField("financial_support")
    private BigDecimal financialSupport;

    @ApiModelProperty("投入经费")
    @TableField("investment_amount")
    private BigDecimal investmentAmount;

    @ApiModelProperty("整体面积")
    @TableField("total_area")
    private BigDecimal totalArea;

    @ApiModelProperty("记录创建时间")
    @TableField("created_at")
    private Date createdAt;

    @ApiModelProperty("最后更新时间")
    @TableField("updated_at")
    private Date updatedAt;
}
