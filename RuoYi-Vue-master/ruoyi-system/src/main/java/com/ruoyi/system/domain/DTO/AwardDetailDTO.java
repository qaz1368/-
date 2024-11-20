package com.ruoyi.system.domain.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;


@Data
@ApiModel("获奖情况")
public class AwardDetailDTO {

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

}
