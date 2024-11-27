package com.ruoyi.system.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "创业园区信息")
public class StudentEntrepreneurshipParkDTO {

    @ApiModelProperty(value = "创业园区唯一标识", example = "1")
    private Integer parkId;

    @ApiModelProperty(value = "创业园名称", example = "科技创新园区")
    private String parkName;

    @ApiModelProperty(value = "所属行业", example = "科技")
    private String industry;

    @TableField("address")
    @ApiModelProperty(value = "入驻地址", example = "北京市海淀区")
    private String address;

    @ApiModelProperty(value = "团队成员数量", example = "50")
    private Integer companyMembers;

    @ApiModelProperty(value = "入驻日期", example = "2024-01-01")
    private Date entryDate;

    @ApiModelProperty(value = "资金支持金额", example = "500000.00")
    private BigDecimal financialSupport;

    @ApiModelProperty(value = "投入经费（单位：元）", example = "2000000.00")
    private BigDecimal investmentAmount;

    @ApiModelProperty(value = "整体面积（单位：平方米）", example = "15000.50")
    private BigDecimal totalArea;

    @ApiModelProperty(value = "创业毕业生数量", example = "100")
    private Integer graduateCount;

    @ApiModelProperty(value = "创业项目数量", example = "25")
    private Integer projectCount;

    @ApiModelProperty(value = "培训学员数量", example = "200")
    private Integer traineeCount;

    @ApiModelProperty(value = "带动就业人数数量", example = "300")
    private Integer employmentCount;

    @ApiModelProperty(value = "政府补贴金额", example = "100000.00")
    private BigDecimal governmentSubsidy;

    @ApiModelProperty(value = "记录创建时间", example = "2024-01-01 12:00:00")
    private Date createdAt;

    @ApiModelProperty(value = "最后更新时间", example = "2024-01-01 12:00:00")
    private Date updatedAt;
}
