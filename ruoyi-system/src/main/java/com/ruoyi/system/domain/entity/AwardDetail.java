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
@ApiModel("获奖情况")
@TableName("award_details")  // 映射到数据库表 'award_details'
public class AwardDetail {

    @ApiModelProperty("奖项ID")
    @TableId("award_id")  // 映射到 'award_id' 字段，作为主键
    private Integer awardId;

    @ApiModelProperty("年份")
    @TableField("year")  // 映射到 'year' 字段
    private Integer year;

    @ApiModelProperty("比赛级别")
    @TableField("level")  // 映射到 'level' 字段
    private String level;

    @ApiModelProperty("补助金额")
    @TableField("subsidy_amount")  // 映射到 'subsidy_amount' 字段
    private BigDecimal subsidyAmount;

    @ApiModelProperty("补助或奖项的描述")
    @TableField("description")  // 映射到 'description' 字段
    private String description;

    @ApiModelProperty("获奖企业ID")
    @TableField("enterprise_id")  // 映射到 'enterprise_id' 字段
    private Integer enterpriseId;

    @ApiModelProperty("比赛ID")
    @TableField("competition_id")  // 映射到 'competition_id' 字段
    private Integer competitionId;

    @ApiModelProperty("获奖创业园ID")
    @TableField("incubator_id")  // 映射到 'incubator_id' 字段
    private Integer incubatorId;

    @ApiModelProperty("记录创建时间")
    @TableField("created_at")  // 映射到 'created_at' 字段
    private Date createdAt;

    @ApiModelProperty("记录更新时间")
    @TableField("updated_at")  // 映射到 'updated_at' 字段
    private Date updatedAt;
}
