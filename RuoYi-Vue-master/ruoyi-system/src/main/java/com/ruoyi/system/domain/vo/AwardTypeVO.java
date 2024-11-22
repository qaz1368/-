package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("比赛类型对应的补助金额")
public class AwardTypeVO {

    @ApiModelProperty("比赛类型名称")
    private String level;

    @ApiModelProperty("补助金额")
    private BigDecimal totalSubsidyAmount;
}
