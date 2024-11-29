package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "创业园区信息")
@TableName("student_entrepreneurship_park")
public class StudentEntrepreneurshipPark {

    @TableId
    @ApiModelProperty(value = "创业园区唯一标识", example = "1")
    @Excel(name = "创业园区唯一标识", type = Excel.Type.EXPORT)
    private Integer parkId;

    @TableField("park_name")
    @ApiModelProperty(value = "创业园名称", example = "科技创新园区")
    @Excel(name = "创业园名称", type = Excel.Type.EXPORT)
    private String parkName;

    @TableField("industry")
    @ApiModelProperty(value = "所属行业", example = "科技")
    @Excel(name = "所属行业", type = Excel.Type.EXPORT)
    private String industry;

    @TableField("address")
    @ApiModelProperty(value = "入驻地址", example = "北京市海淀区")
    @Excel(name = "入驻地址", type = Excel.Type.EXPORT)
    private String address;

    @TableField("company_members")
    @ApiModelProperty(value = "团队成员数量", example = "50")
    @Excel(name = "团队成员数量", type = Excel.Type.EXPORT)
    private Integer companyMembers;

    @TableField("entry_date")
    @ApiModelProperty(value = "入驻日期", example = "2024-01-01")
    @Excel(name = "入驻日期", type = Excel.Type.EXPORT)
    private Date entryDate;

    @TableField("financial_support")
    @ApiModelProperty(value = "资金支持金额", example = "500000.00")
    @Excel(name = "资金支持金额", type = Excel.Type.EXPORT)
    private BigDecimal financialSupport;

    @TableField("investment_amount")
    @ApiModelProperty(value = "投入经费（单位：元）", example = "2000000.00")
    @Excel(name = "投入经费", type = Excel.Type.EXPORT)
    private BigDecimal investmentAmount;

    @TableField("total_area")
    @ApiModelProperty(value = "整体面积（单位：平方米）", example = "15000.50")
    @Excel(name = "整体面积", type = Excel.Type.EXPORT)
    private BigDecimal totalArea;

    @TableField("graduate_count")
    @ApiModelProperty(value = "创业毕业生数量", example = "100")
    @Excel(name = "创业毕业生数量", type = Excel.Type.EXPORT)
    private Integer graduateCount;

    @TableField("project_count")
    @ApiModelProperty(value = "创业项目数量", example = "25")
    @Excel(name = "创业项目数量", type = Excel.Type.EXPORT)
    private Integer projectCount;

    @TableField("trainee_count")
    @ApiModelProperty(value = "培训学员数量", example = "200")
    @Excel(name = "培训学员数量", type = Excel.Type.EXPORT)
    private Integer traineeCount;

    @TableField("employment_count")
    @ApiModelProperty(value = "带动就业人数数量", example = "300")
    @Excel(name = "带动就业人数数量", type = Excel.Type.EXPORT)
    private Integer employmentCount;

    @TableField("government_subsidy")
    @ApiModelProperty(value = "政府补贴金额", example = "100000.00")
    @Excel(name = "政府补贴金额", type = Excel.Type.EXPORT)
    private BigDecimal governmentSubsidy;

    @TableField("created_at")
    @ApiModelProperty(value = "记录创建时间", example = "2024-01-01 12:00:00")
    @Excel(name = "记录创建时间", type = Excel.Type.EXPORT)
    private Date createdAt;

    @TableField("updated_at")
    @ApiModelProperty(value = "最后更新时间", example = "2024-01-01 12:00:00")
    @Excel(name = "最后更新时间", type = Excel.Type.EXPORT)
    private Date updatedAt;
}
