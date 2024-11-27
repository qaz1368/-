package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@ApiModel("获奖情况")
public class AwardYearVO {

    @ApiModelProperty("类型")
    private String level;

    @ApiModelProperty("年份")
    private List<Integer> year;

    
}
