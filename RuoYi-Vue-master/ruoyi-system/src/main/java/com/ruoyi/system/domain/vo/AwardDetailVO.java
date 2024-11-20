package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;


@Data
@ApiModel("获奖情况")
public class AwardDetailVO {

    @ApiModelProperty("奖项ID")
    private Integer awardId;

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("比赛类型")
    private String type;
    
    @ApiModelProperty("比赛")
    private String competition;

    @ApiModelProperty("比赛等级")
    private String level;
    
    @ApiModelProperty("获奖企业")
    private String enterprise;
    
    @ApiModelProperty("补助金额")
    private BigDecimal subsidyAmount;

    @ApiModelProperty("补助或奖项的描述")
    private String description;

    @ApiModelProperty("记录创建时间")
    private Date createdAt;

    @ApiModelProperty("记录更新时间")
    private Date updatedAt;
    
}
