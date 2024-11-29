package com.ruoyi.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("获奖情况")
@TableName("award_details")  // 映射到数据库表 'award_details'
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardDetail {

    @ApiModelProperty("奖项ID")
    @TableId("award_id")  // 映射到 'award_id' 字段，作为主键
    @Excel(name = "奖项ID", type = Excel.Type.EXPORT)
    private Integer awardId;

    @ApiModelProperty("年份")
    @TableField("year")  // 映射到 'year' 字段
    @Excel(name = "年份", type = Excel.Type.EXPORT)
    private Integer year;

    @ApiModelProperty("比赛级别")
    @TableField("type_id")  // 比赛级别
    @Excel(name = "比赛级别", type = Excel.Type.EXPORT)
    private Integer typeId;

    @ApiModelProperty("补助金额")
    @TableField("subsidy_amount")  // 映射到 'subsidy_amount' 字段
    @Excel(name = "补助金额", type = Excel.Type.EXPORT)
    private BigDecimal subsidyAmount;

    @ApiModelProperty("补助或奖项的描述")
    @TableField("description")  // 映射到 'description' 字段
    @Excel(name = "补助或奖项的描述", type = Excel.Type.EXPORT)
    private String description;

    @ApiModelProperty("获奖企业ID")
    @TableField("enterprise_id")  // 映射到 'enterprise_id' 字段
    @Excel(name = "获奖企业ID", type = Excel.Type.EXPORT)
    private Integer enterpriseId;

    @ApiModelProperty("比赛ID")
    @TableField("competition_id")  // 映射到 'competition_id' 字段
    @Excel(name = "比赛ID", type = Excel.Type.EXPORT)
    private Integer competitionId;

    @ApiModelProperty("比赛等级")
    @TableField("level")  // 比赛等级
    @Excel(name = "比赛等级", type = Excel.Type.EXPORT)
    private String level;


    @ApiModelProperty("记录创建时间")
    @TableField("created_at")  // 映射到 'created_at' 字段
    @Excel(name = "记录创建时间", type = Excel.Type.EXPORT)
    private Date createdAt;

    @ApiModelProperty("记录更新时间")
    @TableField("updated_at")  // 映射到 'updated_at' 字段
    @Excel(name = "记录更新时间", type = Excel.Type.EXPORT)
    private Date updatedAt;


}
